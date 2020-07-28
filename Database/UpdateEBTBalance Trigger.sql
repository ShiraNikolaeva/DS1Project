use project_v2
go





create trigger updateEBTBalance
on paymentDetail 
after insert  --only after insert cuz an update/deletion would be a return 
as 
begin 
	declare @ebtid int 
	declare @isValid bit
	declare @dollarAmount decimal(9,2)
	declare @balanceOfEBT decimal(6,2)
	
	select @ebtid = ebt_id from inserted
	if @ebtid is not null
	begin 
		select @isValid = (select is_Valid from ebt where ebt_id = @ebtid)
		if @isValid = 0
		begin; 
			throw 60000, 'EBT Card is not valid', 1
		end

		select @dollarAmount = dollar_Amount from inserted
		select @balanceOfEBT = (select balance_Remaining from ebt where ebt_id = @ebtid)
		if @dollarAmount > @balanceOfEBT
		begin; 
			throw 60001, 'The amount you wish to pay is greater than the balance on your EBT card', 1
		end

		update ebt
		set balance_Remaining = balance_Remaining - @dollarAmount

	end

end 




