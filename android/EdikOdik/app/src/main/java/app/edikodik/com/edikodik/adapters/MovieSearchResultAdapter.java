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
import app.edikodik.com.edikodik.callbacks.OnMovieSearchResultClickListener;
import app.edikodik.com.edikodik.callbacks.OnSubcategoryClickListener;
import app.edikodik.com.edikodik.entities.BaseSubcategories;
import app.edikodik.com.edikodik.entities.moviesearch.Data;

//import app.edikodik.com.edikodik.callbacks.OnSubcategoryClickListener;

public class MovieSearchResultAdapter extends RecyclerView.Adapter<MovieSearchResultAdapter.ViewHolder> {


    private ArrayList<Data> mDataset;
    private OnMovieSearchResultClickListener onMovieSearchResultClickListener;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title, language, genre, director, cast;
        ImageView imgMovie;
        public RatingBar ratingBar;
        Button btnBuyOnline;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            language = (TextView) v.findViewById(R.id.language);
            genre = (TextView) v.findViewById(R.id.genre);
            director = (TextView) v.findViewById(R.id.director);
            cast = (TextView) v.findViewById(R.id.cast);
            imgMovie = (ImageView) v.findViewById(R.id.imgMovie);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            btnBuyOnline = (Button) v.findViewById(R.id.btnBuyOnline);
        }

        public void bind(final Data item, final OnMovieSearchResultClickListener listener) {

            imgMovie.setOnClickListener(new View.OnClickListener() {
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

    public void add(int position, BaseSubcategories item) {
//        mDataset.add(position, item);
//        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MovieSearchResultAdapter(Context context, ArrayList<Data> myDataset, OnMovieSearchResultClickListener onMovieSearchResultClickListener) {
        this.context = context;
        mDataset = myDataset;
        this.onMovieSearchResultClickListener = onMovieSearchResultClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieSearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_row_layout, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_result_row_layout, null);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Data movie = mDataset.get(position);

        String title = movie.getTitle();

        if (title != null && !title.equals(""))
            holder.title.setText(title);

        String language = movie.getLanguage_Name();

        if (language != null && !language.equals(""))
            holder.language.setText(language);

        String genre = movie.getGenre_GenreName();

        if (genre != null && !genre.equals(""))
            holder.genre.setText(genre);

        String director = movie.getDirector();

        if (director != null && !director.equals(""))
            holder.director.setText(director);

        String cast = movie.getCast_Crew();

        if (cast != null && !cast.equals(""))
            holder.cast.setText(cast);


        String rating = movie.getRating();

        if (rating != null) {
            holder.ratingBar.setRating(Float.parseFloat(rating));
        } else {
            holder.ratingBar.setRating(0.0f);
        }


        if (movie.getIsFeatured().equalsIgnoreCase("1")) {
            // TODO: 6/12/16 Featured
            // Add a featured button

//            holder.txtHeader.setTextColor(Color.parseColor("#ffffff"));
//            holder.txtHeader.setBackgroundColor(Color.parseColor("#00572c"));
        } else {
//            holder.txtHeader.setTextColor(Color.parseColor("#222222"));
//            holder.txtHeader.setBackgroundColor(Color.parseColor("#00ffffff"));
        }


        String imgLink = movie.getMovieBookImageLink1();
        if (imgLink != null && !imgLink.isEmpty()) {
            Picasso.with(context).load(imgLink).placeholder(R.drawable.restaurant_1).into(holder.imgMovie);
        }


        holder.bind(mDataset.get(position), onMovieSearchResultClickListener);



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