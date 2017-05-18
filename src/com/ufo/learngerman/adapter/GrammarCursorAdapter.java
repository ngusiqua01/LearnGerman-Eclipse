package com.ufo.learngerman.adapter;

import com.ufo.learngerman.R;
import com.ufo.learngerman.adapter.ListLeftAdapter.ViewHolder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GrammarCursorAdapter extends CursorAdapter {
	


	public GrammarCursorAdapter(Context context, Cursor c) {
		super(context, c);
		
	}

	@Override
	public void bindView(View view, Context pContext, Cursor pCursor) {
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		TextView mTextView = holder.txtView;
		mTextView.setText(pCursor.getString(pCursor.getColumnIndex("title")));
		
		TextView imgTextView = holder.imgView;
		imgTextView.setText(String.valueOf(pCursor.getPosition() +1));
		
	}

	@Override
	public View newView(Context pContext, Cursor pCursor, ViewGroup parent) {
		View v  = LayoutInflater.from(pContext).inflate(R.layout.item_list_view, parent,false);
		
		ViewHolder holder = new ViewHolder();
		holder.txtView =  (TextView)v.findViewById(R.id.item_text);
		holder.imgView = (TextView)v.findViewById(R.id.item_image);
		v.setTag(holder);
		
		return v;
	}
	
}
