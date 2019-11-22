package com.example.noticebot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomKeywordAdapter extends RecyclerView.Adapter<CustomKeywordAdapter.KeywordCustomViewHolder> {
    private ArrayList<DataNotices> mList;

    public class KeywordCustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView link;

        public KeywordCustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.link = (TextView) view.findViewById(R.id.link);
        }
    }

    public CustomKeywordAdapter(ArrayList<DataNotices> list) {
        this.mList = list;
    }

    @Override
    public KeywordCustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notices, viewGroup, false);

        KeywordCustomViewHolder viewHolder =  new KeywordCustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomKeywordAdapter.KeywordCustomViewHolder viewholder, int position) {
        DataNotices item = mList.get(position) ;

        viewholder.title.setText(item.getTitle());
        viewholder.link.setText(item.getLink()) ;
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}
