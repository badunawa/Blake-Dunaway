package programs;


import models.Item;
import models.Staff;
import utils.FileUtils;
import java.io.*;
import java.util.*;
public class Store implements IStore {


	//helper method for HIRE
	public static String assignAvailability(){
		String[] daysOfWeek = {"M", "T", "W", "TR", "F", "SAT", "SUN"};
		//get random numbers
		List<Integer> nums = new ArrayList<>();
		Random random = new Random();
		//Number 3 means we will have 3 random days of week
		while(nums.size() < 3){
			int randomNum = random.nextInt(6)+1;
			if(!nums.contains(randomNum)){
				nums.add(randomNum);
			}
		}
		Collections.sort(nums);
		String day1 = daysOfWeek[nums.get(0)];
		String day2 = daysOfWeek[nums.get(1)];
		String day3 = daysOfWeek[nums.get(2)];
		return day1+"."+day2+"."+day3;
	}

	//take action will handle all file input and call the correct methods
	//Commented out so i could run code, cntrl + / will uncomment
	public void takeAction() throws IOException {
		List<String> comms = FileUtils.readCommandsFromFile();
		for(String ele : comms){
			String[] command = ele.split(" ");
			List<String> listc = new ArrayList<>(Arrays.asList(command));
			if(listc.contains("HIRE")){
				String name = listc.get(1).replace("'", "") + " " + listc.get(2).replace("'", "");
				int age = Integer.parseInt(listc.get(3));
				HIRE(name,age,roleHelper(listc.get(4)),assignAvailability());
			}
			if(listc.contains("FIRE")){
				String name = listc.get(1).replace("'", "") + " " + listc.get(2).replace("'", "");
				FIRE(name);
			}
			if(listc.contains("PROMOTE")){
				String name = listc.get(1).replace("'", "") + " " + listc.get(2).replace("'", "");
				String role = listc.get(3);
				PROMOTE(name, role);
			}
			if(listc.contains("SCHEDULE")){
				StaffScheduler.scheduleStaff();
			}
			//item commands
			if(listc.contains("ADD")){
				String all = "";
				for(int i = 1; i < listc.size();i++){
					all += listc.get(i) + " ";
				}
				String[] splitted = all.split("'");
				String itemName = splitted[1];
				double price = Double.parseDouble(listc.get(listc.size()-5));
				int quantity = Integer.parseInt(listc.get(listc.size()-3));
				int aisle = Integer.parseInt(listc.get(listc.size()-1));
				ADD(itemName,price,quantity,aisle);
			}
			if(listc.contains("COST")){
				String fullString = "";
				for(int i = 1; i < listc.size(); i++){
					fullString += listc.get(i)+ " ";
				}
				String[]fullStr = fullString.split("'");
				fullString = fullStr[1];
				//String realCost = fullString.substring(cost.indexOf('‘')+1,cost.indexOf('‘'));
				COST(fullString);
			}
			if(listc.contains("QUANTITY")){
				String itemName = "";
				for(int i = 1; i < listc.size(); i++){
					itemName += listc.get(i) + " ";
				}
				String[] name = itemName.split("'");
				itemName = name[1];
				QUANTITY(itemName);
			}
			if(listc.contains("FIND")){
				//Statement is for items with length of 2 names
				if(listc.size() == 3){
					String name = listc.get(1).replace("'", "") + " " + listc.get(2).replace("'", "");
					FIND(name);
				}
				else if(listc.size() == 4){
					String name = listc.get(1).replace("'", "") + " " + listc.get(2) + " " +listc.get(3).replace("'", "");
					FIND(name);
				}
				else {
					String name = listc.get(1).replace("'","").replace("'","");
					FIND(name);
				}
			}
			if (listc.contains("SELL")){
				String itemName = "";
				for(int i = 1; i < listc.size(); i++){
					itemName += listc.get(i) + " ";
				}
				String[] splitted = itemName.split("'");
				itemName = splitted[1];
				int quant = Integer.parseInt(listc.get(listc.size()-1));
				SELL(itemName, quant);
			}
			if(listc.contains("SAW")){
				SAW();
			}
			if(listc.contains("EXIT")){
				EXIT();
			}

		}

	}




	@Override
	public List<Item> getItemsFromFile() throws IOException {
		return FileUtils.readInventoryFromFile();
	}


	@Override
	public List<Staff> getStaffFromFile() throws IOException {
		return FileUtils.readStaffFromFile();
	}

	@Override
	public void saveItemsFromFile()  {

	}

	@Override
	public void saveStaffFromFile() throws IOException { //Dont know what this method should do, duplicate method
		FileUtils.readStaffFromFile();
	}

	//This method will call get staff and a get a list of staff, add the new member to the list, then call staffwriter.
	public void HIRE(String name, int age, String role, String availability) throws IOException {
		List<Staff> staff;
		staff = getStaffFromFile();
		staff.add(new Staff(name, age, role, availability));
		FileUtils.writeStaffToFile(staff);
		FileUtils.writeLineToOutputFile(name + " was Hired");

	}

	public void FIRE(String staffname) throws IOException {
		List<Staff> staff;
		staff = getStaffFromFile();
		boolean found = false;

		for(Staff element : staff){
			//The writeup insinuates to check the file for all the employees names,
			//but the array list is always current with the files staff, so i only
			//check the array list
			if(element.getFullName().equals(staffname)){
				found = true;
				staff.remove(element);
				FileUtils.writeStaffToFile(staff);
				FileUtils.writeLineToOutputFile(staffname + " was fired.");
			}
			else if(found){
				break;
			}
		}
		if(!found){
			FileUtils.writeLineToOutputFile("ERROR: " + staffname + " cannot be found.");
		}
	}

	public void PROMOTE(String name, String role) throws IOException {
		List<Staff> staff;
		staff = getStaffFromFile();
		//Searching for staff member in object List, then
		//changing their role. Then updating file.
		//This assumes the input will be a character like 'M' or 'G'
		for(Staff element : staff){
			if(element.getFullName().equals(name)){
				element.setRole(roleHelper(role));
				FileUtils.writeStaffToFile(staff);
				FileUtils.writeLineToOutputFile(name + " was promoted to " + roleHelper(role) + ".");
				break;

			}
		}
	}

	public String roleHelper(String role){

		return switch (role) {
			case "G" -> "Gardening Expert";
			case "C" -> "Cashier";
			case "M" -> "Manager";
			default -> null;
		};
	}

	public void ADD(String name, double price, int quantity, int aisle) throws IOException{
		List<Item> itemArr;
		itemArr = FileUtils.readInventoryFromFile();
		boolean found = false;
		for (Item element : itemArr){
			if(element.getName().equals(name)){
				element.setQuantity(element.getQuantity() + quantity);
				FileUtils.writeInventoryToFile(itemArr);
				FileUtils.writeLineToOutputFile(element.getName() + " quantity was updated");
				found = true;
			}
		}
		if(!found){
			Item item = new Item(name,price,quantity,aisle);
			itemArr.add(item);
			FileUtils.writeInventoryToFile(itemArr);
			FileUtils.writeLineToOutputFile(item.getName() + " was added to inventory");
		}

	}

	public void COST(String itemName) throws IOException {
		List<Item> itemArr= new ArrayList<Item>();
		itemArr = FileUtils.readInventoryFromFile();
		for(Item x : itemArr) {
			if(x.getName().equals(itemName)) {
				String outputString = x.getName() + ":$" + x.getPrice();
				FileUtils.writeLineToOutputFile(outputString);
				break;
			}
		}

	}
	public static void EXIT() throws IOException {
		FileUtils.writeLineToOutputFile("Thank you for visiting High's Hardware and Gardening!");
		System.out.println("Press enter to continue...");
		Scanner in = new Scanner(System.in);
		String nextStroke = in.nextLine();
		if(nextStroke.isEmpty()){
			System.exit(0);
		}

	}

	public void QUANTITY(String itemName) throws IOException {
		List<Item> itemArr;
		boolean found = false;
		itemArr = getItemsFromFile();
		//System.out.println(itemName);
		for(Item x : itemArr) {
			//System.out.println(x.getName());
			if(x.getName().equals(itemName)) {
				FileUtils.writeLineToOutputFile("Quantity of " + itemName  + ": "+ x.getQuantity());
				found = true;
				break;
			}
		}
		if(!found){
			FileUtils.writeLineToOutputFile("Quantity of " + itemName  + ": 0");
		}
	}

	public void FIND(String itemName) throws IOException {
		List<Item> itemArr = new ArrayList<>();
		itemArr = getItemsFromFile();
		boolean found = false;
		FileUtils.writeLineToOutputFile("Performing store lookup for " + itemName);
		for(Item x : itemArr) {
			if(x.getName().equals(itemName)) {
				StoreMapDisplay.display(x);
				found = true;
				//System.out.println("found");

			}
			if(found){
				FileUtils.writeLineToOutputFile(itemName + " was found at aisle " + x.getAsileNum());
				break;
			}
		}
		if(!found){
			FileUtils.writeLineToOutputFile(itemName + " could not be located in the store");
		}
	}


	public void SELL(String itemName, int quantity) throws IOException {
		List<Item> itemArr = new ArrayList<>();
		boolean sold = false;
		itemArr = FileUtils.readInventoryFromFile();
		for(int i = 0; i < itemArr.size(); i++) {
			if(itemArr.get(i).getName().equals(itemName) && itemArr.get(i).getQuantity()>= quantity) {
				FileUtils.writeLineToOutputFile(quantity + " " + itemArr.get(i).getName() + " was sold");
				itemArr.get(i).setQuantity(itemArr.get(i).getQuantity()-quantity);
				sold = true;
				break;
			}

		}
		if (!sold){
			FileUtils.writeLineToOutputFile("Error: " + itemName + " could not be sold");
		}
		FileUtils.writeInventoryToFile(itemArr);

	}
	public void SAW() throws IOException {
		//need to delete old planks
		boolean found = false;
		List<Item> items = new ArrayList<>();
		items = FileUtils.readInventoryFromFile();
		int getIndex = 0;
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).getName().contains("Plank")){
				getIndex = i;
				found = true;
			}
		}
		if (found) {
			Item tempItem = items.get(getIndex);
			String[] plankName = items.get(getIndex).getName().split("-");
			//System.out.println(plankName[plankName.length - 1]);
			int plankLength = Integer.parseInt(plankName[plankName.length - 1]);
			List<Integer> cutPlanks = SawPrimePlanks.getPlankLengths(plankLength);
			items.remove(getIndex);
			String itemName = "Plank-" + cutPlanks.get(0) + "";
			//wait for TA response on this one
			double itemPrice = cutPlanks.get(0) * cutPlanks.get(0);
			int itemQuantity = tempItem.getQuantity() * cutPlanks.size();
			int itemAisle = tempItem.getAsileNum();

			items.add(new Item(itemName, itemPrice, itemQuantity, itemAisle));

			FileUtils.writeInventoryToFile(items);
			FileUtils.writeLineToOutputFile("Planks sawed");
		}
		else{
			FileUtils.writeLineToOutputFile("No planks in inventory");
		}
	}

	public static void main(String[] args) throws IOException {
		Store st = new Store();
		st.takeAction();

	}
}



