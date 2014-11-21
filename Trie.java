import java.util.ArrayList;

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