package mobile.gsd.com.gsd;

/**
 * Created by ry on 7/14/16.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.rampo.updatechecker.UpdateChecker;
import com.rampo.updatechecker.store.Store;

import de.psdev.licensesdialog.LicensesDialog;
import mobile.gsd.com.gsd.Fragments.LandingFragment;
import mobile.gsd.com.gsd.Fragments.RssFragment;
import mobile.gsd.com.gsd.Fragments.StoreFragment;


public class MainInterface extends AppCompatActivity {

    String email = "rgocal09@gmail.com";
    String subject = "Contact";
    String body = "";
    String chooserTitle = "Contact via";

    private TabLayout mTabLayout;
    private Drawer result = null;

    private int[] mTabsIcons = {
            R.drawable.bottom_store,
            R.drawable.bottom_main,
            R.drawable.bottom_news};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_interface);

        View main = findViewById(R.id.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (main != null) {
                main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_toolbar);
        activity.getSupportActionBar().setTitle("GocalSD");
        activity.getSupportActionBar().setSubtitle("News, Content and More");

        UpdateChecker checker = new UpdateChecker(this);
        UpdateChecker.setStore(Store.GOOGLE_PLAY);
        UpdateChecker.setNoticeIcon(R.mipmap.ic_launcher);
        UpdateChecker.start();

        result = new DrawerBuilder(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(false)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerLayout(R.layout.material_drawer_fits_not)
                .withHeader(R.layout.settings_header)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Application"),
                                new PrimaryDrawerItem().withName("Notifications").withIdentifier(1),
                                new PrimaryDrawerItem().withName("Homepage").withIdentifier(2),
                        new ExpandableDrawerItem().withName("More").withSubItems(
                                new PrimaryDrawerItem().withName("Settings").withIdentifier(3),
                                new PrimaryDrawerItem().withName("License").withIdentifier(4),
                                new PrimaryDrawerItem().withName("Contact").withIdentifier(5)
                        )
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        long id = drawerItem.getIdentifier();
                        if (id == 1) {
                            Intent intent = new Intent(MainInterface.this, NewsfeedActivity.class);
                            startActivity(intent);
                        } else if (id == 2) {
                            Intent intent = new Intent(MainInterface.this, WebViewActivity.class);
                            startActivity(intent);
                        } else if (id == 3) {
                            Intent intent = new Intent(MainInterface.this, SettingsActivity.class);
                            startActivity(intent);
                        } else if (id == 4) {
                            new LicensesDialog.Builder(MainInterface.this)
                                    .setNotices(R.raw.licenses)
                                    .build()
                                    .show();

                        } else if (id == 5) {
                            AlertDialog alertDialog = new AlertDialog.Builder(MainInterface.this).create();
                            alertDialog.setTitle(getResources().getString(R.string.feedback_title));
                            alertDialog.setMessage(getResources().getString(R.string.feedback_summary));
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.email),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            sendEmail();
                                        }
                                    });
                            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.hangout),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startHangout();
                                        }
                                    });
                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(android.R.string.cancel),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            alertDialog.show();
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setAlpha(normalizedposition);
            }
        });

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 3;

        private final String[] mTabsTitle = {"Store", "Main", "Newsfeed"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(MainInterface.this).inflate(R.layout.custom_tab, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return StoreFragment.newInstance(1);
                case 1:
                    return LandingFragment.newInstance(2);
                case 2:
                    return RssFragment.newInstance(3);
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }

    private void startHangout(){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "");
        this.startActivity(sendIntent);
    }

    protected void sendEmail() {
        ShareCompat.IntentBuilder.from(this)
                .setType("message/rfc822")
                .addEmailTo(email)
                .setSubject(subject)
                .setText(body)
                //.setHtmlText(body) //If you are using HTML in your body text
                .setChooserTitle(chooserTitle)
                .startChooser();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}