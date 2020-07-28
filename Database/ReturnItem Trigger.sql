use project_v2
go

 


create trigger returnItem 
on salesReturn
after insert, update
as 
begin 
	declare @receiptID int 
	declare @transtype  char(1)
	declare @qtyReturned int


	if exists(select * from deleted) 
			   begin
			        set @transType = 'U';
					throw 60001, 'This table cannot be updated', 1
			   end
            else
			   begin
			        set @transType = 'I'
			   end
	select @receiptID =  receipt_ID from inserted

	

	select @qtyReturned = qty_returned from inserted
	declare @qtyAlreadyReturned int
	select @qtyAlreadyReturned = (select sum(qty_returned) from salesReturn 
									where Receipt_ID = @receiptID and UPC = (select Upc from inserted))
	declare @salesOrderID int 
	select @salesOrderID = (select salesOrder_id from receipt where Receipt_ID = @receiptID)
	declare @qtySold int 
	select @qtySold = (select qty_sold from SalesOrderDetail where SalesOrder_id = @salesORderID)
	if @qtyAlreadyReturned is not null
	begin
	select @qtySold = @qtySold - @qtyAlreadyReturned
	end
	if @qtyReturned > @qtySold
	begin;
	throw 60002, 'Invalid amount returned', 1
	end
	
	declare @upc varchar(45)
	select @upc = upc from inserted

	update item
	set Units_On_Hand = Units_On_Hand + @qtyReturned where upc = @upc


end 

