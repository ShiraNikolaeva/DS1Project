use master
create database pos_v3
go
use pos_v3
go

--VENDOR
create table Vendor(
Vendor_ID int not null identity(1,1),
Vendor_Name varchar(45) not null,
Phone_Num varchar(10) not null,
constraint [PK_VENDOR] primary key (vendor_id),
constraint [UIX_PHONE_NUM] unique (phone_num)
)

--PURCHASE ORDER
create table PurchaseOrder(
Purchase_Order_ID int not null identity(1,1),
Purchase_Order_Date date not null,
Vendor_ID int not null,
Total_Due decimal(9,2) not null,
Tax_Rate_ID int not null,
constraint [PK_PURCHASE_ORDER] primary key (purchase_order_id),
constraint [FK_PURCHASE_ORDER_VENDER] foreign key (vendor_id) references vendor(vendor_ID),
constraint [CHEK_TOTAL_DUE] check (total_due>0.0)
)

--VENDOR PAYMENT
create table VendorPayment(
Vendor_Payment_ID int not null identity(1,1),
Date_Paid date not null,
Purchase_Order_ID int not null,
Total_Paid decimal(9,2) not null,
constraint [PK_VENDOR_PAYMENT] primary key (vendor_payment_id),
constraint [FK_VENDOR_PAYMENT_PURCHASE_ORDER] foreign key (purchase_order_id) references purchaseorder(purchase_order_id),
constraint [CHK_DATE_PAID] check (date_paid<=getdate())
)

--CATEGORY
create table Category(
Category_ID int not null identity(1,1),
Category_Description varchar(45) not null, --changed the name because "description" is a reserved word
constraint [PK_CATEGORY_ID] primary key (category_id)
)

--ITEM
create table Item(
upc varchar(45) not null,
Item_Name varchar(45) not null, --changed the name because "name" is a reserved word
Unit_Price decimal (9,2) not null,
Category_ID int not null,
Taxable char(1) not null,
Food char(1) not null,
Units_On_Hand int not null,
Reorder_Level int not null,
vendorID int not null,
constraint [FK_ITEM_VENDOR] foreign key (vendorID) references vendor(vendor_id),
constraint [PK_ITEM] primary key (upc),
constraint [FK_ITEM_CATEGORY] foreign key (category_id) references category(category_id),
constraint [CHK_TAXABLE] check (taxable in ('Y','N')),
constraint [CHK_FOOD] check (food in ('Y','N')),
constraint [CHK_UNITS_ON_HAND] check (units_on_hand>0),
constraint [CHK_UNIT_PRICE] check (unit_price>0.0),
constraint [CHK_REORDER_LEVEL] check (reorder_level>=0)
)

--DISCOUNTED ITEMS
create table DiscountedItems(
UPC varchar(45) not null,
Discount_Start_Date date not null, 
Discount_End_Date date not null,
QTY_Limit int null,
Discounted_Price decimal(9,2) not null, 
constraint [PK_DISCOUNTED_ITEMS] primary key (upc,discount_start_date),
constraint [FK_DISCOUNTED_iTEMS_ITEM] foreign key (upc) references item(upc),
constraint [CHK_QTY_LIMIT] check (qty_limit>0),
constraint [CHK_DISOUNTED_PRICE] check (discounted_price>0.0),
constraint [CHK_DISCOUNT_DATE] check(discount_end_date>=discount_start_date) --allowing one day sales
)

--LINE ITEM
create table LineItem(
Line_Item_ID int not null identity(1,1),--we had pronlem with assigning fk referencing this table, included this column to reference a record
Purchase_Order_ID int not null,
UPC varchar(45) not null,
QTY_Ordered int not null,
Unit_Cost decimal(9,2) not null, --changed to 9,2 to keep all the prices in sync
Subtotal as qty_ordered*unit_cost,
constraint [PK_LINE_ITEM] primary key (line_item_id), 
constraint [FK_LINE_ITEM_ITEM] foreign key (upc) references item(upc),
constraint [FK_LINE_ITEM_PURCHASE_ORDER] foreign key (purchase_order_id) references purchaseOrder(purchase_order_id),
constraint [CHK_QTY_ORDERED] check (qty_ordered>0),
constraint [CHK_UNIT_COST] check (unit_cost>0.0),
constraint [UIX_PURCHASE_ORDER_ID_UPC] unique (purchase_order_id,upc)--make it a composite to allow more than one item per purchase order
)

--RECEIPT OF GOODS
--trigger attached
create table ReceiptOfGoods(
Line_Item_ID int not null,
QTY_Received int not null,
constraint [PK_LINE_ITEM_ID] primary key (line_item_id),
constraint [FK_RECEIPT_OF_GOODS_LINE_ITEM_PUI] foreign key (line_Item_id) references lineItem(line_item_id),
constraint [CHK_QTY_RECEIVED] check (qty_received>0)
)

 create table Address(
 Address_ID int not null, 
 AddLine1 varchar (45) not null,
AddLine2 varchar (45) null,
State varchar(45) not null, 
City varchar (45) not null, 
Zip varchar (45) not null, 
constraint [PK_ADDRESS] primary key (Address_id)
 );

create table EBT(
Ebt_ID int not null identity (1,1),
Ebt_Num varchar (16)not null,
first_name_on_card varchar (45) not null, 
last_name_on_card varchar (45) not null, 
Ebt_Pin varchar(4) not null,
Monthly_Allowance decimal(6,2) not null,
Balance_Remaining decimal(6,2) not null,
Is_Valid bit not null,
constraint[PK_EBT] primary key (ebt_id)
);

--this can be a credit or debit card
create table CREDITCARD(
Cc_id int identity (1,1),
Credit_Card_Num varchar(16) not null,
first_name_on_card varchar (45) not null, 
last_name_on_card varchar (45) not null, 
Exp_Month int not null,
Exp_Year int not null,
CVC varchar (4) not null,    -- or pin num for debit card
constraint [PK_CREDITCARD] primary key (cc_ID),
constraint[CK_expMonth] check (exp_Month<13),
constraint[CK_expYear] check (exp_Year>= Year(getDate())),
constraint [UIX_CC_NUM] unique (credit_card_num)
);



create table Customer (
Cust_ID int identity (1,1), 
First_Name varchar(45) not null, 
Last_Name varchar(45) not null, 
Address_ID int not null,
Phone_Num varchar(10) not null, 
Credit_or_debit_ID int null, 
Ebt_ID int  null,
constraint [PK_CUSTOMER] primary key (cust_ID), 
constraint [UIX_PHONENUM] unique (phone_Num),
constraint [FK_CUSTOMER_EBT] foreign key (EBT_ID) references EBT(EBT_ID),
 constraint [FK_CUSTOMER_CREDITCARD] foreign key (Credit_or_debit_ID) references creditCard(cc_id),
 constraint [FK_CUSTOMER_ADDRESS] foreign key (address_ID) references address(address_ID)

);


create table Employee(
Emp_ID int identity(1,1),
First_Name varchar (45) not null, 
Last_Name varchar (45) not null, 
Address_ID int null,
Phone_Num varchar(10) not null, 
SSN varchar (9) not null, 
DOB date not null, 
constraint [PK_EMPLOYEE] primary key (emp_ID),
constraint [UIX_EMPPHONENUM] unique (phone_Num), 
constraint[FK_EMPLOYEE_ADDRESS] foreign key (address_id) references address(address_id)
);

create table MANAGER(
Manager_ID int not null, 
constraint [PK_MANAGER] primary key (manager_id), 
constraint [FK_Manager_Employee] foreign key (manager_id) references employee(emp_id)
);

create table Stockclerk(
Clerk_ID int not null, 
Reports_To int null, 
constraint[PK_STOCKCLERK] primary key (clerk_id), 
constraint [FK_StockClerk_Employee] foreign key (clerk_id) references employee(emp_id), 
 constraint [FK_StockClerk_Manager] foreign key (reports_To) references manager(manager_id)


);


 create table Cashier(
 Cashier_ID int not null, 
 Date_Hired date not null
 constraint [DFLT_CASHIER_DATEHIRED] default (getDate()),
 Reports_To int null,
 constraint [PK_CASHIER] primary key (cashier_ID), 
 constraint [FK_Cashier_Employee] foreign key (cashier_id) references employee(emp_id), 
 constraint [FK_Cashier_Manager] foreign key (reports_To) references manager(manager_id)

 );

 create table SalesTax(
SalesTax_ID int not null identity (1,1),
tax_Rate decimal(6,4) not null,
[start_date] as getdate(),
constraint pk_tax primary key (salesTax_id),
constraint chk_rate check (tax_rate>0),
)

alter table purchaseOrder
add constraint [FK_PURCHASE_ORDER_SALES_TAX] foreign key (tax_rate_id) references salestax(salestax_id)

create table minimumPurchase(
minPurchase_id int not null identity (1,1),
start_date date not null,
end_date date not null,
amount decimal(9,2) not null,
constraint pk_minPur primary key (minPurchase_id),
constraint unq_strtDate unique (start_date),
constraint chk_amountMin check (amount>0)
)

create table SalesOrder(
SalesOrder_ID int not null identity(1,1),
Sale_Date date not null,
Customer_ID int null,
Cashier_ID int not null,
Total_Sale decimal(9,2) not null,
Total_Nonsale decimal(9,2) not null, 
Sales_Tax_ID int not null,
minPurchase_id int null,
Sale_Applied char(1) not null,
constraint pk_salesOrder primary key (salesOrder_id),
constraint fk_tax foreign key (sales_tax_id) references salesTax (salesTax_id) ,
constraint fk_cashier foreign key (cashier_id) references cashier(cashier_id), 
constraint fk_cust_id foreign key (customer_id) references customer(cust_ID),
constraint fk_minPurchase foreign key (minPurchase_id) references MinimumPurchase (minPurchase_id),
constraint chk_sale check (total_sale >0),
constraint chk_nonsale check (total_nonsale>=0),
constraint chk_saleApplied check (Sale_Applied in ('Y','N'))
)

create table SalesOrderDetail(
SalesOrder_id int not null,
UPC varchar(45) not null,
Qty_Sold int not null,
Unit_Price decimal(9,2) not null,
On_Sale char(1) not null,
constraint pk_sales_detail primary key (upc, salesOrder_id),
constraint fk_salesOrderID foreign key (salesOrder_id) references salesOrder (salesOrder_id),
constraint fk_upc foreign key (upc) references item (upc),
constraint chk_qty check (qty_sold >0),
constraint chk_price check (unit_price >0),
constraint chk_onSale check (on_sale in ('Y','N'))
)

alter table salesOrderDetail
add total as unit_price * qty_sold

create table Payment(
Payment_ID int not null identity(1,1),
Date_Paid date not null,
SalesOrder_ID int not null,
Total_Paid decimal(9,2) not null,
constraint pk_payment primary key (payment_id),
constraint fk_order_id foreign key (salesOrder_id) references salesOrder(salesOrder_id),
constraint chk_total_paid check (total_paid >0)
)

create table paymentMethod(
PaymentMethod_ID int not null identity(1,1),
MethodDescrip varchar(45) not null,
constraint pk_method primary key(paymentMethod_id) 
)

create table PaymentDetail(
Payment_ID int not null,
PaymentMethod_ID int not null,
Dollar_Amount decimal(9,2) not null,
Cc_or_dc_ID varchar(16) null,
Ebt_ID int null,
constraint pk_paymentDetail primary key (payment_id, paymentMethod_id),
constraint fk_method foreign key (paymentMethod_id) references paymentMethod (paymentMethod_id),
constraint fk_payment foreign key (payment_id) references payment(payment_id),
constraint fk_cc foreign key (cc_or_dc_id) references creditCard (credit_card_num),
constraint fk_ebt foreign key (ebt_id) references ebt(ebt_id),
constraint chk_dollar check (dollar_amount >0)
)

create table Receipt(
Receipt_ID int not null identity (1,1),
SalesOrder_ID int not null,
constraint pk_receipt primary key (receipt_id),
constraint fk_receipt_sales foreign key (salesOrder_id) references salesOrder (salesOrder_id)
)

create table SalesReturn(
Return_ID int not null identity(1,1),
Receipt_ID int not null,
UPC varchar(45) not null,
Qty_Returned int not null,
Return_Date date not null constraint def_date default getdate(),
constraint pk_return primary key (return_id),
constraint fk_receipt_return foreign key (receipt_id) references receipt(receipt_id),
constraint fk_upc_return foreign key (upc) references item(upc),
constraint chk_qty_returned check(qty_returned >0) --need good trigger to make sure when insert that qty_returned is less than or
												   --equal to qty baught, taking previous returns into consideration
)

--TRIGGERS are in separate queries
