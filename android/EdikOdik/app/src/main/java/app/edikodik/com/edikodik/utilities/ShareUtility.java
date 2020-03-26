package app.edikodik.com.edikodik.utilities;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by farhadrubel on 6/21/16.
 */

public class ShareUtility {
    public static void shareIt (Activity activity, String shareUrl) {
        String shareBody = shareUrl;

//         Uri uri = Uri.parse("http://edikodik.com/front/generic_details/19/Fine%20Dining/Distance%20unavailable");

//        Uri uri = ResourceToUri(getApplicationContext(), R.drawable.restaurant_1);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share...");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
