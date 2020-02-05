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

import android.app.SearchManager;
import android.content.Context;
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
import com.actionbarsherlock.widget.SearchView;


public class CseListActivity extends SherlockListActivity {
	private static final String W = "wid";
	private static final String NAME = "name";
	private static final String SE = "stock_exchange";

	private Menu optionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cse_list);

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
						"http://webindream.com/android/sebangladesh/5/cse_company_details.php?company_name="
								+ name);

				startActivity(in);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		this.optionsMenu = menu;

		getSupportMenuInflater().inflate(R.menu.activity_dse_list, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		if (null != searchView) {
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
		}

		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			public boolean onQueryTextChange(String newText) {
				// this is your adapter that will be filtered
				// adapter.getFilter().filter(newText);
				Log.d(W, "typed content is: " + newText);

				btnFilter(newText);

				// I've to work here later
				return true;
			}

			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				// adapter.getFilter().filter(query);

				// I've to work here later
				Log.d(W, "typed content is: " + query);

				btnFilter(query);

				return true;
			}
		};
		searchView.setOnQueryTextListener(queryTextListener);

		if (CommonUtilities.isNetworkAvailable(this)) {
			setRefreshActionButtonState(true);
			new DownloadFilesTask().execute("readCSE,getCseList");
		} else {
			CommonUtilities.showAlertDialog(this, "Error!",
					"Check internet connection and try again.", false);
			setRefreshActionButtonState(false);
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
		} else if (item.getItemId() == R.id.menu_refresh) {

			if (CommonUtilities.isNetworkAvailable(this)) {
				setRefreshActionButtonState(true);
				new DownloadFilesTask().execute("readCSE,getCseList");
			} else {
				CommonUtilities.showAlertDialog(this, "Error!",
						"Check internet connection and try again.", false);
				setRefreshActionButtonState(false);
			}

		}

		return super.onOptionsItemSelected(item);
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

	public void getCseList(String regex) {
		Log.d(W, "getCseList Started");

		setRefreshActionButtonState(false);
		SharedPreferences settings = getSharedPreferences("JSON", 0);

		String readCSE = settings.getString("csejson", "");

		// ArrayList<HashMap<String, String>> companyList = new
		// ArrayList<HashMap<String, String>>();

		List<SharePrice> sharePriceList = new ArrayList<SharePrice>();
		Log.d(W, "showPriceList");

		try {
			Log.d(W, "Trying JSONArray");
			JSONArray jArray = new JSONArray(readCSE);

			int length = jArray.length();

			for (int i = 0; i < length; i++) {

				JSONObject jobj = jArray.getJSONObject(i);

				String name = (String) jobj.get("n");
				String ltp = (String) jobj.get("l");
				String change = (String) jobj.get("c");
				String percent_change = (String) jobj.get("pc");

				// Log.d(W, "Name is: " + name);

				if (IsMatch(name, regex)) {
					SharePrice sharePrice = new SharePrice();
					sharePrice.setName(name);
					sharePrice.setChange(change);
					sharePrice.setPercentChange(percent_change);
					sharePrice.setLtp(ltp);
					sharePriceList.add(sharePrice);

				}

			}

			ArrayAdapter<SharePrice> adapter = new PriceListAdapter(this,
					sharePriceList);
			setListAdapter(adapter);

		} catch (Exception e) {
			Log.d(W, "JSON Failed");
			e.printStackTrace();
		}

	}

	public void readCSE() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://webindream.com/android/sebangladesh/5/cse.php");

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
//				Toast toast = Toast.makeText(CseListActivity.this, "Please Check Internet Connection", 5000);
//		    	toast.setGravity(Gravity.CENTER, 0, 0);
//		    	toast.show();
			//	CommonUtilities.showAlertDialog(this, "Error!", "Failed to load. Check Internet Connection", false);
			}
		} catch (ClientProtocolException e) {
			//CommonUtilities.showAlertDialog(this, "Error!!", "Failed to load. Check Internet Connection", false);
//			Toast toast = Toast.makeText(CseListActivity.this, "Please Check Internet Connection", 5000);
//	    	toast.setGravity(Gravity.CENTER, 0, 0);
//	    	toast.show();
			e.printStackTrace();
		} catch (IOException e) {
//			Toast toast = Toast.makeText(CseListActivity.this, "Please Check Internet Connection", 5000);
//	    	toast.setGravity(Gravity.CENTER, 0, 0);
//	    	toast.show();
			e.printStackTrace();
		}
		// return builder.toString();

		String rawData = builder.toString();

		Log.d(W, "raw data is: " + rawData);

		SharedPreferences settings = getSharedPreferences("JSON", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("csejson", rawData);
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

	public void btnFilter(String query) {
		// ^A(.*)
		query = query.toUpperCase();
		String Regex = "^" + query + "(.*)";

		getCseList(Regex);

	}

	// asyncTask

	private class DownloadFilesTask extends AsyncTask<String, Integer, Long> {

		protected Long doInBackground(String... urls) {

			Log.d(W, "BG started");
			long totalSize = 0;
			readCSE();
			return totalSize;
		}

		protected void onPostExecute(Long result) {
			getCseList(".*");
		}

	}

}
