package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapterChat;
import com.example.pumpkinsoftware.travelmate.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTripInteraction {
    private Context context;
    private RecyclerView rvTrips;
    TripsAdapter adapter;
    TripsAdapterChat adapterChat;
    private ProgressBar progressBar;
    private ArrayList<Trip> mTrips;

    public GetTripInteraction(Context c, RecyclerView rv, ProgressBar progress) {
        context = c;
        rvTrips = rv;
        progressBar = progress;
    }

    public void getTripsFromServer(String query, RequestQueue mQueue, ArrayList<Trip> trips) {
        mTrips = new ArrayList<Trip>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    //JSONArray jsonArray=response.getJSONArray("viaggi")
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject travel = response.getJSONObject(i);
                        final String id = travel.getString("_id");
                        final String image = travel.getString("image");
                        final String name = travel.getString("name");
                        final String descr = travel.getString("description");
                        final String departure = travel.getString("departure");
                        final String dest = travel.getString("destination");
                        final int budget = travel.getInt("budget");
                        final String dep_date = travel.getString("startDate");
                        final String end_date = travel.getString("endDate");
                        final int group_max = travel.getInt("maxPartecipant");
                        final int partecipants = travel.getInt("partecipants");
                        final String tag=travel.getString("tag");
                        final String vehicle= travel.getString("vehicle");
                        final String owner = travel.getString("owner");

                        mTrips.add(new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                                partecipants, group_max, tag, vehicle, owner));
                    }

                    adapter = new TripsAdapter(mTrips);
                    // Attach the adapter to the recyclerview to populate items
                    rvTrips.setAdapter(adapter);
                    hideProgressBar();
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgressBar();
                    Toast.makeText(context, "Errore: connessione fallita", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideProgressBar();
                Toast.makeText(context, "Errore: connessione assente", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }

    // NEW VERSION
    public void getTripsFromServer(String query, RequestQueue mQueue, final TextView text, final ImageView img) {
       mTrips = new ArrayList<Trip>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject travel = response.getJSONObject(i);
                        final String id = travel.getString("_id");
                        final String image = travel.getString("image");
                        final String name = travel.getString("name");
                        final String descr = travel.getString("description");
                        final String departure = travel.getString("departure");
                        final String dest = travel.getString("destination");
                        final int budget = travel.getInt("budget");
                        final String dep_date = travel.getString("startDate");
                        final String end_date = travel.getString("endDate");
                        final int group_max = travel.getInt("maxPartecipant");
                        final int partecipants = travel.getInt("partecipants");
                        final String tag=travel.getString("tag");
                        final String vehicle= travel.getString("vehicle");
                        final String owner = travel.getString("owner");

                        mTrips.add(new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                                partecipants, group_max, tag, vehicle, owner));
                    }

                    if(mTrips.isEmpty()) {
                        text.setVisibility(View.VISIBLE);
                        img.setVisibility(View.VISIBLE);
                    }

                    adapter = new TripsAdapter(mTrips);
                    // Attach the adapter to the recyclerview to populate items
                    rvTrips.setAdapter(adapter);
                    hideProgressBar();
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgressBar();
                    Toast.makeText(context, "Errore: connessione fallita", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideProgressBar();
                Toast.makeText(context, "Errore: connessione assente", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }

    public void getChatTripsFromServer(String query, RequestQueue mQueue, final ArrayList<Trip> trips) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    //JSONArray jsonArray=response.getJSONArray("viaggi")
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject travel = response.getJSONObject(i);
                        final String id = travel.getString("_id");
                        final String image = travel.getString("image");
                        final String name = travel.getString("name");
                        final String descr = travel.getString("description");
                        final String departure = travel.getString("departure");
                        final String dest = travel.getString("destination");
                        final int budget = travel.getInt("budget");
                        final String dep_date = travel.getString("startDate");
                        final String end_date = travel.getString("endDate");
                        final int group_max = travel.getInt("maxPartecipant");
                        final int partecipants = travel.getInt("partecipants");
                        final String tag=travel.getString("tag");
                        final String vehicle= travel.getString("vehicle");
                        final String owner = travel.getString("owner");

                        trips.add(new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                                partecipants, group_max, tag, vehicle, owner));
                    }

                    adapterChat = new TripsAdapterChat(trips);
                    //adapter = new TripsAdapter(mTrips);
                    rvTrips.setAdapter(adapterChat);
                    hideProgressBar();
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgressBar();
                    Toast.makeText(context, "Errore: connessione fallita", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideProgressBar();
                Toast.makeText(context, "Errore: connessione assente", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }

    private void hideProgressBar() { if (progressBar != null) progressBar.setVisibility(View.GONE); }
}
