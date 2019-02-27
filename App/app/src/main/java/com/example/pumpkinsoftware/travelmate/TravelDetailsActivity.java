package com.example.pumpkinsoftware.travelmate;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;

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
    public final static String EXTRA_GROUP = "travelmate_extra_tda_TRIP_GROUP";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        context = (Context) this;

        Bundle b = getIntent().getExtras();
        final String id =  b.getString(EXTRA_ID);
        final String img =  b.getString(EXTRA_IMG);
        final String name =  b.getString(EXTRA_NAME);
        final String descr =  b.getString(EXTRA_DESCR);
        final String dep =  b.getString(EXTRA_DEPARTURE);
        final String dest =  b.getString(EXTRA_DEST);
        final String budget =  b.getString(EXTRA_BUDGET);
        final String start =  b.getString(EXTRA_START);
        final String end =  b.getString(EXTRA_END);
        final String group =  b.getString(EXTRA_GROUP);

        final ImageView imgv = (ImageView) findViewById(R.id.header_cover_image);
        GlideApp.with(this)
                .load(img)
                .placeholder(R.mipmap.placeholder_image)
                .into(imgv);
        final TextView n = (TextView) findViewById(R.id.name);
        n.setText(name);
        final TextView dsc = (TextView) findViewById(R.id.descr);

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
            final ConstraintLayout layout = findViewById(R.id.layout2);
            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.descr_for_older_versions);
            layout.setLayoutParams(params);
        }

        final TextView dp = (TextView) findViewById(R.id.from);
        dp.setText(dep);
        final TextView dt = (TextView) findViewById(R.id.to);
        dt.setText(dest);
        final TextView bud = (TextView) findViewById(R.id.budget);
        bud.setText(budget);
        final TextView s = (TextView) findViewById(R.id.date1);
        s.setText(start);
        final TextView e = (TextView) findViewById(R.id.date2);
        e.setText(end);
        final TextView g = (TextView) findViewById(R.id.n_users);
        g.setText(group);

        final ImageView back_image = (ImageView) findViewById(R.id.back);
        // Handling back to parent activity
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        final CheckBox fav_image = (CheckBox) findViewById(R.id.fav_image);
        // Handling animation on click
        fav_image.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                set.setTarget(buttonView); // set the view you want to animate
                set.start();
            }
        });

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
}
