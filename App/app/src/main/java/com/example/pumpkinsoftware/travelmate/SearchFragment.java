package com.example.pumpkinsoftware.travelmate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.min_max_filter.MinMaxFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;

import io.apptik.widget.MultiSlider;

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        final EditText min1 = (EditText) view.findViewById(R.id.budget_min_value);
        final EditText max1 = (EditText) view.findViewById(R.id.budget_max_value);
        final EditText min2 = (EditText) view.findViewById(R.id.group_min_value);
        final EditText max2 = (EditText) view.findViewById(R.id.group_max_value);
        final SearchView searchView = (SearchView) view.findViewById(R.id.search_bar);
        final TextInputEditText  from = (TextInputEditText ) view.findViewById(R.id.from_text);
        final TextInputEditText  to = (TextInputEditText ) view.findViewById(R.id.to_text);
        final EditText  departure_date = (EditText) view.findViewById(R.id.departure);
        final EditText  return_date = (EditText) view.findViewById(R.id.ret);



        // If click on bg, focus is deleted (Da sistemare)
        view.findViewById(R.id.search_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), v.toString(), Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.clearFocus();
            }
        });

        /* It extends searchview clickable area */
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        // If click on swap button, values of editexts are swapped
        view.findViewById(R.id.swap1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    String tmp = from.getText().toString();
                    from.setText(to.getText());
                    to.setText(tmp);
                }
            }
        });

        /* Utilities for Date Picker Dialog */
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);

        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        /* Date Picker Dialog listener for data_departure, when a date is set, write it on editText and change focus */
        final DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                departure_date.setText(new StringBuilder().append(selectedDay).append("/")
                                       .append(selectedMonth+1).append("/").append(selectedYear));
            }
        };

        departure_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    DatePickerDialog d = new DatePickerDialog(getContext(), datePickerListener1, year, month, day);
                    d.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    d.show();
                }
                return false;
            }
        });

        /* Same for return_date */
        final DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                return_date.setText(new StringBuilder().append(selectedDay).append("/")
                        .append(selectedMonth+1).append("/").append(selectedYear));
            }
        };

        return_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    DatePickerDialog d = new DatePickerDialog(getContext(), datePickerListener2, year, month, day);
                    d.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    d.show();
                }
                return false;
            }
        });

        /* Extracting dates (SI POSSONO INVECE FARE LE CLASSI DEI LISTENER E USARE DELLE GET) */
        /*String date = departure_date.getText().toString();
        String d_day = "", d_month = "";
        int i;

        if(date.charAt(1) == '/') {
            d_day = date.substring(0,1);
            i = 2;
        }
        else {
            d_day = date.substring(0,2);
            i = 3;
        }

        if(date.charAt(i+1) == '/') {
            d_month = date.substring(i,i+1);
            i += 2;
        }
        else {
            d_month = date.substring(i,i+2);
            i += 3;
        }

        String d_year = date.substring(i);
        Toast.makeText(getActivity(), d_day+"/"+d_month+"/"+d_year, Toast.LENGTH_SHORT).show();*/


        /* NON PIU' UTILE MA PER IL MOMENTO NON LO CANCELLIAMO!!
        // Check if departure_date is correct
        departure_date.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Pattern p = Pattern.compile("([1-9]|(0[1-9]|1[0-9]|2[0-9]|3[01]))[^0-9]([1-9]|0[1-9]|1[012])[^0-9](19|20)[0-9]{2}");
                String str = departure_date.getText().toString();
                if(str.length()==10){
                    Matcher m = p.matcher(str);
                    if(!m.matches())
                        Toast.makeText(getActivity(), "Insert a valid date", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });

        // Check if return_date is correct
        return_date.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Pattern p = Pattern.compile("([1-9]|(0[1-9]|1[0-9]|2[0-9]|3[01]))[^0-9]([1-9]|0[1-9]|1[012])[^0-9](19|20)[0-9]{2}");
                String str = return_date.getText().toString();
                if(str.length()==10){
                    Matcher m = p.matcher(str);
                    if(!m.matches())
                        Toast.makeText(getActivity(), "Insert a valid date", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });*/

        // Verificare che la data di ritorno sia successiva a quella di partenza

        /* Multi Slider */
        MultiSlider b_multiSlider = (MultiSlider) view.findViewById(R.id.budget_range_slider);
        MultiSlider g_multiSlider = (MultiSlider) view.findViewById(R.id.group_range_slider);

        /* Set text & filters for EditText linked to multi slider */
        int mi1 = b_multiSlider.getThumb(0).getValue();
        int ma1 = b_multiSlider.getThumb(1).getValue();
        min1.setText(String.valueOf(mi1));
        max1.setText(String.valueOf(ma1));
        min1.setFilters(new InputFilter[]{ new MinMaxFilter(mi1, ma1)});
        max1.setFilters(new InputFilter[]{ new MinMaxFilter(mi1, ma1)});

        int mi2 = g_multiSlider.getThumb(0).getValue();
        int ma2 = g_multiSlider.getThumb(1).getValue();
        min2.setText(String.valueOf(mi2));
        max2.setText(String.valueOf(ma2));
        min2.setFilters(new InputFilter[]{ new MinMaxFilter(mi2, ma2)});
        max2.setFilters(new InputFilter[]{ new MinMaxFilter(mi2, ma2)});

        /* Listener multi slider */
        b_multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider,
                                       MultiSlider.Thumb thumb,
                                       int thumbIndex,
                                       int value)
            {
                if (thumbIndex == 0) {
                    min1.setText(String.valueOf(value));
                } else {
                    max1.setText(String.valueOf(value));
                }
            }

            /*@Override
            public void onStartTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {

            }

            @Override
            public void onStopTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {

            } */
        });

        g_multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if (thumbIndex == 0) {
                    min2.setText(String.valueOf(value));
                } else {
                    max2.setText(String.valueOf(value));
                }
            }

            /*@Override
            public void onStartTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {

            }

            @Override
            public void onStopTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {

            }*/
        });

        return view;
    }
}