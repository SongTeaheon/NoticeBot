package com.example.noticebot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.KeywordEditViewHolder> {
    private ArrayList<String> mList;

    public class KeywordEditViewHolder extends RecyclerView.ViewHolder {
        protected TextView keyword_edit;
        Button keyword_delete;

        public KeywordEditViewHolder(View view) {
            super(view);
            this.keyword_edit = view.findViewById(R.id.keyword_edit);
            this.keyword_delete = view.findViewById(R.id.keyword_delete);
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
    public void onBindViewHolder(@NonNull final KeywordEditViewHolder viewholder, final int position) {
//        DataKeywords item = mList.get(position);
        viewholder.keyword_edit.setText(mList.get(position));
        viewholder.keyword_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //리스트 삭제
//                mList.remove(position);
                mList.remove(viewholder.getAdapterPosition());
                notifyItemRemoved(viewholder.getAdapterPosition());
                notifyItemRangeChanged(viewholder.getAdapterPosition(), mList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}
