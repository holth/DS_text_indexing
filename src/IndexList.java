import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class IndexList {
	
	public ArrayList<LinkedList<String>> buildIndex(File fileName) throws FileNotFoundException {
		
		ArrayList<LinkedList<String>> index = new ArrayList<LinkedList<String>>();
		
		Scanner textScan = new Scanner(fileName);
		
		String paragraph = "";
		
		while(textScan.hasNextLine()) {
			
			String line = textScan.nextLine();
			
			if(line.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list = buildList(paragraph);
				index.add(list);
				paragraph = "";
			} else {
				paragraph += line + " ";
			}
			
		}
		LinkedList<String> list = new LinkedList<String>();
		list = buildList(paragraph);
		index.add(list);
		
		textScan.close();
		
		return index;
		
	}
	
	public void printIndex(ArrayList<LinkedList<String>> index) {
		
		for(int i = 0; i < index.size(); i++) {
			
			System.out.println("\nParagraph " + (i + 1) + ":");
			printList(index.get(i));
			
		}
		
	}
	
	public LinkedList<String> buildList(String sentence) {
		
		LinkedList<String> list = new LinkedList<String>();
		ArrayList<String> stopwords = new ArrayList<String>();
		
		try {
			stopwords = buidStopWords();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Scanner scan = new Scanner(sentence);
		String word = "";
		
		while(scan.hasNext()) {
			
			scan.useDelimiter("[.,!?:;’@/\\[\\]\"\'\\s]+");
			word = scan.next().toLowerCase();
			
			// Prevent duplicate and stopwords
			if(list.contains(word) || stopwords.contains(word) || word.length() < 3) {
				word = "";
			} else {
				list.add(word);
			}
			
		}
		
		scan.close();
		return list;
		
	}

	public void printList(LinkedList<String> list) {
		
		for(int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i));
			
			if( i == list.size() - 1) {
				System.out.println(" ");
			} else {
				System.out.print(" > ");
			}
		}
		
	}
	
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
