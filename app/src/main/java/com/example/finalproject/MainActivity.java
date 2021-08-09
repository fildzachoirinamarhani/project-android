package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<DataAdapter> ListOfDataAdapter;
    RecyclerView recyclerView;
    String URL_JSON = "http://localhost/dinporabudpar/koneksi_wisata.php";

    String TAG_ID = "idwisata";
    String TAG_USAHA = "namausaha";
    String TAG_URL = "foto";

    JsonArrayRequest RequestOfJsonArray;
    RequestQueue requestQueue;

    View view;
    int RecyclerViewItemPosition;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    ArrayList<String> ImageTitleidArrayListForClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageTitleidArrayListForClick = new ArrayList<>();
        ListOfDataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview_layout);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTP();
    }

    public void JSON_HTTP() {
        RequestOfJsonArray = new JsonArrayRequest(URL_JSON,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ParseJsonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(RequestOfJsonArray);

    }

    private void ParseJsonResponse(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            DataAdapter GetDataAdapter2 = new DataAdapter();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                ImageTitleidArrayListForClick.add(json.getString(TAG_ID));
                GetDataAdapter2.setnamausaha(json.getString(TAG_USAHA));
                GetDataAdapter2.seturl(json.getString(TAG_URL));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListOfDataAdapter.add(GetDataAdapter2);
        }
        recyclerViewAdapter =  new RecyclerviewAdapter(ListOfDataAdapter, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}