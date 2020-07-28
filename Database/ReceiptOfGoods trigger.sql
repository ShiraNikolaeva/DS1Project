use project_v2
go

--Trigger on ReceiptOfGoods, updates qtyOnHand for each item received, throws an error if qtyOnHand<0

create trigger [UpdateQtyOnHand]
on [ReceiptOfGoods]
after insert,update,delete
as
begin
	declare @transType  char(1)
	declare @UPC varchar(45)
	declare @qty int 

	--determine type of operation
	if exists (select * from inserted) 
		begin
		    if exists(select * from deleted) 
			   begin
			        set @transType = 'U'
			   end
            else
			   begin
			        set @transType = 'I'
			   end
		end
      else
		begin
		    set @transType = 'D'
		end

	
	--find the upc of the item to be updated and qty
	if @transType='I' or @transType='U'
		begin
			set @UPC= (select item.upc from inserted
						inner join LineItem
						on inserted.Line_Item_ID=LineItem.Line_Item_ID
						inner join item
						on LineItem.UPC=item.UPC)
			set @qty=(select qty_received from inserted)
		end
	else
		begin
			set @UPC= (select item.upc from deleted
						inner join LineItem
						on deleted.Line_Item_ID=LineItem.Line_Item_ID
						inner join item
						on LineItem.UPC=item.UPC)
			set @qty=(select qty_received from deleted)
		end 

	if @transType='I'
		begin
			update item
			set Units_On_Hand=Units_On_Hand+@qty
			where upc=@upc
		end
	if @transType='U'
		begin
			update item
			set Units_On_Hand=Units_On_Hand-(select qty_received from deleted) --subquery because not keeping 2 values of qty for updating
			where upc=@UPC

			update item
			set Units_On_Hand=Units_On_Hand+@qty
			where upc=@upc
			
			--if after updating inventory goes into minus, throw an error
			if (select units_on_hand from item where upc=@UPC)<0
				begin;
					throw 60001,'units on hand is negative',1;
				end 
		end
	if @transType='D'
		begin
			update item
			set Units_On_Hand=Units_On_Hand-@qty
			where upc=@upc

			if (select units_on_hand from item where upc=@UPC)<0
				begin;
					throw 60001,'units on hand is negative',1;
				end 
		end

end