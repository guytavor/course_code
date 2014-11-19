package com.example.exercises;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;




public class MainActivity extends ActionBarActivity {

	
    private ImageView imageView;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageView = (ImageView) findViewById(R.id.imageView);
        
        Button button = (Button) findViewById(R.id.testButton);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onButtonClicked();
			}

		});
    }

    private void onButtonClicked() {
		if (!exitInCache()){
			new DownloadImageTask().execute("http://michaelguberti.com/wp-content/uploads/2014/08/dove_peace-5555px-300x249.png");
		} else {
			showImageViewFromCache();
		}    	
    }
    
    private boolean exitInCache() {
		return new File(getLocalCopyPath()).exists();
	}

	private void showImageViewFromCache() {
    	Uri uri = Uri.fromFile(new File(getLocalCopyPath()));
    	// setImageURI causes bitmap loading and decoding which
    	// should not be done on the UI thread.
    	// Non-sample code will first decode and downsample the bitmap
    	// in an async task, and only when done update the imageView
    	// using ImageView.setImageBitamp()    	
    	imageView.setImageURI(uri);
    }

    private String getLocalCopyPath() {
    	// TODO: getExternalCacheDir will return null if storage
    	// is not mounted. Non-sample code will check this first.
		return getExternalCacheDir().getAbsolutePath() + "cachedImage";
	}
    
    /** Writes input stream content to file. */
    public static void writeToFile(InputStream inputStream, String localPath) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(localPath));
        final byte[] buffer = new byte[512];
        BufferedInputStream in = new BufferedInputStream(inputStream);
        int read;
        do {
          read = in.read(buffer, 0, buffer.length);
          if (read > 0) {
            out.write(buffer, 0, read);
          }
        } while (read >= 0);    
        in.close();
        out.close();    
      }
    
    public class DownloadImageTask extends AsyncTask<String, Void, Boolean> {

    	@Override
		protected void onPreExecute() {
    		Toast.makeText(MainActivity.this, "downloading from the network...", Toast.LENGTH_SHORT).show();
    	}
    	
		@Override
		protected Boolean doInBackground(String... urls) {
			URL url;
			try {
				url = new URL(urls[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				InputStream inputStream = connection.getInputStream();
				writeToFile(inputStream, getLocalCopyPath());
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				showImageViewFromCache();
			} else {
				Toast.makeText(MainActivity.this, "failed to download and save image", Toast.LENGTH_LONG).show();
			}
		}
		
    	
    }
}
