package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private Context context;
    private RequestQueue mRequestQueue;
    private String URL="https://debugtm.herokuapp.com/user/getProgressTripsByUser?userUid=";
    private ArrayList<Trip> trips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        context = this;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return ;
        final String uid=user.getUid();
        final ProgressBar progress = findViewById(R.id.indeterminateBar);
        final RecyclerView rvTrips = (RecyclerView) findViewById(R.id.recyclerview_chat);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(context));
        trips = new ArrayList<Trip>();

        mRequestQueue = Volley.newRequestQueue(context);
        new GetTripInteraction(context, rvTrips, progress).getChatTripsFromServer(URL+uid, mRequestQueue, trips);
/*
        //swipe da finire
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //temporaneo
                        rvTrips.setLayoutManager(new LinearLayoutManager(context));
                        trips=new ArrayList<Trip>();

                        mRequestQueue = Volley.newRequestQueue(context);
                        new GetTripInteraction(context, rvTrips, progress).getChatTripsFromServer(URL+uid,mRequestQueue,trips);
                        swipe.setRefreshing(false);

                    }
                },1500);
            }
        });*/


    }
}