package farhad.sebangladesh.com;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewsDseAdapter extends ArrayAdapter<NewsDse> {
	Context context;
	List<NewsDse> newsDses;

	public NewsDseAdapter(Context context, List<NewsDse> newsDses) {
		super(context, android.R.id.content, newsDses);
		this.context = context;
		this.newsDses = newsDses;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = vi.inflate(R.layout.list_news_dse, null);
		NewsDse newsDse = newsDses.get(position);

		TextView name = (TextView) view.findViewById(R.id.company_name_value);
		name.setText(newsDse.getCompany_name());
		
		
		TextView news = (TextView) view.findViewById(R.id.news);
		news.setText(newsDse.getNews());

		return view;
		
		
		
	}
}
