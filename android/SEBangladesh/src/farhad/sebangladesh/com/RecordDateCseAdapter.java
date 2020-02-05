package farhad.sebangladesh.com;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecordDateCseAdapter extends ArrayAdapter<RecordDateCse> {
	Context context;
	List<RecordDateCse> recordDateCses;

	public RecordDateCseAdapter(Context context, List<RecordDateCse> recordDateCses) {
		super(context, android.R.id.content, recordDateCses);
		this.context = context;
		this.recordDateCses = recordDateCses;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = vi.inflate(R.layout.list_record_date_cse, null);
		RecordDateCse recordDateCse = recordDateCses.get(position);

		TextView name = (TextView) view.findViewById(R.id.company_name_value);
		name.setText(recordDateCse.getName());
		
		
		
		TextView di = (TextView) view.findViewById(R.id.dividend);
		di.setText(recordDateCse.getDividend());
		
		
		TextView da = (TextView) view.findViewById(R.id.agm_date); // Change + Percent Change
		da.setText(recordDateCse.getAgm_date());
		
		Log.d("wid", "Price change: " + recordDateCse.getAgm_date());
		
		
		TextView rd = (TextView) view.findViewById(R.id.record_date); // Change + Percent Change
		rd.setText(recordDateCse.getRecord_date());

		return view;
		
		
		
	}
}
