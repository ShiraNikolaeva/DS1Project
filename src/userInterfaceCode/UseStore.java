package userInterfaceCode;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import dsFinalProjectMyCopy.Address;
import dsFinalProjectMyCopy.Customer;
import dsFinalProjectMyCopy.ITEMTYPE;
import dsFinalProjectMyCopy.Item;
import dsFinalProjectMyCopy.ItemOrder;
import dsFinalProjectMyCopy.LowItem;
import dsFinalProjectMyCopy.PaymentDetail;
import dsFinalProjectMyCopy.ReceivedItem;
import dsFinalProjectMyCopy.SalesOrderDetail;
import dsFinalProjectMyCopy.SalesOrderNotFoundException;
import dsFinalProjectMyCopy.Store;
import dsFinalProjectMyCopy.Vendor;

public class UseStore {
	public static void main(String[] args) {

		Address address = new Address("Main", "Brooklyn", "NEWYORK", "11111");
		Store store = new Store("Store", address, "1234567897");
		Scanner s = new Scanner(System.in);
		menu(s, store);

		s.close();
	}

	private static void menu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
//				System.out.println(store.getItem(itemID))
				System.out.println(
						"Please enter a digit between 1 and 10 inclusive: \n 1)Items \n 2)Vendors \n3)Shipments"
								+ "\n4)purchaseOrders\n5)Payments \n6)Receipts \n7)Sales \n8)Customers \n9)Returns \n10)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					itemMenu(s, store);
					break;
				case 2:
					vendorMenu(s, store);
					break;
				case 3:
					shipmentMenu(s, store);
					break;
				case 4:
					purchaseOrderMenu(s, store);
					break;
				case 5:
					paymentMenu(s, store);
					break;
				case 6:
					receiptMenu(s, store);
					break;
				case 7:
					salesMenu(s, store);
					break;
				case 8:
					customerMenu(s, store);
					break;
				case 9:
					returnMenu(s, store);
					break;
				case 10:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (Exception e) {
				System.out.print(e.getMessage() +" System ending due to error. Restart system to try again.");
				System.exit(1);

			}
		}

	}

	private static void returnMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println("RETURNS: \nPlease enter a digit between 1 and 2 inclusive: "
						+ "\n 1)Process Returns \n 2)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					processReturn(s, store);
					break;

				case 2:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (Exception e) {
				System.out.print(e.getMessage()+" Returning to main menu");
			}
		}

	}

	private static void processReturn(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter the Item upc that you would like to return: ");
		String upc = s.nextLine();
		System.out.println("Enter the amount of Item " + upc + " that you are returning: ");
		int qty = s.nextInt();
		System.out.println("Enter the customer ID: ");
		long customerID = s.nextLong();
		System.out.println("What is the sales order id? ");
		Long salesOrderID = s.nextLong();
		System.out.println("Enter reason for return: ");
		String reason = s.nextLine();
		
		try {
			store.processReturn(upc, qty, customerID, salesOrderID, reason);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void customerMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println("CUSTOMERS: \nPlease enter a digit between 1 and 5 inclusive: "
						+ "\n 1)Get customer record \n 2)Modify Phone Number" + "\n3) Add Customer Record"
						+ "\n4) Get Best Customer" + "\n5)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					getCustomerRecord(s, store);
					break;
				case 2:
					modifyPhoneNumber(s, store);
					break;
				case 3:
					addCustomerRecord(s, store);
					break;
				case 4:
					getBestCustomer(store);
					break;

				case 5:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage()+" Returning to main menu");
			}
		}

	}

	private static void modifyPhoneNumber(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter the id of the customer that you would like to modify the phone number of");
		Long id = s.nextLong();
		s.nextLine();
		int validCount = 0;
		String number = "";
		System.out.println("Enter in the customer's current phone number: ");
		while (validCount < 3) {
			number = s.nextLine();
			if (number.length() != 10) {
				if (validCount == 2) {
					System.out.println("Invalid Phone number. Please try again later.\n");
					return;
				}
				validCount++;
				System.out.println("Reenter phone number with 10 digits");
			} else {
				validCount = 5;
			}

		}
		String newNumber = "";
		int newValidCount = 0;
		System.out.println("Enter in the customer's NEW phone number: ");
		while (newValidCount < 3) {
			newNumber = s.nextLine();
			if (newNumber.length() != 10) {
				if (newValidCount == 2) {
					System.out.println("Invalid Phone number. Please try again later.\n");
					return;
				}
				newValidCount++;
				System.out.println("Reenter phone number with 10 digits");
			} else {
				newValidCount = 5;
			}

		}
		try {
			store.modifyPhoneNumber(id, number, newNumber);
			System.out.println("Customer " + id + " 's phone number was changed to " + newNumber);

		} catch (Exception e) {
			System.out.println("The customer does not exist!");
		}
	}

	private static void getCustomerRecord(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter the id of the customer that you would like to get the record of: ");
		Long id = s.nextLong();
		int validCount = 0;
		s.nextLine();
		String number = "";
		System.out.println("Enter in the customer's phone number: ");
		while (validCount < 3) {
			number = s.nextLine();
			if (number.length() != 10) {
				if (validCount == 2) {
					System.out.println("Invalid Phone number. Please try again later.\n");
					return;
				}
				validCount++;
				System.out.println("Reenter phone number with 10 digits");
			} else {
				validCount = 5;
			}

		}
		Customer cust = store.getCustRecord(id, number);
		if (cust == null) {
			System.out.println("The customer does not exist. \n");
			return;
		} else {
			System.out.println(cust.toString());
		}
	}

	private static void addCustomerRecord(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter in the customer's First Name: ");
		String fname = s.nextLine();
		System.out.println("Enter in the customer's Last Name: ");
		String lname = s.nextLine();
		int validCount = 0;
		String number = "";
		System.out.println("Enter in the customer's phone number: ");
		while (validCount < 3) {
			number = s.nextLine();
			if (number.length() != 10) {
				if (validCount == 2) {
					System.out.println("Invalid Phone number. Please try again later.\n");
					return;
				}
				validCount++;
				System.out.println("Reenter phone number with 10 digits");
			} else {
				validCount = 5;
			}

		}
		int genderCount = 0;
		char gender = 'F';
		System.out.println("Enter in the customer's gender: (F or M)");
		while (genderCount < 3) {
			String response = s.nextLine().toUpperCase();
			gender = response.charAt(0);
			if (gender == ('F') || gender == ('M')) {
				genderCount = 5;

			} else {
				if (genderCount == 2) {
					System.out.println("Invalid Answer. Please try again later.\n");
					return;
				}
				genderCount++;
				System.out.println("Reenter gender using F or M");
			}
		}

		System.out.println("Enter in the customer's Address:" + "\nStreet: ");
		String street = s.nextLine();
		System.out.println("City: ");
		String city = s.nextLine();
		System.out.println("State (without spaces):");
		String state = s.nextLine().toUpperCase();
		System.out.println("Zip code: ");
		String zip = s.nextLine();
		try {
			Address address = new Address(street, city, state, zip);

			Long id = store.addCustomer(fname, lname, address, number, gender);
			if (id == null) {
				System.out.println("Invalid Entry! There is already a customer with the same phone number.\n");
				return;
			} else {

				System.out.println("The customer was added. The customer ID is " + id);
			}
		} catch (Exception e) {
			System.out.println("The Address was not a valid address. Please try again later. ");
		}

	}

	private static void getBestCustomer(Store store) {
		System.out.println("The best customer is " + store.getBestCustomer().toString());

	}

	private static void salesMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println("SALES: \nPlease enter a digit between 1 and 7 inclusive: "
						+ "\n 1)Set up sale \n 2)Complete Sale" + "\n 3)Scan Item" + "\n4)Add To Sale"
						+ "\n5) Remove from sales" + "\n6) Sell Item" + "\n7)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					setUpSale(s, store);
					break;
				case 2:
					completeSale(s, store);
					break;
				case 3:
					scanItem(s, store);
					break;
				case 4:
					addToSale(s, store);
					break;
				case 5:
					removeFromSale(s, store);
					break;
				case 6:
					sellItem(s, store);
					break;
				case 7:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage()+" Returning to main menu");
			}
		}

	}

	private static void scanItem(Scanner s, Store store) { //if had more time would make like a make sale method where they can scan multiple items
		s.nextLine();
		System.out.println("Please enter in the UPC of the item: ");
		String upc = s.nextLine();
		System.out.println("Please enter in the quantity of the item: ");
		int qty = s.nextInt();
		System.out.println("Enter in the sales order ID: ");
		long id = s.nextLong();

		try {
			store.scanItem( upc, qty, id);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void sellItem(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Please enter in the UPC of the item: ");
		String upc = s.nextLine();
		System.out.println("Please enter in the quantity of the item: ");
		int qty = s.nextInt();
		System.out.println("Please enter in the Cashier's id: ");
		Long id = s.nextLong();
		System.out.println("Please enter in the minimum purchase, if there is none, enter 0: ");
		double min = s.nextDouble();
		
		try {
			store.sellItem(upc, qty, id, min);
		} catch (Exception e) {
			System.out.println("Invalid Data!");
		}
	}

	private static void completeSale(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter in the sales order ID: ");
		Long id = s.nextLong();
		s.nextLine();
		int validCount = 0;
		String number = "";
		System.out.println("Enter in the customer's phone number: ");
		while (validCount < 4) {
			number = s.nextLine();
			if (number.length() != 10) {
				if (validCount == 3) {
					System.out.println("Invalid Phone number. Please try again later.\n");
					return;
				}
				validCount++;
				System.out.println("Reenter phone number with 10 digits");
			} else {
				validCount = 5;
			}
		}
		System.out.println("Enter the cashier's ID: ");
		Long cashId = s.nextLong();
		s.nextLine();
		try {
			ArrayList<SalesOrderDetail> details = getSods(s);
			if (details == null) {
				return;
			}
			System.out.println("What is the minimum purchase in the sale? ");
			Double minPurchase = s.nextDouble();
			store.completeSale(id, number, cashId, details, minPurchase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static ArrayList<SalesOrderDetail> getSods(Scanner s) {
		ArrayList<SalesOrderDetail> details = new ArrayList<SalesOrderDetail>();
		s.nextLine();
		boolean b = true;
		while (b) {
			System.out.println("Add a sales Order Detail: ");
			s.nextLine();
			System.out.println("Enter in the Sales Order ID: ");
			Long id = s.nextLong();
			s.nextLine();
			System.out.println("Please enter in the UPC of the item: ");
			String upc = s.nextLine();
			System.out.println("Please enter in the description of the item: ");
			String desc = s.nextLine();
			System.out.println("Enter in the quanitity of the item: ");
			int qty = s.nextInt();
			s.nextLine();
			System.out.println("Please enter in the current price of the item: ");
			double currPrice = s.nextDouble();
			System.out.println("Please enter in the discounted price of the item: ");
			double discPrice = s.nextDouble();
			s.nextLine();
			System.out.println("Please enter in the Item Type of the item: "
					+ "\n(ALUMINUM, BAKERY, CHICKEN, FISH,FRUIT,GROCERY,MAGAZINES,MEAT,PAPERGOODS)");
			String itemType = s.nextLine().toUpperCase();
			int validCount = 0;
			boolean onSale = false;
			String response = "";
			System.out.println("Is the item on Sale? (yes or no)");
			while (validCount < 3) {
				response = s.nextLine().toUpperCase();
				if (response.equals("YES") || response.equals("NO")) {
					validCount = 5;

				} else {
					if (validCount == 2) {
						System.out.println("Invalid Answer. Please try again later.\n");
						return null;
					}
					validCount++;
					System.out.println("Invalid answer. Is the item on sale? (yes or no)");
				}
			}
			if (response.equals("YES")) {
				onSale = true;
			}
			int ebtCount = 0;
			boolean isEBT = false;
			String answer = "";
			System.out.println("Is the item covered by EBT? (yes or no) ");
			while (ebtCount < 3) {
				answer = s.nextLine().toUpperCase();
				if (answer.equals("YES") || answer.equals("NO")) {
					ebtCount = 5;

				} else {
					if (ebtCount == 2) {
						System.out.println("Invalid Answer. Please try again later.\n");
						return null;
					}
					ebtCount++;
					System.out.println("Invalid answer. Is the item covered by EBT? (yes or no)");
				}
			}
			if (answer.equals("YES")) {
				isEBT = true;
			}

			try {
				SalesOrderDetail sod = new SalesOrderDetail(id, upc, desc, qty, currPrice, discPrice, onSale, isEBT,
						ITEMTYPE.valueOf(itemType));
				details.add(sod);
			} catch (Exception e) {
				System.out.println(e.getMessage() + " Try again later");
			}
			System.out.println("Enter 0 if you would like to add another sales Order Detail:");
			int val = s.nextInt();
			s.nextLine();
			if (val != 0) {
				b = false;
			}
		}
		return details;
	}

	private static void addToSale(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter in the Sales Order ID: ");
		Long id = s.nextLong();
		s.nextLine();
		System.out.println("Please enter in the UPC of the item: ");
		String upc = s.nextLine();
		System.out.println("Please enter in the description of the item: ");
		String desc = s.nextLine();
		System.out.println("Enter in the quantity of the item: ");
		int qty = s.nextInt();
		s.nextLine();
		System.out.println("Please enter in the current price of the item: ");
		double currPrice = s.nextDouble();
		System.out.println("Please enter in the discounted price of the item: ");
		double discPrice = s.nextDouble();
		s.nextLine();
		System.out.println("Please enter in the Item Type of the item: "
				+ "\n(ALUMINUM, BAKERY, CHICKEN, FISH,FRUIT,GROCERY,MAGAZINES,MEAT,PAPERGOODS)");
		String itemType = s.nextLine().toUpperCase();
		int validCount = 0;
		boolean onSale = false;
		String response = "";
		System.out.println("Is the item on Sale? (yes or no)");
		while (validCount < 3) {
			response = s.nextLine().toUpperCase();
			if (response.equals("YES") || response.equals("NO")) {
				validCount = 5;

			} else {
				if (validCount == 2) {
					System.out.println("Invalid Answer. Please try again later.\n");
					return;
				}
				validCount++;
				System.out.println("Invalid answer. Is the item on sale? (yes or no)");
			}
		}
		if (response.equals("YES")) {
			onSale = true;
		}
		int ebtCount = 0;
		boolean isEBT = false;
		String answer = "";
		System.out.println("Is the item covered by EBT? (yes or no) ");
		while (ebtCount < 3) {
			answer = s.nextLine().toUpperCase();
			if (answer.equals("YES") || answer.equals("NO")) {
				ebtCount = 5;

			} else {
				if (ebtCount == 2) {
					System.out.println("Invalid Answer. Please try again later.\n");
					return;
				}
				ebtCount++;
				System.out.println("Invalid answer. Is the item covered by EBT? (yes or no)");
			}
		}
		if (answer.equals("YES")) {
			isEBT = true;
		}

		try {
			SalesOrderDetail sod = new SalesOrderDetail(id, upc, desc, qty, currPrice, discPrice, onSale, isEBT,
					ITEMTYPE.valueOf(itemType));
			store.addToSale(id, sod);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " Try again later");
		}
	}

	private static void removeFromSale(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter in the Sales Order ID: ");
		Long id = s.nextLong();
		s.nextLine();
		System.out.println("Please enter in the UPC of the item: ");
		String upc = s.nextLine();
		System.out.println("Please enter in the description of the item: ");
		String desc = s.nextLine();
		System.out.println("Enter in the quanitity of the item: ");
		int qty = s.nextInt();
		s.nextLine();
		System.out.println("Please enter in the current price of the item: ");
		double currPrice = s.nextDouble();
		System.out.println("Please enter in the discounted price of the item: ");
		double discPrice = s.nextDouble();
		s.nextLine();
		System.out.println("Please enter in the Item Type of the item: "
				+ "\n(ALUMINUM, BAKERY, CHICKEN, FISH,FRUIT,GROCERY,MAGAZINES,MEAT,PAPERGOODS)");
		String itemType = s.nextLine().toUpperCase();
		int validCount = 0;
		boolean onSale = false;
		String response = "";
		System.out.println("Is the item on Sale? (yes or no)");
		while (validCount < 3) {
			response = s.nextLine().toUpperCase();
			if (response.equals("YES") || response.equals("NO")) {
				validCount = 5;

			} else {
				if (validCount == 2) {
					System.out.println("Invalid Answer. Please try again later.\n");
					return;
				}
				validCount++;
				System.out.println("Invalid answer. Is the item on sale? (yes or no)");
			}
		}
		if (response.equals("YES")) {
			onSale = true;
		}
		int ebtCount = 0;
		boolean isEBT = false;
		String answer = "";
		System.out.println("Is the item covered by EBT? (yes or no) ");
		while (ebtCount < 3) {
			answer = s.nextLine().toUpperCase();
			if (answer.equals("YES") || answer.equals("NO")) {
				ebtCount = 5;

			} else {
				if (ebtCount == 2) {
					System.out.println("Invalid Answer. Please try again later.\n");
					return;
				}
				ebtCount++;
				System.out.println("Invalid answer. Is the item covered by EBT? (yes or no)");
			}
		}
		if (answer.equals("YES")) {
			isEBT = true;
		}

		try {
			SalesOrderDetail sod = new SalesOrderDetail(id, upc, desc, qty, currPrice, discPrice, onSale, isEBT,
					ITEMTYPE.valueOf(itemType));
			store.removeFromSales(id, sod);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "Try again later");
		}
	}

	private static void setUpSale(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter the customer id: ");
		Long custID = s.nextLong();
		s.nextLine();
		System.out.println("Enter in the customer's phone number: ");
		String num = s.nextLine();
		System.out.println("Enter in the cashier's ID: ");
		int id = s.nextInt();

		try {
			store.setUpSale(custID, num, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void receiptMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println("RECEIPTS: \nPlease enter a digit between 1 and 3 inclusive: "
						+ "\n 1)Get total receipts \n 2)Print Reciept" + "\n3)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					getTotalReceipts(store);
					break;
				case 2:
					printReceipt(s, store);
					break;

				case 3:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage());
				System.exit(1);

			}
		}

	}

	private static void printReceipt(Scanner s, Store store) {

		s.nextLine();
		System.out.println("Enter in the id of the salesOrder: ");
		Long id = s.nextLong();
		try {
			String receipt = store.printReceipt(id);
			System.out.println(receipt);
		} catch (SalesOrderNotFoundException e) {
			System.out.println("The sales order id " + id + " does not exist. Please try again later\n");
		}

	}

	private static void getTotalReceipts(Store store) {
		store.getTotalReceipts();
	}

	private static void paymentMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println("PAYMENTS: \nPlease enter a digit between 1 and 4 inclusive: "
						+ "\n 1)Receive Payment \n 2)Make a payment \n3) Get total payments" + "\n4)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					ReceivePayment(s, store);
					break;
				case 2:
					makePayment(s, store);
					break;
				case 3:
					getTotalPayments(store);
					break;

				case 4:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage()+" Returning to main menu");
			}
		}

	}

	private static void getTotalPayments(Store store) {
		System.out.println(store.getTotalPayments());

	}

	private static void ReceivePayment(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter the order ID: ");
		long orderID = s.nextLong();
		s.nextLine();
		ArrayList<PaymentDetail> details = new ArrayList<PaymentDetail>();
		int val = 1;
		try {
			while (val == 1) {
				System.out.println("Enter the payment ID: ");
				long payID = s.nextLong();
				System.out.println("Enter amount of money: ");
				double dollarAmount = s.nextDouble();
				s.nextLine();
				String method = "";
				int counter = 0;
				int choice = 0;
				while (counter < 3) {
					System.out.println("Enter in the number for the correct payment method: "
							+ "\n1. Cash \n2. Check\n3. Credit Card \n4. Debit Card" + "\n5) EBT card");
					choice = s.nextInt();
					switch (choice) {
					case 1:
						method = "CASH";
						counter = 5;
						break;
					case 2:
						method = "CHECK";
						counter = 5;
						break;
					case 3:
						method = "CREDITCARD";
						counter = 5;
						break;
					case 4:
						method = "DEBITCARD";
						counter = 5;
						break;
					case 5:
						method = "EBTCARD";
						counter = 5;
						break;

					default:
						System.out.println("Invalid Entry");
						counter++;
						if (counter > 2) {
							System.out.println("Payment was not made. Returning to main menu");
							return;
						}
					}
				}
				if (method.equals("CREDITCARD") || method.equals("DEBITCARD") || method.equals("EBTCARD")) {
					s.nextLine();
					System.out.println("Enter the cardID: ");
					String cardID = s.nextLine();
					PaymentDetail detail = new PaymentDetail(payID, method, dollarAmount, cardID);
					details.add(detail);
				} else {
					PaymentDetail detail = new PaymentDetail(payID, method, dollarAmount);
					details.add(detail);
				}
				System.out.println("Enter 1 if you would like to enter in another Payment Detail. If not, enter 0.");
				val = s.nextInt();
			}
			store.receivePayment(orderID, details);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void makePayment(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter in vendor ID:");
		int vendorID = s.nextInt();
		System.out.println("Enter in purchase order ID: ");
		Long purchaseOrderID = s.nextLong();
		System.out.println("Enter amount of money: ");
		double amount = s.nextDouble();
		s.nextLine();
		String method = "";
		int counter = 0;
		;
		int choice = 0;
		while (counter < 3) {
			System.out.println("Enter in the number for the correct payment method: "
					+ "\n1. Cash \n2. Check\n3. Credit Card \n4. Debit Card" + "\n5) EBT card");
			choice = s.nextInt();
			switch (choice) {
			case 1:
				method = "CASH";
				counter = 5;
				break;
			case 2:
				method = "CHECK";
				counter = 5;
				break;
			case 3:
				method = "CREDITCARD";
				counter = 5;
				break;
			case 4:
				method = "DEBITCARD";
				counter = 5;
				break;
			case 5:
				method = "EBTCARD";
				counter = 5;
				break;

			default:
				System.out.println("Invalid Entry");
				counter++;
				if (counter > 2) {
					System.out.println("Payment was not made. Returning to main menu");
					return;
				}
			}

		}
		try {
			store.makePayment(vendorID, purchaseOrderID, amount, method);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void purchaseOrderMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println("PURCHASE ORDERS: \nPlease enter a digit between 1 and 4 inclusive: "
						+ "\n 1)Set Up Purchase Order \n 2) Add item to purchase order from vendor" + "\n3)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					setUpPurchaseOrder(s, store);
					break;
				case 2:
					addItemToPOFromVendor(s, store);
					break;

				case 3:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage()+" Returning to main menu");
			}
		}

	}

	private static void addItemToPOFromVendor(Scanner s, Store store) {
		// TODO Auto-generated method stub
		s.nextLine();
		System.out.println("Enter the Purchase Order ID: ");
		Long poID = s.nextLong();
		s.nextLine();
		System.out.println("Enter the UPC of the item");
		String upc = s.nextLine();
		System.out.println("Enter quantity ordered of the item");
		Integer qtyOrdered = s.nextInt();
		try {
			store.addItemToPOFromVendor(poID, upc, qtyOrdered);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void setUpPurchaseOrder(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter the vendorID");
		int id = s.nextInt();
		Long l = store.setUpPurchaseOrder(id);
		if (l == null) {
			System.out.println("Vendor " + id + " does not exist!");
			return;
		}
		ArrayList<ItemOrder> items = new ArrayList<ItemOrder>();
		try {
			int choice = 1;
			while (choice ==1) {

				System.out.println("Enter the UPC of the item");
				String upc = s.nextLine();
				System.out.println("Enter the quantity of the item received");
				int qty = s.nextInt();

				ItemOrder item = new ItemOrder(upc, qty, id);
				items.add(item);
				System.out.println("To add another item, enter 1, if not enter 0");
				choice=s.nextInt();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long poID = store.setUpPurchaseOrder(id, items);
		System.out.println("Your purchase order number is " + poID);
	}

	private static void shipmentMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println("SHIPMENTS: \nPlease enter a digit between 1 and 3 inclusive: "
						+ "\n 1)Receive Shipment \n " + "2)Receive Item Shipment" + "\n3)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					receiveShipment(s, store);
					break;
				case 2:
					receiveItemShipment(s, store);
					break;

				case 3:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage());
				System.exit(1);

			}
		}

	}

	private static void receiveShipment(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter the Purchase Order ID");
		Long poID = s.nextLong();
		s.nextLine();
		ArrayList<ReceivedItem> items = new ArrayList<ReceivedItem>();
		try {
			int choice = 1;
			while (choice == 1) {

				System.out.println("Enter the UPC of the item");
				String upc = s.nextLine();
				System.out.println("Enter the quantity of the item received");
				int qty = s.nextInt();
				System.out.println("Enter the unit cost of the item");
				Double unitCost = s.nextDouble();

				ReceivedItem ri = new ReceivedItem(upc, qty, unitCost);
				items.add(ri);
				System.out.println(" To add another item, enter 1, if not enter 0");
				choice=s.nextInt();
			}
			store.receiveShipment(poID, items);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void receiveItemShipment(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter the Purchase Order ID");
		Long poID = s.nextLong();
		s.nextLine();
		System.out.println("Enter the UPC of the item");
		String upc = s.nextLine();
		System.out.println("Enter the quantity of the item received");
		int qty = s.nextInt();
		System.out.println("Enter the unit cost of the item");
		Double unitCost = s.nextDouble();
		try {
			store.receiveItemShipment(poID, upc, qty, unitCost);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void vendorMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println("VENDORS: \nPlease enter a digit between 1 and 2 inclusive: "
						+ "\n 1)Add Vendor \n2)Get the vendor with the most items" + "\n3)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					addVendor(s, store);
					break;
				case 2:
					getVendorWithMostItems(store);
					break;

				case 3:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage()+" Returning to main menu");
			}
		}
	}

	private static void addVendor(Scanner s, Store store) {
		s.nextLine();
		System.out.println("Enter in the vendor's name: ");
		String name = s.nextLine();
		int validCount = 0;
		String number = "";
		System.out.println("Enter in the vendor's phone number: ");
		while (validCount < 4) {
			number = s.nextLine();
			if (number.length() != 10) {
				if (validCount == 3) {
					System.out.println("Invalid Phone number. Please try again later.\n");
					return;
				}
				validCount++;
				System.out.println("Reenter phone number with 10 digits");
			} else {
				validCount = 5;
			}

		}

		System.out.println("Enter in the vendor's Address:" + "\nStreet: ");
		String street = s.nextLine();
		System.out.println("City: ");
		String city = s.nextLine();
		System.out.println("State (without spaces):");
		String state = s.nextLine().toUpperCase();
		System.out.println("Zip code: ");
		String zip = s.nextLine();
		try {
			Address address = new Address(street, city, state, zip);
			Vendor vendor = new Vendor(name, address, number);
			store.addVendor(vendor);
			System.out.println("The vendor was added.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void getVendorWithMostItems(Store store) {
		System.out
				.println("The vendor with the most items is Vendor: " + store.getVendorWithMostItems().getVendorName());
	}

	private static void itemMenu(Scanner s, Store store) {
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.println(
						"ITEMS: \nPlease enter a digit between 1 and 6: \n 1)Add Item \n 2)Set Restock Level of an Item\n3)Check if item exists"
								+ "\n4)Check which items you need to restock\n5)Best Selling Item \n6)Exit");

				int answer = s.nextInt();
				switch (answer) {
				case 1:
					addItem(s, store);
					break;
				case 2:
					setRestockLevel(s, store);
					break;
				case 3:
					doesItemExist(s, store);
					break;
				case 4:
					checkRestockLevel(s, store);
					break;
				case 5:
					bestSellingitem(store);
					break;
				case 6:
					exit = true;
					break;
				default:
					System.out.println("Invalid Entry!");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage()+" Returning to main menu");
			}
		}
	}

	private static void bestSellingitem(Store store) {
		Item item = store.getBestSellingItem();
		System.out.println("The best selling item is " + item);
	}

	private static void checkRestockLevel(Scanner s, Store store) {
		ArrayList<LowItem> items = store.checkRestockLevels();
		if (items.isEmpty()) {
			System.out.println("There are no items ready to be restocked");
		} else {
			System.out.println("The following items need to be restocked: ");
			for (int i = 0; i < items.size(); i++) {
				System.out.println(items.get(i));
			}
		}
	}

	private static void doesItemExist(Scanner s, Store store) {

		// enter upc
		s.nextLine();
		System.out.println("Please enter the UPC of the item");
		String response = s.nextLine();
		if (store.hasItem(response)) {
			System.out.println("The item " + response + "  does exist");
		} else {
			System.out.println("The item " + response + "  does not exist");

		}
	}

	private static void setRestockLevel(Scanner s, Store store) {
		// Give the user 3 tries
		String upc = "";
		int level = -1;
		boolean upcEntered = false;

		// enter upc
		s.nextLine();
		for (int i = 0; i < 3; i++) {
			System.out.println("Please enter the UPC of the item you wish to set the restock level of");
			String response = s.nextLine();

			if (store.hasItem(response)) {
				upc = response;
				upcEntered = true;
				i = 3;
			}

		}

		if (upcEntered == false) {
			return;
		}
		// enter level
		System.out.println("Please enter the restock level for item " + upc + ":");
		level = s.nextInt();

		store.setRestockLevel(upc, level);

	}

	private static void addItem(Scanner s, Store store) {
//		Item item = new Item("ABC", "Apple", 0.25, "FRUIT", "WEIGHT", 1);
		String upc = "";
		String desc = "";
		double price = 0.0;
		String itemType = "";
		String unitMeasure = "";
		int vendorID = -1;
		boolean isEBT=false;

		s.nextLine();
		System.out.println("Please enter in the UPC of the item: ");
		upc = s.nextLine();
		System.out.println("Please enter in the description of the item: ");
		desc = s.nextLine();
		System.out.println("Please enter in the price of the item: ");
		price = s.nextDouble();
		s.nextLine();
		System.out.println("Please enter in the Item Type of the item: "
				+ "\n(ALUMINUM, BAKERY, CHICKEN, FISH,FRUIT,GROCERY,MAGAZINES,MEAT,PAPERGOODS)");
		itemType = s.nextLine().toUpperCase();
		System.out.println("Please enter in the Unit Measure of the item:" + "\n(PIECE, WEIGHT)");
		unitMeasure = s.nextLine().toUpperCase();
		System.out.println("Please enter if this item can be covered by EBT (yes or no)");
		String answer = s.nextLine().toLowerCase();
		if(answer.equals("yes")) {
			isEBT=true;
		}
		System.out.println("Please enter in the vendor ID that sells the item: "); //should add validation that this is in fact a valid vendor but no time
		vendorID = s.nextInt();
		try {
			Item item = new Item(upc, desc, price, itemType, unitMeasure, isEBT, vendorID);
			store.addItem(item);
			System.out.println("The item was added. ");
		} catch (RuntimeException e) {  //no time for individual input validation, would take way too long. Just catching general exception n throwing back to main menu. 
			System.out.println(e.getMessage() +" Returning to main menu.");
		} catch(Exception e) {
			System.out.println(e.getMessage() +" Returning to main menu.");
		}

	}

}
