package com.example.noticebot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainNoticesAdapter extends RecyclerView.Adapter<MainNoticesAdapter.NoticesViewHolder> {
    private ArrayList<DataNotices> mList;
    private final String TAG = "TAGLoginActivity";
    private Context mContext;

    public class NoticesViewHolder extends RecyclerView.ViewHolder {
//        protected TextView keyword;
        protected TextView title;

        public NoticesViewHolder(View view) {
            super(view);
//            this.keyword = view.findViewById(R.id.keyword_item_notices);
            this.title = view.findViewById(R.id.title_item_notices);

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

    public MainNoticesAdapter(Context context, ArrayList<DataNotices> list) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public NoticesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notices, viewGroup, false);

        NoticesViewHolder viewHolder = new NoticesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticesViewHolder viewholder, final int position) {
        final DataNotices item = mList.get(position) ;

//        viewholder.keyword.setText(item.getKeyword());
        viewholder.title.setText(item.getTitle()) ;
        viewholder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,(position+1) + " 번 째 공지사항 링크가 선택되었음");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                mContext.startActivity(intent);
            }
        });
//        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();
//            }
//        });
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
    public void onItemClicked(int position) {
        Log.d(TAG,position + "번 아이템 클릭됨");
    }

    public void onTitleButtonClicked(int position) {
        Log.d(TAG,position + "번 링크 클릭됨");
    }
}
