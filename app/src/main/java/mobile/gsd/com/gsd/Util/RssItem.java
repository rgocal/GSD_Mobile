package mobile.gsd.com.gsd.Util;

/**
 * Created by ry on 12/10/16.
 */
public class RssItem {

    private final String title;
    private final String link;

    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}