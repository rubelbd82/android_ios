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
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class NewsListCseActivity extends SherlockListActivity {
	private static final String W = "wid";
	private static final String SE = "stock_exchange";

	private Menu optionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_news_list_cse);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.optionsMenu = menu;

		getSupportMenuInflater().inflate(R.menu.activity_news_list_cse, menu);

		if (CommonUtilities.isNetworkAvailable(this)) {
			setRefreshActionButtonState(true);
			new DownloadFilesTask().execute("readNewsCse,getCseNewsList");
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

	public void getCseNewsList(String regex) {
		setRefreshActionButtonState(false);
		Log.d(W, "getNewsList Started");

		SharedPreferences settings = getSharedPreferences("JSON", 0);

		String readNewsCse = settings.getString("newscsejson", "");

		// ArrayList<HashMap<String, String>> companyList = new
		// ArrayList<HashMap<String, String>>();

		List<NewsCse> newsCseList = new ArrayList<NewsCse>();
		Log.d(W, "getNewsList");

		try {
			Log.d(W, "Trying JSONArray");
			JSONArray jArray = new JSONArray(readNewsCse);

			int length = jArray.length();

			if (length < 1) {
				CommonUtilities.showAlertDialog(this, "Error!",
						"No news found for today.", false);
			}

			for (int i = 0; i < length; i++) {

				JSONObject jobj = jArray.getJSONObject(i);

				String news = (String) jobj.get("n");
				// String compnay_name = (String) jobj.get("c");
				// String expiry_date = (String) jobj.get("e");

				// Log.d(W, "Name is: " + name);

				if (IsMatch(news, regex)) {
					NewsCse newsCse = new NewsCse();
					newsCse.setNews(news);
					// newsCse.setCompany_name(compnay_name);
					// newsCse.setExpiry_date(expiry_date);
					newsCseList.add(newsCse);

				}

			}

			ArrayAdapter<NewsCse> adapter = new NewsCseAdapter(this,
					newsCseList);
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

	public void readNewsCse() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://webindream.com/android/sebangladesh/5/cse_todays_news.php");

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
		editor.putString("newscsejson", rawData);
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
			readNewsCse();
			return totalSize;
		}

		protected void onPostExecute(Long result) {
			getCseNewsList(".*");
		}

	}

}
