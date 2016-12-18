package mobile.gsd.com.gsd;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by ry on 9/6/16.
 */
public class NewsfeedActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6593943218195718/7861137189");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        View main = findViewById(R.id.main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (main != null) {
                main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setTitle("Newsfeed");
        activity.getSupportActionBar().setSubtitle("Current Events, Promotions, and More!");

    }


}
