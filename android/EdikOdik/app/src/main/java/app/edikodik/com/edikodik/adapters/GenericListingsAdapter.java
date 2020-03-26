package app.edikodik.com.edikodik.adapters;

/**
 * Created by farhadrubel on 5/31/16.
 */

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.callbacks.OnGenericListingsClickListener;
import app.edikodik.com.edikodik.entities.genericsearch.Listings;

//import app.edikodik.com.edikodik.callbacks.OnSubcategoryClickListener;

public class GenericListingsAdapter extends RecyclerView.Adapter<GenericListingsAdapter.ViewHolder> {


    private ArrayList<Listings> mDataset;
    private OnGenericListingsClickListener onGenericListingsClickListener;
    private Context context;
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, address1, address2, price, rating, tvDistance;
        public LinearLayout featured, verified, map, share, call;
        public ImageView imgListing;
        public RatingBar ratingBar;


        public FloatingActionButton btnMap, btnShare, btnCall;

        public ViewHolder(View v) {
            super(v);
            tvDistance = (TextView) v.findViewById(R.id.tvDistance);
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

            btnMap = (FloatingActionButton) v.findViewById(R.id.btnMap);
            btnShare = (FloatingActionButton) v.findViewById(R.id.btnShare);
            btnCall = (FloatingActionButton) v.findViewById(R.id.btnCall);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        }

        public void bind(final Listings item, final OnGenericListingsClickListener listener) {
//            txtHeader.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    listener.onItemClick(item);
//                }
//            });

            btnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMapClick(item);
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onShareClick(item);
                }
            });


            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCallClick(item);
                }
            });

            imgListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onImageClick(item);
                }
            });

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onImageClick(item);
                }
            });


        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public GenericListingsAdapter(Context context, ArrayList<Listings> myDataset, OnGenericListingsClickListener onGenericListingsClickListener) {
        this.context = context;
        mDataset = myDataset;

        this.onGenericListingsClickListener = onGenericListingsClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GenericListingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_row_layout, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.generic_listings_row_layout, null);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element



        // TODO: ADD distance HERE.

//        String distance = mDataset.get(position).getName();
//
//        if (!name.equals(""))
//            holder.name.setText(name);

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

        String price = listing.getPriceRange_Name();

        if (price != null && !price.equals(""))
            holder.price.setText(price);


        String featured = listing.getFeatured();

        if (featured == null || !featured.equalsIgnoreCase("1")) {
            holder.featured.setVisibility(View.GONE);
        } else {
            holder.featured.setVisibility(View.VISIBLE);
        }

        String verified = listing.getVerified();

        if (verified == null || !verified.equalsIgnoreCase("1")) {
            holder.verified.setVisibility(View.GONE);
        } else {
            holder.verified.setVisibility(View.VISIBLE);
        }

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

        String rating = listing.getRating();

        if (rating != null) {
            holder.ratingBar.setRating(Float.parseFloat(rating));
        }

        String distanceString = listing.getDistanta();
        if (distanceString != null) {
            float distance = Float.parseFloat(distanceString);
            if (distance > 0 && distance < 100) {

                holder.tvDistance.setText(String.format("%.2f", distance) + " km");
            } else {
                holder.tvDistance.setText("Distance unavailable");
            }

        } else {
            holder.tvDistance.setText("Distance unavailable");
        }

        holder.bind(listing, onGenericListingsClickListener);

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}