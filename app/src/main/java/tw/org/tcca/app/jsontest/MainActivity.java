package tw.org.tcca.app.jsontest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData();
    }

    private void fetchData(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx");
                    HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
                    conn.connect();

                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(conn.getInputStream()));
                    String line = null; StringBuffer sb = new StringBuffer();
                    while ( (line = reader.readLine()) != null){
                        sb.append(line);
                    }
                    reader.close();


                }catch (Exception e){
                    Log.v("brad", e.toString());
                }
            }
        }.start();
    }


}
