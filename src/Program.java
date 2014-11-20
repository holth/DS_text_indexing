import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		IndexList indexList = new IndexList();
		
		Scanner reader = new Scanner(System.in);
		
		System.out.println("Enter the file name (e.g. \"The_State_of_Data_Final.txt\"):");
		
		File fileName = new File(reader.next());
		System.out.println("-------------------------");
		
		ArrayList<LinkedList<String>> index = new ArrayList<LinkedList<String>>();
		
		index = indexList.buildIndex(fileName);
		
		indexList.printIndex(index);
		
	}

}
