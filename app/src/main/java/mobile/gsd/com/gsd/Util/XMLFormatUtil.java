package mobile.gsd.com.gsd.Util;

import android.graphics.Bitmap;

/**
 * Created by ry on 9/5/15.
 */
public class XMLFormatUtil {
    private String text;
    private String headline;
    private String description;
    private String techDetails;
    private String date;
    private String imageURL;
    private Bitmap imageBitmap;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechDetails() {
        return techDetails;
    }

    public void setTechDetails(String techDetails) {
        this.techDetails = techDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

}
