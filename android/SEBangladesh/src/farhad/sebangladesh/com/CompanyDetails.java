package farhad.sebangladesh.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;



public class CompanyDetails extends SherlockActivity {

	private static final String NAME = "name";
	private static final String LTP = "ltp";
	private static final String CHANGE = "change";
	private static final String PERCENT_CHANGE = "percent_change";
	// private static final String FULL_NAME = "full_name";
	private static final String SE = "stock_exchange";
	private static final String DAY_RANGE = "day_range";
	private static final String YEAR_RANGE = "year_range";
	private static final String FACE_VALUE = "face_value";
	private static final String MARKET_LOT = "market_lot";
	private static final String MARKET_CAPITAL = "market_capital";
	private static final String AUTHORIZED_CAPITAL = "authorized_capital";
	private static final String PAIDUP_CAPITAL = "paidup_capital";
	private static final String RESERVE = "reserve";
	private static final String MARKET_CATEGORY = "market_category";
	private static final String EPS = "eps";
	private static final String PE_RATIO = "pe_ratio";
	private static final String LAST_UPDATE = "last_update";
	private static final String VOLUME_TRADED = "volume_traded";
	private static String se_url = "";
	private static String se_name = "";

	private static final String W = "wid";
	
	private Menu optionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_details);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		

		

		Intent i = getIntent();
		String name = i.getStringExtra(NAME);
		se_url = i.getStringExtra(SE);
		se_name = i.getStringExtra("se_name");
		CommonUtilities.setText(this, R.id.company_name, name);

		// new DownloadFilesTask().execute("populateCompanyDetails");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.optionsMenu = menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_company_details, menu);
		


		
		if (CommonUtilities.isNetworkAvailable(this)) {
			setRefreshActionButtonState(true);
			new DownloadFilesTask().execute("readSE,populateCompanyDetails");
		} else {
			CommonUtilities.showAlertDialog(this, "Error!",
					"Check internet connection and try again.", false);
			setRefreshActionButtonState(false);
		}
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			// app icon in action bar clicked; go home
			Intent intent;

//			if (se_name.equalsIgnoreCase("DSE")) {
//				intent = new Intent(this, DseListActivity.class);
//			} else if (se_name.equalsIgnoreCase("CSE")) {
//				intent = new Intent(this, CseListActivity.class);
//			} else {
//				intent = new Intent(this, DseListActivity.class);
//			}
//
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			super.onBackPressed();
			
			//startActivity(intent);
			return true;
		} else if (item.getItemId() == R.id.menu_refresh) {
			if (CommonUtilities.isNetworkAvailable(this)) {
				setRefreshActionButtonState(true);
				new DownloadFilesTask().execute("readSE,populateCompanyDetails");
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
	                refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
	            } else {
	                refreshItem.setActionView(null);
	            }
	        }
	    }
	}

	public void populateCompanyDetails() {
		setRefreshActionButtonState(false);

		SharedPreferences settings = getSharedPreferences("JSON", 0);

		String readSE = settings.getString("companyDetails", "");

		try {
			// JSONArray jArray = new JSONArray(readCSE);

			JSONObject jobj = new JSONObject(readSE);

			/*
			 * TextView txtName = (TextView) findViewById(R.id.name);
			 * txtName.setText(readCSE);
			 */

			// JSONObject jobj = jArray.getJSONObject(0);

			// String name = (String) jobj.get(NAME);
			String ltp = (String) jobj.get(LTP);
			String last_update = (String) jobj.get(LAST_UPDATE);
			String change = (String) jobj.get(CHANGE);
			String percent_change = (String) jobj.get(PERCENT_CHANGE);
			String volume_traded = (String) jobj.get(VOLUME_TRADED);
			// String full_name = (String) jobj.get(FULL_NAME);
			String day_range = (String) jobj.get(DAY_RANGE);
			String year_range = (String) jobj.get(YEAR_RANGE);
			String face_value = (String) jobj.get(FACE_VALUE);
			String market_lot = (String) jobj.get(MARKET_LOT);
			String market_capital = (String) jobj.get(MARKET_CAPITAL);
			String authorized_capital = (String) jobj.get(AUTHORIZED_CAPITAL);
			String paidup_capital = (String) jobj.get(PAIDUP_CAPITAL);
			String reserve = (String) jobj.get(RESERVE);
			String market_category = (String) jobj.get(MARKET_CATEGORY);

			String eps = (String) jobj.get(EPS);
			String pe_ratio = (String) jobj.get(PE_RATIO);

			CommonUtilities.setText(this, R.id.ltp, ltp);
			CommonUtilities.setText(this, R.id.last_update, last_update);
			CommonUtilities.setText(this, R.id.change, change);
			CommonUtilities.setText(this, R.id.percent_change, percent_change);
			CommonUtilities.setText(this, R.id.volume_traded, volume_traded);
			CommonUtilities.setText(this, R.id.day_range, day_range);
			CommonUtilities.setText(this, R.id.year_range, year_range);
			CommonUtilities.setText(this, R.id.face_value, face_value);
			CommonUtilities.setText(this, R.id.market_lot, market_lot);
			CommonUtilities.setText(this, R.id.market_capital, market_capital);
			CommonUtilities.setText(this, R.id.authorized_capital,
					authorized_capital);
			CommonUtilities.setText(this, R.id.paidup_capital, paidup_capital);
			CommonUtilities.setText(this, R.id.reserve, reserve);
			CommonUtilities
					.setText(this, R.id.market_category, market_category);
			// CommonUtilities.setText(this, R.id.eps, eps);
			CommonUtilities.setText(this, R.id.pe_ratio, pe_ratio);

			/* listCompanies.add(name) ; AAAA */

			/*
			 * ListAdapter adapter = new SimpleAdapter(this, companyList,
			 * R.layout.list_item, new String[] { NAME, LTP, CHANGE,
			 * PERCENT_CHANGE }, new int[] { R.id.name, R.id.ltp, R.id.change,
			 * R.id.percent_change });
			 */

			// setListAdapter(adapter);

		} catch (Exception e) {

			// CommonUtilities.setText(this, R.id.company_name,
			// "Check Internet Connection");

			Log.d(W, "No Internet connection");

			e.printStackTrace();
		}

	}

	public String readSE(String se_url) {
		Log.d(W, "se url is: " + se_url);
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(se_url);

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
				CommonUtilities.setText(this, R.id.company_name_value,
						"Check Internet Connection");

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		  String rawData = builder.toString();
		  
		  SharedPreferences settings = getSharedPreferences("JSON", 0);
		  SharedPreferences.Editor editor = settings.edit();
		  editor.putString("companyDetails", rawData); editor.commit();
		 
		return builder.toString();
	}

	private class DownloadFilesTask extends AsyncTask<String, Integer, Long> {

		protected Long doInBackground(String... urls) {

			Log.d(W, "BG started");
			long totalSize = 0;
			readSE(se_url);
			return totalSize;
		}

		protected void onPostExecute(Long result) {
			// getDseList(".*");
			// Nothing to run
			populateCompanyDetails();
		}

	}

}
