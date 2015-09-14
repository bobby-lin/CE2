import java.util.*;

public class TextBuddyService {
	private static final String MESSAGE_WELCOME = "\nWelcome to TextBuddy. %1$s is ready for use\n";
	private static final String MESSAGE_ADDED = "\nadded to %1$s: \"%2$s\"\n";
	private static final String MESSAGE_DISPLAY = "\n%1$s. %2$s\n";
	private static final String MESSAGE_DISPLAY_EMPTY = "\n%1$s is empty\n";
	private static final String MESSAGE_DELETE = "\ndeleted from %1$s: \"%2$s\"\n";
	private static final String MESSAGE_CLEAR = "\nall content deleted from %1$s\n";
	private static final String MESSAGE_INVALID_FORMAT = "\ninvalid command format: %1$s\n";
	
	private static final String add = "add";
	private static final String display = "display";
	private static final String delete = "delete";
	private static final String clear = "clear";
	private static final String exit = "exit";
	
	private static Scanner scanner = new Scanner(System.in);
	private static TextFile textFile;
	
	public TextBuddyService(String[] args) {
		String fileName = getFileName(args);
		textFile = new TextFile(fileName);
		printWelcomeMsg();
	}

	private void printWelcomeMsg() {
		System.out.println(MESSAGE_WELCOME);
	}

	public void runProgram()  {
		System.out.print("command: ");
		System.out.println(executeCommand(scanner.nextLine()));
	}

	public static String executeCommand(String userInput)  {
			if (userInput == "") {
				return String.format(MESSAGE_INVALID_FORMAT, userInput);
			}
			
			String command = getFirstWord(userInput);
	
			switch (command.toLowerCase()) {
				case add : 	  		
					return addContent(userInput);
				case display : 	
					return displayAllContent();
				case delete :  	
					return deleteContent(userInput);
				case clear :   		
					return clearAllContent();
				case exit : 
					textFile.save();
					System.exit(0);
				default :       		
					throw new Error("Unrecognized command type");
			}
	}
	
	/***************************************************************************/
	/***************Basic commands methods in the program**************/
	/***************************************************************************/

	private static String addContent(String userInput) {
		String message = getTextContent(userInput);
		textFile.addItem(message);
		return String.format(MESSAGE_ADDED, textFile.getFileName(), message);
	}

	private static String displayAllContent() {
		StringBuilder stringBuilder = getContentFromFile(new StringBuilder());
		return stringBuilder.toString();
	}

	private static String deleteContent(String userInput) {
		int indexToDelete = getIndexToDelete(userInput);
		String deletedLine  = textFile.getItem(indexToDelete);
		textFile.deleteItem(indexToDelete);
		return String.format(MESSAGE_DELETE, textFile.getFileName(), deletedLine );
	}

	private static String clearAllContent() {
		textFile.clearList();
		return String.format(MESSAGE_CLEAR, textFile.getFileName());
	}
	
	/*******************************************************************/
	/***********************Getters and Setters***********************/
	/*******************************************************************/
	
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
		
		if(list.size()==0) {
			return stringBuilder.append(String.format(MESSAGE_DISPLAY_EMPTY, textFile.getFileName()));
		}
		
		for(int index = 0; index < list.size(); index++) {
			stringBuilder.append(String.format(MESSAGE_DISPLAY, index+1, list.get(index)));
		}
		
		return stringBuilder;
	}
}
