package farhad.sebangladesh.com;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PriceListAdapter extends ArrayAdapter<SharePrice> {
	Context context;
	List<SharePrice> sharePrices;

	public PriceListAdapter(Context context, List<SharePrice> sharePrices) {
		super(context, android.R.id.content, sharePrices);
		this.context = context;
		this.sharePrices = sharePrices;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = vi.inflate(R.layout.listitem_price, null);
		SharePrice sharePrice = sharePrices.get(position);

		TextView name = (TextView) view.findViewById(R.id.company_name_value);
		name.setText(sharePrice.getName());
		
		
		
		TextView ltp = (TextView) view.findViewById(R.id.ltp_value);
		ltp.setText(sharePrice.getLtp());
		
		
		TextView change = (TextView) view.findViewById(R.id.percent_change_value); // Change + Percent Change
		change.setText(sharePrice.getChange() + " (" + sharePrice.getPercentChange() + "%)" );
		
		Log.d("wid", "Price change: " + sharePrice.getChange() + " (" + sharePrice.getPercentChange() + "%)" );
		
//		TextView high = (TextView) view.findViewById(R.id.high_value);
//		high.setText(sharePrice.getHigh());
//		
//		TextView low = (TextView) view.findViewById(R.id.low_value);
//		low.setText(sharePrice.getLow());
//		
		
		float priceChange;
		
		if(sharePrice.getChange().equals("")) {
			priceChange =  0;
		} else {
			priceChange =  Float.parseFloat(sharePrice.getChange());
		}
		
		ImageView iv = (ImageView) view.findViewById(R.id.up_down_indicator);
		
		String imageName = "up_arrow32x32";
		

		if (priceChange < 0) {
			imageName = "down_arrow32x32"; 
		} else if (priceChange > 0) {
			imageName = "up_arrow32x32";
		} else {
			imageName = "two_headed_arrow32x32";
		}
		
		int imageResource = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
		
		if(imageResource !=0)  {
			iv.setImageResource(imageResource);
		}

		return view;
		
		
		
	}
}
