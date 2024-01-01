import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SubUrl {
    private String rootUrl;
    private String content;

    public SubUrl(String rootUrl) {
        this.rootUrl = rootUrl;
        try {
            this.content = fetchContent(rootUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fetchContent(String urlStr) throws IOException {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 設定你的請求屬性，例如 User-Agent
         // 在 fetchContent 方法中的 URLConnection 前加上以下代碼
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Referer", "http://www.google.com/");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");



            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream in = conn.getInputStream();
                     BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"))) {

                    StringBuilder retVal = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        retVal.append(line).append("\n");
                    }

                    return retVal.toString();
                }
            } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                // HTTP 403 Forbidden
                System.out.println("HTTP 403 Forbidden for URL: " + urlStr);
                return null;
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                // HTTP 400 Bad Request
                System.out.println("HTTP 400 Bad Request for URL: " + urlStr);
                return null;
            } else {
                // 其他錯誤處理
                System.out.println("Error: " + responseCode + " for URL: " + urlStr);
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public ArrayList<String> getResults() {
        if (content == null || content.isEmpty()) {
            return new ArrayList<>();
        }

        Document doc = null;
        try {
            doc = Jsoup.parse(content);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        Elements aTags = doc.select("a");

        ArrayList<String> resultUrls = new ArrayList<>();

        for (Element aTag : aTags) {
            try {
                String href = aTag.attr("href");
                if (isValidHref(href)) {
                    // 使用 URL 類別進行網址的解析，以確保獲得絕對網址
                    URL absoluteUrl = new URL(new URL(rootUrl), href);
                    resultUrls.add(absoluteUrl.toString());
                }
            } catch (IndexOutOfBoundsException | MalformedURLException ignored) {
            }
        }

        return resultUrls;
    }



    private boolean isValidHref(String href) {
        try {
            URL url = new URL(href);
            return url.getProtocol().startsWith("http");
        } catch (MalformedURLException e) {
            return false;
        }
    }
}











