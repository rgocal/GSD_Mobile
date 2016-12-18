package mobile.gsd.com.gsd.Util;

/**
 * Created by ry on 12/10/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import mobile.gsd.com.gsd.WebviewActivity;

/**
 * A Fallback that opens a Webview when Custom Tabs is not available
 */
public class WebviewFallback implements CustomTabActivityHelper.CustomTabFallback {
    @Override
    public void openUri(Activity activity, Uri uri) {
        Intent intent = new Intent(activity, WebviewActivity.class);
        intent.putExtra(WebviewActivity.EXTRA_URL, uri.toString());
        activity.startActivity(intent);
    }
}
