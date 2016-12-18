package mobile.gsd.com.gsd.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mobile.gsd.com.gsd.R;
import mobile.gsd.com.gsd.Util.XMLFormatUtil;

/**
 * Created by ry on 9/5/15.
 */
public class NewsListAdapter extends ArrayAdapter<XMLFormatUtil> {
    Activity context;
    List<XMLFormatUtil> news;

    public NewsListAdapter(Activity context, List<XMLFormatUtil> news) {
        super(context, R.layout.rss_list_item, news);
        this.context = context;
        this.news = news;
    }

    /* private view holder class */
    private class ViewHolder {
        TextView txtText;
        TextView txtHeadline;
        TextView txtDate;

    }

    @Override
    public XMLFormatUtil getItem(int position) {
        return news.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.rss_list_item, null);
            holder = new ViewHolder();
            holder.txtHeadline = (TextView) convertView
                    .findViewById(R.id.headline);
            holder.txtText = (TextView) convertView.findViewById(R.id.text);
            holder.txtDate = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        XMLFormatUtil newsitem = getItem(position);

        holder.txtHeadline.setText(newsitem.getHeadline());
        holder.txtText.setText(newsitem.getText());
        holder.txtDate.setText(newsitem.getDate());

        return convertView;
    }
}
