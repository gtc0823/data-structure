import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.SSLHandshakeException;

public class KeywordCounter {
	private String urlStr;
    private String content;
    
    public KeywordCounter(String urlStr){
    	this.urlStr = urlStr;
    }
    
    private String fetchContent() throws IOException,FileNotFoundException{
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		
		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
            int responseCode = httpConn.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				String retVal = "";
			
				String line = null;
				
				while ((line = br.readLine()) != null){
				    retVal = retVal + line + "\n";
				}
			
				return retVal;
			
			}else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                System.out.println("HTTP 403 Forbidden for URL: " + urlStr);
                // 可以在這裡進行其他處理，如跳過或記錄錯誤信息
            } else {
                // 其他 HTTP 錯誤處理
                System.out.println("HTTP Error: " + responseCode + " for URL: " + urlStr);
                // 可以在這裡進行其他處理，如跳過或記錄錯誤信息
            }
		}catch(FileNotFoundException e) {
				System.out.println("文件未找到: " + e.getMessage());
			}
		catch(java.net.UnknownHostException e) {
			System.out.println("Unknown host: " + e.getMessage());
		}
		catch(SSLHandshakeException e) {
			System.out.println("SSL exception: " + e.getMessage());
		}
		return null;
			
	    }
    
    
    public int countKeyword(String keyword) throws IOException{
		if (content == null){
		    content = fetchContent();
		}
		
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:
		if(content == null) {
			return 0;
		}
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
	
		int retVal = 0;
		int fromIdx = 0;
		int found = -1;
	
		while ((found = content.indexOf(keyword, fromIdx)) != -1){
		    retVal++;
		    fromIdx = found + keyword.length();
		}
	
		return retVal;
    }
}