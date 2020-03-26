package app.edikodik.com.edikodik.callbacks;

import app.edikodik.com.edikodik.entities.BaseSubcategories;
import app.edikodik.com.edikodik.entities.genericsearch.GenericSearchResponse;
import app.edikodik.com.edikodik.entities.genericsearch.Listings;

/**
 * Created by farhadrubel on 6/5/16.
 */

public interface OnGenericListingsClickListener {
    public void onImageClick(Listings listing);
    public void onMapClick(Listings listing);
    public void onShareClick(Listings listing);
    public void onCallClick(Listings listing);

}
