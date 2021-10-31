package com.example.vijaya.myorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    ArrayList<String> checkedToppings=new ArrayList<>();

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String topping = mData.get(position);
        holder.myCheckBox.setText(topping);
        final MainActivity act=new MainActivity();

        holder.myCheckBox.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                final boolean isChecked = holder.myCheckBox.isChecked();
                for (int i=0; i<mData.size();i++) {
                    if (isChecked) {
                        if (!checkedToppings.contains(mData.get(position)))
                            checkedToppings.add(i, mData.get(position));
                    } else {
                        checkedToppings.remove(mData.get(position));
                    }


                  }
                act.selectedToppings(checkedToppings);

            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox myCheckBox;

        ViewHolder(View itemView) {
            super(itemView);
            myCheckBox = itemView.findViewById(R.id.tvAnimalName);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
