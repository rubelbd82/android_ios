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

public class RecordDateDseActivity extends SherlockListActivity {
	private static final String W = "wid";
	private static final String NAME = "name";
	private static final String SE = "stock_exchange";
	
	private Menu optionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_date_dse);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		
		
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem

				String name = ((TextView) view
						.findViewById(R.id.company_name_value)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						CompanyDetails.class);
				in.putExtra(NAME, name);
				in.putExtra("se_name", "DSE");

				in.putExtra(
						SE,
						"http://webindream.com/android/sebangladesh/5/dse_company_details.php?company_name="
								+ name);

			//	startActivity(in);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.optionsMenu = menu;
		
		

		getSupportMenuInflater().inflate(R.menu.activity_record_date_dse, menu);
		
		if (CommonUtilities.isNetworkAvailable(this)) {
	setRefreshActionButtonState(true);
			new DownloadFilesTask().execute("readDSE,getDseList");
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
	
	
	public void getDseList(String regex) {
		setRefreshActionButtonState(false);
		Log.d(W, "getRefreshList Started");

		SharedPreferences settings = getSharedPreferences("JSON", 0);

		String readDSE = settings.getString("recorddatedsejson", "");

		// ArrayList<HashMap<String, String>> companyList = new
		// ArrayList<HashMap<String, String>>();

		List<RecordDateDse> recordDateDseList = new ArrayList<RecordDateDse>();
		Log.d(W, "showRecordDateList");

		try {
			Log.d(W, "Trying JSONArray");
			JSONArray jArray = new JSONArray(readDSE);

			int length = jArray.length();

			for (int i = 0; i < length; i++) {

				JSONObject jobj = jArray.getJSONObject(i);

				String name = (String) jobj.get("n");
				String dividend = (String) jobj.get("di");
				String agm_date = (String) jobj.get("da");
				String record_date = (String) jobj.get("rd");

				// Log.d(W, "Name is: " + name);

				if (IsMatch(name, regex)) {
					RecordDateDse recordDate = new RecordDateDse();
					recordDate.setName(name);
					recordDate.setDividend(dividend);
					recordDate.setAgm_date(agm_date);
					recordDate.setRecord_date(record_date);
					recordDateDseList.add(recordDate);

				}

			}

			ArrayAdapter<RecordDateDse> adapter = new RecordDateDseAdapter(this,
					recordDateDseList);
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

	public void readDSE() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://webindream.com/android/sebangladesh/5/dse_agm.php");

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
		editor.putString("recorddatedsejson", rawData);
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
			readDSE();
			return totalSize;
		}

		protected void onPostExecute(Long result) {
			getDseList(".*");
		}

	}

}
