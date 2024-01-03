

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
		Keywords.add(new Keyword("金馬獎", 10.0));
 		Keywords.add(new Keyword("台灣", 30.0));
		Keywords.add(new Keyword("國片", 20.0));
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
			String inputWord = request.getParameter("keyword");
			if(!containsChineseCharacters(inputWord)) {
				Translator translator = new Translator();
				inputWord=inputWord.toUpperCase();
				inputWord = translator.translate("", "zh-TW", inputWord);
				System.out.println("input keyword is translated");
			}
			
			
			
            GoogleQuery googleQuery = new GoogleQuery(inputWord);
            HashMap<String, String> searchResults = googleQuery.query();
            
            for (Map.Entry<String, String> entry : searchResults.entrySet()) {
            	String result = entry.getKey();
                String url = entry.getValue();
                
                //Keywords.find(url); // find the LCS with this result

                WebPage rootPage = new WebPage(url, result);
                rootPage.loadContent();
                Keywords.find(rootPage.content);
                WebTree tree = new WebTree(rootPage);
                System.out.println("Running...");//確定程式有在跑
                SubUrl subUrl = new SubUrl(url);
                ArrayList<String> subUrls = subUrl.getResults();

                //子網頁的部分
                int count = 0;
                for (String childUrl : subUrls) {
                	if(count>=10) {
                		break;
                	}
                    WebPage subPage = new WebPage(childUrl, "SubPage");
                    tree.root.addChild(new WebNode(subPage));
                    // 只加入一個子網頁，怕跑太久
                    count++;
                   
                }
                
          
                
                tree.setPostOrderScore(Keywords.getlst());
                trees.add(tree);
            }
            
            quickSort(trees, 0, trees.size()-1);
            /*Collections.sort(trees, new Comparator<WebTree>() {
            	@Override
            	public int compare(WebTree tree1,WebTree tree2) {
            		return Double.compare(tree2.root.nodeScore, tree1.root.nodeScore);
            	}
            });*/
            
            
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
            
            for (WebTree tree1 : trees) {
                tree1.eularPrintTree(); // 打印每棵排序後的樹
            	
            }
            
            
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
		public static boolean containsChineseCharacters(String input) {
	        // 使用正则表达式匹配中文字符
	        String regex = "[\u4e00-\u9fa5]";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(input);

	        // 查找是否有匹配的中文字符
	        return matcher.find();
	    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
