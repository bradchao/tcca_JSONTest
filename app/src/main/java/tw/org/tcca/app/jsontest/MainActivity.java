package tw.org.tcca.app.jsontest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter adapter;
    private String[] from = {"name", "tel", "address"};
    private int[] to = {R.id.item_name, R.id.item_tel,
        R.id.item_address};
    private LinkedList<HashMap<String,String>> data = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        initListView();

        fetchData();
    }

    private void initListView(){
        adapter = new SimpleAdapter(
                this,data,R.layout.item, from, to);
        listView.setAdapter(adapter);
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

                    //Log.v("brad", sb.toString());
                    parseJSON(sb.toString());

                }catch (Exception e){
                    Log.v("brad", e.toString());
                }
            }
        }.start();
    }


    private void parseJSON(String json){
        try {
            JSONArray root = new JSONArray(json);
            Log.v("brad", "count: " + root.length());
            for (int i=0; i< root.length(); i++){
                JSONObject row = root.getJSONObject(i);
                String name = row.getString("Name");
                Log.v("brad", name);
            }



        }catch (Exception e){
            Log.v("brad", e.toString());
        }

    }


}
