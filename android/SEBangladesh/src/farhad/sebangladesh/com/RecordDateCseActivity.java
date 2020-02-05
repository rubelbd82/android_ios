package farhad.sebangladesh.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public class RecordDateCseActivity extends SherlockListActivity {
	private static final String W = "wid";
	private static final String NAME = "name";
	private static final String SE = "stock_exchange";
	
	private Menu optionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_date_cse);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Log.d(W, "REcord Date CSE Started");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.optionsMenu = menu;
		
		

		getSupportMenuInflater().inflate(R.menu.activity_record_date_cse, menu);
		
		if (CommonUtilities.isNetworkAvailable(this)) {
	setRefreshActionButtonState(true);
			new DownloadFilesTask().execute("readCse,getCseList");
		} else {
			setRefreshActionButtonState(false);
			CommonUtilities.showAlertDialog(this, "Error!",
					"Check internet connection and try again.", false);
		}
		
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void getCseList(String regex) {
		setRefreshActionButtonState(false);
		Log.d(W, "getRefreshList Started");

		SharedPreferences settings = getSharedPreferences("JSON", 0);

		String readCse = settings.getString("recorddatecsejson", "");

		// ArrayList<HashMap<String, String>> companyList = new
		// ArrayList<HashMap<String, String>>();

		List<RecordDateCse> recordDateCseList = new ArrayList<RecordDateCse>();
		Log.d(W, "showRecordDateList");

		try {
			Log.d(W, "Trying JSONArray");
			JSONArray jArray = new JSONArray(readCse);

			int length = jArray.length();

			for (int i = 0; i < length; i++) {

				JSONObject jobj = jArray.getJSONObject(i);

				String name = (String) jobj.get("n");
				String dividend = (String) jobj.get("di");
				String agm_date = (String) jobj.get("da");
				String record_date = (String) jobj.get("rd");

				// Log.d(W, "Name is: " + name);

				if (IsMatch(name, regex)) {
					RecordDateCse recordDate = new RecordDateCse();
					recordDate.setName(name);
					recordDate.setDividend(dividend);
					recordDate.setAgm_date(agm_date);
					recordDate.setRecord_date(record_date);
					recordDateCseList.add(recordDate);

				}

			}

			ArrayAdapter<RecordDateCse> adapter = new RecordDateCseAdapter(this,
					recordDateCseList);
			setListAdapter(adapter);

		} catch (Exception e) {
			Log.d(W, "JSON Failed");
			e.printStackTrace();
		}

	}
	
	
	
	public void setRefreshActionButtonState(final boolean refreshing) {
		if (optionsMenu != null) {
			final MenuItem refreshItem = optionsMenu
					.findItem(R.id.menu_refresh);
			if (refreshItem != null) {
				if (refreshing) {
					refreshItem
							.setActionView(R.layout.actionbar_indeterminate_progress);
				} else {
					refreshItem.setActionView(null);
				}
			}
		}
	}

	public void readCse() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://webindream.com/android/sebangladesh/5/cse_agm.php");

		try {

			HttpResponse response = client.execute(httpGet);

			StatusLine statusLine = response.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				// CommonUtilities.showAlertDialog(this, "Error!",
				// "Failed to load. Check Internet Connection", false);
			}
		} catch (ClientProtocolException e) {
			// CommonUtilities.showAlertDialog(this, "Error!!",
			// "Failed to load. Check Internet Connection", false);
			e.printStackTrace();
		} catch (IOException e) {
			// CommonUtilities.showAlertDialog(this, "Error!!!",
			// "Failed to load. Check Internet Connection", false);
			e.printStackTrace();
		}
		// return builder.toString();

		String rawData = builder.toString();

		Log.d(W, "raw data is: " + rawData);

		SharedPreferences settings = getSharedPreferences("JSON", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("recorddatecsejson", rawData);
		editor.commit();

	}

	private static boolean IsMatch(String s, String pattern) {
		try {
			Pattern patt = Pattern.compile(pattern);
			Matcher matcher = patt.matcher(s);
			return matcher.matches();
		} catch (RuntimeException e) {
			return false;
		}
	}


	// asyncTask

	private class DownloadFilesTask extends AsyncTask<String, Integer, Long> {

		protected Long doInBackground(String... urls) {

			Log.d(W, "BG started");
			long totalSize = 0;
			readCse();
			return totalSize;
		}

		protected void onPostExecute(Long result) {
			getCseList(".*");
		}

	}

}
