package com.example.loadwebview;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {
	
	ProgressDialog dialog;
	WebView myWebView;
	
	static final String TAG = MainActivity.class.getSimpleName();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
		
		Bundle extras = getIntent().getExtras();
	    String url = "http://www.nairaland.com";
		
		dialog = new ProgressDialog(MainActivity.this);
		
			myWebView = (WebView) findViewById(R.id.webView);
			
			WebSettings settings = myWebView.getSettings();
			settings.setJavaScriptEnabled(true);
		
			//myWebView.loadUrl("http://www.nairaland.com");
			myWebView.setWebViewClient(new WebViewClient(){
				@Override
		        public void onPageFinished(WebView view, String url) {                  
		            if (dialog.isShowing()) {
		                dialog.dismiss();
		            }
		        }
			});
			if(isConnected){
			myWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
			dialog.setMessage("Loading..Please wait.");
		    dialog.setCanceledOnTouchOutside(true);
		    dialog.show();
		    myWebView.loadUrl(url);
			}else{
				myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
				Toast.makeText(this, "Not Connected to the Internet", Toast.LENGTH_LONG).show();
			}
		 // just to get the look of facebook (changing background color)
			getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    // Check if the key event was the Back button and if there's history
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
	        myWebView.goBack();
	        return true;
	    }
	    // If it wasn't the Back key or there's no web page history, bubble up to the default
	    // system behavior (probably exit the activity)
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
