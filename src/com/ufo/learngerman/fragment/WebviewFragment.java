package com.ufo.learngerman.fragment;

import com.ufo.learngerman.MainActivity;
import com.ufo.learngerman.R;
import com.ufo.learngerman.R.drawable;
import com.ufo.learngerman.R.id;
import com.ufo.learngerman.R.layout;
import com.ufo.learngerman.database.Database;
import com.ufo.learngerman.utils.Utils;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebviewFragment extends Fragment {
	WebView mWebview = null;
	TextView txtView = null;
	String title = "";
	String content;
	int grammarId ;
	MainActivity mActivity;
	
	Database mDatabase;
	
	ProgressDialog loadingDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		grammarId = getArguments().getInt(Utils.GRAMMAR_COLUMN_ID);
		mActivity = (MainActivity) getActivity();
		// mActivity.findViewById(android.R.id.home).setBackgroundResource(R.drawable.nav_arrow_back);

		mActivity.getActionBar().setDisplayHomeAsUpEnabled(true);
		mActivity.getActionBar().setHomeButtonEnabled(true);
		
		if(mActivity.getDrawerToogle() != null)
			mActivity.getDrawerToogle().setDrawerIndicatorEnabled(false);
		title = getArguments().getString("TITLE");
		
		mDatabase = Database.newInstance(mActivity, Utils.GRAMMAR_DATABASE_NAME);
//		mDatabase.open();
		this.content = mDatabase.getGrammarContent(grammarId);
		
//		loadingDialog = ProgressDialog.show(mActivity, null, null);

		//mActivity.showBannerAd();
		mActivity.increaseCounter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.webview, container, false);

		/*mWebview = (WebView) v.findViewById(R.id.web_view);
		mWebview.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
		mWebview.getSettings().setBuiltInZoomControls(true);
		mWebview.getSettings().setDisplayZoomControls(false);
		
		mWebview.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				loadingDialog.dismiss();
			}
		});
		*/
		
		txtView = (TextView) v.findViewById(R.id.txt_view);
		txtView.setText(content);
		
		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mActivity.showBannerAd();
		getActivity().setTitle(title);
	}

}
