package tw.org.tcca.app.jsontest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
    private String url;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        img = findViewById(R.id.img);
        url = getIntent().getStringExtra("pic");
        fetchImageData();

    }

    private void fetchImageData(){
        new Thread(){
            @Override
            public void run() {

            }
        }.start();
    }

}
