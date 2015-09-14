/**
 * This class is used to manipulate text in a textfile through a CLI (Command Line Interface) program 
 * A command that is not {add, display, delete, clear or exit} are considered to be invalid.
 * Here is an example of an interaction with the program and a textfile named 'mytestfile':

			Welcome to TextBuddy. mytestfile.txt is ready for use

			Enter command: add little brown fox

			added to mytestfile.txt: "little brown fox"

			Enter command: display

			1. little brown fox

			Enter command: add jumped over the moon

			Enter command: delete 1

			deleted from mytestfile.txt: "little brown fox"

			Enter command: display

			1. jumped over the moon

			Enter command: clear

			all content deleted from mytestfile.txt

			Enter command: exit

 * @author Lin Jiahao Bobby
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TextBuddy {

	private static final String MESSAGE_WELCOME = "\nWelcome to TextBuddy. %1$s is ready for use\n";
	private static final String MESSAGE_ADDED = "\nadded to %1$s: \"%2$s\"\n";
	private static final String MESSAGE_DISPLAY = "\n%1$s. %2$s\n";
	private static final String MESSAGE_DISPLAY_EMPTY = "\n%1$s is empty\n";
	private static final String MESSAGE_DELETE = "\ndeleted from %1$s: \"%2$s\"\n";
	private static final String MESSAGE_CLEAR = "\nall content deleted from %1$s\n";
	private static final String MESSAGE_INVALID_FORMAT = "\ninvalid command format: %1$s\n";

	//These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, INVALID, EXIT
	};

	//These are the possible command types in String version
	private static final String add = "add";
	private static final String display = "display";
	private static final String delete = "delete";
	private static final String clear = "clear";
	private static final String exit = "exit";

	//This charset is used during buffer-reading
	private static final String charset = "UTF-8";

	private static Scanner scanner = new Scanner(System.in);

	//This is used to indicate name of textfile processed
	private static String fileName = "";

	public static void main(String[] args)  {
		bootProgram(args);
		while(true) {
			runLoop();
		}
	}

	private static void bootProgram(String[] args)  {
		setFileName(args);
		findFile();
		printWelcomeMsg();	
	}

	private static void runLoop()  {
		System.out.print("command: ");
		System.out.println(executeCommand(scanner.nextLine()));
	}

	public static String executeCommand(String userInput)  {
			if (isCommandEmpty(userInput)) {
				return String.format(MESSAGE_INVALID_FORMAT, userInput);
			}
			
			String commandTypeString = getFirstWord(userInput);
			COMMAND_TYPE commandType = determineCommandType(commandTypeString);
	
			switch (commandType) {
				case ADD : 	  		
					return addContent(userInput);
				case DISPLAY : 	
					return displayAllContent();
				case DELETE :  	
					return deleteContent(userInput);
				case CLEAR :   		
					return clearAllContent();
				case INVALID : 	
					return String.format(MESSAGE_INVALID_FORMAT, userInput);
				case EXIT :    		
					System.exit(0);
				default :       		
					throw new Error("Unrecognized command type");
			}
	}

	private static COMMAND_TYPE determineCommandType(String command) {
		if (isFileEmpty(command)) {
			throw new Error("command type string cannot be null!");
		}

		if (equalsCommand(command, add)) { 
			return COMMAND_TYPE.ADD;
		}
		else if (equalsCommand(command, display)) { 
			return COMMAND_TYPE.DISPLAY;
		}
		else if (equalsCommand(command, delete)) { 
			return COMMAND_TYPE.DELETE;
		}
		else if (equalsCommand(command, clear)) { 
			return COMMAND_TYPE.CLEAR;
		}
		else if (equalsCommand(command, exit)) { 
			return COMMAND_TYPE.EXIT;
		}
		else { 
			return COMMAND_TYPE.INVALID;
		}
	}

	/**********************************************************************
	 ***************Basic commands methods in the program**************
	 ***********************************************************************/

	private static String addContent(String userInput) {
		try {
			String message = getTextContent(userInput);
			writeToFile(message);
			return String.format(MESSAGE_ADDED, fileName, message);
		} catch (IOException e) {
			return "Caught IOException: " + e.getMessage();
		}
	}

	private static String displayAllContent() {
		try {
			StringBuilder sb = copyContentFromFile(new StringBuilder());
			return sb.toString();
		} catch (IOException e) {
			return "Caught IOException: " + e.getMessage();
		}
	}

	private static String deleteContent(String userInput) {
		try {
			int lineNumToBeDeleted = getLineNumToDelete(userInput);
			String lineDeleted  = getDeletedLine(lineNumToBeDeleted);
			return String.format(MESSAGE_DELETE, fileName, lineDeleted );
		} catch (IOException e) {
			return "Caught IOException: " + e.getMessage();
		}
	}

	private static String clearAllContent() {
		try {
			Files.write(Paths.get(fileName), (new String()).getBytes());
			return String.format(MESSAGE_CLEAR, fileName);
		} catch (IOException e) {
			return "Caught IOException: " + e.getMessage();
		}
	}

	/***********************************************************************
	 *********Miscellaneous  methods used by Command Methods*********
	 ***********************************************************************/
	
	private static boolean equalsCommand(String commandTypeString, String command) {
		return commandTypeString.equalsIgnoreCase(command);
	}
	
	private static void setFileName(String[] args) {
		for (String str: args) { 
			fileName = str; 
		}
	}

	private static void printWelcomeMsg() {
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
	}

	private static void findFile()  {
			File file = new  File(fileName);
			if( !file.isFile() ) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	private static boolean isCommandEmpty(String userInput) {
		return userInput.trim().equals("");
	}

	private static boolean isFileEmpty(String str) {
		return str == null;
	}

	private static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}

	private static String getTextContent(String userInput) {
		String[] contentArr = userInput.split(" ", 2);
		return contentArr[1];
	}

	private static int getLineNumToDelete(String userInput) {
		return Integer.parseInt(getTextContent(userInput));
	}

	private static String getDeletedLine(int lineNumToDelete)
			throws IOException {
		File file = new File(fileName), temp = createTempFile(file);
		BufferedReader br = readFromCurrentFile();
		PrintWriter pw = writeToTempFile(temp);
		String lineDeleted = "", line;
		for (int index = 1; (line = br.readLine()) != null; index++) {
			if(index == lineNumToDelete){
				lineDeleted = line;
				continue;
			};
			pw.println(line);
		}
		closeIO(br, pw);
		file.delete();
		temp.renameTo(file);
		return lineDeleted;
	}

	// This method creates a BufferedReader to read text from current file
	private static BufferedReader readFromCurrentFile() 
			throws UnsupportedEncodingException, FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));
	}

	// This method creates a PrintWriter for writing text to temp file
	private static PrintWriter writeToTempFile(File temp) 
			throws UnsupportedEncodingException, FileNotFoundException {
		return new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
	}

	private static File createTempFile(File file) throws IOException {
		return File.createTempFile("temp", ".txt", file.getParentFile());
	}

	private static void writeToFile(String message) throws IOException {
		Files.write(Paths.get(fileName), (message + "\n").getBytes(), StandardOpenOption.APPEND);
	}

	private static void closeIO(BufferedReader reader, PrintWriter writer) throws IOException {
		reader.close();
		writer.close();
	}

	private static StringBuilder copyContentFromFile(StringBuilder sb) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		BufferedReader br = readFromCurrentFile();
		String strLine = br.readLine();

		if(isFileEmpty(strLine)) {
			br.close();
			return sb.append(String.format(MESSAGE_DISPLAY_EMPTY, fileName));
		}

		sb = copyByLines(sb, br, strLine);
		br.close();
		return sb;
	}

	// This method copies text in the file by lines
	private static StringBuilder copyByLines(StringBuilder sb, BufferedReader br, String strLine) throws IOException {
		for(int count = 1; strLine != null; count++) {
			sb.append(String.format(MESSAGE_DISPLAY, count, strLine));
			strLine = br.readLine();
		}
		return sb;
	}

}

class Message {
	
}