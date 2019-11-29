package com.example.noticebot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.KeywordEditViewHolder> {
    private ArrayList<String> mList;

    public class KeywordEditViewHolder extends RecyclerView.ViewHolder {
        protected TextView keyword_edit;

        public KeywordEditViewHolder(View view) {
            super(view);
            this.keyword_edit = view.findViewById(R.id.keyword_edit);
        }
    }

    public EditAdapter(ArrayList<String> list) {
        this.mList = list;
    }

    @Override
    public KeywordEditViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_keywords_edit, viewGroup, false);

        KeywordEditViewHolder viewHolder =  new KeywordEditViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordEditViewHolder viewholder, int position) {
//        DataKeywords item = mList.get(position);
        viewholder.keyword_edit.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}
