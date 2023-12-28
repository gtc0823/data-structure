import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Scanner;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("Type a keyword: ");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
		try {
			String keyword = sc.nextLine(); 
			//String keyword1 = keyword.replaceAll("\\W",""); 
            GoogleQuery googleQuery = new GoogleQuery(keyword);
            HashMap<String, String> searchResults = googleQuery.query();
            
            for (Map.Entry<String, String> entry : searchResults.entrySet()) {
              String title = entry.getKey();
              String url = entry.getValue();
              
              // 創建獨立的 WebTree，以每個搜尋結果為根節點
              WebPage rootPage = new WebPage(url, title);
              WebTree webTree = new WebTree(rootPage);
            
         //  KeywordList key = new KeywordList();
              KeywordCounter counter = new KeywordCounter(url); 
              String rootContent = counter.fetchContent(); // Fetch content of the root URL

              Document rootDoc = Jsoup.parse(rootContent);
              Elements subpageLinks = rootDoc.select("a[href]"); // Find links in the content

              // Add subpages as child nodes to the root node
              for (Element link : subpageLinks) {
                  String subpageTitle = link.text(); 
                  String subpageUrl = link.attr("abs:href"); 

                  WebPage subpage = new WebPage(subpageUrl, subpageTitle);
                  WebNode subpageNode = new WebNode(subpage);
                  webTree.root.addChild(subpageNode);
              }
            webTree.setPostOrderScore(new KeywordList().add());
            webTree.eularPrintTree();
            }   
    } catch (IOException e) {
        e.printStackTrace();
    }
		}

}
}	
