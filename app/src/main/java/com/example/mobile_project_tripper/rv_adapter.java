package com.example.mobile_project_tripper;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class rv_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<DiaryList> profileDataArray;

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_location, tv_date;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.d_title_contain);
            tv_location = itemView.findViewById(R.id.d_location_contain);
            tv_date = itemView.findViewById(R.id.d_start_date_contain);
        }
    }

    // 리사이클러뷰 어댑터 생성자 설정
    rv_adapter(ArrayList<DiaryList> profileDataArray) {
        // 출력할 객체들을 담고있는 ArrayList를 parameter로 받음
        this.profileDataArray = profileDataArray;
    }

    @NonNull
    @Override // RecyclerView의 item(row)를 표시하는데 사용되는 rv_profile.row.xml을 가져오는 역할
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viweType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_list, parent, false);
        return new myViewHolder(v);
    }

    @Override // RecyclerView의 item(row)에 보여질 TextView를 설정
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        myViewHolder myViewHolder = (rv_adapter.myViewHolder) holder;
        myViewHolder.tv_title.setText(profileDataArray.get(position).getTitle1());
        myViewHolder.tv_location.setText(String.valueOf(profileDataArray.get(position).getLocation()));
        myViewHolder.tv_date.setText(profileDataArray.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return profileDataArray.size(); // ArrayList에 저장된 객체의 개수를 리턴
    }

}