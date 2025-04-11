package com.example.newproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    private List<BookingR> resultList;

    public ResultsAdapter(List<BookingR> resultList) {
        this.resultList = resultList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtResult;

        public ViewHolder(View itemView) {
            super(itemView);
            txtResult = itemView.findViewById(R.id.txtResult);
        }
    }

    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookingR result = resultList.get(position);
        holder.txtResult.setText("Name: " + result.getName()
                + "\nService: " + result.getService()
                + "\nDateTime: " + result.getDatetime());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}
