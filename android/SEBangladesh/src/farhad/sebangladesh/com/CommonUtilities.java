package farhad.sebangladesh.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.net.ConnectivityManager;

public class CommonUtilities {

	public static boolean isNetworkAvailable(Context context) {
		return ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo() != null;
	}

	public static void showAlertDialog(Context context, String title,
			String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		if (status != null)
			// Setting alert dialog icon
			alertDialog.setIcon((status) ? R.drawable.ic_launcher
					: R.drawable.ic_launcher);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public static void setText(Activity activity, int id, String text) {
		TextView tv = (TextView) activity.findViewById(id);
		tv.setText(text);
	}

	public static void setButtonLabel(Activity activity, int id, String text) {
		Button b = (Button) activity.findViewById(id);
		b.setText(text);
	}

	public static void hideButton(Activity activity, int id) {
		Button b = (Button) activity.findViewById(id);
		b.setVisibility(View.GONE);

	}

	public static String getText(Activity activity, int id) {
		EditText et = (EditText) activity.findViewById(id);
		return et.getText().toString();
	}

	public static String getTextViewText(Activity activity, int id) {
		TextView et = (TextView) activity.findViewById(id);
		return et.getText().toString();
	}

	public static boolean getCBChecked(Activity activity, int id) {
		CheckBox cb = (CheckBox) activity.findViewById(id);
		return cb.isChecked();
	}

	public static void setCBChecked(Activity activity, int id, boolean checked) {
		CheckBox cb = (CheckBox) activity.findViewById(id);
		cb.setChecked(checked);
	}

	public static String getSpinnerText(Activity activity, int id) {
		Spinner mySpinner = (Spinner) activity.findViewById(id);
		return mySpinner.getSelectedItem().toString();
	}

	public static void hideSpinner(Activity activity, int id) {
		Spinner mySpinner = (Spinner) activity.findViewById(id);
		mySpinner.setVisibility(View.GONE);
	}

	public static void showSpinner(Activity activity, int id) {
		Spinner mySpinner = (Spinner) activity.findViewById(id);
		mySpinner.setVisibility(View.VISIBLE);
	}

	public static boolean checkExternalStorage() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateForm(Activity activity, String[] elements) {
		String msg = "";
		for (int i = 0; i < elements.length; i++) {
			String element = elements[i];

			int resId = activity.getResources().getIdentifier(element, "id",
					activity.getPackageName());
			String text = getText(activity, resId);
			Log.i("wid", "ResID: " + resId);
			if (text.equalsIgnoreCase("")) {

				String eleName = element.replace("_", " ");
				eleName = eleName.toUpperCase();
				msg += eleName + "\n";
			}
		}

		if (!msg.equalsIgnoreCase("")) {
			showAlertDialog(activity, "Oops! Missed something.",
					"Please fill-in the following fileds:\n" + msg, false);
			return false;
		} else {
			return true;
		}

	}

	public static Bitmap getResizedBitmap(Bitmap image, int newWidth,
			int newHeight) {
		newWidth = newWidth / 2;
		newHeight = newHeight / 2;

		int width = image.getWidth();
		int height = image.getHeight();
		Log.i("wid", "width" + width);

		int bound_width = newWidth;
		int bound_height = newHeight;
		int new_width = width;
		int new_height = height;

		if (width > bound_width) {
			// scale width to fit
			new_width = bound_width;
			// scale height to maintain aspect ratio
			new_height = (new_width * height) / width;
		}

		if (new_height > bound_height) {
			// scale height to fit instead
			new_height = bound_height;
			// scale width to maintain aspect ratio
			new_width = (new_height * width) / height;
		}

		float scaleWidth = ((float) new_width) / width;
		float scaleHeight = ((float) new_height) / height;

		Log.i("wid", "scaleWidth" + scaleWidth);
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

	public static String currentDate() {
		Date date = new Date();
		SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.US);
		String formattedDate = "" + FORMATTER.format(date);
		return formattedDate;
	}

	public static void moveFile(String inputPath, String inputFile,
			String outputPath) {

		InputStream in = null;
		OutputStream out = null;
		try {

			// create output directory if it doesn't exist
			File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			in = new FileInputStream(inputPath + inputFile);
			out = new FileOutputStream(outputPath + inputFile);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			in = null;

			// write the output file
			out.flush();
			out.close();
			out = null;

			// delete the original file
			new File(inputPath + inputFile).delete();

		}

		catch (FileNotFoundException fnfe1) {
			Log.e("tag", fnfe1.getMessage());
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

	}

	public static void deleteFile(String inputPath, String inputFile) {
		try {
			// delete the original file
			new File(inputPath + inputFile).delete();

		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}
	}

	public static void copyFile(String inputPath, String inputFile,
			String outputPath) {
		Log.i("wid", "inputFile: " + inputPath + inputFile);

		Log.i("wid", "outputFile: " + outputPath);
		InputStream in = null;
		OutputStream out = null;
		try {

			// create output directory if it doesn't exist
			File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			in = new FileInputStream(inputPath + inputFile);
			out = new FileOutputStream(outputPath + inputFile);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			in = null;

			// write the output file (You have now copied the file)
			out.flush();
			out.close();
			out = null;

		} catch (FileNotFoundException fnfe1) {
			Log.e("tag", fnfe1.getMessage());
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

	}

	public static void setItemOfSpinner(Activity activity, int id, String val) {
		Spinner mySpinner = (Spinner) activity.findViewById(id);
		ArrayAdapter myAdap = (ArrayAdapter) mySpinner.getAdapter(); // cast
																		// to
																		// an
																		// ArrayAdapter
		int spinnerPosition = myAdap.getPosition(val);
		// set the default according to value
		mySpinner.setSelection(spinnerPosition);
	}

	public static String removeHtmlTags(String in) {
		in = in.replaceAll("<.*?>", "");
		return in;
	}

}
