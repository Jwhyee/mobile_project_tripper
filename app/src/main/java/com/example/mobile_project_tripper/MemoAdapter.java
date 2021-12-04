package com.example.mobile_project_tripper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>{

    private ArrayList<MemoItem> m_MemoItems;
    private Context m_Context;
    private DBHelper m_DBHelper;

    public MemoAdapter(ArrayList<MemoItem> m_MemoItems, Context m_Context){
        this.m_MemoItems = m_MemoItems;
        this.m_Context = m_Context;
        m_DBHelper = new DBHelper(m_Context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_write, parent, false);
        return new ViewHolder((holder));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sub_title_contain.setText(m_MemoItems.get(position).getSub_title());
        holder.date_contain.setText(m_MemoItems.get(position).getDate());
        holder.trans_contain.setText(m_MemoItems.get(position).getTrans());
        holder.cost_contain.setText(m_MemoItems.get(position).getCost());

    }

    @Override
    public int getItemCount() {
        return m_MemoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView sub_title_contain;
        private TextView date_contain;
        private TextView trans_contain;
        private TextView cost_contain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            sub_title_contain = itemView.findViewById(R.id.sub_title_contain);
            date_contain = itemView.findViewById(R.id.date_contain);
            trans_contain = itemView.findViewById(R.id.trans_contain);
            cost_contain = itemView.findViewById(R.id.cost_contain);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition();
                    MemoItem memoItem = m_MemoItems.get(curPos);

                    String[] str_choice_items = {"수정하기, 삭제하게"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(m_Context);
                    builder.setTitle("원하는 착업을 선택해주세요");

                    builder.setItems(str_choice_items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if(position == 0){//수정
                            }

                        }
                    });
                }
            });

        }
    }
}
