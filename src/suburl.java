import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class suburl {
    private String rootUrl;
    private String content;

    public suburl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    private String fetchContent() throws IOException {
        URL url = new URL(rootUrl);
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
    }

    public ArrayList<String> getResults() throws IOException {
        if (content == null) {
            try {
                content = fetchContent();
            } catch (Exception e) {
                return new ArrayList<>(); // Return an empty list in case of an exception
            }
        }

        Document doc = Jsoup.parse(content);
        Elements aTags = doc.select("a");

        ArrayList<String> resultUrls = new ArrayList<>();

        for (Element aTag : aTags) {
            try {
                String href = aTag.attr("href");
                if (isValidHref(href)) {
                    String url = rootUrl + href;
                    resultUrls.add(url);
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }

        return resultUrls;
    }

    private boolean isValidHref(String href) {
        return !(href.startsWith("h") || href.startsWith("#") || href.startsWith("/") || href.isEmpty());
    }
}
