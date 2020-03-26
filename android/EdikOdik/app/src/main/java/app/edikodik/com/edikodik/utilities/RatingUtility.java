package app.edikodik.com.edikodik.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.activities.GenericDetailActivity;
import app.edikodik.com.edikodik.activities.LoginActivity;
import app.edikodik.com.edikodik.activities.RegisterActivity;
import app.edikodik.com.edikodik.dao.UserDao;
import app.edikodik.com.edikodik.entities.custom.UserModel;
import app.edikodik.com.edikodik.entities.requests.RatingRequest;
import app.edikodik.com.edikodik.enums.ListingType;
import app.edikodik.com.edikodik.network.NetworkUtility;
import app.edikodik.com.edikodik.services.RatingService;

/**
 * Created by farhadrubel on 7/7/16.
 */

public class RatingUtility implements Handler.Callback {
    Activity activity;
    String listingId;
    ListingType listingType;

    UserModel user = null;


    public RatingUtility(Activity activity, String listingId, ListingType listingType) {
        this.activity = activity;
        this.listingId = listingId;
        this.listingType = listingType;
    }

    public void initialize() {

        user = new UserDao().getUser();
        if (user == null) {
            this.showDialogForNonUser();
        } else {
            this.showDialogForUser();
        }
    }

    private void showDialogForNonUser() {
        final Context context =  activity.getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Title");
        builder.setMessage("You must be logged-in user to rate.");
        builder.setItems(new CharSequence[]
                        {"Cancel", "Sign Up", "Sign In"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
//                                Toast.makeText(context, "clicked 1", 0).show();
                                break;
                            case 1:
                                activity.startActivity(new Intent(activity, RegisterActivity.class));
                                break;
                            case 2:
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void showDialogForUser() {



//
//        final RatingBar ratingBar = new RatingBar(activity.getApplicationContext());
//        ratingBar.setNumStars(5);
//        ratingBar.setIsIndicator(false);
//        ratingBar.setStepSize(1);

        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.rating_bar, null);

        new android.app.AlertDialog.Builder(activity)
                .setTitle("Rate")
                .setCancelable(true)
                .setView(dialoglayout)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Rate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        customLocation(etLocation.getText().toString());
                        RatingBar ratingBar = (RatingBar) dialoglayout.findViewById(R.id.ratingBar);
                        sendRatingToServer(ratingBar.getRating());

                    }
                }).show();
    }

    private void sendRatingToServer(float score) {

        RatingRequest request = new RatingRequest();
        request.setListingId(listingId);
        request.setUserId(user.getUserId());
        request.setScore(score);
        request.setListingType(listingType);

        RatingService service = new RatingService(activity.getApplicationContext(), this, false);
        service.execute(request);

        CommonUtilities.toast(activity, "Rating submitted");
    }

    @Override
    public boolean handleMessage(Message message) {
        // TODO: Will handle response later.
        return false;
    }
}
