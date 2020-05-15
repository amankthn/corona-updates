package com.ndapps.coronaupdates.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ndapps.coronaupdates.R;
import com.ndapps.coronaupdates.fragments.MainFragment;
import com.ndapps.coronaupdates.fragments.countryFragment;
import com.ndapps.coronaupdates.fragments.dummy.DummyContent;
import com.ndapps.coronaupdates.model.Cases;
import com.ndapps.coronaupdates.model.Deaths;
import com.ndapps.coronaupdates.model.MyResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements countryFragment.OnListFragmentInteractionListener {

    private String url = "https://covid-193.p.rapidapi.com/statistics?country=all";
    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private MyResponse myResponse = new MyResponse();
    private RecyclerView myRecView;
    Cases cases = new Cases();
    Deaths deaths = new Deaths();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.mainfragment, mainFragment, "MainFragment").commit();


//        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
//
////        mSwipeRefreshLayout.setColorSchemeResources(R.color.Green, R.color.Orange, R.color.Blue, R.color.Green);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.v("Refresh", "************** MAPS - SWIPE REFRESH EVENT TRIGGERED!!!!!");
//                jsonCall();
//                if(mSwipeRefreshLayout.isRefreshing())
//                    mSwipeRefreshLayout.setRefreshing(false);
//            }
//
//        });


    }

    public void jsonCall() {


        request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d("JSONCALL", "WORKING");
                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                JSONObject jsonCases = null;
                JSONObject jsonDeaths = null;
                JSONObject jsonTests = null;
                try {
                    jsonArray = response.getJSONArray("response");
                    jsonObject = jsonArray.getJSONObject(0);
                    Log.d("JSON", jsonObject.getString("country"));
                    myResponse.setCountry(jsonObject.getString("country"));
                    myResponse.setDay(jsonObject.getString("day"));
                    myResponse.setTime(jsonObject.getString("time"));
                    jsonCases = jsonObject.getJSONObject("cases");


                    cases.setNewCases(jsonCases.getString("new"));
                    cases.setActive(jsonCases.getInt("active"));
                    cases.setCritical(jsonCases.getInt("critical"));
                    cases.setRecovered(jsonCases.getInt("recovered"));
                    cases.setTotal(jsonCases.getInt("total"));
                    Log.d("CASES", jsonCases.getString("new"));

                    jsonDeaths = jsonObject.getJSONObject("deaths");
                    deaths.setNewDeaths(jsonDeaths.getString("new"));
                    deaths.setTotal(jsonDeaths.getInt("total"));
                    Log.d("DEATHS", deaths.getNewDeaths());
//                        Tests tests = new Tests();
//                        jsonTests = jsonObject.getJSONObject("tests");
//                        tests.setTotal(jsonTests.getInt("total"));
//                        Log.d("TEST", tests.toString());
                    myResponse.setCases(cases);
                    myResponse.setDeaths(deaths);
//                        myResponse.setTests(tests);
                    Log.d("RESPONSE", myResponse.toString());


                    TextView casesT = findViewById(R.id.cases);
                    TextView deathsT = findViewById(R.id.deaths);
                    TextView recT = findViewById(R.id.rec);
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    String s = new DecimalFormat("##,##,##0").format(myResponse.getCases().getTotal());
                    casesT.setText(s);
                    String d = new DecimalFormat("##,##,##0").format(myResponse.getDeaths().getTotal());
                    deathsT.setText(d);
                    String r = new DecimalFormat("##,##,##0").format(myResponse.getCases().getRecovered());
                    recT.setText(r);
                    casesT.setTextColor(Color.parseColor("#9e9e98"));
                    deathsT.setTextColor(Color.parseColor("#63635e"));
                    recT.setTextColor(Color.parseColor("#1ce615"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Check your Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("x-rapidapi-host", "covid-193.p.rapidapi.com");
                headers.put("x-rapidapi-key", "ebc95198c8mshcee1a92d129da7dp1e0004jsn59561e100a92");
                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);


    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
