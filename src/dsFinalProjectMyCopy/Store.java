package dsFinalProjectMyCopy;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class Store implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Address address;
	private String phoneNumber;
	private Inventory inventory;
	private ArrayList<Person> employees;
	private ArrayList<Vendor> vendors;
	private ArrayList<PurchaseOrder> purchases;
	private ArrayList<Person> persons;
	private ArrayList<SalesOrder> sales;
	private ArrayList<Payment> payments;

	public Store(String name, Address address, String phoneNumber) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		inventory = new Inventory();
		employees = new ArrayList<Person>();
		vendors = new ArrayList<Vendor>();
		purchases = new ArrayList<PurchaseOrder>();
		persons = new ArrayList<Person>();
		sales = new ArrayList<SalesOrder>();
		payments = new ArrayList<Payment>();
	}

	
	public SalesOrder getSale(Long saleid) {
		for(SalesOrder s: sales) {
			if(s.getSalesOrderID().equals(saleid)) {
				return s;
			}
		}
		throw new  SalesOrderNotFoundException();				
	}
	
	public Vendor getVendor(int vendID) {
		for(Vendor v: vendors) {
			if(v.getVendorID()==vendID) {
				return v;
			}
		}
		throw new VendorNotFoundException();
	}
	
	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void addItem(Item theItem) {		
		Vendor vend = getVendor(theItem.getVendorID());
		inventory.addNewItem(theItem);
		vend.incrementItemCount();				
	}

	public void addVendor(Vendor theVendor) {
		// don't allow duplicates
		if (!vendors.isEmpty()) {
			for (Vendor v : vendors) {
				if (v.equals(theVendor)) {
					throw new DuplicateVendorException("cannot add this vendor because it is already in the system");
				}
			}
		}
		vendors.add(theVendor);
	}

	public void receiveItemShipment(Long poID, String upc, int qty, Double unitCost) {

		PurchaseOrder po=findPurchaseOrder(poID);
		po.receiveItem(upc, qty, unitCost);
		inventory.restockItem(upc,qty);
	}

	public void receiveShipment(Long poID, ArrayList<ReceivedItem> items) {
		PurchaseOrder po=findPurchaseOrder(poID);
		for(ReceivedItem ri:items) {
			po.receiveItem(ri);
			inventory.restockItem(ri.getUpc(),ri.getQty());
		}		
	}

	public Long setUpPurchaseOrder(int vendorID) {
		Vendor vendor = getVendor(vendorID);
		PurchaseOrder po = new PurchaseOrder(vendorID);
		vendor.addPurchaseOrder(po);
		purchases.add(po);
		return po.getPurchaseOrderID();
	}

	public Long setUpPurchaseOrder(int vendorID, ArrayList<ItemOrder> items) {
		Long poID=setUpPurchaseOrder(vendorID);
		PurchaseOrder order = findPurchaseOrder(poID);
		//check if the item is ordered from this vendor. if not, ignore
		if(!items.isEmpty()) {
			for(ItemOrder i:items) {
				if(i.getVendorID()==vendorID) {
					PurchaseOrderDetail pod=new PurchaseOrderDetail(i);
					order.addOrderDetails(pod);
				}
			}
		}			
		return poID;
	}

	public void addItemToPOFromVendor(Long poID, String upc, Integer qtyOrdered) {
		if(!inventory.hasItem(upc)) {
			throw new ItemNotFoundException(upc+" not found in store");
		}
		if(!purchases.isEmpty()) {
			for(PurchaseOrder po:purchases) {
				if(po.getPurchaseOrderID()==poID) {
					po.addOrderDetails(upc, qtyOrdered);
				}
			}
		}
	}

	public void receivePayment(long orderID, ArrayList<PaymentDetail> details) throws WrongEBTAmountException {
		Payment pay = new Payment(orderID, PaymentType.RECEIVABLE);
		SalesOrder thisOne = getSale(orderID);
		for(PaymentDetail pd: details) {
			if(pd.getPayMethod().equals("EBT")) {
				if(pd.getAmount() > thisOne.getTotalEBTitems()) {
					throw new WrongEBTAmountException("The total of EBT eligible items is less than the amount this payment is trying to make");
				}
			}
		}
		payments.add(pay);
		thisOne.makePayment(pay);
	}

	public void makePayment(int vendorID, long purchaseOrderID, double amount, String method) {
		getVendor(vendorID);  //checking if vendorID is valid
		PurchaseOrder po=findPurchaseOrder(purchaseOrderID);
		Payment p = new Payment(purchaseOrderID, PaymentType.PAYABLE);
		PaymentDetail pd = new PaymentDetail(p.getPaymentID(), method, amount);
		p.addDetail(pd);
		po.makePayment(p);
		payments.add(p);
	}

	public void setRestockLevel(String upc, int level) {
		if(!inventory.hasItem(upc)) {
			throw new ItemNotFoundException(upc+" not found in store");
		}
		inventory.setRestockLevel(upc, level);
	}

	public boolean hasItem(String upc) {
		return inventory.hasItem(upc);
	}

	public Double getTotalReceipts() {
		double total=0;
		for(Payment p: payments) {
			if(p.getPaymentType().equals("RECEIVABLE")) {
				total+=p.getTotalPaid();
			}
		}
		return total;
	}

	// ASSUMPTION: you meant get total of payments that are receivables within date range, noninclusive of start and end
	public Double getTotalReceipts(LocalDate start, LocalDate end) {
		double total=0;
		for(Payment p: payments) {
			if(p.getPaymentType().equals("RECEIVABLE") && p.getDatePaid().isAfter(start) && p.getDatePaid().isBefore(end)) {
				total+=p.getTotalPaid();
			}
		}
		return total;
	}

	
	public Double getTotalPayments() {
		double total=0;
		for(Payment p: payments) {
			if(p.getPaymentType().equals("PAYABLE")) {
				total+=p.getTotalPaid();
			}
		}
		return total;
	}


	public Double getTotalPayments(LocalDate start, LocalDate end) {
		double total=0;
		for(Payment p: payments) {
			if(p.getPaymentType().equals("PAYABLE") && p.getDatePaid().isAfter(start) && p.getDatePaid().isBefore(end)) {
				total+=p.getTotalPaid();
			}
		}
		return total;
	}

	
	public Long setUpSale(Long customerID, String phoneNumber, long cashierID) {
		Customer customer =getCustRecord(customerID, phoneNumber);
		SalesOrder so;
		if(customer !=null) {
			 so = new SalesOrder(customer, cashierID);
		}
		else {
			so = new SalesOrder(cashierID);
		}
		sales.add(so);
		return so.getSalesOrderID();
	}

	public Customer getCustRecord(Long customerID, String num) {
		
		if(!persons.isEmpty()) {
			for(Person p:persons) {
				if(p.getPhoneNumber().equals(num)) {
					if(p.getID()==customerID) {
						return (Customer) p;
					}
				}
			}
		}
		throw new CustomerNotFoundException("customer not found");
	}
	
	public Customer getCustRecord(Long customerID) {
		
		if(!persons.isEmpty()) {
			for(Person p:persons) {
				if(p.getID()==customerID) {
					return (Customer) p;
				}				
			}
		}
		throw new CustomerNotFoundException("customer not found");
	}

	public Long completeSale(Long salesOrderID, String phoneNumber, Long cashierID, ArrayList<SalesOrderDetail> details,
			Double minPurchase) {
		SalesOrder so=getSale(salesOrderID);
		if(so.isCompleted()) {
			throw new OrderCompleteException();
		}
		for(SalesOrderDetail sd: details) {
			so.addDetail(sd);
		}
		so.applyDiscount(minPurchase); //exception might get thrown here, will be caught in useStore
		so.setCompleted(true);
		
		if(so.getCustomerID()!=null) { //apply to balance of customer
			Customer cust = getCustRecord(so.getCustomerID(), phoneNumber);
			cust.completeSale(so);
		}
		return so.getSalesOrderID();
	}

	public SalesOrderDetail scanItem(String upc, int qty, long salesID) {
		if(!inventory.hasItem(upc)) {
			throw new ItemNotFoundException(upc+" not found in store");
		}
		getSale(salesID); //just checking if its a valid salesOrder id, otherwise exception will be thrown						
		double price = inventory.getCurrentPrice(upc);
		Double discount = inventory.getDiscountedPrice(upc); //will be null if no discount applies now
		boolean isOnsale=false;
		if(discount!=null) {
			isOnsale=true;
		}
		Item item = inventory.getItem(upc);
		SalesOrderDetail thisOrderDet = new SalesOrderDetail(salesID, upc, item.getDescription(), qty, price, discount, isOnsale,
				item.isEBT(), item.getItemType());		
		inventory.sellItem(upc, qty); //updating inventory
		return thisOrderDet;
	}


	public void addToSale(Long salesOrderID, SalesOrderDetail theDetail) {
		SalesOrder so=getSale(salesOrderID);
		if(so.isCompleted()) {
			throw new OrderCompleteException();
		}
		so.addDetail(theDetail);
	}


	public void removeFromSales(Long salesOrderID, SalesOrderDetail theDetail) {
		SalesOrder so=null;
		for(SalesOrder s: sales) {
			if(s.getSalesOrderID()==salesOrderID) {
				so=s;
				break;
			}
		}
		if(so==null) {
			throw new SalesOrderNotFoundException();
		}
		if(so.isCompleted()) {
			throw new OrderCompleteException();
		}
		so.removeDetail(theDetail);
	}

	public void sellItem(String upc, int qty, Long cashierID, double  minimumPurchase) {
		if(!inventory.hasItem(upc)) {
			throw new ItemNotFoundException(upc+" not found in store");
		}
		boolean validCashier=false;
		for(Person p: employees) {
			Cashier c=(Cashier) p;
			if(c.getID()==cashierID) {
				validCashier=true;
				break;
			}			
		}
		if(!validCashier) {
			throw new CashierNotFoundException();
		}
		Item theItem=inventory.getItem(upc);
		double price = inventory.getCurrentPrice(upc);
		Double discount = inventory.getDiscountedPrice(upc); //will be null if no discount applies now
		boolean isOnsale=false;
		if(discount!=null) {
			isOnsale=true;
		}
		SalesOrder quickSale = new SalesOrder(cashierID);
		sales.add(quickSale);
		SalesOrderDetail sod=new SalesOrderDetail(quickSale.getSalesOrderID(), upc, theItem.getDescription(), qty, price,
				discount, isOnsale, theItem.isEBT(), theItem.getItemType() );
		ArrayList<SalesOrderDetail> list=new ArrayList<>();
		list.add(sod);
		completeSale(quickSale.getSalesOrderID(), "nothing", cashierID, list, minimumPurchase );		
	}

	public ArrayList<LowItem> checkRestockLevels() {
		return inventory.getBelowRestockLevel();
	}

	public void modifyPhoneNumber(Long ID, String currPhoneNO, String newPhoneNO) {

		if(!persons.isEmpty()) {
			for(Person p:persons) {
				if(p.getPhoneNumber().equals(phoneNumber)) {
					p.setPhoneNumber(newPhoneNO);
				}
			}
		}
	}

	public Long addCustomer(String firstName, String lastName, Address address, String phoneNumber, char gender) {

		if(!persons.isEmpty()) {
			for(Person p:persons) {
				if(p.getPhoneNumber().equals(phoneNumber)) {
					//there's an account associated with this phone number;
					return null;
				}
			}
		}
		Customer c=new Customer(firstName,lastName,phoneNumber,address,gender);
		persons.add(c);
		return c.getID();
		
	}

	// Do you think we need all of the lists in the toString?
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Store Name: " + name + "\nStore Address: " + address + "\nPhone Number: " + phoneNumber
//				+ "\nEmployees: " + employees.toString() + "Inventory: " + inventory + "\nVendors: " + vendors
//				+ "\nPurchases: " + purchases + "Persons: " + persons
//				+ "\nSales: " + sales + "\nPayments: " + payments
				);
		return s.toString();
	}

	public String printReceipt(Long salesOrderID) throws SalesOrderNotFoundException{

		SalesOrder so=null;
		for(SalesOrder s: sales) {
			if(s.getSalesOrderID() == salesOrderID) {
				so=s;
			}
		}
		if(so==null) {
			throw new SalesOrderNotFoundException();
		}
		String receipt = so.printReceipt();
		return receipt;
	}

	public void processReturn(String upc, int qty, long customerID, Long salesOrderID, String reason) {
		Customer cust=getCustRecord(customerID);
		CustomerReturn cr=new CustomerReturn(salesOrderID, upc, qty, reason);
		double ret=cust.processReturn(cr);
		cust.creditBalance(ret);		
	}

	public Customer getBestCustomer() { //not taking into account returns, too complicated, just getting which customer spent the most $
		double maxSpent=0;
		Customer bestCust=null;
		for(Person p: persons) {
			Customer c=(Customer)p;
			if(c.getTotalSpent()>maxSpent) {
				maxSpent=c.getTotalSpent();
				bestCust=c;
			}
		}
		return bestCust;		
	}

	public Item getBestSellingItem() {
		return inventory.getItemWithMostSales();
	}

	public Vendor getVendorWithMostItems() {
			
		int maxPosition = 0;
		for (int i = 0;i <vendors.size();i++) {
			if (vendors.get(i).getItemCount()>vendors.get(maxPosition).getItemCount()) {
				maxPosition = i;
			}
		}
		return vendors.get(maxPosition);
	}
	
	//IMPLEMENT
	public void storeData() { //we never learned how to do serialization....
		
	}
	
	private PurchaseOrder findPurchaseOrder(Long poID) {
		if(!purchases.isEmpty()) {
			for(PurchaseOrder po:purchases) {
				if(po.getPurchaseOrderID().equals(poID)) {
					return po;
				}
			}
		}
		throw new PurchaseNotFoundException();
	}

}
