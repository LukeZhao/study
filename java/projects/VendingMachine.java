/**
 * A vending machine application that will take your money and give you back candy
 * or other items.
 * 
 * (1) Display coin input menu. This is a loop until user select done.
 * (2) Display item to select.
 *      a. invalid item. Go back choose again.
 *      b. Not enough money. Choose between more money or different item.
 *      c. Right item. Dislplay change and exit.
 * (3) Display change and exit.
 * 
 * @author Alair Zhao 
 * @version 0.1
 */
import java.text.DecimalFormat;
import java.util.*;

public class VendingMachine {
	public static DecimalFormat dc = new DecimalFormat("#.00");
	public static Scanner sc = new Scanner(System.in);
	static Item items[] = {
			new Item("Pepsi", "A1", 5, 0.50),
			new Item("Coke", "A2", 5, 0.5),
			new Item("Dr. Pepper", "A3", 5, 0.75),
			new Item("Orange Juice", "B1", 4, 1.00),
			new Item("Apple Juice", "B2", 5, 1.50),
			new Item("Skittles", "B3", 5, 1.00),
			new Item("Hot Tamales 1", "C1", 5, 1.25),
			new Item("Hot Tamales 2", "C2", 5, 1.25),
			new Item("Hot Tamales 3", "C3", 5, 1.25),
			new Item("Hot Tamales 4", "D1", 5, 1.25),
			new Item("Hot Tamales 5", "D2", 5, 1.25),
			new Item("Hot Tamales 6", "D3", 5, 1.25) };
	static Coin coins[] = {
			new Coin("Quarter", "Q", 0.25),
			new Coin("Half Dollar", "H", 0.5),
			new Coin("Dollar", "D", 1.0)
	};
	static double balance = 0.0;

	public static void main(String args[]) {
		String choice = "0";
		while(true)
		{
			if (choice.equals("9"))
			{
				refundChange();
				break;
			}
			if (choice.equals("0"))
				choice = mainmenu();
			else if (choice.equals("1"))
				choice = displayPrice();
			else if (choice.equals("2"))
				choice = addMoney();
		}
		System.out.println("Thanks for using Alair's Vending Machine, bye!");
	}

	public static String mainmenu() {
		String choice = null;
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("1");
		choices.add("2");
		choices.add("9");
		System.out.println("---------Welcome to Alair's Vending Machine-------------");
		System.out.println("Please choose:");
		System.out.println("1 - check availability and price");
		System.out.println("2 - add money");
		System.out.println("9 - exit");
		displayBalance();
		choice = sc.nextLine().trim();
		while (!choices.contains(choice)) {
			System.out.println("invalid input, try again.");
			choice = sc.nextLine().trim();
		}
		return choice;
	}

	public static void refundChange()
	{
	   if (balance > 0.0)
		   System.out.println("Here is your change: $" + dc.format(balance));
	   balance = 0.0;
	}

	public static void displayBalance()
	{
		System.out.println("Your current balance is: $" + dc.format(balance));
	}
	
	public static String displayPrice() {
		ArrayList<String> choices = new ArrayList<String>();
		System.out.println("Please select an item from the following choices:");
		for(Item item: items)
		{
			System.out.println(item.code + ": " + item.name + "(qty: " + item.qty + ")(price: " + dc.format(item.price) + ")");
			choices.add(item.code);
		}
		System.out.println("Or select one of the following menu items:");
		System.out.println("2 - add money");
		System.out.println("9 - exit");
		choices.add("2");
		choices.add("9");
		displayBalance();
		
		String choice = sc.nextLine().trim();
		while (true)
		{
			while (!choices.contains(choice)) {
				System.out.println("invalid input, try again.");
				choice = sc.nextLine().trim();
			}
			if (choice.equals("2") || choice.equals("9"))
				break;
			Item item = null;
			for (Item it: items)
			{
				if (it.code.equals(choice))
				{
					item = it;
					break;
				}
			}
			if (item.qty == 0)
			{
				System.out.println("the item you selected is out of stock, try again.");
			}
			else if (item.price > balance)
			{
				System.out.println("not enough money, try again.");
			}
			else
			{
				System.out.println("Thanks for your purchase, please enjoy your " + item.name);
				balance -= item.price;
				item.qty -= 1;
				refundChange();
				choice = "0";
				break;
			}
			choice = sc.nextLine().trim();
		}
		return choice;
	}

	// Method to show the menu items.
	public static String addMoney() {
		ArrayList<String> choices = new ArrayList<String>();
		System.out.println("Please input your choice of coins:");
		for(Coin coin: coins)
		{
			System.out.println(coin.code + ": " + coin.name + "(value: $" + dc.format(coin.value));
			choices.add(coin.code);
		}
		System.out.println();
		System.out.println("or select the following: ");
		System.out.println("1 - check availability and price");
		System.out.println("9 - exit");
		choices.add("1");
		choices.add("9");
		displayBalance();
		
		String choice = sc.nextLine().trim();
		while (true)
		{
			while (!choices.contains(choice)) {
				System.out.println("invalid input, try again.");
				choice = sc.nextLine().trim();
			}
			if (choice.equals("1") || choice.equals("9"))
				break;
			Coin item = null;
			for (Coin it: coins)
			{
				if (it.code.equals(choice))
				{
					item = it;
					break;
				}
			}
			balance += item.value;
			displayBalance();
			choice = sc.nextLine().trim();
		}
		return choice;
	}

	static class Item {
		public String name;
		public String code;
		public int qty;
		public double price;

		public Item(String n, String c, int q, double p) {
			name = n;
			code = c;
			qty = q;
			price = p;
		}
	}
	
	static class Coin{
		public String name;
		public String code;
		public double value;
		
		public Coin(String n, String c, double v)
		{
			name = n;
			code = c;
			value = v;
		}
	}
}