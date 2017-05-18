package com.ufo.learngerman.fragment;

import com.ufo.learngerman.MainActivity;
import com.ufo.learngerman.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SeemoreFragment extends Fragment{
	
	MainActivity mActivity = null;
	
	public SeemoreFragment() {
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mActivity = (MainActivity) getActivity();
		mActivity.getDrawerToogle().setDrawerIndicatorEnabled(false);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mActivity.setTitle(mActivity.getResources().getString(R.string.see_more));
	}

}
