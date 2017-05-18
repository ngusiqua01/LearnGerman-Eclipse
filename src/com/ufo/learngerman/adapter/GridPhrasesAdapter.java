package com.ufo.learngerman.adapter;

import java.util.ArrayList;

import com.ufo.learngerman.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridPhrasesAdapter extends BaseAdapter{
	
	Context mContext = null;
//	int[] listimgId ;
//	String [] listName;
	int itemLayoutId;
	
	ArrayList<CategoryItem> mListCategoryItems;
	
	public GridPhrasesAdapter(Context context,ArrayList<CategoryItem> listCategoryItems, int layoutId) {
		
		this.mContext = context;
//		this.listName = listText;
//		this.listimgId = arrayImg;
		this.itemLayoutId = layoutId;
		this.mListCategoryItems =  listCategoryItems;
	}

	@Override
	public int getCount() {
		
		return mListCategoryItems.size();
	}

	@Override
	public CategoryItem getItem(int position) {
		
		return mListCategoryItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView =  LayoutInflater.from(mContext).inflate(itemLayoutId, parent,false);
			holder.img = (ImageView) convertView.findViewById(R.id.img_grid_item);
			holder.txt = (TextView) convertView.findViewById(R.id.txt_grid_item);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		int resID = mContext.getResources().getIdentifier(mListCategoryItems.get(position).thumbnail, 
				"drawable", mContext.getPackageName());
		if (resID > 0 )
			holder.img.setImageResource(resID);
		else
			holder.img.setImageResource(R.drawable.ic_general_conversation);
		
		holder.txt.setText(mListCategoryItems.get(position).categoryName);
		holder.txt.setHorizontalFadingEdgeEnabled(true);
		convertView.invalidate();
		return convertView;
	}
	
	 static class ViewHolder{
		TextView txt;
		ImageView img;
	}


}
