import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            URLConnection conn = url.openConnection();

            try (InputStream in = conn.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"))) {

                StringBuilder retVal = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    retVal.append(line).append("\n");
                }

                return retVal.toString();
            }
        } catch (IOException e) {
        	if (e.getMessage().contains("Server returned HTTP response code: 403")) {
                // 捕獲 HTTP 403 Forbidden 的例外，返回空字串
                System.out.println("HTTP 403 Forbidden for URL: " + urlStr);
                return "";
            } else {
                e.printStackTrace();
                return "";
            }
        }
    }



    public ArrayList<String> getResults() {
        if (content == null) {
            return new ArrayList<>();
        }

        Document doc = Jsoup.parse(content);
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


