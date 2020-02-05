package farhad.sebangladesh.com;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public class DseIndexesActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dse_indexes);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		
		if (CommonUtilities.isNetworkAvailable(this)) {
			// Let's take some toasts :)
			Toast toast = Toast.makeText(this, "Please Wait...", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else {
			CommonUtilities.showAlertDialog(this, "Error!",
					"Check internet connection and try again.", false);
			return;
		}

		WebView wv1 = (WebView) findViewById(R.id.webView1);
		wv1.loadUrl("http://webindream.com/android/sebangladesh/5/dse_indexes.php?t=1");

		WebView wv2 = (WebView) findViewById(R.id.webView2);
		wv2.loadUrl("http://webindream.com/android/sebangladesh/5/dse_indexes.php?t=2");

		WebView wv3 = (WebView) findViewById(R.id.cse30_index);
		wv3.loadUrl("http://webindream.com/android/sebangladesh/5/dsex_index.html");

		WebView wv4 = (WebView) findViewById(R.id.cscx_index);
		wv4.loadUrl("http://webindream.com/android/sebangladesh/5/dgen_index.html");

		WebView wv5 = (WebView) findViewById(R.id.caspi_index);
		wv5.loadUrl("http://webindream.com/android/sebangladesh/5/ds30_index.html");

		Button btn1 = (Button) findViewById(R.id.btn1);

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentquick = new Intent(Intent.ACTION_VIEW);
				Uri url = Uri
						.parse("http://web.dsebd.org/admin-real/graphdynamictest/dsbi_index.png");
				intentquick.setDataAndType(url, "image/*");

				intentquick.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try {
					startActivity(intentquick);

				} catch (ActivityNotFoundException e) {
					// TODO: handle exception
					// Show Toast...
					showAlertDialog();

				}
			}
		});

		Button b2 = (Button) findViewById(R.id.btn2);

		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentquick = new Intent(Intent.ACTION_VIEW);
				Uri url = Uri
						.parse("http://web.dsebd.org/admin-real/graphdynamictest/dse_gen_index.png");
				intentquick.setDataAndType(url, "image/*");

				intentquick.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				try {
					startActivity(intentquick);

				} catch (ActivityNotFoundException e) {
					// TODO: handle exception
					// Show Toast...
					showAlertDialog();

				}

			}
		});

		Button b3 = (Button) findViewById(R.id.btn3);

		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentquick = new Intent(Intent.ACTION_VIEW);
				Uri url = Uri
						.parse("http://web.dsebd.org/admin-real/graphdynamictest/ds30_index.png");
				intentquick.setDataAndType(url, "image/*");

				intentquick.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try {
					startActivity(intentquick);

				} catch (ActivityNotFoundException e) {
					// TODO: handle exception
					// Show Toast...
					showAlertDialog();

				}
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			super.onBackPressed();

		}
		return true;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_dse_indexes, menu);
		return true;
	}

	public void showAlertDialog() {
		CommonUtilities.showAlertDialog(this, "Error!",
				"Your device doesn't have Image Viewer", false);
	}

}
