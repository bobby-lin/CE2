import java.io.IOException;
import java.util.*;

public class TextBuddyService {
	private static Scanner scanner = new Scanner(System.in);
	private static TextFile textFile;
	
	public TextBuddyService(String[] args) {
		String fileName = getFileName(args);
		textFile = new TextFile(fileName);
		printWelcomeMsg();
	}

	private void printWelcomeMsg() {
		System.out.println(String.format(Constant.MESSAGE_WELCOME, textFile.getFileName()));
	}

	public void runProgram() throws IOException  {
		System.out.print("command: ");
		System.out.println(executeCommand(scanner.nextLine()));
	}

	public static String executeCommand(String userInput) throws IOException  {
			if (userInput.equals("")) {
				return String.format(Constant.MESSAGE_INVALID_FORMAT, userInput);
			}
			
			String command = getFirstWord(userInput);
	
			switch (command.toLowerCase()) {
				case Constant.add : 	  		
					return addContent(userInput);
				case Constant.display : 	
					return displayAllContent();
				case Constant.delete :  	
					return deleteContent(userInput);
				case Constant.clear :   		
					return clearAllContent();
				case Constant.sort :  	
					return sort();
				case Constant.search :   		
					return search(userInput);
				case Constant.exit : 
					textFile.saveFile();
					System.exit(0);
				default :       		
					throw new Error("Unrecognized command type");
			}
	}
	
	/***************************************************************************/
	/*********************Basic commands in the program******************/
	/***************************************************************************/

	private static String addContent(String userInput) {
		String message = getTextContent(userInput);
		textFile.addItem(message);
		return String.format(Constant.MESSAGE_ADDED, textFile.getFileName(), message);
	}

	private static String displayAllContent() {
		StringBuilder stringBuilder = getContentFromFile(new StringBuilder());
		return stringBuilder.toString();
	}

	private static String deleteContent(String userInput) {
		int indexToDelete = getIndexToDelete(userInput);
		String deletedLine  = textFile.getItem(indexToDelete);
		textFile.deleteItem(indexToDelete);
		return String.format(Constant.MESSAGE_DELETE, textFile.getFileName(), deletedLine );
	}

	private static String clearAllContent() {
		textFile.clearList();
		return String.format(Constant.MESSAGE_CLEAR, textFile.getFileName());
	}
	
	private static String sort() {
		
		return String.format(Constant.MESSAGE_SORT, textFile.getFileName());
	}

	private static String search(String keyword) {
		Boolean check = true;
		if(check == false) {
			return String.format(Constant.MESSAGE_SEARCH_EMPTY, textFile.getFileName());
		}
		return String.format(Constant.MESSAGE_SEARCH, textFile.getFileName());
	}
	
	/*************************************************************************/
	/************************Getters and Setters****************************/
	/*************************************************************************/
	
	private String getFileName(String[] args) {
		String name = null;
		for (String str: args) { 
			name = str; 
		}
		return name;
	}
	
	private static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static int getIndexToDelete(String userInput) {
		return Integer.parseInt(getTextContent(userInput));
	}
	
	private static String getTextContent(String userInput) {
		String[] contentArray = userInput.split(" ", 2);
		return contentArray[1];
	}
	
	private static StringBuilder getContentFromFile(StringBuilder stringBuilder) {
		ArrayList<String> list = textFile.displayList();
		
		if(list.size() == 0) {
			return stringBuilder.append(String.format(Constant.MESSAGE_DISPLAY_EMPTY, textFile.getFileName()));
		}
		
		for(int index = 0; index < list.size(); index++) {
			stringBuilder.append(String.format(Constant.MESSAGE_DISPLAY, index+1, list.get(index)));
		}
		
		return stringBuilder;
	}
}
