import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TextFile {
	private String fileName;
	private ArrayList<String> msgList;
	
	public TextFile(String fileName) {
		setFileName(fileName);
		setMsgList(new ArrayList<String>());
	}
	
	/******************Methods*********************/
	
	public void addItem(String message) {
		getMsgList().add(message);
	}

	public ArrayList<String> displayList() {
		return msgList;
	}

	public void deleteItem(int index) {
		getMsgList().remove(index-1);
	}

	public void clearList() {
		getMsgList().clear();
	}
	
	public void sortAlphabetically() {
		Collections.sort(getMsgList());
	}
	
	public ArrayList<String> searchWord(String word) {
		ArrayList<String> result = new ArrayList<String>();
		for(int index = 0; index < getMsgList().size(); index++) {
			if(getMsgList().get(index).matches(".*\\b" + word + "\\b.*")) {
				result.add(getMsgList().get(index));
			}
		}
		return result;
	}
	
	public void saveFile() throws IOException {
		File file = new File(fileName);
		
		if( !file.isFile()) {
			file.createNewFile();
		}
		
		for(int index = 0; index < getMsgList().size(); index++ ) {
			Files.write(Paths.get(fileName), (getMsgList().get(index)+ "\n").getBytes(), StandardOpenOption.APPEND);
		}
	}
	
	/**********Accessors and Mutators*********/
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<String> getMsgList() {
		return msgList;
	}

	public void setMsgList(ArrayList<String> msgArray) {
		this.msgList = msgArray;
	}
	
	public String getItem(int index) {
		return getMsgList().get(index);
	}

	public int getItemSize() {
		return msgList.size();
	}

}
