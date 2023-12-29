import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery 
{
	public String searchKeyword;
	public String url;
	public String content;
	
	public GoogleQuery(String searchKeyword)
	{
		this.searchKeyword = searchKeyword;
		this.url = "http://www.google.com/search?q="+searchKeyword+"電影"+"&oe=utf8&num=20";
	}
	
	private String fetchContent() throws IOException
	{
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null)
		{
			retVal += line;
		}
		return retVal;
	}
	
	public HashMap<String, String> query() throws IOException
	{
		if(content == null)
		{
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		
		
		/* 
		 * some Jsoup source
		 * https://jsoup.org/apidocs/org/jsoup/nodes/package-summary.html
		 * https://www.1ju.org/jsoup/jsoup-quick-start
 		 */
		
		//using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis)
		{
			try 
			{
				 Element linkElement = li.select("a").first();
	                if (linkElement == null) {
	                    continue;
	                }

	                String linkHref = linkElement.attr("href");
	                if (linkHref.startsWith("/url?q=")) {
	                    linkHref = linkHref.substring(7);
	                    int end = linkHref.indexOf("&");
	                    if (end != -1) {
	                        linkHref = linkHref.substring(0, end);
	                    }

	                    // Decode URL
	                    linkHref = java.net.URLDecoder.decode(linkHref, "UTF-8");
	                    // Check if the decoded string is a valid URL
	                    try {
	                        new URL(linkHref);
	                    } catch (MalformedURLException e) {
	                        continue; // Skip this result if the URL is not valid
	                    }
	                } else {
	                    continue; // Skip if not a valid link format
	                }

	                String title = linkElement.text();
	                if (title.isEmpty()) {
	                    continue;
	                }

	                retVal.put(title, linkHref);


			} catch (IndexOutOfBoundsException e) 
			{
//				e.printStackTrace();
			}
		}
		return retVal;
	}
}
