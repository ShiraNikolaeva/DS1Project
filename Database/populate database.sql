--populate database:
use pos_v3
go

insert into address (Address_ID,AddLine1, State, City, zip) values (1,'123 Main ave', 'NJ', 'Town', '16234')
insert into address (Address_ID,AddLine1, State, City, zip) values (2,'345 happy ave', 'NY', 'orange', '11234')
insert into address (Address_ID,AddLine1, State, City, zip) values (3,'90 central ave', 'NY', 'cedarhusrt', '12113')

insert into employee (first_Name, last_name, phone_Num, ssn, dob) values ('john', 'doe',  '1512222111', '12455589', getDate())
insert into employee (first_Name, last_name, phone_Num, ssn, dob) values ('larry', 'hill',  '13475665787', '12499589', '2000-12-12')
insert into employee (first_Name, last_name, phone_Num, ssn, dob) values ('rachel', 'plut',  '17185555787', '12099589', '2003-01-12')
insert into employee (first_Name, last_name, phone_Num, ssn, dob) values ('lenny', 'engar',  '19085665787', '12400589', '1990-12-12')
insert into employee (first_Name, last_name, phone_Num, ssn, dob) values ('polly', 'hope',  '13075665787', '13499589', '2001-09-12')

insert into MANAGER values(1)

insert into CASHIER (cashier_ID, Date_Hired, Reports_To)values (3,'2013-12-09', 1)
insert into CASHIER (cashier_ID, Reports_To)values (2, 1)
insert into CASHIER (cashier_ID, Reports_To)values (4, 1)

insert into Category (Category_Description)values ( 'FRUIT')
insert into Category (Category_Description)values ( 'BAKERY')
insert into Category (Category_Description)values ( 'MEAT')
insert into Category (Category_Description)values ( 'GROCERY')
insert into Category (Category_Description)values ( 'FROZEN')
insert into Category (Category_Description)values ( 'FISH')
insert into Category (Category_Description)values ( 'PAPERGOODS')

insert into salesTax (tax_Rate) values (1.5)

insert into paymentMethod (methodDescrip) values ( 'CreditCard')
insert into paymentMethod (methodDescrip) values ( 'ebt')
insert into paymentMethod (methodDescrip) values ( 'cash')
insert into paymentMethod (methodDescrip) values ( 'check')

insert into CREDITCARD (Credit_Card_Num, Exp_Month, Exp_Year, CVC, firstName_on_card, lastName_on_card)
values('1111222233334444', 12, 21, '334', 'jane', 'doe')
insert into CREDITCARD (Credit_Card_Num, Exp_Month, Exp_Year, CVC, firstName_on_card, lastName_on_card)
values('2211322233334444', 11, 23, '222', 'john', 'smith')

insert into EBT values('1122112211221122', 1234, 65.50, 55.00, 1, 'jane', 'mary')
insert into EBT values('6622112211221192', 1122, 85.50, 75.00, 1, 'smith', 'john')

insert into Customer (Address_ID, first_name,Last_Name, Phone_Num, Credit_or_debit_ID) values (1,'joe', 'shmo', '1234567898', 1)
insert into Customer (Address_ID, first_name,Last_Name, Phone_Num, Ebt_ID) values (2,'John', 'Smith', '1234567897', 2)
insert into Customer (Address_ID, first_name,Last_Name, Phone_Num, Ebt_ID) values (3,'Mary', 'Jane', '4534567898', 1)

insert into Vendor (Vendor_Name, Phone_Num) values ('herrs', '1234567897')
insert into Vendor (Vendor_Name, Phone_Num) values ('gefen', '125467897')
insert into Vendor (Vendor_Name, Phone_Num) values ('mehadrin', '128867897')

insert into Item values('ABC', 'Apple', 0.28, 1, 'n', 'y', 50,10,1) 
insert into Item values('123', 'pan', 0.20, 7, 'y', 'n', 50,10,1) 
insert into Item values('888743', 'pear', 0.40, 1, 'n', 'y', 50,10,3) 
insert into Item values('2345rt', 'salmon', 10.20, 6, 'n', 'y', 50,10,2) 
insert into Item values('BB5553', 'pasta', 2.20, 4, 'n', 'y', 50,10,1) 
insert into Item values('198TH5', 'ice cream', 6.99, 5, 'n', 'y', 50,10,2) 

insert into DiscountedItems values ('ABC', '2020-01-13', '2020-01-30', 20, 0.23)
insert into DiscountedItems values ('BB5553', '2019-01-13', '2020-01-17', 10, 1.13)
insert into DiscountedItems values ('123', '2020-01-22', '2020-02-17', 15, 0.13)
insert into DiscountedItems values ('2345rt', '2020-01-26', getdate(), 4, 9.00)

insert into minimumPurchase values('2020-01-01', '2020-02-17', 20.00)

insert into PurchaseOrder (Purchase_Order_Date, Vendor_ID, Total_Due, Tax_Rate_ID) values (getDate(), 1, 34.00, 1)
insert into PurchaseOrder (Purchase_Order_Date, Vendor_ID, Total_Due, Tax_Rate_ID) values ('2019-12-12', 2, 15.00, 1)
insert into PurchaseOrder (Purchase_Order_Date, Vendor_ID, Total_Due, Tax_Rate_ID) values ('2019-12-12', 3, 580.00, 1)

insert into LineItem values (1, 'ABC', 90, .10)
insert into LineItem values (1, '123', 100, .15)
insert into LineItem values (1, 'BB5553', 100, .10)
insert into LineItem values (2, '198TH5', 100, .15)
insert into LineItem values (3, '2345rt', 100, 5.70)
insert into LineItem values (3, '888743', 100, .10)

insert into SalesOrder(cashier_id, total_sale, sales_tax_id, Total_Nonsale, Sale_Applied, Customer_ID)values  ( 1, 30.00, 1, 11,'n',3)
insert into SalesOrder(cashier_id, total_sale, sales_tax_id, Total_Nonsale, Sale_Applied, Customer_ID)values  ( 2, 270.00, 1, 100,'n',1)
insert into SalesOrder(cashier_id, total_sale, sales_tax_id, Total_Nonsale, Sale_Applied, Customer_ID)values  ( 1, 50.00, 1, 12.45,'n',2)

insert into payment (total_paid,salesOrder_id)values ( 270.00,2)
insert into payment(total_paid,salesOrder_id)values ( 30.00,1)

insert into paymentDetail (payment_ID, paymentMethod_id, dollar_Amount) values (1, 3, 250)
insert into paymentDetail (payment_ID, paymentMethod_id, dollar_Amount, ebt_id) values (1, 2,20.00, 1)
insert into paymentDetail (payment_ID, paymentMethod_id, dollar_Amount) values (2, 2, 30)

insert into receipt values (2) 

insert into SalesOrderDetail (SalesOrder_id,upc, Qty_Sold,Unit_Price,On_Sale) values(2,'ABC', 4, 0.28, 'n')
insert into SalesOrderDetail (SalesOrder_id,upc, Qty_Sold,Unit_Price,On_Sale) values(2,'123', 14, 0.20, 'n')
insert into SalesOrderDetail (SalesOrder_id,upc, Qty_Sold,Unit_Price,On_Sale) values(1,'ABC', 8, 0.28, 'n')
insert into SalesOrderDetail (SalesOrder_id,upc, Qty_Sold,Unit_Price,On_Sale) values(1,'123', 4, 0.20, 'n')

insert into SalesReturn (Receipt_ID, upc, Qty_Returned) values (1, 'ABC', 1)


