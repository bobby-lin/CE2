import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class UnitTest {
	String[] args = {"test"};
	ArrayList<String> sortTestList = new ArrayList<String>();
	ArrayList<String> searchTestList = new ArrayList<String>();
	
	@Before
	public void createFile() {
		sortTestList .add("apple tea");
		sortTestList .add("mango tea");
		sortTestList .add("orange juice");
		searchTestList.add("apple tea");
		searchTestList.add("mango tea");
	}
	
	@Test
	public void testSort() {
		TextFile testFile = new TextFile("test");
		testFile.addItem("mango tea");
		testFile.addItem("apple tea");
		testFile.addItem("orange juice");
		testFile.sortAlphabetically();
		for(int index = 0; index < sortTestList .size() -1; index++ ) {
			assertEquals(testFile.getItem(index),sortTestList .get(index));
		}
	}
	
	@Test
	public void testSearch() {
		TextFile testFile = new TextFile("test");
		testFile.addItem("mango tea");
		testFile.addItem("apple tea");
		testFile.addItem("orange juice");
		String word = "tea";
		ArrayList<String>result = testFile.searchWord(word);
		for(int index = 0; index < searchTestList.size() -1; index++ ) {
			assertEquals(result.get(index), searchTestList.get(index));
		}
	}

}
