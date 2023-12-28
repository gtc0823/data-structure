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

public class Main_Stage3
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
            
            KeywordList key = new KeywordList();
			key.add();
			
            ArrayList<WebPage> webPages = new ArrayList<>();
            for (String title : searchResults.keySet()) {
                String url = searchResults.get(title);
                WebPage webPage = new WebPage(url, title);
                webPages.add(webPage);
            }
            
            for (WebPage rootPage : webPages) {
                GoogleQuery subPageQuery = new GoogleQuery(rootPage.name); // 使用網頁標題作為關鍵字搜索
                HashMap<String, String> subPageResults = subPageQuery.query();
                for (String subTitle : subPageResults.keySet()) {
                    String subUrl = subPageResults.get(subTitle);
                    WebPage subPage = new WebPage(subUrl, subTitle);
                    rootPage.addChild(subPage); // 將子網頁添加為根節點的子節點

			
                    lcs lcs = new lcs();
                    lcs.find(subTitle);
                    for (Keyword derivedKeyword : lcs.lst()) {
                        key.keywords.add(derivedKeyword);
                    }
                }
            }
            
            for (WebPage rootPage : webPages) {
                WebTree webTree = new WebTree(rootPage);
                webTree.setPostOrderScore(key.keywords);
                webTree.eularPrintTree();
                // 可以在這裡進行其他操作，如節點排名等
            }
            

    } catch (IOException e) {
        e.printStackTrace();
    }
		}
}
}
		
 

            
        
        
    

				
		
