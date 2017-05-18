package com.ufo.learngerman.fragment;

import com.ufo.learngerman.R;
import com.ufo.learngerman.adapter.PhraseListAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FavoritesFragment extends ExpandableFragment {
		
	int categoryId;
	
	String mTitle = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.mTitle  = mActivity.getResources().getString(R.string.favorite_title);
		
		liPhraseItems = mDatabase.getListPhraseFavorite2();
		
		strNoresult = mActivity.getResources().getString(R.string.no_favorites);
		
		mPhraseListAdapter = new PhraseListAdapter(mActivity, liPhraseItems, R.layout.phrase_detail_item_2, mDatabase);
		mActivity.increaseCounter();
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mActivity.showBannerAd();
		mActivity.setTitle(mTitle);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mActivity.increaseCounter();
		
	}
}
