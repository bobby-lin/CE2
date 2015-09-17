import java.io.*;
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
		
		FileWriter fileWriter = new FileWriter(file);
		String newLine = System.getProperty("line.separator");
		
		for(int index = 0; index < getMsgList().size(); index++ ) {
			fileWriter.write( getMsgList().get(index) + newLine);
		}
		
		fileWriter.close();
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
