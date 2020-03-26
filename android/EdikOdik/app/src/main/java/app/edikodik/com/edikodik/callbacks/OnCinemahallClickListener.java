package app.edikodik.com.edikodik.callbacks;

import app.edikodik.com.edikodik.entities.genericsearch.Listings;

/**
 * Created by farhadrubel on 6/5/16.
 */

public interface OnCinemahallClickListener {
    public void onWebsiteClick(Listings listing);
    public void onFacebookClick(Listings listing);
    public void onCallClick(Listings listing);

}
