package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.user.User;

import org.json.JSONObject;

public class UserDetailsActivity extends AppCompatActivity {
    public final static String EXTRA_UID = "travelmate_extra_uda_USER_UID";
    private final static String URL = "https://debugtm.herokuapp.com/user/getUserByUid?uid=";
    private Context context;
    private boolean so_prev_lol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partecipant_details);
        context = (Context) this;

        Bundle b = getIntent().getExtras();
        final String uid = b.getString(EXTRA_UID);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        final GetUserByUid server =  new GetUserByUid(context);
        server.getUserFromServer(URL+uid, mRequestQueue, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        loadUser(server);
                    }
                }
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)    finishAfterTransition();
                else    finish();
            }
        });
    }

    private boolean loadUser(GetUserByUid server) {
        User mUser = server.getUser();

        if(mUser == null) return false;

        ImageView img = (ImageView) findViewById(R.id.profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            img.setTransitionName("image");

        loadImg(mUser.getPhotoProfile(), img);

        img = findViewById(R.id.header_cover_image);
        GlideApp.with(context)
                .load(mUser.getCover())
                .into(img);

        TextView txt = findViewById(R.id.name);
        String ns = mUser.getName()+ " "+ mUser.getSurname(); //così non rompe
        txt.setText(ns);

        txt = findViewById(R.id.bio);
        txt.setText(mUser.getDescr());

        txt = findViewById(R.id.age2);
        txt.setText(String.valueOf(mUser.getAge()));

        txt = findViewById(R.id.gender2);
        txt.setText(mUser.getGender());

        txt = findViewById(R.id.rel2);
        txt.setText(mUser.getRelationship());

        txt = findViewById(R.id.email2);
        txt.setText(mUser.getEmail());

        txt = findViewById(R.id.n_review);
        String n="( "+(String.valueOf(mUser.getNumReviews()))+" )"; //così non rompe
        txt.setText(n);

        txt = findViewById(R.id.rating);
        txt.setText(String.valueOf(ProfileFragment.roundToHalf(mUser.getRank())));

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating((float) mUser.getRank());

        return true;
    }

    private void loadImg(String img, ImageView imgv) {
        so_prev_lol = false;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            postponeEnterTransition();
        else {
            so_prev_lol = true;
            supportPostponeEnterTransition();
        }

        GlideApp.with(this)
                .load((img.isEmpty())?(R.mipmap.placeholder_image):(img))
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

}