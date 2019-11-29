package com.example.noticebot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomKeywordAdapter extends RecyclerView.Adapter<CustomKeywordAdapter.KeywordCustomViewHolder> {
    private ArrayList<String> mList;

    protected class KeywordCustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;

        public KeywordCustomViewHolder(View view) {
            super(view);
            this.title = view.findViewById(R.id.keyword_custom);
        }
    }

    public CustomKeywordAdapter(ArrayList<String> list) {
        this.mList = list;
    }

    @Override
    public KeywordCustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_keyowrds_custom, viewGroup, false);

        KeywordCustomViewHolder viewHolder =  new KeywordCustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomKeywordAdapter.KeywordCustomViewHolder viewholder, int position) {
        String item = mList.get(position) ;

        viewholder.title.setText(item);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}
