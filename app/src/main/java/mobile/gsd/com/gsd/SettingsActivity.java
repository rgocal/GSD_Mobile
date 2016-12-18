package mobile.gsd.com.gsd;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by ry on 9/6/16.
 */
public class SettingsActivity extends PreferenceActivity {

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.preferences);

    }
}
