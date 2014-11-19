package com.example.exercises;

import com.example.coursecode.solutions.MemoryCache1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;




public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        Button button = (Button) findViewById(R.id.testButton);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected Boolean doInBackground(Void... params) {
						return doTest();
					}

					@Override
					protected void onPostExecute(Boolean result) {
						Toast.makeText(MainActivity.this, String.valueOf(result), Toast.LENGTH_LONG).show();
					}			
					
				}.execute();
			}
		});
    }

    public boolean doTest() {
    	MemoryCache1 cache = new MemoryCache1(10);
		cache.put("url", System.currentTimeMillis() + 2000, "value");
		String value = cache.get("url");
		if (!"value".equals(value)) {
			return false;
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		value = cache.get("url");
		if (value != null) {
			return false;
		}
		return true;
    }  
}
