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
import android.preference.PreferenceManager;
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

public class DseFavoriteActivity extends SherlockListActivity {
	private static final String W = "wid";
	private static final String NAME = "name";
	private static final String SE = "stock_exchange";
	
	private Menu optionsMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dse_favorite);
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

				startActivity(in);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		this.optionsMenu = menu;

		getSupportMenuInflater().inflate(R.menu.activity_dse_favorite, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		if (null != searchView) {
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
			
			
			
			
			if (CommonUtilities.isNetworkAvailable(this)) {
				setRefreshActionButtonState(true);
				new DownloadFilesTask().execute("readDSE,getDseList");
			} else {
				CommonUtilities.showAlertDialog(this, "Error!",
						"Check internet connection and try again.", false);
				setRefreshActionButtonState(false);
			}
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
		} else if (item.getItemId() == R.id.menu_edit) {
			CommonUtilitiesLocal.goToPrefDse(this);
		} else if (item.getItemId() == R.id.menu_refresh) {

			if (CommonUtilities.isNetworkAvailable(this)) {
				setRefreshActionButtonState(true);
				new DownloadFilesTask().execute("readDSE,getDseList");
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

	public void getDseList(String regex) {
		Log.d(W, "getDseList Started");

		setRefreshActionButtonState(false);
		SharedPreferences settings = getSharedPreferences("JSON", 0);

		String readDSE = settings.getString("dsejson", "");

		// ArrayList<HashMap<String, String>> companyList = new
		// ArrayList<HashMap<String, String>>();

		List<SharePrice> sharePriceList = new ArrayList<SharePrice>();
		Log.d(W, "showPriceList");

		try {
			Log.d(W, "Trying JSONArray");
			JSONArray jArray = new JSONArray(readDSE);

			int length = jArray.length();
			
			if (length < 1) {
				CommonUtilities.showAlertDialog(this, "No Company found",
						"Tap edit button to set your favorite companies or try later. ", false);
			}


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

	public void readDSE() {
		
		String[] cpList = { "1JANATAMF", "1STBSRS", "1STICB", "1STPRIMFMF",
				"2NDICB", "3RDICB", "4THICB", "5THICB", "6THICB", "7THICB",
				"8THICB", "AAMRATECH", "ABB1STMF", "ABBANK", "ACI",
				"ACIFORMULA", "ACIZCBOND", "ACTIVEFINE", "AFTABAUTO",
				"AGNISYSL", "AGRANINS", "AIBL1STIMF", "AIMS1STMF", "AL-HAJTEX",
				"ALARABANK", "ALLTEX", "AMBEEPHA", "AMCL(PRAN)", "ANLIMAYARN",
				"ANWARGALV", "APEXADELFT", "APEXFOODS", "APEXSPINN",
				"APEXTANRY", "ARAMIT", "ARAMITCEM", "ASIAINS", "ASIAPACINS",
				"ATLASBANG", "AZIZPIPES", "BANGAS", "BANKASIA", "BATASHOE",
				"BATBC", "BAYLEASING", "BDAUTOCA", "BDCOM", "BDFINANCE",
				"BDLAMPS", "BDSERVICE", "BDTHAI", "BDWELDING", "BEACHHATCH",
				"BEACONPHAR", "BEDL", "BERGERPBL", "BEXIMCO", "BGIC", "BIFC",
				"BRACBANK", "BRACSCBOND", "BSC", "BSCCL", "BSRMSTEEL",
				"BXPHARMA", "BXSYNTH", "CENTRALINS", "CITYBANK", "CITYGENINS",
				"CMCKAMAL", "CONFIDCEM", "CONTININS", "CVOPRL", "DACCADYE",
				"DAFODILCOM", "DBH", "DBH1STMF", "DEBARACEM", "DEBBDLUGG",
				"DEBBDWELD", "DEBBDZIPP", "DEBBXDENIM", "DEBBXFISH",
				"DEBBXKNI", "DEBBXTEX", "DELTALIFE", "DELTASPINN", "DESCO",
				"DESHBANDHU", "DHAKABANK", "DHAKAINS", "DSHGARME",
				"DULAMIACOT", "DUTCHBANGL", "EASTERNINS", "EASTLAND",
				"EASTRNLUB", "EBL", "EBL1STMF", "EBLNRBMF", "ECABLES", "EHL",
				"EXIMBANK", "FAREASTLIF", "FASFIN", "FBFIF", "FEDERALINS",
				"FINEFOODS", "FIRSTSBANK", "FLEASEINT", "FUWANGCER",
				"FUWANGFOOD", "GBBPOWER", "GEMINISEA", "GLAXOSMITH",
				"GLOBALINS", "GOLDENSON", "GP", "GPHISPAT", "GQBALLPEN",
				"GRAMEEN1", "GRAMEENS2", "GREENDELMF", "GREENDELT",
				"GSPFINANCE", "HAKKANIPUL", "HEIDELBCEM", "HRTEX", "IBBLPBOND",
				"IBNSINA", "ICB", "ICB1STNRB", "ICB2NDNRB", "ICB3RDNRB",
				"ICBAMCL1ST", "ICBAMCL2ND", "ICBEPMF1S1", "ICBIBANK",
				"ICBISLAMIC", "IDLC", "IFIC", "IFIC1STMF", "IFILISLMF1",
				"ILFSL", "IMAMBUTTON", "INTECH", "IPDC", "ISLAMIBANK",
				"ISLAMICFIN", "ISLAMIINS", "ISNLTD", "JAMUNABANK", "JAMUNAOIL",
				"JANATAINS", "JUTESPINN", "KARNAPHULI", "KAY&QUE",
				"KEYACOSMET", "KOHINOOR", "KPCL", "LAFSURCEML", "LANKABAFIN",
				"LEGACYFOOT", "LIBRAINFU", "LINDEBD", "LRGLOBMF1",
				"MAKSONSPIN", "MALEKSPIN", "MARICO", "MBL1STMF", "MEGCONMILK",
				"MEGHNACEM", "MEGHNALIFE", "MEGHNAPET", "MERCANBANK",
				"MERCINS", "METROSPIN", "MICEMENT", "MIDASFIN", "MIRACLEIND",
				"MITHUNKNIT", "MJLBD", "MODERNDYE", "MONNOCERA", "MONNOSTAF",
				"MPETROLEUM", "MTBL", "NATLIFEINS", "NAVANACNG", "NBL",
				"NCCBANK", "NCCBLMF1", "NHFIL", "NITOLINS", "NLI1STMF",
				"NORTHERN", "NORTHRNINS", "NPOLYMAR", "NTC", "NTLTUBES", "OCL",
				"OLYMPIC", "ONEBANKLTD", "ORIONINFU", "PADMALIFE", "PADMAOIL",
				"PARAMOUNT", "PEOPLESINS", "PF1STMF", "PHARMAID", "PHENIXINS",
				"PHOENIXFIN", "PHPMF1", "PIONEERINS", "PLFSL", "POPULAR1MF",
				"POPULARLIF", "POWERGRID", "PRAGATIINS", "PRAGATILIF",
				"PREMIERBAN", "PREMIERLEA", "PRIME1ICBA", "PRIMEBANK",
				"PRIMEFIN", "PRIMEINSUR", "PRIMELIFE", "PRIMETEX",
				"PROGRESLIF", "PROVATIINS", "PUBALIBANK", "PURABIGEN",
				"QSMDRYCELL", "RAHIMAFOOD", "RAHIMTEXT", "RAKCERAMIC",
				"RANFOUNDRY", "RDFOOD", "RECKITTBEN", "RELIANCE1",
				"RELIANCINS", "RENATA", "RENWICKJA", "REPUBLIC", "RNSPIN",
				"RUPALIBANK", "RUPALIINS", "RUPALILIFE", "SAFKOSPINN",
				"SAIHAMCOT", "SAIHAMTEX", "SALAMCRST", "SALVOCHEM",
				"SAMATALETH", "SAMORITA", "SANDHANINS", "SAPORTL", "SAVAREFR",
				"SEBL1STMF", "SHAHJABANK", "SHYAMPSUG", "SIBL", "SINGERBD",
				"SINOBANGLA", "SONALIANSH", "SONARBAINS", "SONARGAON",
				"SOUTHEASTB", "SPCERAMICS", "SQUARETEXT", "SQURPHARMA",
				"STANCERAM", "STANDARINS", "STANDBANKL", "STYLECRAFT",
				"SUMITPOWER", "TAKAFULINS", "TALLUSPIN", "TITASGAS",
				"TRUSTB1MF", "TRUSTBANK", "UCBL", "ULC", "UNIONCAP",
				"UNIQUEHRL", "UNITEDAIR", "UNITEDINS", "USMANIAGL",
				"UTTARABANK", "UTTARAFIN", "ZAHINTEX", "ZEALBANGLA" };

		String companies = "";
		StringBuilder cp = new StringBuilder(companies);

		for (int i = 0; i < cpList.length; i++) {
			cp.append(check_company(cpList[i]));
		}
		
		Log.d(W, "Fetch Link: "+"http://webindream.com/android/sebangladesh/5/dse_fav.php?companies="
						+ cp);

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://webindream.com/android/sebangladesh/5/dse_fav.php?companies="
						+ cp);
		
		

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
		editor.putString("dsejson", rawData);
		editor.commit();

	}
	
	
	public String check_company(String company) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);

		Boolean COMPANYNAME = settings.getBoolean(company, false);
		if (COMPANYNAME == true) {
			return company + "yw";
		} else {
			return "";
		}

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

		getDseList(Regex);

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