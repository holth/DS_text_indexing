import java.util.ArrayList;
import java.util.Collections;


/**
 * Class implementing a Trie
 */
public class Trie
{
	public Node root = new Node();
	
	public void addKeyword(String keyword, int parNumber)
	{
		Node parent = root;
		for(int i = 0; i < keyword.length(); i++)
		{
		    char ch = keyword.charAt(i);
		    int charIndex = 0;
		    while(charIndex <= parent.children.size() - 1)
		    {
		    	if(parent.children.get(charIndex).element == ch)
		    		break;
		    	else
		    		charIndex ++;
		    }
		    if(charIndex >= parent.children.size())
		    	parent.children.add(new Node(ch));
		    parent = parent.children.get(charIndex);
		}
		parent.isEnd = true;
		if(parent.pars == null)
			parent.pars = new ArrayList<Integer>();
		parent.pars.add(parNumber);
	}
	

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
			if(!found)
				return null;
		}
		if(!currentNode.isEnd)
			return null;
		else
			return currentNode.pars;
	}
	
	public ArrayList<Integer> handle2Keywords(String kws)
	{
		String[] list = kws.split(" ");
		ArrayList<Integer> para1 = search(list[0]);
		ArrayList<Integer> para2 = search(list[2]);
	
		if(list[1].compareTo("and") == 0)
			return and(para1, para2);
		else
			return or(para1, para2);
	}
	
	public ArrayList<Integer> and(ArrayList<Integer> p1, ArrayList<Integer> p2)
	{
		ArrayList<Integer> paras = new ArrayList<Integer>();
		
		if(p1 == null | p2 == null)
			return null;
		
		for(int i = 0; i < p1.size(); i++)
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
		if(paras.isEmpty())
			return null;
		return paras;
	}
	
	public ArrayList<Integer> or(ArrayList<Integer> p1, ArrayList<Integer> p2)
	{
		ArrayList<Integer> paras = new ArrayList<Integer>();
		
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
	
	public ArrayList<Integer> handle3Keywords(String kws)
	{
		ArrayList<Integer> para1, para2;
		
		int num = kws.split("[\\(||\\)]").length;
		if(num == 1)									//patterns like "good and bad or nice"
		{
			String[] list = kws.split(" ");
			
			para1 = handle2Keywords(list[0] + " " + list[1] + " " + list[2]);
			para2 = search(list[4]);
			
			if(list[3].compareTo("and") == 0)
				return and(para1, para2);
			else
				return or(para1, para2);
		}
		else if(num == 2)								//patterns like "good and (bad or nice)"
		{
			String[] list = kws.split("[\\(||\\)]");		//list pattern [good and , bad or nice]
			
			para2 = handle2Keywords(list[1]);
			String[] newList = list[0].split(" ");
			para1 = search(newList[0]);
			
			if(newList[1].compareTo("and") == 0)
				return and(para1, para2);
			else
				return or(para1, para2);
		}
		else											//patterns like "(good and bad) or nice"
		{
			String[] list = kws.split("[\\(||\\)]");		//list pattern [, good and bad,  or nice]
			
			para1 = handle2Keywords(list[1]);
			String[] newList = list[2].split(" ");
			para2 = search(newList[2]);
			
			if(newList[1].compareTo("and") == 0)
				return and(para1, para2);
			else
				return or(para1, para2);
		}
		
	}
	
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