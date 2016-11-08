package com.example.infinitbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infinitbank.R;
import com.example.infinitbank.model.HistoryItem;
import com.example.infinitbank.util.ImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kylee on 28/10/2016.
 */

public class HistoryAdapter extends ArrayAdapter<HistoryItem> {

    class ViewHolder {
        @Bind(R.id.history_icon)
        ImageView mIconImage;
        @Bind(R.id.history_title)
        TextView mTitleText;
        @Bind(R.id.history_description)
        TextView mDescText;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    private final Context context;
    private final int resource;
    private final LayoutInflater inflater;

    public HistoryAdapter(Context context, int resource, List<HistoryItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        HistoryItem item = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageUtil.loadImage(item.iconUrl, holder.mIconImage);
        holder.mTitleText.setText(item.title);
        holder.mDescText.setText(item.descript);


        return convertView;
    }
}
