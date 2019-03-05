package com.example.pumpkinsoftware.travelmate;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.example.pumpkinsoftware.travelmate.content_fragment_travels.Tab1;
import com.example.pumpkinsoftware.travelmate.pager_adapter.SlidePagerAdapter;
import com.example.pumpkinsoftware.travelmate.trip.Trip;

import java.util.ArrayList;

public class ViaggiFragment extends Fragment implements View.OnClickListener {
    public FloatingActionButton fabBtn;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View view = inflater.inflate(R.layout.fragment_viaggi, container, false);
         context = getContext();

        //copiato dal tutorial prima che funzionasse da fare tentativi se serve
        fabBtn = (FloatingActionButton) inflater.inflate(R.layout.fragment_viaggi, container, false).findViewById(R.id.floatbutton);
        fabBtn.setOnClickListener(this);
        //fine

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        PagerAdapter mAdapter = new SlidePagerAdapter(getFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout = view.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                int position = tab.getPosition();
                /*Log.i("posizione",String.valueOf(position));
                if(position==0){
                    jsonParse(URL0, progress);
                }else if (position==1){
                    jsonParse(URL1, progress);
                }else jsonParse(URL2, progress);*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;

    }


    //necessario per implements del tutorial
    public void onClick(View v) {
        Intent intent = new Intent(ViaggiFragment.this.getActivity(), CreationTrip.class);
        startActivity(intent);
    };
    //il metodo run this è dichiarato nel main, l'activity genitore

}