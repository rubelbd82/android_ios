package app.edikodik.com.edikodik.adapters;

/**
 * Created by farhadrubel on 5/31/16.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.callbacks.OnBookSearchResultClickListener;
import app.edikodik.com.edikodik.entities.booksearch.Data;

//import app.edikodik.com.edikodik.callbacks.OnSubcategoryClickListener;

public class BookSearchResultAdapter extends RecyclerView.Adapter<BookSearchResultAdapter.ViewHolder> {


    private ArrayList<Data> mDataset;
    private OnBookSearchResultClickListener onBookSearchResultClickListener;
    private Context context;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title, price, genre, writer;
        ImageView imgBook;
        public RatingBar ratingBar;
        Button btnBuyOnline;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            price = (TextView) v.findViewById(R.id.price);
            genre = (TextView) v.findViewById(R.id.genre);
            writer = (TextView) v.findViewById(R.id.writer);
            imgBook = (ImageView) v.findViewById(R.id.imgBook);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            btnBuyOnline = (Button) v.findViewById(R.id.btnBuyOnline);
        }

        public void bind(final Data item, final OnBookSearchResultClickListener listener) {
            imgBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onImageClick(item);
                }
            });

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onImageClick(item);
                }
            });

            btnBuyOnline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onBuyOnlineClick(item);
                }
            });
        }

    }

    public void add(int position, Data item) {
//        mDataset.add(position, item);
//        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookSearchResultAdapter(Context context, ArrayList<Data> myDataset, OnBookSearchResultClickListener onBookSearchResultClickListener) {
        this.context = context;
        mDataset = myDataset;
        this.onBookSearchResultClickListener = onBookSearchResultClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookSearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_row_layout, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_search_result_row_layout, null);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Data book = mDataset.get(position);


        String title = book.getTitle();

        if (title != null && !title.equals(""))
            holder.title.setText(title);

        String price = book.getPrice();

        if (price != null && !price.equals(""))
            holder.price.setText(price);

        String genre = book.getGenre_GenreName();

        if (genre != null && !genre.equals(""))
            holder.genre.setText(genre);

        String writer = book.getWriter();

        if (writer != null && !writer.equals(""))
            holder.writer.setText(writer);


        String rating = book.getRating();

        if (rating != null) {
            holder.ratingBar.setRating(Float.parseFloat(rating));
        }

        if (book.getIsFeatured().equalsIgnoreCase("1")) {
            // TODO: 6/12/16 Featured
            // Add a featured button

//            holder.txtHeader.setTextColor(Color.parseColor("#ffffff"));
//            holder.txtHeader.setBackgroundColor(Color.parseColor("#00572c"));
        } else {
//            holder.txtHeader.setTextColor(Color.parseColor("#222222"));
//            holder.txtHeader.setBackgroundColor(Color.parseColor("#00ffffff"));
        }


        String imgLink = book.getMovieBookImageLink1();
        if (imgLink != null && !imgLink.isEmpty()) {
            Picasso.with(context).load(imgLink).placeholder(R.drawable.restaurant_1).into(holder.imgBook);
        }

        holder.bind(mDataset.get(position), onBookSearchResultClickListener);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}