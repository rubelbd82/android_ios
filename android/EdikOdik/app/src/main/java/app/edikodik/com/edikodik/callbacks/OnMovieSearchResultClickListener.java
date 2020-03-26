package app.edikodik.com.edikodik.callbacks;

import app.edikodik.com.edikodik.entities.genericsearch.Listings;
import app.edikodik.com.edikodik.entities.moviesearch.Data;

/**
 * Created by farhadrubel on 6/5/16.
 */

public interface OnMovieSearchResultClickListener {
    public void onImageClick(Data movie);
    public void onBuyOnlineClick(Data movie);
    public void onRatingClick(Data movie);
}
