import java.io.IOException;

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
		WebPage rootPage = new WebPage("http://soslab.nccu.edu.tw/Welcome.html", "Soslab");		
		WebTree tree = new WebTree(rootPage);
		
		//build childnode
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Publications.html","Publication")));
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Projects.html","Projects")));
		tree.root.children.get(1).addChild(new WebNode(new WebPage("https://vlab.cs.ucsb.edu/stranger/", "Stranger")));
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Members.html", "Member")));
		tree.root.addChild(new WebNode(new WebPage("http://www3.nccu.edu.tw/~yuf/course.htm","Course")));
		
            // 創建關鍵字列表（name, weight）
        ArrayList<Keyword> Keywords = new ArrayList<>();
        Keywords.add(new Keyword("金馬獎", 7.0));
        Keywords.add(new Keyword("推薦", 6.0));
        Keywords.add(new Keyword("熱門", 6.0));            
        Keywords.add(new Keyword("獲獎", 5.0));           
        Keywords.add(new Keyword("必看", 4.0));            
        Keywords.add(new Keyword("高分", 3.0));         
        Keywords.add(new Keyword("IMDb", 3.0));

            
        // 計算分數
        tree.setPostOrderScore(Keywords);

        // 顯示結果
        tree.eularPrintTree();   

            
        }
        
    
}
				
		


