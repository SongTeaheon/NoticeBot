package com.example.noticebot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainNoticesAdapter extends RecyclerView.Adapter<MainNoticesAdapter.NoticesViewHolder> {
    private ArrayList<DataNotices> mList;
    private final String TAG = "TAGLoginActivity";

    public class NoticesViewHolder extends RecyclerView.ViewHolder {
        protected TextView keyword;
        protected TextView title;

        public NoticesViewHolder(View view) {
            super(view);
            this.keyword = (TextView) view.findViewById(R.id.keyword_item_notices);
            this.title = (TextView) view.findViewById(R.id.title_item_notices);

            // 공지사항 제목 클릭 처리
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        DataNotices item = mList.get(pos) ;
                        Log.d(TAG,"버튼을 누르면 링크로 넘어간다.");
                    }

                }
            });

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

        viewholder.keyword.setText(item.getTitle());
        viewholder.title.setText(item.getLink()) ;
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }

    //리사이클러뷰 내 아이템 클릭 처리
    public interface MyRecyclerViewClickListener {
        // 아이템 전체 부분 클릭
        void onItemClicked(int position);

        // 공제사항 제목 버튼 클릭
        void onTitleButtonClicked(int position);

    }
}
