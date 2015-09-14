import java.util.*;

public class TextFile {
	private String fileName;
	private ArrayList<String> msgArray;
	
	public TextFile(String fileName) {
		setFileName(fileName);
		setMsgArray(new ArrayList<String>());
	}
	
	/******************Methods*********************/
	
	public void addItem(String message) {
		getMsgArray().add(message);
	}

	public ArrayList<String> displayList() {
		return msgArray;
	}

	public void deleteItem(int index) {
		getMsgArray().remove(index-1);
	}

	public void clearList() {
		getMsgArray().clear();
	}
	
	public void save() {
		
	}
	
	/**********Accessors and Mutators*********/
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<String> getMsgArray() {
		return msgArray;
	}

	public void setMsgArray(ArrayList<String> msgArray) {
		this.msgArray = msgArray;
	}
	
	public String getItem(int index) {
		return getMsgArray().get(index-1);
	}
}
