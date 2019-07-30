package tw.org.tcca.app.jsontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    private UIHandler handler;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new UIHandler();

        listView = findViewById(R.id.listView);
        initListView();

        initProgressBar();
        fetchData();
    }

    private void initProgressBar(){
        progressBar = new ProgressBar(this);
    }

    private void initListView(){
        adapter = new SimpleAdapter(
                this,data,R.layout.item, from, to);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showImage(i);
            }
        });
    }

    private void showImage(int index){
        Intent intent = new Intent(this, ?);
        intent.putExtra("pic", data.get(index).get("pic"));
        startActivity(intent);

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
        data.clear();
        try {
            JSONArray root = new JSONArray(json);
            Log.v("brad", "count: " + root.length());
            for (int i=0; i< root.length(); i++){
                JSONObject row = root.getJSONObject(i);
                String name = row.getString("Name");

                HashMap<String,String> dd = new HashMap<>();
                dd.put(from[0],row.getString("Name"));
                dd.put(from[1],row.getString("Tel"));
                dd.put(from[2],row.getString("Address"));
                dd.put("pic", row.getString("PicURL"));
                data.add(dd);
            }
            handler.sendEmptyMessage(0);
        }catch (Exception e){
            Log.v("brad", e.toString());
        }

    }


    private class UIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0){
                adapter.notifyDataSetChanged();
            }


        }
    }


}
