package com.ndapps.coronaupdates.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.ndapps.coronaupdates.R;
import com.ndapps.coronaupdates.fragments.countryFragment.OnListFragmentInteractionListener;
import com.ndapps.coronaupdates.fragments.dummy.DummyContent.DummyItem;
import com.ndapps.coronaupdates.model.MyResponse;

import java.text.DecimalFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MycountryRecyclerViewAdapter extends RecyclerView.Adapter<MycountryRecyclerViewAdapter.ViewHolder> {

    RequestOptions options;
    private Context mContext;
    private List<MyResponse> mData;


    public MycountryRecyclerViewAdapter(Context mContext, List lst) {
        this.mContext = mContext;
        this.mData = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.fragment_country, parent, false);

        // click listener here

        CardView cardView = view.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView activeCasesNo = v.findViewById(R.id.activeCasesNo);
                TextView activeCases = v.findViewById(R.id.activeCases);
                TextView criticalCasesNo = v.findViewById(R.id.criticalCasesNo);
                TextView criticalCases = v.findViewById(R.id.criticalCases);
                TextView recoveredNo = v.findViewById(R.id.recoveredNo);
                TextView recovered = v.findViewById(R.id.recovered);
                TextView deathRateNo = v.findViewById(R.id.deathRateNo);
                TextView deathRate = v.findViewById(R.id.deathRate);
                TextView recRateNo = v.findViewById(R.id.recRateNo);
                TextView recRate = v.findViewById(R.id.recRate);
                TextView totalTestsNo = v.findViewById(R.id.totalTestsNo);
                TextView totalTests = v.findViewById(R.id.totalTests);
                TextView texts[] = {activeCases, activeCasesNo, criticalCases, criticalCasesNo, recovered, recoveredNo, deathRate, deathRateNo, recRate,
                        recRateNo, totalTests, totalTestsNo};
                for (TextView t : texts) {


                    if (t.getVisibility() == View.VISIBLE)
                        t.setVisibility(View.GONE);
                    else
                        t.setVisibility(View.VISIBLE);
                }

            }
        });


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.country.setText(mData.get(position).getCountry());
        holder.totalCases.setText(new DecimalFormat("##,##,##0").format(mData.get(position).getCases().getTotal()));
        holder.newCases.setText(mData.get(position).getCases().getNewCases());
        holder.totalDeaths.setText(new DecimalFormat("##,##,##0").format(mData.get(position).getDeaths().getTotal()));
        holder.newDeaths.setText(mData.get(position).getDeaths().getNewDeaths());
        holder.activeCases.setText(new DecimalFormat("##,##,##0").format(mData.get(position).getCases().getActive()));
        holder.criticalCases.setText(new DecimalFormat("##,##,##0").format(mData.get(position).getCases().getCritical()));
        holder.recovered.setText(new DecimalFormat("##,##,##0").format(mData.get(position).getCases().getRecovered()));
        float deathRateNo = (float) mData.get(position).getDeaths().getTotal() / (float) mData.get(position).getCases().getTotal() * 100;
        float recRateNo = (float) mData.get(position).getCases().getRecovered() / (float) mData.get(position).getCases().getTotal() * 100;
        holder.deathRate.setText(new DecimalFormat("##,##,##0.##").format(deathRateNo) + "%");
        holder.recRate.setText(new DecimalFormat("##,##,##0.00").format(recRateNo) + "%");
        if (mData.get(position).getTests().getTotal().equals("null"))
            holder.totalTests.setText("Not Available");
        else
            holder.totalTests.setText(new DecimalFormat("##,##,##0").format(Integer.parseInt(mData.get(position).getTests().getTotal())));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView country, totalCases, newCases, totalDeaths, newDeaths, textload, activeCases, criticalCases, recovered, deathRate, recRate, totalTests;

        public ViewHolder(View view) {
            super(view);
            country = view.findViewById(R.id.country);
            totalCases = view.findViewById(R.id.totalCasesNo);
            newCases = view.findViewById(R.id.newCasesNo);
            totalDeaths = view.findViewById(R.id.totalDeathsNo);
            newDeaths = view.findViewById(R.id.newDeathsNo);
            activeCases = view.findViewById(R.id.activeCasesNo);
            criticalCases = view.findViewById(R.id.criticalCasesNo);
            recovered = view.findViewById(R.id.recoveredNo);
            deathRate = view.findViewById(R.id.deathRateNo);
            recRate = view.findViewById(R.id.recRateNo);
            totalTests = view.findViewById(R.id.totalTestsNo);
        }
    }

    public void updateList(List<MyResponse> lst) {
        mData = lst;
        notifyDataSetChanged();
    }


}
