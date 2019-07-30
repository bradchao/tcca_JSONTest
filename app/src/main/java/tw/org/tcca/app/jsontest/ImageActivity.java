package tw.org.tcca.app.jsontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

public class ImageActivity extends AppCompatActivity {
    private String url = null;
    private ImageView img;
    private Bitmap bmp = null;
    private UIHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        handler = new UIHandler();
        img = findViewById(R.id.img);
        url = getIntent().getStringExtra("pic");
        
        fetchImageData(url);

    }

    private void fetchImageData(final String imgurl){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(imgurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    bmp = BitmapFactory.decodeStream(conn.getInputStream());
                    handler.sendEmptyMessage(0);

                }catch (Exception e){
                    Log.v("brad", e.toString());
                }
            }
        }.start();
    }


    private class UIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            img.setImageBitmap(bmp);
        }
    }

}
