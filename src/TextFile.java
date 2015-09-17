import java.io.*;
import java.nio.file.*;
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
	
	public void sortAlphabetically() {
		Collections.sort(getMsgArray());
	}
	
	public ArrayList<String> searchWord(String word) {
		return null;
	}
	
	public void saveFile() throws IOException {
		File file = new File(fileName);
		
		if( !file.isFile()) {
			file.createNewFile();
		}
		
		for(int index = 0; index < getMsgArray().size(); index++ ) {
			Files.write(Paths.get(fileName), (getMsgArray().get(index)+ "\n").getBytes(), StandardOpenOption.APPEND);
		}
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
		return getMsgArray().get(index);
	}

	public int getItemSize() {
		return msgArray.size();
	}

}
