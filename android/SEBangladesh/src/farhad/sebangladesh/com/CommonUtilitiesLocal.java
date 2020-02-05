package farhad.sebangladesh.com;

import android.app.Activity;
import android.content.Intent;

public class CommonUtilitiesLocal {
	public static void goToDseList(Activity activity) {
		 Intent i = new Intent(activity, DseListActivity.class); 
		 activity.startActivity(i);
	 }
	
	public static void goToCseList(Activity activity) {
		 Intent i = new Intent(activity, CseListActivity.class);
		 activity.startActivity(i);
	 }
	
	public static void goToRecordDateDse(Activity activity) {
		 Intent i = new Intent(activity, RecordDateDseActivity.class);
		 activity.startActivity(i);
	 }
	
	public static void goToRecordDateCse(Activity activity) {
		 Intent i = new Intent(activity, RecordDateCseActivity.class);
		 activity.startActivity(i);
	 }
	
	public static void goToNewsListDse(Activity activity) {
		 Intent i = new Intent(activity, NewsListDseActivity.class);
		 activity.startActivity(i);
	}
	
	
	public static void goToNewsListCse(Activity activity) {
		 Intent i = new Intent(activity, NewsListCseActivity.class);
		 activity.startActivity(i);
	 }
	
	
	public static void goToDseFav(Activity activity) {
		 Intent i = new Intent(activity, DseFavoriteActivity.class);
		 activity.startActivity(i);
	 }
	
	public static void goToCseFav(Activity activity) {
		 Intent i = new Intent(activity, CseFavoriteActivity.class);
		 activity.startActivity(i);
	 }
	
	public static void goToPrefDse(Activity activity) {
		 Intent i = new Intent(activity, PreferencesActivity.class);
		 activity.startActivity(i);
	 }
	
	public static void goToPrefCse(Activity activity) {
		 Intent i = new Intent(activity, PreferencescseActivity.class);
		 activity.startActivity(i);
	 }
	
	public static void goToDseIndexes(Activity activity) {
		 Intent i = new Intent(activity, DseIndexesActivity.class);
		 activity.startActivity(i);
	 }
	
	public static void goToCseIndexes(Activity activity) {
		 Intent i = new Intent(activity, CseIndexesActivity.class);
		 activity.startActivity(i);
	 }
}
