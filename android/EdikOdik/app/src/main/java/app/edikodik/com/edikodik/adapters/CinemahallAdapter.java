package app.edikodik.com.edikodik.adapters;

/**
 * Created by farhadrubel on 5/31/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.callbacks.OnCinemahallClickListener;
import app.edikodik.com.edikodik.entities.moviedetail.Listings;


public class CinemahallAdapter extends RecyclerView.Adapter<CinemahallAdapter.ViewHolder> {


    private ArrayList<Listings> mDataset;
    private OnCinemahallClickListener onCinemahallClickListener;
    private Context context;
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, address1, address2, price, rating;
        public LinearLayout featured, verified, map, share, call;
        public ImageView imgListing;


        public Button btnMap, btnShare, btnCall;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            address1 = (TextView) v.findViewById(R.id.address1);
            address2 = (TextView) v.findViewById(R.id.address2);
            price = (TextView) v.findViewById(R.id.price);
            rating = (TextView) v.findViewById(R.id.rating);
            featured = (LinearLayout) v.findViewById(R.id.featured);
            verified = (LinearLayout) v.findViewById(R.id.verified);
            map = (LinearLayout) v.findViewById(R.id.map);
            share = (LinearLayout) v.findViewById(R.id.share);
            call = (LinearLayout) v.findViewById(R.id.call);
            imgListing = (ImageView) v.findViewById(R.id.imgListing);

            btnMap = (Button) v.findViewById(R.id.btnMap);
            btnShare = (Button) v.findViewById(R.id.btnShare);
            btnCall = (Button) v.findViewById(R.id.btnCall);

        }

        public void bind(final Listings item, final OnCinemahallClickListener listener) {


//            btnShare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onShareClick(item);
//                }
//            });
        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public CinemahallAdapter(Context context, ArrayList<Listings> myDataset, OnCinemahallClickListener onGenericListingsClickListener) {
        this.context = context;
        mDataset = myDataset;

        this.onCinemahallClickListener = onCinemahallClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CinemahallAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_row_layout, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinemahalls_row_layout, null);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Listings listing = mDataset.get(position);

        String name = listing.getName();

        if (name != null && !name.equals(""))
            holder.name.setText(name);

        String address1 = listing.getAddress1();

        if (address1 != null && !address1.equals(""))
            holder.address1.setText(address1);

        String address2 = listing.getAddress2();

        if (address2 != null && !address2.equals("")) {
            holder.address2.setText(address2);
        } else {
            holder.address2.setVisibility(View.GONE);
        }

        String featured = listing.getFeatured();

        String imgLink = listing.getListingImageLink1();

        if (imgLink != null && !imgLink.isEmpty()) {
            Picasso.with(context).load(imgLink).placeholder(R.drawable.restaurant_1).into(holder.imgListing);
        }

        String mobile = listing.getMobile();

        if (mobile == null || mobile.isEmpty()) {
            holder.call.setVisibility(View.GONE);
        } else {
            holder.call.setVisibility(View.VISIBLE);
        }

        holder.bind(listing, onCinemahallClickListener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}