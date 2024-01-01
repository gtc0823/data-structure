import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class WebTree
{
	public WebNode root;

	public WebTree(WebPage rootPage)
	{
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException
	{
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException
	{
		// YOUR TURN
		// 3. compute the score of children nodes via post-order, then setNodeScore for
		// startNode
		for(WebNode childs : startNode.children) {
			setPostOrderScore(childs,keywords);
		}
		startNode.setNodeScore(keywords);

	}
	
	public static class NodeScoreComparator implements Comparator<WebNode> {
	    public int compare(WebNode node1, WebNode node2) {
	        return Double.compare(node2.nodeScore, node1.nodeScore); // Descending order
	    }
	}
	
	public ArrayList<WebNode> getSortedNodes() {
	    ArrayList<WebNode> nodes = new ArrayList<>();
	    collectNodes(root, nodes);
	    nodes.sort(new NodeScoreComparator());
	    return nodes;
	}

	private void collectNodes(WebNode node, ArrayList<WebNode> nodes) {
	    if (node == null) return;
	    nodes.add(node);
	    for (WebNode child : node.children) {
	        collectNodes(child, nodes);
	    }
	}
	
	
	public void eularPrintTree()
	{
		//eularPrintTree(root);
		System.out.println(root.webPage.url + " : " + root.webPage.name);
	}

	private void eularPrintTree(WebNode startNode)
	{
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));

		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);
		
		// YOUR TURN
		// 4. print child via pre-order
		for (WebNode childs : startNode.children)
	    {
	        eularPrintTree(childs);
	    }
		
		System.out.print(")");

		if (startNode.isTheLastChild())
			System.out.print("\n" + repeat("\t", nodeDepth - 2));
	}

	private String repeat(String str, int repeat)
	{
		String retVal = "";
		for (int i = 0; i < repeat; i++)
		{
			retVal += str;
		}
		return retVal;
	}
}
