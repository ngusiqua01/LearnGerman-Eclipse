package com.ufo.learngerman.fragment;

import com.ufo.learngerman.MainActivity;
import com.ufo.learngerman.R;
import com.ufo.learngerman.R.id;
import com.ufo.learngerman.R.layout;
import com.ufo.learngerman.adapter.GrammarCursorAdapter;
import com.ufo.learngerman.adapter.ListLeftAdapter;
import com.ufo.learngerman.database.Database;
import com.ufo.learngerman.utils.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class GrammarFragment extends Fragment {
	String mtableName = null;
	ListView mListView = null;
	GrammarCursorAdapter mGrammarAdapter = null;
	Database mDatabase = null;
	Cursor mCursor = null;
	String mTitle = "";
	MainActivity mActivity;
	public GrammarFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		mActivity = (MainActivity) getActivity();
		// set title
		this.mTitle = getArguments().getString(Utils.TITLE);
		mActivity.setTitle(this.mTitle);
		
		mActivity.getDrawerToogle().setDrawerIndicatorEnabled(false);
		// create database
		mDatabase =  Database.newInstance(mActivity, Utils.GRAMMAR_DATABASE_NAME);
//		mDatabase.open();
		// create adapter
		mCursor = mDatabase.getListGrammar();
		mGrammarAdapter = new GrammarCursorAdapter(mActivity, mCursor);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view =  inflater.inflate(R.layout.my_fragment, container,false);
		mListView = (ListView) view.findViewById(R.id.list_fragment);
		mListView.setAdapter(mGrammarAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				mCursor.moveToPosition(position);
				int id = mCursor.getInt(mCursor.getColumnIndex(Utils.GRAMMAR_COLUMN_ID));
				// start new fragment
				Fragment fragment = new WebviewFragment();
				Bundle mBundle = new Bundle();
				mBundle.putInt(Utils.GRAMMAR_COLUMN_ID, id);
				mBundle.putString("TITLE", mCursor.getString(1));
				fragment.setArguments(mBundle);
				FragmentTransaction mTransaction = getActivity().getFragmentManager().beginTransaction();
				mTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
				mTransaction.replace(R.id.main_content, fragment);
				mTransaction.addToBackStack(null);
				mTransaction.commit();
				
			}
		});
		return view;
			
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mActivity.setTitle(this.mTitle);
		mActivity.hideBannerAd();
	}
	
}
