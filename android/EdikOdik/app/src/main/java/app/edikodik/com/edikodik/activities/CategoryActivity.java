package app.edikodik.com.edikodik.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.adapters.SubCategoryAdapter;
import app.edikodik.com.edikodik.callbacks.OnSubcategoryClickListener;
import app.edikodik.com.edikodik.entities.BaseSubcategories;
import app.edikodik.com.edikodik.entities.CategoryResponse;
import app.edikodik.com.edikodik.entities.Subcategories;
import app.edikodik.com.edikodik.entities.Subcategories_featured;
import app.edikodik.com.edikodik.entities.requests.CategoryRequest;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.CategoryService;
import app.edikodik.com.edikodik.services.SubCategoryListingsService;
import app.edikodik.com.edikodik.utilities.AdvertisementUtility;
import app.edikodik.com.edikodik.utilities.CacheDb;
import app.edikodik.com.edikodik.utilities.CommonUtilities;

public class CategoryActivity extends BaseActivity implements Handler.Callback  {
    private ArrayList<BaseSubcategories> baseSubcategories = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String searchedTerm;

    private String targetUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        super.initializeView(ActivityType.CATEGORY);

        // Set Title in titlebar
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");
        getSupportActionBar().setTitle(categoryName);



        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Get Subcategories from CacheDb;
        addFeaturedSubcategoriesToBase();
        addSubcategoriesToBase();

        // specify an adapter (see also next example)
        mAdapter = new SubCategoryAdapter(baseSubcategories, new OnSubcategoryClickListener() {
            @Override
            public void onItemClick(BaseSubcategories baseSubcategory) {
                goToSearchResultScreen(baseSubcategory);
            }
        });
        mRecyclerView.setAdapter(mAdapter);


        AdvertisementUtility advertisementUtility = new AdvertisementUtility(this);

        if (CacheDb.getCategoryResponse().getAds_mobile() != null) {
            targetUrl = advertisementUtility.setAdvertisement(CacheDb.getCategoryResponse().getAds_mobile());
        } else {
            advertisementUtility.setAdvertisement(null);
        }
    }

    public void actionTargetUrl() {
        String url = targetUrl;

        if (url == null || url.isEmpty()) {
            CommonUtilities.toast(this, "URL is empty");
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    public void goToSearchResultScreen(BaseSubcategories baseSubcategory) {
        searchedTerm = baseSubcategory.getSubCategoryName();

        if (NetworkUtility.isNetworkAvailable(this)) {
            SubCategoryListingsService service = new SubCategoryListingsService(CategoryActivity.this, this, true);
            service.execute(baseSubcategory);
        } else {
            CommonUtilities.toast(this, "No internet connection");
        }

    }

    private void addFeaturedSubcategoriesToBase() {
        try {
            Subcategories_featured[] subcategories = CacheDb.getCategoryResponse().getSubcategories_featured();

            for (Subcategories_featured subcategory : subcategories) {
                BaseSubcategories baseSubcategory = new BaseSubcategories(subcategory.getHits(),
                        subcategory.getDescription(),
                        subcategory.getSubCatId(),
                        subcategory.getSubCategoryName(),
                        true);
                baseSubcategories.add(baseSubcategory);
//             snd
            }
        } catch (Exception e) {
            Log.d("wid", "Exception: " + e.getMessage());
        }


    }

    private void addSubcategoriesToBase() {
        try {
            Subcategories[] subcategories = CacheDb.getCategoryResponse().getSubcategories();

            for (Subcategories subcategory : subcategories) {
                BaseSubcategories baseSubcategory = new BaseSubcategories(subcategory.getHits(),
                        subcategory.getDescription(),
                        subcategory.getSubCatId(),
                        subcategory.getSubCategoryName(),
                        false);
                baseSubcategories.add(baseSubcategory);
            }
        } catch (Exception e) {
            Log.d("wid", "Exception: " + e.getMessage());
        }

    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.arg1 == ServiceType.SUBCATEGORY_SERVICE.ordinal()) {
            Intent intent = new Intent(this, GenericSearchResultActivity.class);
            intent.putExtra("searchedTerm", searchedTerm);
            startActivity(intent);
        }
        return false;
    }
}
