use project_v2
go

create trigger updateUnitsAfterSale
on salesOrderDetail
after insert --not dealing with update now, we just dont have enough time

as 
begin 
	declare @qty_sold int
	declare @upc varchar(45)
	declare @qty_on_hand int 

	select @qty_sold= qty_sold from inserted 
	select @upc = upc from inserted
	select @qty_on_hand = units_on_hand from item where upc = @upc
	
	if @qty_sold > @qty_on_hand
		begin;
			throw 60013, 'not enough of this item in stock', 1;
		end;
	else
		begin
			update Item
			set Units_On_Hand = Units_On_Hand - @qty_sold  
			where upc = @upc
		end
end
												