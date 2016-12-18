package mobile.gsd.com.gsd;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import mobile.gsd.com.gsd.Adapter.NewsListAdapter;
import mobile.gsd.com.gsd.Util.ConnectionDetector;
import mobile.gsd.com.gsd.Util.XMLFormatUtil;
import mobile.gsd.com.gsd.Util.XMLParser;

/**
 * Created by ry on 9/5/15.
 */
public class Newsfeed extends Fragment implements OnClickListener,
        OnItemClickListener {

    private static final String ARGS_INSTANCE = "com.NxIndustries.Sapphire.argsInstance";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    private ProgressDialog mProgressDialog;
    Button button;
    ListView listView;
    List<XMLFormatUtil> newsview;
    NewsListAdapter listViewAdapter;
    String URL = "https://raw.githubusercontent.com/rgocal/gsd_feed/master/rss.xml";


    public static Newsfeed newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        Newsfeed fragment = new Newsfeed();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rss_list, container, false);
        setHasOptionsMenu(true);

        listView = (ListView) view.findViewById(R.id.newsList);
        cd = new ConnectionDetector(getActivity());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            GetXMLTask task = new GetXMLTask(getActivity());
            SharedPreferences getPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            boolean news_on_start = getPrefs.getBoolean("load_news", true);
            if (news_on_start == true)
                task.execute(URL);

        } else {
            Toast.makeText(getActivity(), "Newsfeed requires an active internet connection",
                    Toast.LENGTH_LONG).show();
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }

    @Override
    public void onClick(View view) {
        if (isInternetPresent) {
            GetXMLTask task = new GetXMLTask(getActivity());
            task.execute(URL);

        } else {
            Toast.makeText(getActivity(), "Newsfeed requires an active internet connection",
                    Toast.LENGTH_LONG).show();

        }
    }

    private class GetXMLTask extends AsyncTask<String, Void, List<XMLFormatUtil>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.retrieve_item));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        private Activity context;

        public GetXMLTask(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(List<XMLFormatUtil> newsview) {
            listViewAdapter = new NewsListAdapter(context, newsview);
            listView.setAdapter(listViewAdapter);
            mProgressDialog.dismiss();
        }

        private String getXmlFromUrl(String urlString) {
            String xml = null;

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URL);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return xml;
        }

        @Override
        protected List<XMLFormatUtil> doInBackground(String... urls) {
            List<XMLFormatUtil> newsview = null;
            String xml = null;
            for (String url : urls) {
                xml = getXmlFromUrl(url);

                InputStream stream = new ByteArrayInputStream(xml.getBytes());
                newsview = XMLParser.parse(stream);

                for (XMLFormatUtil newsviewsingle : newsview) {
                    String imageURL = newsviewsingle.getImageURL();
                    Bitmap bitmap = null;
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inSampleSize = 1;

                    try {
                        bitmap = BitmapFactory
                                .decodeStream(new URL(imageURL).openStream(),
                                        null, bmOptions);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    newsviewsingle.setImageBitmap(bitmap);
                }
            }
            return newsview;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.news_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                if (isInternetPresent) {
                    GetXMLTask task = new GetXMLTask(getActivity());
                    task.execute(URL);

                } else {
                    Toast.makeText(getActivity(), "Newsfeed requires an active internet connection, Please connect to an active Wifi connection or LTE",
                            Toast.LENGTH_LONG).show();

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}