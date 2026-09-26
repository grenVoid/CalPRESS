package com.example.calpress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter
        extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private ArrayList<ItemHistory> historyList;

    public HistoryAdapter(
            ArrayList<ItemHistory> historyList
    ) {

        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(
                parent.getContext()
        ).inflate(
                R.layout.item_history,
                parent,
                false
        );

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull HistoryViewHolder holder,
            int position
    ) {

        ItemHistory item =
                historyList.get(position);

        holder.txtExpression.setText(
                item.getExpression()
        );

        holder.txtResult.setText(
                "= " + item.getResult()
        );
    }

    @Override
    public int getItemCount() {

        return historyList.size();
    }

    public void updateData(
            ArrayList<ItemHistory> newList
    ) {

        historyList = newList;

        notifyDataSetChanged();
    }

    public static class HistoryViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtExpression;
        TextView txtResult;

        public HistoryViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            txtExpression =
                    itemView.findViewById(
                            R.id.txtExpression
                    );

            txtResult =
                    itemView.findViewById(
                            R.id.txtResult
                    );
        }
    }
}