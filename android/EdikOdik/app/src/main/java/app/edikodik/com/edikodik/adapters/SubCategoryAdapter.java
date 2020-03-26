package app.edikodik.com.edikodik.adapters;

/**
 * Created by farhadrubel on 5/31/16.
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.edikodik.com.edikodik.R;
//import app.edikodik.com.edikodik.callbacks.OnSubcategoryClickListener;
import app.edikodik.com.edikodik.callbacks.OnSubcategoryClickListener;
import app.edikodik.com.edikodik.entities.BaseSubcategories;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {


    private ArrayList<BaseSubcategories> mDataset;
    private OnSubcategoryClickListener onSubcategoryClickListener;




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.title);
        }

        public void bind(final BaseSubcategories item, final OnSubcategoryClickListener listener) {
            txtHeader.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    public void add(int position, BaseSubcategories item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubCategoryAdapter(ArrayList<BaseSubcategories> myDataset, OnSubcategoryClickListener onSubcategoryClickListener) {
        mDataset = myDataset;
        this.onSubcategoryClickListener = onSubcategoryClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_row_layout, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_row_layout, null);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        if (mDataset.get(position).isFeatured()) {
            holder.txtHeader.setTextColor(Color.parseColor("#ffffff"));
            holder.txtHeader.setBackgroundColor(Color.parseColor("#00572c"));
        } else {
            holder.txtHeader.setTextColor(Color.parseColor("#222222"));
            holder.txtHeader.setBackgroundColor(Color.parseColor("#00ffffff"));
        }

        holder.txtHeader.setText(mDataset.get(position).getSubCategoryName());

        holder.bind(mDataset.get(position), onSubcategoryClickListener);



//        holder.txtHeader.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                remove(name);
//            }
//        });

//        holder.txtFooter.setText("Footer: " + mDataset.get(position));

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}