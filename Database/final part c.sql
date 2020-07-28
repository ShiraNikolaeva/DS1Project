use pos_v3
go

--1
select first_name, last_name, AddLine1 , AddLine2, City, state, zip ,phone_num 
from customer 
inner join Address on Customer.Address_ID = Address.Address_ID

--2. For each category of item, list the category and the amount of sales that has been generated
--ASSUMPTION: the amount of sales is the total $ amount that was spent on items in this category
select Category_Description, sum(total) as amountSales
from SalesOrderDetail 
inner join item on item.upc= SalesOrderDetail.upc 
inner join category on category.category_id= item.category_id
group by Category_Description

--3. For each Item, list the upc, description, and how much income was generated (qty_sold*unit_price - qty_ordered*unit_cost) 
select item.upc, item_name, isNull(sum(sod.total) - sum(l.subtotal), 0 ) as itemProfit --wud get 0 if store never purchased this item yet
from item
left outer join salesOrderDetail sod on sod.upc=item.upc
left outer join LineItem l on l.upc = item.upc
group by item.upc, Item_Name

--4. For each item, list the item, description, vendor name and the number of times its price has been discounted
select item.upc, item_name, vendor_name, count(DiscountedItems.upc) as timesOnSale
from item 
left outer join vendor on item.vendorID=Vendor.Vendor_ID
left outer join DiscountedItems on DiscountedItems.UPC = item.upc
group by item.upc, Item_Name, vendor_name


--5. For a given date, list which items’ prices have been discounted on that date.
select di.upc 
from DiscountedItems di
where di.Discount_Start_Date <= '2019-12-20' and di.Discount_End_Date >= '2019-12-20' --my given date

--6. Which item has been purchased the most often?
--ASSUMPTION: doesnt matter the qty bought per sales Order, its the number of times it appears in a salesOrder that matters
select upc
from SalesOrderDetail
group by upc
having count(upc) = (select max(sold) from 
		(select upc, count(upc) sold from SalesOrderDetail group by upc) as timesSold)

--7. Which cashier has rung up the largest total of sales? 
--ASSUMPTION: largest total sales is the greatest $ amount and not number of salesOrders
select cashier_id from 
(select cashier_id, sum(total_sale) as TotalSales from salesOrder
	group by Cashier_ID) as table1
where TotalSales = (
	select max(TotalSales) as maxSales from 
		(select cashier_id, sum(total_sale) as TotalSales from salesOrder
		group by Cashier_ID) as table1 )

--8. How many items are supplied by each Vendor?
select i.vendorid,count(i.upc) as items_provided
from item i
group by i.vendorid

--9. List the upc, description, vendor name of each item for which the quantity in stock is less that reorder level
select i.upc,i.item_name,v.vendor_name
from item i
inner join vendor v
on i.vendorid=v.vendor_id
where i.Units_On_Hand<i.Reorder_Level

--10. What is the description of the item that is the most expensive item (unit price) in inventory?
select item_name, unit_price
from item
where Unit_Price = (select max(unit_price) from item)

--11. List each EBT card on file, the name of the Customer, current balance on the card, and when (date) 
--it was last used to pay for a Sale
--NOTE: since we allowed for more than 1 customer to be associated with the same card (like child using parents cc 
-- whose dorming away from home or s/t), we added name on card column in ebt and cc and r using this for "customer name" 
select Ebt_Num, firstName_on_card + ' '+lastName_on_card as CustomerName, Balance_Remaining,  max(Date_Paid) as lastUsed
from ebt
inner join paymentDetail on  ebt.Ebt_ID = PaymentDetail.ebt_id
inner join payment on PaymentDetail.Payment_ID = payment.Payment_ID
group by ebt_num, firstName_on_card, lastName_on_card, Balance_Remaining

--12. Which customer has generated the most sales
select customer_id from SalesOrder
group by customer_id
having sum(total_sale) = (
		select max(Total_Sales) from 
		(select customer_ID, sum(total_sale) as Total_Sales
		from SalesOrder
			group by customer_id) as table1)

--13. Which category of item has generated the largest $ amount of sales
select Category_Description
from SalesOrderDetail 
inner join item on item.upc= SalesOrderDetail.upc 
inner join category on category.category_id= item.category_id
group by Category_Description
having  sum(total) = (select max(amountSales) from (select sum(total) as amountSales 
							from SalesOrderDetail
							inner join item on item.upc= SalesOrderDetail.upc 
							inner join category on category.category_id= item.category_id
							group by Category_Description) as blah) 

--14.	For each vendor list the vendor information and the description of each Item that the vendor provides.
select Vendor_ID,Vendor_Name,Phone_Num, Item_name  
from vendor 
inner join item on vendor.Vendor_ID = item.vendorID

--15.	List the SalesOrders that were sold without identifying a customer
select * from SalesOrder
where Customer_id is null

--16.	From which Vendor(s) have no purchase orders been generated during the current month.
select vendor_name from vendor 
where Vendor_ID not in 
	(select vendor_ID from PurchaseOrder
	where Month(Purchase_Order_Date) not in 
		(select month(purchase_order_date) as orderMonth  from purchaseOrder
		where month(purchase_order_date) = month(getDate()))
		) 

--17.	To which Customers have no sales orders been generated during the last 30 days.
	select First_Name + ' ' + Last_Name as FullName from customer
	where cust_id not in (
	select cust_id from customer 
	where Cust_ID  in 
		(select Customer_ID from SalesOrder  where sale_date between  (getDate()-30) and getDate()))
		
--18. List the names of Customers who have purchased items of both categories of MEAT and FISH.
select First_Name, Last_Name
from Customer
where Cust_ID in (
	select Customer_ID from SalesOrder 
		inner join SalesOrderDetail on SalesOrderDetail.SalesOrder_ID = SalesOrder.SalesOrder_ID 
		where salesOrderDetail.upc in (select upc from item where Category_ID=3) --3 is meat, 4 is fish
	) 
and Cust_ID in (
	select Customer_ID from SalesOrder 
		inner join SalesOrderDetail on SalesOrderDetail.SalesOrder_ID = SalesOrder.SalesOrder_ID 
		where salesOrderDetail.upc in (select upc from item where Category_ID=4)
)

--19.	List the names of each Vendor who supplies all the same categories of items as Vendor ‘X’. (not an easy query) 
select Vendor_Name from Vendor v1
where not exists ( select distinct Category_ID from item item1 where VendorID=8 and category_id not in 
	(select distinct Category_ID from item item2 where VendorID=v1.vendor_id) ) and Vendor_ID != 8

--20.	For each item purchased, list how many times this item has been returned by any customer.
select distinct SalesOrderDetail.upc, (select count(upc) from salesReturn where upc= SalesOrderDetail.upc) as TimesReturned
from SalesOrderDetail


--21 
--ASSUMPTION: since it does not say each food item, we assumed you wanted the total $ spent on all food items 
select sum(dollar_amount) as Totalsales, 
	(select  sum(dollar_amount) 
		from paymentDetail 
		inner join payment on paymentDetail.payment_ID = payment.payment_ID
		inner join salesOrderDetail on payment.SalesOrder_ID = SalesOrderDetail.salesOrder_id
		inner join item on salesOrderDetail.upc  = item.upc
		where item.Food = 'y' or item.food = 'Y' 
		and  ebt_id is  null) as salesNOTFromEbt, 
			(select  sum(dollar_amount) 
				from paymentDetail
				inner join payment on paymentDetail.payment_ID = payment.payment_ID
				inner join salesOrderDetail on payment.SalesOrder_ID = SalesOrderDetail.salesOrder_id
				inner join item on salesOrderDetail.upc  = item.upc
				where (item.Food = 'y' or item.food = 'Y' )
				and  ebt_id is not null) as SalesFromEbt
from paymentDetail
inner join payment on paymentDetail.payment_ID = payment.payment_ID
inner join salesOrderDetail on payment.SalesOrder_ID = SalesOrderDetail.salesOrder_id
inner join item on salesOrderDetail.upc  = item.upc
where (item.Food = 'y' or item.food = 'Y' )








