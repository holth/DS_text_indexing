import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * Class implementing a Trie
 */
public class Trie
{
	public Node root = new Node();			//root of the trie
	
	//add a keyword and its paragraph number into the trie
	public void addKeyword(String keyword, int parNumber)
	{
		Node parent = root;
		for(int i = 0; i < keyword.length(); i++)
		{
		    char ch = keyword.charAt(i);
		    int charIndex = 0;
		    while(charIndex <= parent.children.size() - 1)
		    {
		    	if(parent.children.get(charIndex).element == ch)	//if char is found
		    		break;
		    	else												//continue search for char
		    		charIndex ++;
		    }
		    if(charIndex >= parent.children.size())				//the char is not yet added
		    	parent.children.add(new Node(ch));
		    parent = parent.children.get(charIndex);	//get the right parent node to continue search for next char
		}
		parent.isEnd = true;
		if(parent.pars == null)					//each external node contains an ArrayList to store paragraph numbers
			parent.pars = new ArrayList<Integer>();
		parent.pars.add(parNumber);				//add the paragraph number
	}
	
	//search a keyword
	public ArrayList<Integer> search(String kw)
	{
		Node currentNode = root;
		for(int i = 0; i < kw.length(); i++)
		{
			char currentChar = kw.charAt(i);
			boolean found = false;
			for(int j = 0; j < currentNode.children.size(); j++)
			{
				if(currentNode.children.get(j).element == currentChar)
				{
					currentNode = currentNode.children.get(j);
					found = true;
					break;
				}
			}
			if(!found)					//return null if no keyword is found
				return null;
		}
		if(!currentNode.isEnd)			//return null if keyword is only a prefix
			return null;
		else
			return currentNode.pars;	//return the ArrayList that contains paragraph numbers
	}
	
	//to handle input pattern like "a and b" or "a or b"
	public ArrayList<Integer> handle2Keywords(String kws)
	{
		String[] list = cleanList(kws.split(" "));
		
		ArrayList<Integer> para1 = search(list[0]);
		ArrayList<Integer> para2 = search(list[2]);
	
		if(list[1].compareTo("and") == 0)				//pattern "a and b"
			return and(para1, para2);
		else if(list[1].compareTo("or") == 0)			//pattern "a or b"
			return or(para1, para2);
		else
		{
			System.out.println("!!!!!!Error: Invalid input!!!!!!!");
			return null;
		}
	}
	
	private String[] cleanList(String[] old)	//this method is to remove all " " elements in a string array 
	{
		ArrayList<String> temp = new ArrayList<String>(Arrays.asList(old));
		//temp.removeAll(Collections.singleton(null));
		temp.removeAll(Collections.singleton(""));
		
		String[] clean = new String[temp.size()];
		for(int i = 0; i < clean.length; i++)
		{
			clean[i] = temp.get(i);
		}
		return clean;
	}
	
	public ArrayList<Integer> and(ArrayList<Integer> p1, ArrayList<Integer> p2)		//do the "and"
	{
		ArrayList<Integer> paras = new ArrayList<Integer>();
		
		if(p1 == null | p2 == null)			//return null if either p1 or p2 is null
			return null;
		
		for(int i = 0; i < p1.size(); i++) 		//only select elements that are both in p1 and p2
		{
			int temp = p1.get(i);
			int index = 0;
			while(index < p2.size())
			{
				if(p2.get(index) == temp)
				{
					paras.add(temp);
					break;
				}
				index ++;
			}
		}
		if(paras.isEmpty())		//return null if there is no intersection
			return null;
		return paras;
	}
	
	public ArrayList<Integer> or(ArrayList<Integer> p1, ArrayList<Integer> p2) 		//do the "or"
	{
		ArrayList<Integer> paras = new ArrayList<Integer>();
		
		//return the other if one is null, else add all the elements that are in either p1 or p2
		if(p1 == null)
			return p2;
		else if(p2 == null)
			return p1;
		for(int i = 0; i < p1.size(); i++)
			paras.add(p1.get(i));
		for(int i = 0; i < p2.size(); i++)
			if(!paras.contains(p2.get(i)))
				paras.add(p2.get(i));
		Collections.sort(paras);
		return paras;
	}
	
	//to handle input that contains three keywords
	public ArrayList<Integer> handle3Keywords(String kws)
	{
		ArrayList<Integer> para1, para2;
		
		int num = kws.split("[\\(||\\)]").length;
		if(num == 1)									//patterns like "good and bad or nice"
		{
			String[] list = cleanList(kws.split(" "));
			
			para1 = handle2Keywords(list[0] + " " + list[1] + " " + list[2]);
			para2 = search(list[4]);
			
			if(list[3].compareTo("and") == 0)
				return and(para1, para2);
			else if(list[3].compareTo("or") == 0)
				return or(para1, para2);
			else
			{
				System.out.println("!!!!!!Error: Invalid input!!!!!!!");
				return null;
			}
		}
		else if(num == 2)								//patterns like "good and (bad or nice)"
		{
			String[] list = kws.split("[\\(||\\)]");		//list pattern [good and , bad or nice]
			
			para2 = handle2Keywords(list[1]);
			String[] newList = cleanList(list[0].split(" "));
			para1 = search(newList[0]);
			
			if(newList[1].compareTo("and") == 0)
				return and(para1, para2);
			else if(newList[1].compareTo("or") == 0)
				return or(para1, para2);
			else
			{
				System.out.println("!!!!!!Error: Invalid input!!!!!!!");
				return null;
			}
		}
		else											//patterns like "(good and bad) or nice"
		{
			String[] list = kws.split("[\\(||\\)]");		//list pattern [, good and bad,  or nice]
			
			para1 = handle2Keywords(list[1]);
			String[] newList = cleanList(list[2].split(" "));
			para2 = search(newList[2]);
			
			if(newList[1].compareTo("and") == 0)
				return and(para1, para2);
			else if(newList[1].compareTo("or") == 0)
				return or(para1, para2);
			else
			{
				System.out.println("!!!!!!Error: Invalid input!!!!!!!");
				return null;
			}
		}
		
	}
	
	/**
	 * Class implementing a node of trie
	 */
	class Node {
		char element;
		ArrayList<Node> children;
		boolean isEnd;
		ArrayList<Integer> pars;
		
		Node() {
			children = new ArrayList<Node>();
			isEnd = false;
		}
		
		Node(char e) {
			element = e;
			children = new ArrayList<Node>();
			isEnd = false;
		}
		
		Node(char e, ArrayList<Node> c, boolean end) {
			element = e;
			children = c;
			isEnd = end;
		}
		
		public String toString() {
			String output = null;
			output = "element: " + element + " || children:";
			for(int i = 0; i <= children.size() - 1; i++)
				output += " " + children.get(i).element;
			return output;
		}
	}
	
}