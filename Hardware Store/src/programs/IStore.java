package programs;
import models.Item;
import models.Staff;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public interface IStore {

	//reads in all inventory items
	//items are all on own line, listed as itemName, itemQuantity, itemCost, itemAisle
	List<Item> getItemsFromFile() throws IOException;
	
	
	//reads in all staff
	List<Staff> getStaffFromFile() throws IOException;
	
	//saves all items from inventory.txt
	void saveItemsFromFile();
	
	//saves all stalf from stall_availability_IN.txt
	void saveStaffFromFile() throws IOException;
	
	//interprets user commands
	void takeAction() throws IOException;
	
	
}
