package com.ufo.learngerman.adapter;

import com.ufo.learngerman.R;
import com.ufo.learngerman.R.id;
import com.ufo.learngerman.R.layout;
import com.ufo.learngerman.utils.Utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListLeftAdapter extends BaseAdapter{
	Context mContext = null;
	String mListName [];
	int listImageId [];
	int itemLayoutId ;
	
	public ListLeftAdapter(Context context, String[] listName, int[] listId, int itemLayoutId) {
		this.mContext = context;
		this.mListName = listName;
		this.listImageId = listId;
		this.itemLayoutId = itemLayoutId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			//inflate
			convertView = LayoutInflater.from(mContext).inflate(itemLayoutId, parent,false);
			holder = new ViewHolder();
			holder.txtView =  (TextView)convertView.findViewById(R.id.item_text);
			holder.imgView = (TextView)convertView.findViewById(R.id.item_image);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (!Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET) && position >= 1){
			position = position +1;
		}
		
		holder.txtView.setText(mListName[position]);
		holder.imgView.setBackgroundResource(listImageId[position]);
		holder.imgView.setText("");
		convertView.invalidate();
		return convertView;
	}
	

	@Override
	public int getCount() {
		if (!Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET))
			return mListName.length -1;
		return mListName.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}
	
	 static class ViewHolder{
		TextView txtView;
		TextView imgView;
	}

}
