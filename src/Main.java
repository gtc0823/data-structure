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
		KeywordList Keywords = new KeywordList();
 		Keywords.add(new Keyword("金馬獎", 100.0));
 		Keywords.add(new Keyword("最佳", 8.0));
 		Keywords.add(new Keyword("推薦", 6.0));
 		Keywords.add(new Keyword("熱門", 6.0));
 		Keywords.add(new Keyword("獲獎", 5.0));
 		Keywords.add(new Keyword("必看", 4.0));
 		Keywords.add(new Keyword("高分", 3.0));
 		Keywords.add(new Keyword("IMDb", 3.0));
 		
		System.out.println("Type a keyword: ");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
		try {
			String keyword = sc.nextLine(); 
            GoogleQuery googleQuery = new GoogleQuery(keyword);
            HashMap<String, String> searchResults = googleQuery.query();
            
            for (Map.Entry<String, String> entry : searchResults.entrySet()) {
            	String result = entry.getValue();
                 String url = entry.getKey();
                Keywords.find(result); // find the LCS with this result
                
                WebPage rootPage = new WebPage(url, result);
                WebTree tree = new WebTree(rootPage);
                
                ArrayList<WebNode> sortedNodes = tree.getSortedNodes();

                
                tree.setPostOrderScore(Keywords.getlst());

        		// 顯示結果
        		tree.eularPrintTree();
            }
            
            
    } catch (IOException e) {
        e.printStackTrace();
    }
		}

}
}	
