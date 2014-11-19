package com.example.exercises;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;




public class MainActivity extends ActionBarActivity {

    private static final String IMAGE_URL = "http://michaelguberti.com/wp-content/uploads/2014/08/dove_peace-5555px-300x249.png";

    private NetworkImageView imageView;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageView = (NetworkImageView) findViewById(R.id.imageView);
        
        Button button = (Button) findViewById(R.id.testButton);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
				ImageLoader.ImageCache imageCache = new ImageCache() {
					private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
					
					@Override
					public void putBitmap(String url, Bitmap bitmap) {
						cache.put(url, bitmap);						
					}
					
					@Override
					public Bitmap getBitmap(String url) {						
						return cache.get(url);
					}
				};
				ImageLoader imageLoader = new ImageLoader(queue, imageCache);
				
				imageView.setImageUrl(IMAGE_URL, imageLoader);
			}

		});
    }
    
}
