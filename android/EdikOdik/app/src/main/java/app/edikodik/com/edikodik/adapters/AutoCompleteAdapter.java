package app.edikodik.com.edikodik.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import app.edikodik.com.edikodik.entities.autocomplete.AutoCompleteResponse;
import app.edikodik.com.edikodik.services.AutoCompleteService;

/**
 * Created by farhadrubel on 11/23/15.
 */
public class AutoCompleteAdapter extends ArrayAdapter<AutoCompleteResponse> implements Filterable {
    private ArrayList<AutoCompleteResponse> mData;

    public AutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mData = new ArrayList<AutoCompleteResponse>();
    }

    @Override
    public int getCount() {
        if (mData != null) return mData.size();
        else return 0;
    }

    @Override
    public AutoCompleteResponse getItem(int index) {
        return mData.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null && constraint.toString().length() > 2) {
//                if(constraint != null) {
                    // A class that queries a web API, parses the data and returns an ArrayList<Style>
                    AutoCompleteService fetcher = new AutoCompleteService();
                    try {
                        mData = fetcher.callAutoCompleteService(getContext(), constraint.toString());

                        // Now assign the values and count to the FilterResults object
                        filterResults.values = mData;
                        filterResults.count = mData.size();
                    }
                    catch(Exception e) {
                        Log.e("myException", e.getMessage());
                    }

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }

}