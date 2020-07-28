use project_v2
go

create trigger metMinPurchase  --checking if sales_applied is true and they did NOT meet minimumPurchase
on salesOrder
after insert, update

as
begin

declare @salesOrderID int
declare @transtype  char(1)
declare @minPurchaseID int 
declare @minAmount decimal(9,2) --get this amount if minpurID is not null
declare @totalNonSale decimal(9,2)
declare @saleApplied char(1) 

if exists(select * from deleted) 
	begin
		set @transType = 'U';
	end
else
	begin
		set @transType = 'I'
	end

select @salesOrderID = salesOrder_id from inserted

if @transtype= 'I'
	begin
		select @minPurchaseID = minPurchase_id from inserted
		if @minPurchaseID is not null
			begin
				select @minAmount = amount from minimumPurchase where minPurchase_id = @minPurchaseID
				select @totalNonSale = Total_Nonsale from inserted
				select @saleApplied = Sale_Applied from inserted
				if @totalNonSale < @minAmount and @saleApplied = 'Y'
					begin;
						throw 60001, 'sale cannot be applied because customer did not meet minimum Purchase amount', 1;
					end;
			end
	end
else
	begin
		select @minPurchaseID = minPurchase_id from SalesOrder where SalesOrder_ID= @salesOrderID
		select @minAmount = amount from minimumPurchase where minPurchase_id = @minPurchaseID
				if exists (select sale_applied from inserted) 
					begin 
						select @saleApplied = sale_applied from inserted
						if exists (select total_nonsale from inserted)
							begin
								select @totalNonSale = total_nonsale from inserted
							end
						else
							begin
								select @totalNonSale = total_nonsale from SalesOrder where SalesOrder_ID= @salesOrderID
							end
						if @totalNonSale < @minAmount and @saleApplied = 'Y'
							begin;
								throw 60012, 'sale cannot be applied because customer did not meet minimum Purchase amount', 1;
							end;
					end
			end			
end
		

