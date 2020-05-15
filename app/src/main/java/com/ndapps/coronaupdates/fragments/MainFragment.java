package com.ndapps.coronaupdates.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ndapps.coronaupdates.R;
import com.ndapps.coronaupdates.model.Cases;
import com.ndapps.coronaupdates.model.Deaths;
import com.ndapps.coronaupdates.model.MyResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String url = "https://covid-193.p.rapidapi.com/statistics?country=all";
    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private MyResponse myResponse = new MyResponse();
    private RecyclerView myRecView;
    Cases cases = new Cases();
    Deaths deaths = new Deaths();


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container,
                false);


        Button button = v.findViewById(R.id.myButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainfragment, new countryFragment());
                ft.commit();
                ft.addToBackStack("MainFragment");

            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jsonCall();


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


                    TextView casesT = getView().findViewById(R.id.cases);
                    TextView deathsT = getView().findViewById(R.id.deaths);
                    TextView recT = getView().findViewById(R.id.rec);
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
                    TextView lastUpdated = getView().findViewById(R.id.lastUpdtated);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSZ");
                    Date date = dateFormat.parse(myResponse.getTime());//You will get date object relative to server/client timezone wherever it is parsed
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
                    String dateStr = formatter.format(date);
                    Time t = new Time(date.getTime());
                    lastUpdated.setText("Last Updated: " + t.toLocaleString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Check your Connection", Toast.LENGTH_SHORT).show();
                jsonCall();
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
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }


}

