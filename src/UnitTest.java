import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class UnitTest {
	String[] args = {"test"};
	ArrayList<String> list = new ArrayList<String>();
	
	@Before
	public void createFile() {
		list.add("apple");
		list.add("mango");
		list.add("orange");
	}
	
	@Test
	public void testSort() {
		TextFile testFile = new TextFile("test");
		testFile.addItem("mango");
		testFile.addItem("apple");
		testFile.addItem("orange");
		testFile.sortAlphabetically();
		for(int index = 0; index < list.size() -1; index++ ) {
			assertEquals(list.get(index), testFile.getItem(index));
		}
	}
	
	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

}
