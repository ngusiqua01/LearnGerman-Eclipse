package com.ufo.learngerman.adapter;

import com.ufo.learngerman.R;
import com.ufo.learngerman.database.Database;
import com.ufo.learngerman.utils.DecodeUtil;
import com.ufo.learngerman.utils.Utils;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhraseDetailAdapter extends CursorAdapter{
	
	Context mContext;
	TextView txtKorean;
//	TextView txtPinpyn;
	TextView txtVietnam;
//	ImageView imgfavorite;
	
	Cursor mCursor;
	
	MediaPlayer mPlayer;
	
	int itemResId;
	
	String phraseColumnName = "";
	Database mDatabase;
	
	/**
	 * 
	 * @param context
	 * @param c
	 * @param flag should be set to 0
	 */
	public PhraseDetailAdapter(Context context, Cursor c, int flag, int resId, Database pDatabase) {
		super(context, c,flag);
		this.mContext = context;
		this.mCursor = c;
		this.itemResId = resId;
		this.phraseColumnName = mContext.getResources().getString(R.string.phrase_name_column);
		this.mDatabase = pDatabase;
	}


	@Override
	public void bindView(View view, Context context, final Cursor cursor) {
//		System.out.println("PhraseDetailAdapter.bindView()");
		ViewHolder holder = (ViewHolder) view.getTag();
		int position = cursor.getPosition();
		
		txtKorean = holder.txtKorean;
		/*txtPinpyn = holder.txtPinyin;*/
		txtVietnam = holder.txtVietnamese;
		final ImageView imgfavorite = holder.imgStar;
		
		// france do not have pinyin
//		String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
        String korean = cursor.getString(cursor.getColumnIndex(Utils.LANGUAGE_COLLUNM));
        String vietnamese = cursor.getString(cursor.getColumnIndex(this.phraseColumnName));
        
        // no need for learn japanese
       /* DecodeUtil mDecodeUtil = new DecodeUtil(DecodeUtil.keycode_phrase);
        pinyin  = mDecodeUtil.decode(pinyin);
        korean = mDecodeUtil.decode(korean);*/
        
        korean = DecodeUtil.decodeFrance(korean);
        
        txtKorean.setText(position+". "+korean);
//        txtPinpyn.setText(pinyin);
        txtVietnam.setText(vietnamese);
        
        final String voice = cursor.getString(cursor.getColumnIndex("voice"))+"_f";
        final int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));
        final int phraseId = cursor.getInt(cursor.getColumnIndex("_id"));
//        System.out.println("bindview phraseId = "+phraseId+" && favorite = "+favorite);
        if(favorite == 1){
        	imgfavorite.setSelected(true);
        }else{
        	imgfavorite.setSelected(false);
        }
        
        imgfavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				/*if(mPlayer != null && mPlayer.isPlaying()){
					try{
						mPlayer.stop();
						mPlayer.release();
					}catch (Exception e) {
						
					}
				}
				
				int rawId = mContext.getResources().getIdentifier(voice, "raw", mContext.getPackageName());
				if(rawId != 0){
					mPlayer = MediaPlayer.create(mContext,rawId);
					mPlayer.start();
				}*/
				if(!imgfavorite.isSelected()){
					mDatabase.updateFavorite(phraseId, 1);
					imgfavorite.setSelected(true);
				}else {
					mDatabase.updateFavorite(phraseId, 0);
					imgfavorite.setSelected(false);
				}
				
				
			}
		});
	}


	@Override
	public View newView(Context context, final Cursor cursor, ViewGroup parent) {
//		System.out.println("PhraseDetailAdapter.newView()");
		View view = LayoutInflater.from(mContext).inflate(itemResId,parent, false);
		ViewHolder holder = new ViewHolder();
		holder.txtKorean = (TextView) view.findViewById(R.id.txt_korea);
		/*holder.txtPinyin = (TextView) view.findViewById(R.id.txt_pinpyn);;*/
		holder.txtVietnamese = (TextView) view.findViewById(R.id.txt_vietnam);
		holder.imgStar  =  (ImageView)view.findViewById(R.id.imgVoice);
		
		view.setTag(holder);
		return view;
	}
	
	 static class ViewHolder{
		TextView txtKorean;
		/*TextView txtPinyin;*/
		TextView txtVietnamese;
		ImageView imgStar;
	}
	
	
}
