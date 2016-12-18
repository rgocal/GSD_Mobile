package mobile.gsd.com.gsd.Fragments;

/**
 * Created by ry on 12/12/16.
 */

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import mobile.gsd.com.gsd.Adapter.CustomListAdapter;
import mobile.gsd.com.gsd.FeedDetailsActivity;
import mobile.gsd.com.gsd.R;
import mobile.gsd.com.gsd.Util.FeedItem;

public class StoreFragment extends Fragment {

    private ArrayList<FeedItem> feedList = null;
    private ProgressBar progressbar = null;
    private ListView feedListView = null;

    public static final String ARGS_INSTANCE = "mobile.gsd.com.gsd.argsInstance";

    public static StoreFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        StoreFragment fragment = new StoreFragment();
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

        View v = inflater.inflate(R.layout.fragment_store, container, false);
        setHasOptionsMenu(true);

        MobileAds.initialize(getActivity(), "ca-app-pub-6593943218195718/7861137189");

        AdView mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        progressbar = (ProgressBar) v.findViewById(R.id.progressBar);
        //For the dummy, we are going to use the IT Cutties feed. For using your json, we need to follow the format below if you are going to use jsonblog.com
        //http://jsonblob.com/api/jsonBlob/(insert url id here)
        String url = "http://www.itcuties.com/feed/";
        new DownloadFilesTask().execute(url);

        return v;
    }

    public void updateList() {
        feedListView= (ListView) getActivity().findViewById(R.id.custom_list);
        feedListView.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);

        feedListView.setAdapter(new CustomListAdapter(getActivity(), feedList));
        feedListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,	long id) {
                Object o = feedListView.getItemAtPosition(position);
                FeedItem newsData = (FeedItem) o;

                Intent intent = new Intent(getActivity(), FeedDetailsActivity.class);
                intent.putExtra("feed", newsData);
                startActivity(intent);
            }
        });
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            if (null != feedList) {
                updateList();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            JSONObject json = getJSONFromUrl(url);
            parseJson(json);
            return null;
        }
    }


    public JSONObject getJSONFromUrl(String url) {
        InputStream is;
        JSONObject jObj = null;
        String json = null;

        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            final HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString() + e.getMessage());
        }

        // return JSON String
        return jObj;

    }

    public void parseJson(JSONObject json) {
        try {

            // parsing json object
            if (json.getString("status").equalsIgnoreCase("ok")) {
                JSONArray posts = json.getJSONArray("posts");

                feedList = new ArrayList<>();

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.getJSONObject(i);
                    FeedItem item = new FeedItem();
                    item.setTitle(post.getString("title"));
                    item.setDate(post.getString("date"));
                    item.setId(post.getString("id"));
                    item.setUrl(post.getString("url"));
                    item.setContent(post.getString("content"));
                    JSONArray attachments = post.getJSONArray("attachments");

                    if (null != attachments && attachments.length() > 0) {
                        JSONObject attachment = attachments.getJSONObject(0);
                        if (attachment != null)
                            item.setAttachmentUrl(attachment.getString("url"));
                    }
                    feedList.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.store_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.refresh):
                updateList();
                return true;
            case (R.id.help):
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle(("GocalSD Apps"));
                alertDialog.setMessage(getResources().getString(R.string.help_dialog));
                alertDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
