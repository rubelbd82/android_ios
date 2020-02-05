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


public class CseFavoriteActivity extends SherlockListActivity {
	private static final String W = "wid";
	private static final String NAME = "name";
	private static final String SE = "stock_exchange";
	
	private Menu optionsMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cse_favorite);
		
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

		getSupportMenuInflater().inflate(R.menu.activity_cse_favorite, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		if (null != searchView) {
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
			
			
			
			
			if (CommonUtilities.isNetworkAvailable(this)) {
				setRefreshActionButtonState(true);
				new DownloadFilesTask().execute("readDSE,getCseList");
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
			CommonUtilitiesLocal.goToPrefCse(this);
		} else if (item.getItemId() == R.id.menu_refresh) {

			if (CommonUtilities.isNetworkAvailable(this)) {
				setRefreshActionButtonState(true);
				new DownloadFilesTask().execute("readDSE,getCseList");
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

		String readDSE = settings.getString("csejson", "");

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
		
		String[] cpListCse = { "CSE-1JANATAMF", "CSE-1JANATAMF",
				"CSE-1STPRIMFMF", "CSE-1STPRIMFMF", "CSE-6THICB", "CSE-6THICB",
				"CSE-AAMRATECH", "CSE-AAMRATECH", "CSE-ABBANK", "CSE-ABBANK",
				"CSE-ACI", "CSE-ACI", "CSE-ACIFORMULA", "CSE-ACIFORMULA",
				"CSE-ACIZCBOND", "CSE-ACIZCBOND", "CSE-ACTIVEFINE",
				"CSE-ACTIVEFINE", "CSE-AFTABAUTO", "CSE-AFTABAUTO",
				"CSE-AGNISYSL", "CSE-AGNISYSL", "CSE-AIMS1STMF",
				"CSE-AIMS1STMF", "CSE-ALARABANK", "CSE-ALARABANK",
				"CSE-AMBEEPHA", "CSE-AMBEEPHA", "CSE-ANLIMAYARN",
				"CSE-ANLIMAYARN", "CSE-APEXTANRY", "CSE-APEXTANRY",
				"CSE-ARAMITCEM", "CSE-ARAMITCEM", "CSE-ASIAINS", "CSE-ASIAINS",
				"CSE-ASIAPACINS", "CSE-ASIAPACINS", "CSE-AZIZPIPES",
				"CSE-AZIZPIPES", "CSE-BANGAS", "CSE-BANGAS", "CSE-BANKASIA",
				"CSE-BANKASIA", "CSE-BATASHOE", "CSE-BATASHOE", "CSE-BATBC",
				"CSE-BATBC", "CSE-BAYLEASING", "CSE-BAYLEASING", "CSE-BDCOM",
				"CSE-BDCOM", "CSE-BDFINANCE", "CSE-BDFINANCE", "CSE-BDTHAI",
				"CSE-BDTHAI", "CSE-BDWELDING", "CSE-BDWELDING",
				"CSE-BEACHHATCH", "CSE-BEACHHATCH", "CSE-BEACONPHAR",
				"CSE-BEACONPHAR", "CSE-BEDL", "CSE-BEDL", "CSE-BEXIMCO",
				"CSE-BEXIMCO", "CSE-BGIC", "CSE-BGIC", "CSE-BIFC", "CSE-BIFC",
				"CSE-BRACBANK", "CSE-BRACBANK", "CSE-BSC", "CSE-BSC",
				"CSE-BSCCL", "CSE-BSCCL", "CSE-BSRMSTEEL", "CSE-BSRMSTEEL",
				"CSE-BXPHARMA", "CSE-BXPHARMA", "CSE-BXSYNTH", "CSE-BXSYNTH",
				"CSE-CITYBANK", "CSE-CITYBANK", "CSE-CITYGENINS",
				"CSE-CITYGENINS", "CSE-CONFIDCEM", "CSE-CONFIDCEM",
				"CSE-CONTININS", "CSE-CONTININS", "CSE-CVOPRL", "CSE-CVOPRL",
				"CSE-DACCADYE", "CSE-DACCADYE", "CSE-DAFODILCOM",
				"CSE-DAFODILCOM", "CSE-DBH1STMF", "CSE-DBH1STMF",
				"CSE-DELTASPINN", "CSE-DELTASPINN", "CSE-DESCO", "CSE-DESCO",
				"CSE-DESHBANDHU", "CSE-DESHBANDHU", "CSE-DHAKABANK",
				"CSE-DHAKABANK", "CSE-EASTERNINS", "CSE-EASTERNINS",
				"CSE-EASTLAND", "CSE-EASTLAND", "CSE-EBL", "CSE-EBL",
				"CSE-EBL1STMF", "CSE-EBL1STMF", "CSE-ECABLES", "CSE-ECABLES",
				"CSE-EHL", "CSE-EHL", "CSE-EXIMBANK", "CSE-EXIMBANK",
				"CSE-FAREASTLIF", "CSE-FAREASTLIF", "CSE-FASFIN", "CSE-FASFIN",
				"CSE-FEDERALINS", "CSE-FEDERALINS", "CSE-FINEFOODS",
				"CSE-FINEFOODS", "CSE-FIRSTSBANK", "CSE-FIRSTSBANK",
				"CSE-FLEASEINT", "CSE-FLEASEINT", "CSE-FUWANGCER",
				"CSE-FUWANGCER", "CSE-FUWANGFOOD", "CSE-FUWANGFOOD",
				"CSE-GBBPOWER", "CSE-GBBPOWER", "CSE-GOLDENSON",
				"CSE-GOLDENSON", "CSE-GP", "CSE-GP", "CSE-GPHISPAT",
				"CSE-GPHISPAT", "CSE-GQBALLPEN", "CSE-GQBALLPEN",
				"CSE-GRAMEEN1", "CSE-GRAMEEN1", "CSE-GRAMEENS2",
				"CSE-GRAMEENS2", "CSE-GREENDELMF", "CSE-GREENDELMF",
				"CSE-GREENDELT", "CSE-GREENDELT", "CSE-GSPFINANCE",
				"CSE-GSPFINANCE", "CSE-HEIDELBCEM", "CSE-HEIDELBCEM",
				"CSE-HRTEX", "CSE-HRTEX", "CSE-IBBLPBOND", "CSE-IBBLPBOND",
				"CSE-ICB", "CSE-ICB", "CSE-ICB2NDNRB", "CSE-ICB2NDNRB",
				"CSE-ICB3RDNRB", "CSE-ICB3RDNRB", "CSE-ICBAMCL1ST",
				"CSE-ICBAMCL1ST", "CSE-ICBAMCL2ND", "CSE-ICBAMCL2ND",
				"CSE-ICBEPMF1S1", "CSE-ICBEPMF1S1", "CSE-IDLC", "CSE-IDLC",
				"CSE-IFIC", "CSE-IFIC", "CSE-IFIC1STMF", "CSE-IFIC1STMF",
				"CSE-IFILISLMF1", "CSE-IFILISLMF1", "CSE-ILFSL", "CSE-ILFSL",
				"CSE-IMAMBUTTON", "CSE-IMAMBUTTON", "CSE-INTECH", "CSE-INTECH",
				"CSE-IPDC", "CSE-IPDC", "CSE-ISLAMIBANK", "CSE-ISLAMIBANK",
				"CSE-ISLAMICFIN", "CSE-ISLAMICFIN", "CSE-ISLAMIINS",
				"CSE-ISLAMIINS", "CSE-ISNLTD", "CSE-ISNLTD", "CSE-JAMUNABANK",
				"CSE-JAMUNABANK", "CSE-JAMUNAOIL", "CSE-JAMUNAOIL",
				"CSE-JANATAINS", "CSE-JANATAINS", "CSE-KAY&QUE", "CSE-KAY&QUE",
				"CSE-KEYACOSMET", "CSE-KEYACOSMET", "CSE-KPCL", "CSE-KPCL",
				"CSE-LAFSURCEML", "CSE-LAFSURCEML", "CSE-LANKABAFIN",
				"CSE-LANKABAFIN", "CSE-LEGACYFOOT", "CSE-LEGACYFOOT",
				"CSE-MAKSONSPIN", "CSE-MAKSONSPIN", "CSE-MALEKSPIN",
				"CSE-MALEKSPIN", "CSE-MEGHNACEM", "CSE-MEGHNACEM",
				"CSE-MEGHNALIFE", "CSE-MEGHNALIFE", "CSE-MERCANBANK",
				"CSE-MERCANBANK", "CSE-MERCINS", "CSE-MERCINS",
				"CSE-METROSPIN", "CSE-METROSPIN", "CSE-MICEMENT",
				"CSE-MICEMENT", "CSE-MIDASFIN", "CSE-MIDASFIN",
				"CSE-MIRACLEIND", "CSE-MIRACLEIND", "CSE-MITHUNKNIT",
				"CSE-MITHUNKNIT", "CSE-MJLBD", "CSE-MJLBD", "CSE-MPETROLEUM",
				"CSE-MPETROLEUM", "CSE-MTBL", "CSE-MTBL", "CSE-NATLIFEINS",
				"CSE-NATLIFEINS", "CSE-NAVANACNG", "CSE-NAVANACNG", "CSE-NBL",
				"CSE-NBL", "CSE-NCCBANK", "CSE-NCCBANK", "CSE-NHFIL",
				"CSE-NHFIL", "CSE-NITOLINS", "CSE-NITOLINS", "CSE-NORTHRNINS",
				"CSE-NORTHRNINS", "CSE-OCL", "CSE-OCL", "CSE-OLYMPIC",
				"CSE-OLYMPIC", "CSE-ONEBANKLTD", "CSE-ONEBANKLTD",
				"CSE-PADMALIFE", "CSE-PADMALIFE", "CSE-PADMAOIL",
				"CSE-PADMAOIL", "CSE-PARAMOUNT", "CSE-PARAMOUNT",
				"CSE-PEOPLESINS", "CSE-PEOPLESINS", "CSE-PF1STMF",
				"CSE-PF1STMF", "CSE-PHENIXINS", "CSE-PHENIXINS",
				"CSE-PHOENIXFIN", "CSE-PHOENIXFIN", "CSE-PHPMF1", "CSE-PHPMF1",
				"CSE-PLFSL", "CSE-PLFSL", "CSE-POPULAR1MF", "CSE-POPULAR1MF",
				"CSE-POWERGRID", "CSE-POWERGRID", "CSE-PREMIERBAN",
				"CSE-PREMIERBAN", "CSE-PREMIERLEA", "CSE-PREMIERLEA",
				"CSE-PRIME1ICBA", "CSE-PRIME1ICBA", "CSE-PRIMEBANK",
				"CSE-PRIMEBANK", "CSE-PRIMEFIN", "CSE-PRIMEFIN",
				"CSE-PRIMELIFE", "CSE-PRIMELIFE", "CSE-PROVATIINS",
				"CSE-PROVATIINS", "CSE-PUBALIBANK", "CSE-PUBALIBANK",
				"CSE-QSMDRYCELL", "CSE-QSMDRYCELL", "CSE-RAHIMAFOOD",
				"CSE-RAHIMAFOOD", "CSE-RAKCERAMIC", "CSE-RAKCERAMIC",
				"CSE-RDFOOD", "CSE-RDFOOD", "CSE-REPUBLIC", "CSE-REPUBLIC",
				"CSE-RNSPIN", "CSE-RNSPIN", "CSE-RUPALIBANK", "CSE-RUPALIBANK",
				"CSE-RUPALIINS", "CSE-RUPALIINS", "CSE-SAFKOSPINN",
				"CSE-SAFKOSPINN", "CSE-SAIHAMCOT", "CSE-SAIHAMCOT",
				"CSE-SAIHAMTEX", "CSE-SAIHAMTEX", "CSE-SALAMCRST",
				"CSE-SALAMCRST", "CSE-SALVOCHEM", "CSE-SALVOCHEM",
				"CSE-SANDHANINS", "CSE-SANDHANINS", "CSE-SAPORTL",
				"CSE-SAPORTL", "CSE-SEBL1STMF", "CSE-SEBL1STMF",
				"CSE-SHAHJABANK", "CSE-SHAHJABANK", "CSE-SIBL", "CSE-SIBL",
				"CSE-SINGERBD", "CSE-SINGERBD", "CSE-SINOBANGLA",
				"CSE-SINOBANGLA", "CSE-SONARBAINS", "CSE-SONARBAINS",
				"CSE-SONARGAON", "CSE-SONARGAON", "CSE-SOUTHEASTB",
				"CSE-SOUTHEASTB", "CSE-SPCERAMICS", "CSE-SPCERAMICS",
				"CSE-SQUARETEXT", "CSE-SQUARETEXT", "CSE-SQURPHARMA",
				"CSE-SQURPHARMA", "CSE-STANDARINS", "CSE-STANDARINS",
				"CSE-STANDBANKL", "CSE-STANDBANKL", "CSE-SUMITPOWER",
				"CSE-SUMITPOWER", "CSE-TAKAFULINS", "CSE-TAKAFULINS",
				"CSE-TALLUSPIN", "CSE-TALLUSPIN", "CSE-TITASGAS",
				"CSE-TITASGAS", "CSE-TRUSTB1MF", "CSE-TRUSTB1MF",
				"CSE-TRUSTBANK", "CSE-TRUSTBANK", "CSE-UCBL", "CSE-UCBL",
				"CSE-UNIONCAP", "CSE-UNIONCAP", "CSE-UNIQUEHRL",
				"CSE-UNIQUEHRL", "CSE-UNITEDAIR", "CSE-UNITEDAIR",
				"CSE-UTTARABANK", "CSE-UTTARABANK", "CSE-UTTARAFIN",
				"CSE-UTTARAFIN", "CSE-ZAHINTEX", "CSE-ZAHINTEX",
				"CSE-1JANATAMF", "CSE-1STBSRS", "CSE-1STICB", "CSE-1STPRIMFMF",
				"CSE-2NDICB", "CSE-3RDICB", "CSE-4THICB", "CSE-5THICB",
				"CSE-6THICB", "CSE-7THICB", "CSE-8THICB", "CSE-AAMRATECH",
				"CSE-ABB1STMF", "CSE-ABBANK", "CSE-ACI", "CSE-ACIFORMULA",
				"CSE-ACIZCBOND", "CSE-ACTIVEFINE", "CSE-AFTABAUTO",
				"CSE-AGNISYSL", "CSE-AIBL1STIMF", "CSE-AIMS1STMF",
				"CSE-ALARABANK", "CSE-ALLTEX", "CSE-AMBEEPHA",
				"CSE-AMCL(PRAN)", "CSE-ANLIMAYARN", "CSE-ANWARGALV",
				"CSE-APEXADELFT", "CSE-APEXFOODS", "CSE-APEXSPINN",
				"CSE-APEXTANRY", "CSE-ARAMIT", "CSE-ARAMITCEM", "CSE-ASIAINS",
				"CSE-ASIAPACINS", "CSE-AZIZPIPES", "CSE-BANGAS",
				"CSE-BANKASIA", "CSE-BATASHOE", "CSE-BATBC", "CSE-BAYLEASING",
				"CSE-BDAUTOCA", "CSE-BDCOM", "CSE-BDFINANCE", "CSE-BDLAMPS",
				"CSE-BDTHAI", "CSE-BDWELDING", "CSE-BEACHHATCH",
				"CSE-BEACONPHAR", "CSE-BEDL", "CSE-BERGERPBL", "CSE-BEXIMCO",
				"CSE-BGIC", "CSE-BIFC", "CSE-BRACBANK", "CSE-BRACSCBOND",
				"CSE-BSC", "CSE-BSCCL", "CSE-BSRMSTEEL", "CSE-BXPHARMA",
				"CSE-BXSYNTH", "CSE-CENTRALINS", "CSE-CITYBANK",
				"CSE-CITYGENINS", "CSE-CONFIDCEM", "CSE-CONTININS",
				"CSE-CTGVEG", "CSE-CVOPRL", "CSE-DACCADYE", "CSE-DAFODILCOM",
				"CSE-DBH", "CSE-DBH1STMF", "CSE-DELTALIFE", "CSE-DELTASPINN",
				"CSE-DESCO", "CSE-DESHBANDHU", "CSE-DHAKABANK", "CSE-DHAKAINS",
				"CSE-DULAMIACOT", "CSE-DUTCHBANGL", "CSE-EASTERNINS",
				"CSE-EASTLAND", "CSE-EBL", "CSE-EBL1STMF", "CSE-EBLNRBMF",
				"CSE-ECABLES", "CSE-EHL", "CSE-EXIMBANK", "CSE-FAREASTLIF",
				"CSE-FASFIN", "CSE-FBFIF", "CSE-FEDERALINS", "CSE-FINEFOODS",
				"CSE-FIRSTSBANK", "CSE-FLEASEINT", "CSE-FUWANGCER",
				"CSE-FUWANGFOOD", "CSE-GBBPOWER", "CSE-GOLDENSON", "CSE-GP",
				"CSE-GPHISPAT", "CSE-GQBALLPEN", "CSE-GRAMEEN1",
				"CSE-GRAMEENS2", "CSE-GREENDELMF", "CSE-GREENDELT",
				"CSE-GSPFINANCE", "CSE-HAKKANIPUL", "CSE-HEIDELBCEM",
				"CSE-HRTEX", "CSE-IBBLPBOND", "CSE-IBNSINA", "CSE-ICB",
				"CSE-ICB1STNRB", "CSE-ICB2NDNRB", "CSE-ICB3RDNRB",
				"CSE-ICBAMCL1ST", "CSE-ICBAMCL2ND", "CSE-ICBEPMF1S1",
				"CSE-ICBISLAMIC", "CSE-IDLC", "CSE-IFIC", "CSE-IFIC1STMF",
				"CSE-IFILISLMF1", "CSE-ILFSL", "CSE-IMAMBUTTON", "CSE-INTECH",
				"CSE-IPDC", "CSE-ISLAMIBANK", "CSE-ISLAMICFIN",
				"CSE-ISLAMIINS", "CSE-ISNLTD", "CSE-JAMUNABANK",
				"CSE-JAMUNAOIL", "CSE-JANATAINS", "CSE-KAY&QUE",
				"CSE-KEYACOSMET", "CSE-KOHINOOR", "CSE-KPCL", "CSE-LAFSURCEML",
				"CSE-LANKABAFIN", "CSE-LEGACYFOOT", "CSE-LIBRAINFU",
				"CSE-LINDEBD", "CSE-LRGLOBMF1", "CSE-MAKSONSPIN",
				"CSE-MALEKSPIN", "CSE-MARICO", "CSE-MBL1STMF", "CSE-MEGHNACEM",
				"CSE-MEGHNALIFE", "CSE-MERCANBANK", "CSE-MERCINS",
				"CSE-METROSPIN", "CSE-MICEMENT", "CSE-MIDASFIN",
				"CSE-MIRACLEIND", "CSE-MITHUNKNIT", "CSE-MJLBD",
				"CSE-MONNOCERA", "CSE-MPETROLEUM", "CSE-MTBL",
				"CSE-NATLIFEINS", "CSE-NAVANACNG", "CSE-NBL", "CSE-NCCBANK",
				"CSE-NCCBLMF1", "CSE-NHFIL", "CSE-NITOLINS", "CSE-NLI1STMF",
				"CSE-NORTHRNINS", "CSE-NPOLYMAR", "CSE-NTC", "CSE-OCL",
				"CSE-OLYMPIC", "CSE-ONEBANKLTD", "CSE-ORIONINFU",
				"CSE-PADMALIFE", "CSE-PADMAOIL", "CSE-PARAMOUNT",
				"CSE-PEOPLESINS", "CSE-PF1STMF", "CSE-PHENIXINS",
				"CSE-PHOENIXFIN", "CSE-PHPMF1", "CSE-PIONEERINS", "CSE-PLFSL",
				"CSE-POPULAR1MF", "CSE-POPULARLIF", "CSE-POWERGRID",
				"CSE-PRAGATIINS", "CSE-PRAGATILIF", "CSE-PREMIERBAN",
				"CSE-PREMIERLEA", "CSE-PRIME1ICBA", "CSE-PRIMEBANK",
				"CSE-PRIMEFIN", "CSE-PRIMEINSUR", "CSE-PRIMELIFE",
				"CSE-PRIMETEX", "CSE-PROGRESLIF", "CSE-PROVATIINS",
				"CSE-PUBALIBANK", "CSE-QSMDRYCELL", "CSE-RAHIMAFOOD",
				"CSE-RAKCERAMIC", "CSE-RANFOUNDRY", "CSE-RDFOOD",
				"CSE-RECKITTBEN", "CSE-RELIANCE1", "CSE-RELIANCINS",
				"CSE-REPUBLIC", "CSE-RNSPIN", "CSE-RUPALIBANK",
				"CSE-RUPALIINS", "CSE-RUPALILIFE", "CSE-SAFKOSPINN",
				"CSE-SAIHAMCOT", "CSE-SAIHAMTEX", "CSE-SALAMCRST",
				"CSE-SALVOCHEM", "CSE-SAMATALETH", "CSE-SAMORITA",
				"CSE-SANDHANINS", "CSE-SAPORTL", "CSE-SEBL1STMF",
				"CSE-SHAHJABANK", "CSE-SIBL", "CSE-SINGERBD", "CSE-SINOBANGLA",
				"CSE-SONARBAINS", "CSE-SONARGAON", "CSE-SOUTHEASTB",
				"CSE-SPCERAMICS", "CSE-SQUARETEXT", "CSE-SQURPHARMA",
				"CSE-STANCERAM", "CSE-STANDARINS", "CSE-STANDBANKL",
				"CSE-SUMITPOWER", "CSE-TAKAFULINS", "CSE-TALLUSPIN",
				"CSE-TITASGAS", "CSE-TRUSTB1MF", "CSE-TRUSTBANK", "CSE-UCBL",
				"CSE-UNIONCAP", "CSE-UNIQUEHRL", "CSE-UNITEDAIR",
				"CSE-USMANIAGL", "CSE-UTTARABANK", "CSE-UTTARAFIN",
				"CSE-ZAHINTEX" };

		String companiescse = "";
		StringBuilder cpcse = new StringBuilder(companiescse);

		for (int i = 0; i < cpListCse.length; i++) {
			cpcse.append(check_company(cpListCse[i]));
		}

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://webindream.com/android/sebangladesh/5/cse_fav.php?companies="
						+ cpcse);

	
		Log.d(W, "Fetch Link: "+"http://webindream.com/android/sebangladesh/5/cse_fav.php?companies="
						+ cpcse);


		

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
		editor.putString("csejson", rawData);
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

		getCseList(Regex);

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
			getCseList(".*");
		}

	}

}
