import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
		this.url = "http://www.google.com/search?q="+searchKeyword+"電影"+"&oe=utf8&num=10";
	}
	
	private String fetchContent() throws IOException {
	    String retVal = "";

	    URL u = new URL(url);
	    URLConnection conn = u.openConnection();
	    // set HTTP header
	 // 在 fetchContent 方法中的 URLConnection 前加上以下代碼
	    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
	    conn.setRequestProperty("Referer", "http://www.google.com/");
	    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


	    InputStream in = conn.getInputStream();

	    try (InputStreamReader inReader = new InputStreamReader(in, "utf-8");
	         BufferedReader bufReader = new BufferedReader(inReader)) {

	        String line = null;

	        while ((line = bufReader.readLine()) != null) {
	            retVal += line;
	        }
	    }

	    return retVal;
	}

	
	public HashMap<String, String> query() throws IOException {
	    if (content == null) {
	        content = fetchContent();
	    }

	    HashMap<String, String> retVal = new HashMap<String, String>();

	    Document doc = Jsoup.parse(content);

	    Elements lis = doc.select("div");
	    lis = lis.select(".kCrYT");

	    for (Element li : lis) {
	        try {
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
	                    URL url = new URL(linkHref);
	                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	                    int responseCode = urlConnection.getResponseCode();

	                    if (responseCode == HttpURLConnection.HTTP_OK) {
	                        retVal.put(linkElement.text(), linkHref);
	                    } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
	                        // HTTP 403 Forbidden
	                        System.out.println("HTTP 403 Forbidden for URL: " + linkHref);
	                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
	                        // HTTP 400 Bad Request
	                        System.out.println("HTTP 400 Bad Request for URL: " + linkHref);
	                    } else {
	                        // 其他錯誤處理
	                        System.out.println("Error: " + responseCode + " for URL: " + linkHref);
	                    }
	                } catch (MalformedURLException e) {
	                    // Handle malformed URL
	                }
	            } else {
	                continue; // Skip if not a valid link format
	            }

	        } catch (Exception e) {
	            // Handle other exceptions
	            e.printStackTrace();
	        }
	    }
	    return retVal;
	}



}


