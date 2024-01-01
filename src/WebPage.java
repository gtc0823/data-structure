import java.io.IOException;
import java.util.ArrayList;

public class WebPage
{
	public String url;
	public String name;
	public KeywordCounter counter;
	public double score;
	 private ArrayList<WebPage> children;

	public WebPage(String url, String name)
	{
		this.url = url;
		this.name = name;
		this.counter = new KeywordCounter(url);
		this.children = new ArrayList<>();
	}
	
	public void addChild(WebPage childPage) {
        this.children.add(childPage);
    }

	public void setScore(ArrayList<Keyword> keywords) throws IOException
	{
		score = 0;
		// YUR TURN
//		1. calculate the score of this webPage
		for(Keyword keyword : keywords) {
			double pagescore = counter.countKeyword(keyword.name) * keyword.weight;
			score += pagescore;
		}
		/*for (WebPage child : children) {
            child.setScore(keywords);
            score += child.score;
        }*/
		
		
	}
}
