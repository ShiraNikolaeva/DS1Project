package dsFinalProjectMyCopy;
import java.util.Comparator;
import java.io.Serializable;


public class ItemDescriptionComparator implements Serializable, Comparator<Item>{
	
	public ItemDescriptionComparator() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int compare(Item item1, Item item2) {
		return item1.getDescription().compareTo(item2.getDescription());
	}


}
