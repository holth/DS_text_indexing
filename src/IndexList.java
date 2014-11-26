import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class implementing a index of a text file
 */
public class IndexList {
	
	// Create an index based on a text file
	public ArrayList<LinkedList<String>> buildIndex(File fileName) throws FileNotFoundException {
		
		ArrayList<LinkedList<String>> index = new ArrayList<LinkedList<String>>();
		
		Scanner textScan = new Scanner(fileName);
		
		String paragraph = "";
		
		while(textScan.hasNextLine()) {
			
			String line = textScan.nextLine();
			
			if(line.isEmpty() && !(paragraph.isEmpty())) {
				// Build a list if an empty line is encountered
				LinkedList<String> list = new LinkedList<String>();
				list = buildList(paragraph);
				index.add(list);
				
				// Clear the paragraph
				paragraph = "";
			} else {
				// Add line to paragraph
				paragraph += line + " ";
			}
			
		}
		
		if(!(paragraph.isEmpty())) {
			// Build a list with the last paragraph if it is not empty
			LinkedList<String> list = new LinkedList<String>();
			list = buildList(paragraph);
			index.add(list);
		}
		
		textScan.close();
		
		return index;
		
	}
	
	// To print the text on screen
	public void printIndex(ArrayList<LinkedList<String>> index) {
		
		for(int i = 0; i < index.size(); i++) {
			
			System.out.print("\nParagraph " + (i + 1) + ": ");
			printList(index.get(i));
			
		}
		
	}
	
	// Build a list based on a line of text
	public LinkedList<String> buildList(String sentence) {
		
		LinkedList<String> list = new LinkedList<String>();
		ArrayList<String> stopwords = new ArrayList<String>();
		
		try {
			// Create a list of stop words
			stopwords = buidStopWords();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Scanner scan = new Scanner(sentence);
		String word = "";
		
		while(scan.hasNext()) {
			
			scan.useDelimiter("[.,!?:;’`“@=&/()_\\[\\]\"\'\\s-]+");
			word = scan.next().toLowerCase(); // Make all words lowercase
			
			// Prevent duplicate, stopwords and words with length of 2 or less
			if(list.contains(word) || stopwords.contains(word) || word.length() < 3) {
				word = "";
			} else {
				list.add(word);
			}
			
		}
		
		scan.close();
		return list;
		
	}

	// Print the content of list on screen
	public void printList(LinkedList<String> list) {
		
		for(int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i));
			
			if( i == list.size() - 1) {
				// Print with new line if last list
				System.out.println(" ");
			} else {
				System.out.print(" > ");
			}
		}
		
	}
	
	// Build a list of stop words based the assets/stopwords.txt file
	private ArrayList<String> buidStopWords() throws FileNotFoundException {
		
		ArrayList<String> stopwords = new ArrayList<String>();
		
		File fileName = new File("assets/stopwords.txt");
		Scanner scan = new Scanner(fileName);
		
		while(scan.hasNext()) {
			stopwords.add(scan.next());
		}
		
		scan.close();
		return stopwords;
		
	}
	
}
