package app.edikodik.com.edikodik.utilities;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.edikodik.com.edikodik.R;

/**
 * Created by farhadrubel on 7/17/16.
 */

public class AdvertisementUtility {
    Activity activity;

    private final String SPLITTER = "-zxTargetUrlzx-";

    ImageView ivAd;
    ImageView googleAd;

    public AdvertisementUtility(Activity activity) {
        this.activity = activity;
    }

    public boolean isLocalAdEnabled(String advertisement) {
        if (!LocalUtils.emptyString(advertisement) && advertisement.split(SPLITTER).length == 2) {
            return true;
        }
        return false;
    }

    public String setAdvertisement(String advertisement) {
        ivAd = (ImageView) activity.findViewById(R.id.ivAd);
        googleAd = (ImageView) activity.findViewById(R.id.googleAd);

        if (ivAd != null && !LocalUtils.emptyString(advertisement) && advertisement.split(SPLITTER).length == 2) {
            localAdVisible(true);
            googleAdVisible(false);
            return setLocalAdImage(advertisement);
        }

        if (googleAd != null) {
            localAdVisible(false);
            googleAdVisible(true);
        }

        return null;

    }

    private String setLocalAdImage(String advertisement) {
        String[] ad = advertisement.split(SPLITTER);

        if (ad.length == 2) {
            String imgPath = ad[0];
            String targetUrl = ad[1];

            Picasso.with(activity.getApplicationContext()).load(imgPath).placeholder(R.drawable.placeholder).into(ivAd);
            return targetUrl;
        }
        return null;
    }

    private void localAdVisible(boolean isVisible) {
        if (isVisible) {
            ivAd.setVisibility(View.VISIBLE);
        } else {
            ivAd.setVisibility(View.GONE);
        }
    }

    private void googleAdVisible(boolean isVisible) {
        if (isVisible) {
            // TODO: VISIBLE
            googleAd.setVisibility(View.GONE);
        } else {

            googleAd.setVisibility(View.GONE);
        }
    }
}
