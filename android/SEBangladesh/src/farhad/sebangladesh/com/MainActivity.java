package farhad.sebangladesh.com;


import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar; 
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter); 

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    			MenuInflater inflater = getSupportMenuInflater(); 
    			inflater.inflate(R.menu.activity_main, menu);
    			return true;
    }
    
    
    /**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1)
						.toUpperCase(Locale.US);
			case 1:
				return getString(R.string.title_section2)
						.toUpperCase(Locale.US);
			}
			return null;
		}
	}
    
  
	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends SherlockFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			View myFragmentView;

			// textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

			int currentPosition = getArguments().getInt(ARG_SECTION_NUMBER);
			
			Log.d("wid", "currentPosition is: "+currentPosition);

			if (currentPosition == 1) {
				myFragmentView = inflater.inflate(R.layout.activity_dse,
						container, false);
			} else if (currentPosition == 2) {
				myFragmentView = inflater.inflate(R.layout.activity_cse,
						container, false);
			} else {
				myFragmentView = inflater.inflate(R.layout.activity_dse,
						container, false);
			}

			return myFragmentView;
		}
	}

	@Override
	public void onTabReselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}


	@Override
	public void onTabUnselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void goToDseList(View v) {
		CommonUtilitiesLocal.goToDseList(this);
	}
	
	public void goToCseList(View v) {
		CommonUtilitiesLocal.goToCseList(this);
	}
	
	public void goToRecordDateDse(View v) { 
		CommonUtilitiesLocal.goToRecordDateDse(this);
	}
	
	public void goToRecordDateCse(View v) {
		CommonUtilitiesLocal.goToRecordDateCse(this);
	}
	
	public void goToNewsListCse(View v) {
		CommonUtilitiesLocal.goToNewsListCse(this);
	}
	
	
	public void goToNewsListDse(View v) {
		CommonUtilitiesLocal.goToNewsListDse(this);
	}
	
	public void goToDseFav(View v) {
		CommonUtilitiesLocal.goToDseFav(this);
	}
	
	
	public void goToCseFav(View v) {
		CommonUtilitiesLocal.goToCseFav(this);
	}
	
	public void goToDseIndexes(View v) {
		CommonUtilitiesLocal.goToDseIndexes(this);
	}
	
	public void goToCseIndexes(View v) {
		CommonUtilitiesLocal.goToCseIndexes(this);
	}

	
}
