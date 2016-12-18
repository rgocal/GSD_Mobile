package mobile.gsd.com.gsd.Util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ry on 9/5/15.
 */
public class XMLHandler extends DefaultHandler {
    private List<XMLFormatUtil> news;
    private String tempVal;
    private XMLFormatUtil newsitem;

    public XMLHandler() {
        news = new ArrayList<XMLFormatUtil>();
    }

    public List<XMLFormatUtil> getNews() {
        return news;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        tempVal = "";
        if (qName.equalsIgnoreCase("newsitem")) {
            newsitem = new XMLFormatUtil();
            newsitem.setHeadline(attributes.getValue("headline"));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("newsitem")) {
            news.add(newsitem);
        } else if (qName.equalsIgnoreCase("text")) {
            newsitem.setText(tempVal);
        } else if (qName.equalsIgnoreCase("description")) {
            newsitem.setDescription(tempVal);
        } else if (qName.equalsIgnoreCase("technical-details")) {
            newsitem.setTechDetails(tempVal);
        } else if (qName.equalsIgnoreCase("image-url")) {
            newsitem.setImageURL(tempVal);
        } else if (qName.equalsIgnoreCase("date")) {
            newsitem.setDate(tempVal);
        }
    }
}
