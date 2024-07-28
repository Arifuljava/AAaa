package com.example.aaaa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private List<RowData> dataList;

    public TableAdapter(List<RowData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RowData data = dataList.get(position);
        holder.column1.setText(data.getColumn1());
        holder.column2.setText(data.getColumn2());
        holder.column3.setText(data.getColumn3());
        holder.column4.setText(data.getColumn4());
        holder.column5.setText(data.getColumn5());
        holder.column6.setText(data.getColumn6());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView column1, column2, column3, column4, column5, column6;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            column1 = itemView.findViewById(R.id.column1);
            column2 = itemView.findViewById(R.id.column2);
            column3 = itemView.findViewById(R.id.column3);
            column4 = itemView.findViewById(R.id.column4);
            column5 = itemView.findViewById(R.id.column5);
            column6 = itemView.findViewById(R.id.column6);
        }
    }
}
