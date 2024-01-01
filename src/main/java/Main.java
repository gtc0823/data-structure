

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<WebTree> trees = new ArrayList<>();
		KeywordList Keywords = new KeywordList();
 		Keywords.add(new Keyword("金馬獎", 100.0));
 		Keywords.add(new Keyword("電影", 30.0));
 		Keywords.add(new Keyword("最佳", 8.0));
 		Keywords.add(new Keyword("推薦", 6.0));
 		Keywords.add(new Keyword("熱門", 6.0));
 		Keywords.add(new Keyword("劇情", 6.0));
 		Keywords.add(new Keyword("獲獎", 5.0));
 		Keywords.add(new Keyword("票房", 4.0));
 		Keywords.add(new Keyword("高分", 3.0));
 		Keywords.add(new Keyword("上映", 3.0));
 		
 		/*System.out.println("Type a keyword: ");
		Scanner sc = new Scanner(System.in);*/
 		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if(request.getParameter("keyword")== null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("Search.jsp").forward(request, response);
			return;
		}
		
		
		try {
			
			
			
            GoogleQuery googleQuery = new GoogleQuery(request.getParameter("keyword"));
            HashMap<String, String> searchResults = googleQuery.query();
            
            for (Map.Entry<String, String> entry : searchResults.entrySet()) {
            	String result = entry.getKey();
                String url = entry.getValue();
                
                //Keywords.find(url); // find the LCS with this result

                WebPage rootPage = new WebPage(url, result);
                WebTree tree = new WebTree(rootPage);
                SubUrl subUrl = new SubUrl(url);
                ArrayList<String> subUrls = subUrl.getResults();

                //子網頁的部分
                for (String childUrl : subUrls) {
                    WebPage subPage = new WebPage(childUrl, "SubPage");
                    tree.root.addChild(new WebNode(subPage));
                    // 只加入一個子網頁，怕跑太久
                    break;
                }
                
          
                
                tree.setPostOrderScore(Keywords.getlst());
                trees.add(tree);
            }
            
            quickSort(trees, 0, trees.size()-1);
            
            String[][] s = new String[trees.size()][2];
            int num = 0;
            for(WebTree tree:trees) {
            	String name = tree.root.webPage.name;
    		    String url = tree.root.webPage.url;
    		    s[num][0] = name;
    		    s[num][1] = url;
    		    num++;
            }
            request.setAttribute("query", s);
            request.getRequestDispatcher("SearchResult.jsp").forward(request, response); 
            
            /*for (WebTree tree1 : trees) {
                //tree1.eularPrintTree(); // 打印每棵排序後的樹
            	
            }*/
            
            
		} catch (IOException e) {
        e.printStackTrace();
		}
		

	}
		public static void quickSort(ArrayList<WebTree> arr, int low, int high) {
		    if (low < high) {
		        int pi = partition(arr, low, high);
	
		        quickSort(arr, low, pi-1);
		        quickSort(arr, pi+1, high);
		    }
		}
	
		public static int partition(ArrayList<WebTree> arr, int low, int high) {
		    WebTree pivot = arr.get(high);
		    int i = (low-1);
		    for (int j=low; j<high; j++) {
		        if (arr.get(j).root.nodeScore > pivot.root.nodeScore) {
		            i++;
	
		            Collections.swap(arr, i, j);
		        }
		    }
	
		    Collections.swap(arr, i+1, high);
	
		    return i+1;
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
