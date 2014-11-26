import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		IndexList indexList = new IndexList();
		
		Scanner reader = new Scanner(System.in);
		
		System.out.print("Enter the file name (e.g. \"The_State_of_Data_Final.txt\"): ");
		
		File fileName = new File(reader.next());

		System.out.println("-------------------------");
		
		ArrayList<LinkedList<String>> index = new ArrayList<LinkedList<String>>();
		
		index = indexList.buildIndex(fileName);
		
		indexList.printIndex(index);
		
		Trie trie = new Trie();
		for(int i = 0; i <= index.size() - 1; i++)
		{
			int j = 0;
			while(j <= index.get(i).size() - 1)
			{
				trie.addKeyword(index.get(i).get(j), i);
				j++;
			}
		}
		

		reader.nextLine();
		ArrayList<Integer> paragraphs;
		while(true)
		{
			System.out.println("\n-------------------------");
			System.out.print("Enter keyword (press \"q\" to quit): ");
			
			String keyword = reader.nextLine().toLowerCase();
			if(keyword.equals("q"))
				break;
			
			int num = keyword.split("and | or").length;
			if(num == 1)								//one keyword
				paragraphs = trie.search(keyword);
			else if(num == 2)							//two keywords
			{
				paragraphs = trie.handle2Keywords(keyword);
			}
			else										//three keywords
			{
				paragraphs = trie.handle3Keywords(keyword);
			}
			
			
			if(paragraphs != null)
			{
				System.out.println(keyword + " found at:");;
				for(int i = 0; i < paragraphs.size(); i++)
					System.out.println("			Paragraph " + (paragraphs.get(i) + 1));
			}
			else
				System.out.println(keyword + " NOT found!");
		}
		
		reader.close();
		System.out.println("Program ends!");
	}

}
