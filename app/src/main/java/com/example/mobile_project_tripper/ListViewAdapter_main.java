package com.example.mobile_project_tripper;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListViewAdapter_main extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext1;
    private ArrayList<DiaryItem_main> IvList1;
    int Position1;

    public ListViewAdapter_main(ArrayList<DiaryItem_main> items1, Context context1) {
        IvList1 = items1;
        mContext1 = context1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_list, parent, false);
        return new ViewHolder1(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position1) {
        // ListView의 getView 부분을 담당하는 메소드
        ((ViewHolder1) holder1).onBind1(IvList1.get(position1));
    }

    @Override
    public int getItemCount() {
        return IvList1.size(); // 데이터 개수 리턴
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public TextView d_no_contain;
        public TextView d_title_contain;
        public TextView d_location_contain;
        public TextView d_start_date_contain;
        public TextView d_end_date_contain;

        public ViewHolder1(View itemView) {
            super(itemView);
            // 화면에 표시될 View 로부터 위젯에 대한 참조 획득
            d_title_contain = itemView.findViewById(R.id.d_title_contain);
            d_location_contain = itemView.findViewById(R.id.d_location_contain);
            d_start_date_contain = itemView.findViewById(R.id.d_start_date_contain);
            d_end_date_contain = itemView.findViewById(R.id.d_end_date_contain);
            d_no_contain = itemView.findViewById(R.id.d_no_contain);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position1 = getAdapterPosition();
                    Intent intent = new Intent(mContext1, DetailView.class);
                    intent.putExtra(mContext1.getString(R.string.row_id),IvList1.get(Position1).getId());
                    intent.putExtra("d_title", IvList1.get(Position1).getTitle());
                    Log.e("rowID", String.valueOf(IvList1.get(Position1).getId()));
                    mContext1.startActivity(intent);
                }
            });
        }
        
        public void onBind1(DiaryItem_main item) {
            d_title_contain.setText(item.getTitle());
            d_location_contain.setText(item.getLocate());
            d_start_date_contain.setText(item.getStartDate());
            d_end_date_contain.setText(item.getEndDate());
        }
    }
}
