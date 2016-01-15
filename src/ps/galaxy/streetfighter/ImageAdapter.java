package ps.galaxy.streetfighter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private boolean[] isSelected;
	private Integer[] mThumbIds;
	
	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageView.setPadding(1, 1, 1, 1);
		} else {
			imageView = (ImageView) convertView;
		}
		if(isSelected[position]){
			imageView.setImageResource(mThumbIds[position]);
			imageView.setImageAlpha(70);
		}
		else 
			imageView.setImageResource(mThumbIds[position]);
			imageView.setBackgroundResource(R.drawable.character_shape);
			return imageView;
	}

	public void setThumbIds(Integer [] ids){
		mThumbIds = ids;
	}
	public void isSelected(boolean [] imgs){
		isSelected = imgs;
	}

}
