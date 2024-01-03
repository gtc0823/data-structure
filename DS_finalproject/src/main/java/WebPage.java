import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;

import org.jsoup.Jsoup;

public class WebPage
{
	public String url;
	public String name;
	public KeywordCounter counter;
	public double score;
	 private ArrayList<WebPage> children;
	 public String content;

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

	public void loadContent()throws IOException {
		Document doc = Jsoup.connect(this.url).get();
		this.content =doc.text();
	}
	
	public void setScore(ArrayList<Keyword> keywords) throws IOException
	{
		score = 0;
		// YOUR TURN
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
