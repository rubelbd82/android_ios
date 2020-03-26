package app.edikodik.com.edikodik.callbacks;

import app.edikodik.com.edikodik.entities.booksearch.Data;
import app.edikodik.com.edikodik.entities.genericsearch.Listings;

/**
 * Created by farhadrubel on 6/5/16.
 */

public interface OnBookSearchResultClickListener {
    public void onImageClick(Data movie);
    public void onBuyOnlineClick(Data movie);
    public void onRatingClick(Data movie);

}
