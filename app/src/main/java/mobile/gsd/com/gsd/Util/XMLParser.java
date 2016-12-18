package mobile.gsd.com.gsd.Util;

import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by ry on 9/5/15.
 */
public class XMLParser {

    public static List<XMLFormatUtil> parse(InputStream is) {
        List<XMLFormatUtil> news = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            XMLHandler saxHandler = new XMLHandler();
            xmlReader.setContentHandler(saxHandler);
            xmlReader.parse(new InputSource(is));
            news = saxHandler.getNews();

        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
            ex.printStackTrace();
        }

        return news;
    }
}