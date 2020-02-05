package farhad.sebangladesh.com;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecordDateDseAdapter extends ArrayAdapter<RecordDateDse> {
	Context context;
	List<RecordDateDse> recordDateDses;

	public RecordDateDseAdapter(Context context, List<RecordDateDse> recordDateDses) {
		super(context, android.R.id.content, recordDateDses);
		this.context = context;
		this.recordDateDses = recordDateDses;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = vi.inflate(R.layout.list_record_date_dse, null);
		RecordDateDse recordDateDse = recordDateDses.get(position);

		TextView name = (TextView) view.findViewById(R.id.company_name_value);
		name.setText(recordDateDse.getName());
		
		
		
		TextView di = (TextView) view.findViewById(R.id.dividend);
		di.setText(recordDateDse.getDividend());
		
		
		TextView da = (TextView) view.findViewById(R.id.agm_date); // Change + Percent Change
		da.setText(recordDateDse.getAgm_date());
		
		Log.d("wid", "Price change: " + recordDateDse.getAgm_date());
		
		
		TextView rd = (TextView) view.findViewById(R.id.record_date); // Change + Percent Change
		rd.setText(recordDateDse.getRecord_date());

		return view;
		
		
		
	}
}
