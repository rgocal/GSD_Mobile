package mobile.gsd.com.gsd.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import mobile.gsd.com.gsd.R;
import mobile.gsd.com.gsd.Util.CustomTabActivityHelper;
import mobile.gsd.com.gsd.Util.WebviewFallback;

/**
 * Created by ry on 12/10/16.
 */

public class LandingFragment extends Fragment {

    public static final String ARGS_INSTANCE = "mobile.gsd.com.gsd.argsInstance";

    public static LandingFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        LandingFragment fragment = new LandingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.landing_fragment, container, false);
        setHasOptionsMenu(true);

        MobileAds.initialize(getActivity(), "ca-app-pub-6593943218195718/7861137189");

        AdView mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button facebook = (Button) v.findViewById(R.id.f_button);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                intentBuilder.setToolbarColor(getResources().getColor(R.color.toolbar));
                intentBuilder.setSecondaryToolbarColor(getResources().getColor(R.color.accent));
                CustomTabActivityHelper.openCustomTab(
                        getActivity(), intentBuilder.build(), Uri.parse("https://www.facebook.com/Gocalsd/"), new WebviewFallback());
            }
        });

        Button gplus = (Button) v.findViewById(R.id.g_button);
        gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                intentBuilder.setToolbarColor(getResources().getColor(R.color.toolbar));
                intentBuilder.setSecondaryToolbarColor(getResources().getColor(R.color.accent));
                CustomTabActivityHelper.openCustomTab(
                        getActivity(), intentBuilder.build(), Uri.parse("https://plus.google.com/u/0/communities/115542523912843355119"), new WebviewFallback());
            }
        });

        return v;
    }
}