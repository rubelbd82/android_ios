package app.edikodik.com.edikodik.entities.requests;

import app.edikodik.com.edikodik.enums.ListingType;

/**
 * Created by farhadrubel on 7/8/16.
 */

public class RatingRequest {
    String listingId, userId;
    float score;
    ListingType listingType;

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ListingType getListingType() {
        return listingType;
    }

    public void setListingType(ListingType listingType) {
        this.listingType = listingType;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
