package com.example.mobile_project_tripper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListViewAdapter_detail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext2;
    private ArrayList<DiaryItem_detail> IvList2;
    int Position2;

    public ListViewAdapter_detail(ArrayList<DiaryItem_detail> items2, Context context2) {
        IvList2 = items2;
        mContext2 = context2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_view_list, parent, false);
        return new ViewHolder2(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, int position2) {
        // ListView의 getView 부분을 담당하는 메소드
        ((ViewHolder2) holder2).onBind2(IvList2.get(position2));
    }

    @Override
    public int getItemCount() {
        return IvList2.size(); // 데이터 개수 리턴
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView d_title;
        public TextView detail_time;
        public TextView detail_subtitle;
        public TextView detail_type;
        public TextView detail_cost;

        public ViewHolder2(View itemView) {
            super(itemView);
            // 화면에 표시될 View 로부터 위젯에 대한 참조 획득
            d_title = itemView.findViewById(R.id.d_title);
            detail_time = itemView.findViewById(R.id.detail_time);
            detail_subtitle = itemView.findViewById(R.id.detail_subtitle);
            detail_type = itemView.findViewById(R.id.detail_type);
            detail_cost = itemView.findViewById(R.id.detail_cost);

            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position2 = getAdapterPosition();
                    Intent intent = new Intent(mContext2, DetailView.class);
                    intent.putExtra(mContext2.getString(R.string.row_id),IvList2.get(Position2).getId());
                    intent.putExtra("d_title", IvList2.get(Position2).getTitle());
                    Log.e("rowID", String.valueOf(IvList2.get(Position2).getId()));
                    mContext2.startActivity(intent);
                }
            });
            */
        }
        
        public void onBind2(DiaryItem_detail item) {
            d_title.setText(item.getTitle());
            detail_time.setText(item.getTime());
            detail_subtitle.setText(item.getSubtitle());
            detail_type.setText(item.getType());
            detail_cost.setText(item.getCost());
        }
    }
}
