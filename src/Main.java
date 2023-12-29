import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		ArrayList<WebTree> trees = new ArrayList<>();
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
            	String result = entry.getKey();
                 String url = entry.getValue();
                Keywords.find(url); // find the LCS with this result
                
                WebPage rootPage = new WebPage(url, result);
                WebTree tree = new WebTree(rootPage);
          
                
                tree.setPostOrderScore(Keywords.getlst());
                trees.add(tree);
            }
            Collections.sort(trees, new Comparator<WebTree>() {
                @Override
                public int compare(WebTree tree1, WebTree tree2) {
                    return Double.compare(tree2.root.nodeScore, tree1.root.nodeScore);
                }
            });
            
            for (WebTree tree1 : trees) {
                tree1.eularPrintTree(); // 打印每棵排序後的樹
            }
            
            
    } catch (IOException e) {
        e.printStackTrace();
    }
		}

}
}	
