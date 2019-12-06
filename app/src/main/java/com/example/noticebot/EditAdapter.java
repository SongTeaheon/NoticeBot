package com.example.noticebot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.KeywordEditViewHolder> {
    private ArrayList<String> mList;
    private ArrayList<String> mDeletedList;


    public class KeywordEditViewHolder extends RecyclerView.ViewHolder {
        protected TextView TextView_KeywordEdit;
        Button Button_KeywordDelete;

        public KeywordEditViewHolder(View view) {
            super(view);
            this.TextView_KeywordEdit = view.findViewById(R.id.TextView_KeywordEdit);
            this.Button_KeywordDelete = view.findViewById(R.id.Button_KeywordDelete);
        }
    }

    public EditAdapter(ArrayList<String> list, ArrayList<String> deletedList) {

        this.mList = list;
        this.mDeletedList = deletedList;
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
        viewholder.TextView_KeywordEdit.setText(mList.get(position));
        viewholder.Button_KeywordDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //리스트 삭제
//                mList.remove(position);
                Log.d("TAGEditAdpater", "position : " + position);
                mDeletedList.add(mList.get(position));
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
