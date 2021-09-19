import java.io.*;
import java.util.*;

/**
 * This is a class that asks the user for the name of a ".lex" or ".set" file.
 * If the file is a ".lex" file, it saves it as a ".set". If it is a ".set" file
 * it reads it and interacts with the user.
 * 
 * @author Ben Scarbrough
 * @Version 1.0
 *
 */
class WordChecker2 {

	/**
	 * A private method that attempts to load a ".lex" file.
	 * 
	 * @param lexFileName
	 *            The name of a ".lex" file.
	 * @return A tree set.
	 */
	private static TreeSet<String> loadLexFile(String lexFileName) {
		TreeSet<String> set = new TreeSet<>();
		try {
			Scanner file_in = new Scanner(new File(lexFileName));
			while (file_in.hasNext()) {
				String line = file_in.nextLine();
				set.add(line);
			}
			file_in.close();
		} catch (IOException exception) {
			System.out.println(exception.toString());
			System.exit(0);
		}
		return set;
	}

	/**
	 * A method that saves a new file.
	 * 
	 * @param treeSet
	 *            The tree set containing the data.
	 * @param outputName
	 *            The name of the file.
	 */
	private static void saveTreeFile(TreeSet<String> treeSet, String outputName) {
		try {
			FileOutputStream fileStream = new FileOutputStream(outputName);
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
			objectStream.writeObject(treeSet);
			objectStream.close();
		} catch (IOException exception) {
			System.out.println(exception.toString());
		}
	}

	/**
	 * A private method that attempts to load a ".set" file.
	 * 
	 * @param setFileName
	 *            The name of a ".set" file.
	 * @return A tree set.
	 */
	@SuppressWarnings("unchecked")
	private static TreeSet<String> loadSetFile(String setFileName) {
		TreeSet<String> set = new TreeSet<>();
		try {
			FileInputStream fileStream = new FileInputStream(setFileName);
			ObjectInputStream inputStream = new ObjectInputStream(fileStream);
			set = (TreeSet<String>) inputStream.readObject();
			inputStream.close();
		} catch (IOException exception) {
			System.out.println(exception.toString());
			System.exit(0);
		} catch (ClassNotFoundException exception) {
			System.out.println(exception.toString());
		}
		return set;
	}

	/**
	 * A method that interacts with the user.
	 */
	private static void queryUser() {
		Scanner scan = new Scanner(System.in);
		String fileName;
		String name;
		System.out.println("Welcome to the Word Checker 2.0!");
		System.out.println("Please enter a .lex or .set file: ");
		while (true) {
			String nextLine = scan.nextLine();
			if (!nextLine.endsWith("lex") && !nextLine.endsWith("set")) {
				System.out.println("Please enter a .lex or .set file: ");
			} else if (nextLine.endsWith("lex") || nextLine.endsWith("set")) {
				fileName = nextLine;
				name = nextLine.substring(0, nextLine.length() - 4);
				break;
			}
		}
		if (fileName.endsWith("lex")) {
			TreeSet<String> set = loadLexFile(fileName);
			System.out.println("Loading file " + "\"" + fileName + "\"" + ", which contains " + set.size() + " words.");
			System.out.println("Saving file " + "\"" + name + ".set" + "\"" + "...done.");
			saveTreeFile(set, name += ".set");
			System.out.println("Goodbye!");
		} else if (fileName.endsWith("set")) {
			TreeSet<String> set = loadSetFile(fileName);
			System.out.println("Loading file " + "\"" + fileName + "\"" + ", which contains " + set.size() + " words.");
			System.out.println("Please enter a word, or hit enter to quit.");
			while (true) {
				String nextLine = scan.nextLine();
				if (nextLine.length() == 0) {
					System.out.println("Goodbye!");
					scan.close();
					break;
				} else if (set.contains(nextLine))
					System.out.println("\"" + nextLine + "\"" + " is a valid word.");
				else
					System.out.println("\"" + nextLine + "\"" + " is NOT a valid word.");
			}
		}
	}

	// Main method
	public static void main(String[] args) {
		queryUser();
	}
}
