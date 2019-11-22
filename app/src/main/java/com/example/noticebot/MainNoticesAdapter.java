package com.example.noticebot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainNoticesAdapter extends RecyclerView.Adapter<MainNoticesAdapter.NoticesViewHolder> {
    private ArrayList<DataNotices> mList;

    public class NoticesViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView link;

        public NoticesViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.link = (TextView) view.findViewById(R.id.link);
        }
    }

    public MainNoticesAdapter(ArrayList<DataNotices> list) {
        this.mList = list;
    }

    @Override
    public NoticesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notices, viewGroup, false);

        NoticesViewHolder viewHolder = new NoticesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticesViewHolder viewholder, int position) {
        DataNotices item = mList.get(position) ;

        viewholder.title.setText(item.getTitle());
        viewholder.link.setText(item.getLink()) ;
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}
