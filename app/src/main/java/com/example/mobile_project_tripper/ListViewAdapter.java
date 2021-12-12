package com.example.mobile_project_tripper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    private ArrayList<DiaryItem> IvList;
    int Position;

    public ListViewAdapter(ArrayList<DiaryItem> items, Context context) {
        IvList = items;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // ListView의 getView 부분을 담당하는 메소드
        ((ViewHolder) holder).onBind(IvList.get(position));
    }

    @Override
    public int getItemCount() {
        return IvList.size(); // 데이터 개수 리턴
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mType;
        public TextView mDetail;
        public TextView mCost;
        public TextView mTime;
        public TextView mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            // 화면에 표시될 View 로부터 위젯에 대한 참조 획득
            mTitle = itemView.findViewById(R.id.title);
            mCost = itemView.findViewById(R.id.cost);
            mType = itemView.findViewById(R.id.type);
            mDetail = itemView.findViewById(R.id.Detail);
            mTime = itemView.findViewById(R.id.time);
            mDate = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position = getAdapterPosition();
                    Intent intent = new Intent(mContext, ViewNote.class);
                    intent.putExtra(mContext.getString(R.string.row_id),IvList.get(Position).getUid());
                    Log.e("rowID", String.valueOf(IvList.get(Position).getUid()));
                    mContext.startActivity(intent);
                }
            });
        }

        public void onBind(DiaryItem item) {
            mTitle.setText(item.getTitle());
            mType.setText(item.getMemo_type());
            mCost.setText(item.getCost());
            mDetail.setText(item.getDetail());
            mTime.setText(item.getTime());
            mDate.setText(item.getDate());
        }
    }
}
