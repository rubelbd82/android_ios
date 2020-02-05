package farhad.sebangladesh.com;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewsCseAdapter extends ArrayAdapter<NewsCse> {
	Context context;
	List<NewsCse> newsCses;

	public NewsCseAdapter(Context context, List<NewsCse> newsCses) {
		super(context, android.R.id.content, newsCses);
		this.context = context;
		this.newsCses = newsCses;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = vi.inflate(R.layout.list_news_cse, null);
		NewsCse newsCse = newsCses.get(position);

		TextView name = (TextView) view.findViewById(R.id.news);
		name.setText(newsCse.getNews());

		return view;
		
		
		
	}
}
