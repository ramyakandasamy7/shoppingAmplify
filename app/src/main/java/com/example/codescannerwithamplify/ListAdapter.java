package com.example.codescannerwithamplify;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class ListAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /*private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView;
        private TextView textView2;

        public ListViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.itemText);
            textView2 = (TextView) itemView.findViewById(R.id.quantityText);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            SharedPreferences
        }
    }*/
}
