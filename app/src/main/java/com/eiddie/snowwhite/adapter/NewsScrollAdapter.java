package com.eiddie.snowwhite.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eiddie.snowwhite.R;

import org.mcsoxford.rss.RSSItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by actmember on 3/8/17.
 */
public class NewsScrollAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RSSItem> newsList = new ArrayList<>();

    public NewsScrollAdapter(List<RSSItem> newsList) {
        this.newsList = newsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scroll_child, parent, false);
        return new NewsViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleDateFormat newsDateFormat = new SimpleDateFormat("hh:mm a");
        String newsDateString = newsDateFormat.format(newsList.get(position).getPubDate());

        ((NewsViewHolderItem) holder).newsDateText.setText(newsDateString);
        ((NewsViewHolderItem) holder).newsTitleText.setText(newsList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void updateData(List<RSSItem> updateList) {
        this.newsList.clear();
        this.newsList.addAll(updateList);
        notifyDataSetChanged();
    }

    public static class NewsViewHolderItem extends RecyclerView.ViewHolder {
        public View mView;
        public TextView newsDateText;
        public TextView newsTitleText;

        public NewsViewHolderItem(View v) {
            super(v);
            mView = v;

            newsDateText = (TextView) v.findViewById(R.id.text_news_date);
            newsTitleText = (TextView) v.findViewById(R.id.text_news_title);
        }
    }
}
