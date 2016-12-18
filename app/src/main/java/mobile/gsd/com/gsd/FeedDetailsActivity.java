package mobile.gsd.com.gsd;

/**
 * Created by ry on 11/17/16.
 */
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mobile.gsd.com.gsd.Util.CustomTabActivityHelper;
import mobile.gsd.com.gsd.Util.FeedItem;
import mobile.gsd.com.gsd.Util.ImageDownloaderTask;
import mobile.gsd.com.gsd.Util.WebviewFallback;

public class FeedDetailsActivity extends AppCompatActivity {

    private FeedItem feed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_details);

        feed = (FeedItem) this.getIntent().getSerializableExtra("feed");

        if (null != feed) {
            ImageView thumb = (ImageView) findViewById(R.id.featuredImg);
            new ImageDownloaderTask(thumb).execute(feed.getAttachmentUrl());

            TextView title = (TextView) findViewById(R.id.title);
            title.setText(feed.getTitle());

            TextView htmlTextView = (TextView) findViewById(R.id.content);
            htmlTextView.setText(Html.fromHtml(feed.getContent(), null, null));

            FloatingActionButton fabOne = (FloatingActionButton) findViewById(R.id.fab_one);
            fabOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                    intentBuilder.setToolbarColor(getResources().getColor(R.color.toolbar));
                    intentBuilder.setSecondaryToolbarColor(getResources().getColor(R.color.accent));
                    CustomTabActivityHelper.openCustomTab(
                            FeedDetailsActivity.this, intentBuilder.build(), Uri.parse(feed.getUrl()), new WebviewFallback());
                }
            });

            FloatingActionButton fabTwo = (FloatingActionButton) findViewById(R.id.fab_two);
            fabTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareContent();
                }
            });
        }
    }

    private void shareContent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, feed.getTitle() + "\n" + feed.getUrl());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share using"));

    }
}