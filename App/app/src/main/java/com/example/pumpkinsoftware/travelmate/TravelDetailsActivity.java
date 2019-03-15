package com.example.pumpkinsoftware.travelmate;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetPartecipantIteration;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripById;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostJoin;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TravelDetailsActivity extends AppCompatActivity {
    public final static String EXTRA_ID = "travelmate_extra_tda_TRIP_ID";
    public final static String EXTRA_IMG = "travelmate_extra_tda_TRIP_IMG";
    public final static String EXTRA_NAME = "travelmate_extra_tda_TRIP_NAME";
    public final static String EXTRA_DESCR = "travelmate_extra_tda_TRIP_DESCR";
    public final static String EXTRA_DEPARTURE = "travelmate_extra_tda_TRIP_DEP";
    public final static String EXTRA_DEST = "travelmate_extra_tda_TRIP_DEST";
    public final static String EXTRA_BUDGET = "travelmate_extra_tda_TRIP_BUDGET";
    public final static String EXTRA_START = "travelmate_extra_tda_TRIP_START";
    public final static String EXTRA_END = "travelmate_extra_tda_TRIP_END";
    public final static String EXTRA_PARTECIPANTS_NUMBER = "travelmate_extra_tda_TRIP_EXTRA_PARTECIPANTS_NUMBER";
    public final static String EXTRA_GROUP_NUMBER = "travelmate_extra_tda_TRIP_EXTRA_GROUP_NUMBER";
    public final static String EXTRA_TAG = "travelmate_extra_tda_TRIP_TAG";
    public final static String EXTRA_VEHICLE ="travelmate_extra_tda_TRIP_VEHICLE";
    public final static String EXTRA_OWNER_UID ="travelmate_extra_tda_TRIP_OWNER_UID";
    public final static String EXTRA_ADAPTER ="travelmate_extra_tda_TRIP_EXTRA_ADAPTER";
    public final static String EXTRA_ADAPTER_POS ="travelmate_extra_tda_TRIP_EXTRA_ADAPTER_POS";

    private Context context;
    private boolean so_prev_lol; // Useful for transitions

    private final static String QUERY= "https://debugtm.herokuapp.com/user/getUsersByTrip?tripId=";
    private RequestQueue mRequestQueue;
    private ArrayList<User> partecipants;
    private CardView card;
    private CircleImageView o_image;
    private String travelId;
    private String userUid;
    private String owner_uid;
    private int partecipantsNumber;
    private int group;
    private Button joinBtn;
    private RecyclerView rvUsers;
    private ProgressBar progress;
    private TripsAdapter adapter;
    private int adapterPos;
    private Trip trip;
    private ArrayList<Trip> trips;
    private boolean isFileDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        context = (Context) this;

        Bundle b = getIntent().getExtras();
        travelId =  b.getString(EXTRA_ID);
        final String img =  b.getString(EXTRA_IMG);
        final String name =  b.getString(EXTRA_NAME);
        final String descr =  b.getString(EXTRA_DESCR);
        final String dep =  b.getString(EXTRA_DEPARTURE);
        final String dest =  b.getString(EXTRA_DEST);
        final String budget =  b.getString(EXTRA_BUDGET);
        final String start =  b.getString(EXTRA_START);
        final String end =  b.getString(EXTRA_END);
        partecipantsNumber =  b.getInt(EXTRA_PARTECIPANTS_NUMBER);
        group =  b.getInt(EXTRA_GROUP_NUMBER);
        final String tag = b.getString(EXTRA_TAG);
        final String vehicle = b.getString(EXTRA_VEHICLE);
        owner_uid = b.getString(EXTRA_OWNER_UID);
        adapter = (TripsAdapter) b.getSerializable(EXTRA_ADAPTER);
        adapterPos = b.getInt(EXTRA_ADAPTER_POS);

        // OLD
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userUid = user.getUid();
            if(userUid.equals(owner_uid)) {
                final ImageView edit = findViewById(R.id.edit_image);
                edit.setVisibility(View.VISIBLE);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO edit trip
                    }
                });

            }
        }

        final ImageView imgv = (ImageView) findViewById(R.id.header_cover_image);
        loadImg(img, imgv);
        final TextView n = (TextView) findViewById(R.id.name);
        n.setText(name);
        final TextView dsc = (TextView) findViewById(R.id.descr);
        dsc.setText(descr);

        // Justified text alignment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dsc.setText(descr);
            dsc.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        else {
            final WebView view = (WebView) findViewById(R.id.descr_for_older_versions);
            String text = "<html><body><p align=\"justify\">";
            text+= descr;
            text+= "</p></body></html>";
            view.loadData(text, "text/html", "utf-8");
            view.setVisibility(View.VISIBLE);
            dsc.setVisibility(View.GONE);
            // Now I've to change the below param of the below elements
            final RelativeLayout layout = findViewById(R.id.layout2);
            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.descr_for_older_versions);
            layout.setLayoutParams(params);
        }

        final TextView t_tag=(TextView) findViewById(R.id.tag);
        if(tag.equals("cultura")){
            t_tag.setBackgroundColor(Color.parseColor("#008000")); //verde
        }else if (tag.equals("musica")){
            t_tag.setBackgroundColor(Color.parseColor("#FF8C00")); //arancione(dark)
        }else if(tag.equals("intrattenimento")){
            t_tag.setBackgroundColor(Color.parseColor("#FF0000")); //rosso
        }else{
            t_tag.setBackgroundColor(Color.parseColor("#1E90FF")); //blu
        }
        t_tag.setText(tag);

        final TextView dp = (TextView) findViewById(R.id.from);
        dp.setText(dep);
        final TextView dt = (TextView) findViewById(R.id.to);
        dt.setText(dest);
        final TextView bud = (TextView) findViewById(R.id.budget);
        bud.setText(budget);
        final TextView s = (TextView) findViewById(R.id.date1);
        s.setText(getData(start));
        final TextView e = (TextView) findViewById(R.id.date2);
        e.setText(getData(end));
        final TextView g = (TextView) findViewById(R.id.n_users);
        g.setText(partecipantsNumber+"/"+group);

        final TextView vm= (TextView) findViewById(R.id.vehicle_text);
        final ImageView vi= (ImageView) findViewById(R.id.vehicle_image);
        vm.setText(vehicle);
        if(vehicle.equals("treno")){
            vi.setImageResource(R.drawable.ic_train_black_12dp);
        }else{
            vi.setImageResource(R.drawable.ic_directions_car_black_12dp);
        }


        final ImageView back_image = (ImageView) findViewById(R.id.back);
        // Handling back to parent activity
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NON FUNZIONA, trips risulta null
                /*if(trip != null && adapter != null) {
                    trips = new ArrayList<Trip>(adapter.getTrips());
                    trips.set(adapterPos, trip);
                    adapter.notifyItemChanged(adapterPos);
                }*/

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)    finishAfterTransition();
                else    finish();
            }
        });

        /* favoriti
        final CheckBox fav_image = (CheckBox) findViewById(R.id.fav_image);
        // Handling animation on click
        fav_image.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                set.setTarget(buttonView); // set the view you want to animate
                set.start();
            }
        });*/

        final ImageView sharing_image = (ImageView) findViewById(R.id.sharing_image);
        // Handling sharing on click with animation
        sharing_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                set.setTarget(v); // set the view you want to animate
                set.start();
                shareText(name);
            }
        });

        card = (CardView) findViewById(R.id.cardView);
        joinBtn = (Button) findViewById(R.id.join_button);

        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(getData(start));
            //Toast.makeText(context, date1.toString(), Toast.LENGTH_SHORT).show();
            Calendar now = Calendar.getInstance();
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(date1);

            // Check if travel is expired
            if (now.equals(startDate) || now.after(startDate)) {
                card.setVisibility(View.GONE);
            }

            else {
                // Handling join on click with animation
                joinBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                set.setTarget(v); // set the view you want to animate
                set.start();*/
                        join();
                    }
                });
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        // Handling partecipants
        /*RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        final GetUserByUid server =  new GetUserByUid(context);
        server.getUserFromServer(URL+owner_uid, mRequestQueue, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        final User owner = server.getUser();
                        o_image = findViewById(R.id.profile1);

                        GlideApp.with(context)
                                .load((owner.getPhotoProfile().isEmpty())?(R.drawable.girl):(owner.getPhotoProfile()))
                                .placeholder(R.mipmap.placeholder_image)
                                .into(o_image);
                        TextView o_name = findViewById(R.id.user1);
                        o_name.setText(owner.getName());

                        View.OnClickListener lis = new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                openUser(owner);
                            }
                        };

                        o_image.setOnClickListener(lis);
                        o_name.setOnClickListener(lis);
                    }
                }
        );*/

        progress = findViewById(R.id.indeterminateBar);
        rvUsers = (RecyclerView) findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvUsers.setLayoutManager(new LinearLayoutManager(context));
        rvUsers.setNestedScrollingEnabled(false);
        //updateLayout();
        getPartecipants(rvUsers, progress, travelId, owner_uid);

        //openDynamicLink();

        //swipe da finire
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateLayout();
                        swipe.setRefreshing(false);

                    }
                },1500);
            }
        });
    }

    // OLD
    private void getPartecipants(RecyclerView rvUsers, ProgressBar progress, String id, final String owner_uid) {
        partecipants = new ArrayList<User>();

        mRequestQueue = Volley.newRequestQueue(context);
        final GetPartecipantIteration server =  new GetPartecipantIteration(context, rvUsers, progress);
        server.getPartecipantFromServer(QUERY+id, owner_uid, mRequestQueue,
                partecipants, userUid, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        final String img = server.getOwnerImg();
                        o_image = findViewById(R.id.profile1);

                        GlideApp.with(context)
                                .load((img.isEmpty())?(R.drawable.blank_avatar):(img))
                                .placeholder(R.mipmap.placeholder_image)
                                .into(o_image);
                        TextView o_name = findViewById(R.id.user1);
                        o_name.setText(server.getOwnerName());

                        View.OnClickListener lis = new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                openUser(owner_uid);
                            }
                        };

                        o_image.setOnClickListener(lis);
                        o_name.setOnClickListener(lis);

                        if(partecipantsNumber == group) card.setVisibility(View.GONE);

                        if(userUid.equals(owner_uid)) {
                            if(partecipantsNumber == 1)   joinBtn.setText("Elimina");
                            else                          joinBtn.setText("Abbandona");
                            card.setCardBackgroundColor(colorTo);
                        }

                        else if(server.isUserAPartecipant()) {
                            joinBtn.setText("Abbandona");
                            card.setCardBackgroundColor(colorTo);
                        }

                        else joinBtn.setText("Unisciti");

                    }
                }
        );
    }

    // Handling sharing
    private void shareText(String s) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        //String shareBodyText = "Your sharing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Che ne dici di dare un'occhiata a "+ s + "?");
        startActivity(Intent.createChooser(intent, "Condividi"));
    }

    // CHIAMEREMO QUESTA QUANDO PRONTA INVECE DI shareText E DA QUI CHIAMEREMO shareText
    private void createDynamicUri() {
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://"+R.string.host))
                .setDomainUriPrefix("https://example.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            //shareText(shortLink.toString());
                        } else {
                            Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Handling receive a dynamic link
    private void openDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            // TODO open link
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }

    // Handling join button with animation
    private int colorFrom = R.color.colorPrimary;
    private int colorTo = Color.RED;

    private void join() {
        String label = joinBtn.getText().toString();
        if (label.equals("Abbandona")) {
            new AlertDialog.Builder(this)
                    .setTitle("Abbandona evento")
                    .setMessage("Vuoi lasciare il gruppo?")
                    .setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                            set.setTarget(b); // set the view you want to animate
                            set.start();*/

                            if(userUid.equals(owner_uid))
                                changeOwner();

                            // Remove user from travel
                            else {
                                new PostJoin(context).send("https://debugtm.herokuapp.com/user/removeTrip/",
                                        travelId, userUid, PostJoin.request.ABANDON);
                            }

                            animate(joinBtn);

                            ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(card,
                                    "backgroundColor",
                                    new ArgbEvaluator(),
                                    colorTo,
                                    colorFrom);
                            backgroundColorAnimator.setDuration(300);
                            backgroundColorAnimator.start();
                            joinBtn.setText("Unisciti");

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateLayout();
                                }
                            },200);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }

        else if(label.equals("Elimina")) {
            new AlertDialog.Builder(this)
                    .setTitle("Elimina evento")
                    .setMessage("Vuoi eliminare l'evento?")
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                            set.setTarget(b); // set the view you want to animate
                            set.start();*/

                            String storageUrl = trip.getImage();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(storageUrl);
                            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // File deleted successfully
                                    isFileDeleted = true;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                   // Log.d(TAG, "onFailure: did not delete file");
                                }
                            });

                            if(!isFileDeleted) {
                                Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Delete travel
                            final PostJoin server = new PostJoin(context);
                            server.delete("https://debugtm.herokuapp.com/trip/deleteTrip?tripId="+travelId, new ServerCallback() {
                                @Override
                                public void onSuccess(JSONObject response) {
                                    // Check if trip is really deleted from server
                                    if (server.isDeleted()) {
                                        // TODO delete travel img from server
                                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                            finishAfterTransition();
                                        else finish();
                                    }
                                }
                            });

                            animate(joinBtn);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            }

        else {
            /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
            set.setTarget(b); // set the view you want to animate
            set.start();*/
            // Add user from travel
            new PostJoin(this).send("https://debugtm.herokuapp.com/user/addTrip/", travelId,
                    userUid, PostJoin.request.JOIN);
            animate(joinBtn);
            ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(card,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    colorFrom,
                    colorTo);
            backgroundColorAnimator.setDuration(300);
            backgroundColorAnimator.start();
            joinBtn.setText("Abbandona");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateLayout();
                }
            },200);
        }
    }

    // Animation zoom in on a view
    private void animate(View v) {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
        set.setTarget(v); // set the view you want to animate
        set.start();
    }

    private void changeOwner() {
        User user = partecipants.get(1);
        new PostJoin(this).send("https://debugtm.herokuapp.com/user/changeOwnerAndRemoveLast", travelId,
                user.getUid(), PostJoin.request.CHANGE);
    }

    private void updateLayout() {
        final GetTripById server = new GetTripById(this);
        server.getTripFromServer("https://debugtm.herokuapp.com/trip/getTripByIdWithUsers?id="+travelId,
                owner_uid, partecipants, userUid,
                new ServerCallback(){
                    @Override
                    public void onSuccess(JSONObject response) {
                        trip = server.getTrip();
                        loadTrip(trip);

                        //getPartecipants(rvUsers, progress, travelId, owner_uid);
                        final String img = server.getOwnerImg();
                        o_image = findViewById(R.id.profile1);

                        GlideApp.with(context)
                                .load((img.isEmpty())?(R.drawable.blank_avatar):(img))
                                .placeholder(R.mipmap.placeholder_image)
                                .into(o_image);
                        TextView o_name = findViewById(R.id.user1);
                        o_name.setText(server.getOwnerName());

                        View.OnClickListener lis = new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                openUser(owner_uid);
                            }
                        };

                        o_image.setOnClickListener(lis);
                        o_name.setOnClickListener(lis);

                        if(partecipantsNumber == group) card.setVisibility(View.GONE);

                        if(userUid.equals(owner_uid)) {
                            if(partecipantsNumber == 1)   joinBtn.setText("Elimina");
                            else                          joinBtn.setText("Abbandona");
                            card.setCardBackgroundColor(colorTo);
                        }

                        else if(server.isUserAPartecipant()) {
                            joinBtn.setText("Abbandona");
                            card.setCardBackgroundColor(colorTo);
                        }

                        else joinBtn.setText("Unisciti");
                    }
                });
    }

    private void loadTrip(Trip t) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userUid = user.getUid();
            if(userUid.equals(owner_uid)) {
                final ImageView edit = findViewById(R.id.edit_image);
                edit.setVisibility(View.VISIBLE);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO edit trip
                    }
                });

            }
        }

        final ImageView imgv = (ImageView) findViewById(R.id.header_cover_image);
        loadImg(t.getImage(), imgv);
        final TextView n = (TextView) findViewById(R.id.name);
        n.setText(t.getName());
        final TextView dsc = (TextView) findViewById(R.id.descr);

        // Justified text alignment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dsc.setText(t.getDescr());
            dsc.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        else {
            final WebView view = (WebView) findViewById(R.id.descr_for_older_versions);
            String text = "<html><body><p align=\"justify\">";
            text+= t.getDescr();
            text+= "</p></body></html>";
            view.loadData(text, "text/html", "utf-8");
            view.setVisibility(View.VISIBLE);
            dsc.setVisibility(View.GONE);
            // Now I've to change the below param of the below elements
            final RelativeLayout layout = findViewById(R.id.layout2);
            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.descr_for_older_versions);
            layout.setLayoutParams(params);
        }

        final TextView t_tag=(TextView) findViewById(R.id.tag);
        String tag = t.getTag();
        if(tag.equals("cultura")){
            t_tag.setBackgroundColor(Color.parseColor("#008000")); //verde
        }else if (tag.equals("musica")){
            t_tag.setBackgroundColor(Color.parseColor("#FF8C00")); //arancione(dark)
        }else if(tag.equals("intrattenimento")){
            t_tag.setBackgroundColor(Color.parseColor("#FF0000")); //rosso
        }else{
            t_tag.setBackgroundColor(Color.parseColor("#1E90FF")); //blu
        }
        t_tag.setText(tag);

        final TextView dp = (TextView) findViewById(R.id.from);
        dp.setText(t.getDeparture());
        final TextView dt = (TextView) findViewById(R.id.to);
        dt.setText(t.getDest());
        final TextView bud = (TextView) findViewById(R.id.budget);
        bud.setText(t.getBudget());
        final TextView s = (TextView) findViewById(R.id.date1);
        s.setText(getData(t.getStartDate()));
        final TextView e = (TextView) findViewById(R.id.date2);
        e.setText(getData(t.getEndDate()));
        final TextView g = (TextView) findViewById(R.id.n_users);
        g.setText(t.getGroup());

        final TextView vm = (TextView) findViewById(R.id.vehicle_text);
        final ImageView vi = (ImageView) findViewById(R.id.vehicle_image);
        String vehicle = t.getVehicle();
        vm.setText(vehicle);

        if(vehicle.equals("treno"))     vi.setImageResource(R.drawable.ic_train_black_12dp);
        else                            vi.setImageResource(R.drawable.ic_directions_car_black_12dp);
    }

    // Open user on click
    private void openUser(User u) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(UserDetailsActivity.EXTRA_UID, u.getUid());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                    Pair.create((View)o_image, "image"));
            //Pair.create((View)trip_name, "travel_name"));
            // start the new activity
            context.startActivity(intent, options.toBundle());
        }

        else {
            context.startActivity(intent);
        }
    }

    // Open user on click
    private void openUser(String uid) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(UserDetailsActivity.EXTRA_UID, uid);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                    Pair.create((View)o_image, "image"));
            //Pair.create((View)trip_name, "travel_name"));
            // start the new activity
            context.startActivity(intent, options.toBundle());
        }

        else {
            context.startActivity(intent);
        }
    }

    // Load travel image with transition efficiently
    private void loadImg(String img, ImageView imgv) {
        so_prev_lol = false;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            postponeEnterTransition();
        else {
            so_prev_lol = true;
            supportPostponeEnterTransition();
        }

        GlideApp.with(this)
                .load((img.isEmpty())?(R.mipmap.default_trip):(img))
                .placeholder(R.mipmap.placeholder_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(so_prev_lol)
                            supportStartPostponedEnterTransition();
                        else
                            startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(so_prev_lol)
                            supportStartPostponedEnterTransition();
                        else
                            startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(imgv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sharing_menu, menu);
        return true; //super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. Your message goes here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;

            default:
                return false;
        }
    }
    public String getData(String data) {
        String[] d=data.split("-");
        return d[2].substring(0,2) +"/"+d[1]+"/"+d[0];
    }

    @Override
    public void onBackPressed() {
        if(trip != null && adapter != null) {
            adapter.notifyDataSetChanged();
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)    finishAfterTransition();
        else    finish();
    }

}