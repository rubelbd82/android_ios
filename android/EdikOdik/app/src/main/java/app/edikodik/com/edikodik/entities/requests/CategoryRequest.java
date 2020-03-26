package app.edikodik.com.edikodik.entities.requests;

/**
 * Created by farhadrubel on 6/4/16.
 */

public class CategoryRequest {
    String catId, catName;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
