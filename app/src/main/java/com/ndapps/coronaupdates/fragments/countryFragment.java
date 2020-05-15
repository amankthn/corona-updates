package com.ndapps.coronaupdates.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ndapps.coronaupdates.R;
import com.ndapps.coronaupdates.fragments.dummy.DummyContent.DummyItem;
import com.ndapps.coronaupdates.model.Cases;
import com.ndapps.coronaupdates.model.Deaths;
import com.ndapps.coronaupdates.model.MyResponse;
import com.ndapps.coronaupdates.model.Tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class countryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    String url = "https://covid-193.p.rapidapi.com/statistics";
    JsonObjectRequest request;
    RequestQueue requestQueue;

    RecyclerView myRecView;
    List<MyResponse> countries = new ArrayList<>();


    public countryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static countryFragment newInstance(int columnCount) {
        countryFragment fragment = new countryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
//        myResponse.setCases(cases);
//        myResponse.setCountry("USA");
//        myResponse.setDay("02-10-1999");
//        myResponse.setTests(tests);
//        myResponse.setDeaths(deaths);
//        myResponse.setTime("09:00AM");
//        countries.add(myResponse);
//
//        MyResponse myResponse1 = new MyResponse();
//        myResponse1.setCases(cases);
//        myResponse1.setCountry("UK");
//        myResponse1.setDay("02-10-1999");
//        myResponse1.setTests(tests);
//        myResponse1.setDeaths(deaths);
//        myResponse1.setTime("09:00AM");
//        countries.add(myResponse1);


        jsonCall();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_list, container, false);

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new MycountryRecyclerViewAdapter(DummyContent.ITEMS, mListener));
//        }
        myRecView = (RecyclerView) view.findViewById(R.id.list);

        EditText s = view.findViewById(R.id.search);
        s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    public void jsonCall() {
        request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                JSONObject jsonCases = null;
                JSONObject jsonDeaths = null;
                JSONObject jsonTests = null;

                Log.d("RL", Integer.toString(response.length()));
                try {
                    for (int i = 0; i < response.getInt("results"); i++) {

                        try {
                            MyResponse myResponse = new MyResponse();
                            Cases cases = new Cases();
                            Deaths deaths = new Deaths();
                            Tests tests = new Tests();
                            jsonArray = response.getJSONArray("response");
                            jsonObject = jsonArray.getJSONObject(i);
                            Log.d("JSONCON", jsonObject.getString("country"));
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

                            jsonTests = jsonObject.getJSONObject("tests");
                            tests.setTotal(jsonTests.getString("total"));

                            Log.d("TEST", tests.toString());
                            if (cases.getNewCases().equals("null"))
                                cases.setNewCases("+0");
                            if (deaths.getNewDeaths().equals("null"))
                                deaths.setNewDeaths("+0");
                            myResponse.setCases(cases);
                            myResponse.setDeaths(deaths);
                            myResponse.setTests(tests);

                            countries.add(myResponse);
                            Log.d("RESPONSE2", Integer.toString(i));
//                        Log.d("COUNTRY", countries.get(i).toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }

                setRvadapter(countries);
                Toast.makeText(getActivity(), "You can click on card to get more details", Toast.LENGTH_LONG).show();
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

        Log.d("LISTSIZE", Integer.toString(countries.size()));


    }

    public void setRvadapter(List<MyResponse> lst) {

        MycountryRecyclerViewAdapter myAdapter = new MycountryRecyclerViewAdapter(getActivity(), lst);
        myRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecView.setAdapter(myAdapter);
        TextView load = getView().findViewById(R.id.textload);
        load.setVisibility(View.INVISIBLE);
    }

    void filter(String text) {
        List<MyResponse> temp = new ArrayList();
        text = text.toLowerCase().trim();
        for (MyResponse d : countries) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getCountry().toLowerCase().contains(text)) {
                temp.add(d);
            }
            Log.d("FILTER", d.getCountry());
        }
        //update recyclerview
        MycountryRecyclerViewAdapter myAdapter = new MycountryRecyclerViewAdapter(getActivity(), temp);
        myRecView.setAdapter(myAdapter);
    }


}
