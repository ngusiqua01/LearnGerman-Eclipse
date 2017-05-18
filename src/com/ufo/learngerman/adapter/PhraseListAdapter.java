package com.ufo.learngerman.adapter;

import java.util.ArrayList;

import com.ufo.learngerman.R;
import com.ufo.learngerman.adapter.PhraseDetailAdapter.ViewHolder;
import com.ufo.learngerman.database.Database;
import com.ufo.learngerman.utils.DecodeUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhraseListAdapter extends BaseAdapter{
	
	
	View hiddenLayout;
	TextView txtKorean;
//	TextView txtPinpyn;
	TextView txtVietnam;
	Context mContext;
	ArrayList<PhraseItem> listPhraseItems;
	int itemLayoutId;
	Database mDatabase;
	String phraseColumnName = "";
	int currentPosition = -1;
	
	public PhraseListAdapter(Context context, ArrayList<PhraseItem> listItems, int itemResId, Database database) {
		this.mContext  = context;
		this.listPhraseItems = listItems;
		this.itemLayoutId = itemResId;
		this.mDatabase = database;
	}

	@Override
	public int getCount() {
		
		return listPhraseItems.size();
	}

	@Override
	public PhraseItem getItem(int position) {
		
		return listPhraseItems.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if(view == null){
			holder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(itemLayoutId,parent, false);
			holder.txtKorean = (TextView) view.findViewById(R.id.txt_korea);
			holder.txtPinyin = (TextView) view.findViewById(R.id.txt_pinpyn);;
			holder.txtVietnamese = (TextView) view.findViewById(R.id.txt_vietnam);
			holder.imgStar  =  (ImageView)view.findViewById(R.id.imgVoice);
			holder.hiddenLayout = view.findViewById(R.id.item_hide);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
			
		}
		final PhraseItem item = listPhraseItems.get(position);
		if(position != currentPosition){
			// hide
			holder.hiddenLayout.setVisibility(View.GONE);
		}else{
			holder.hiddenLayout.setVisibility(View.VISIBLE);
		}
		txtKorean = holder.txtKorean;
//		txtPinpyn = holder.txtPinyin;
		txtVietnam = holder.txtVietnamese;
		final ImageView imgfavorite = holder.imgStar;
		
//		String pinyin = item.getTxtPinpyn();
        String korean = item.getTxtKorean();
        String vietnamese = item.getTxtVietnamese();
        
//        System.out.println(vietnamese+" -- "+ korean);
        /*DecodeUtil mDecodeUtil = new DecodeUtil(DecodeUtil.keycode_phrase);
        pinyin  = mDecodeUtil.decode(pinyin);
        korean = mDecodeUtil.decode(korean);*/
        
        txtKorean.setText(korean);
//        txtPinpyn.setText(pinyin);
        txtVietnam.setText(vietnamese);
        
        int favorite = item.getFavorite();
        final int phraseId = item.getId();

        if(favorite == 1){
        	imgfavorite.setSelected(true);
        }else{
        	imgfavorite.setSelected(false);
        }
        
        imgfavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(!imgfavorite.isSelected()){
					mDatabase.updateFavorite(phraseId, 1);
					item.setFavorite(1);
					imgfavorite.setSelected(true);
				}else {
					mDatabase.updateFavorite(phraseId, 0);
					item.setFavorite(0);
					imgfavorite.setSelected(false);
				}
				
				
			}
		});
		
		return view;
	}
	
	public void setPositionSelected(int position)
	{
		this.currentPosition =position;
	}
	
	static class ViewHolder{
		TextView txtKorean;
		TextView txtPinyin;
		TextView txtVietnamese;
		ImageView imgStar;
		
		View hiddenLayout;
	}
	
	public void updateListPhraseItems(ArrayList<PhraseItem> listItems){
		this.listPhraseItems = listItems;
		this.notifyDataSetChanged();
	}

}
