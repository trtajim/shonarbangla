package com.tajim.shonarbangla.frag;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.tajim.shonarbangla.MainActivity;
import com.tajim.shonarbangla.databinding.FragmentPricBinding;
import com.tajim.shonarbangla.others.BaseFragment;
import com.tajim.shonarbangla.others.SQLiteHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class PricFragment extends BaseFragment {
    SQLiteHelper sqLiteHelper;
    FragmentPricBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPricBinding.inflate(inflater, container, false);

        showInformation(0, true, true);
        implementGraph();
        handleSpinner();


        return binding.getRoot();
    }


    private void showInformation(int which, Boolean isGold, Boolean isBd) {
        sqLiteHelper = new SQLiteHelper(getContext());

        Cursor priceCursor = sqLiteHelper.getData();
        if (priceCursor.moveToLast()) {

            int price_k22 = Integer.parseInt(priceCursor.getString(1));
            int k21_price = Integer.parseInt(priceCursor.getString(2));
            int k18_price = Integer.parseInt(priceCursor.getString(3));

            int price_k22S = Integer.parseInt(priceCursor.getString(4));
            int k21_priceS = Integer.parseInt(priceCursor.getString(5));
            int k18_priceS = Integer.parseInt(priceCursor.getString(6));


            float price_k22Saudi = Float.parseFloat(priceCursor.getString(8));
            float k21_priceSaudi = Float.parseFloat(priceCursor.getString(9));
            float k18_priceSaudi = Float.parseFloat(priceCursor.getString(10));
            String date = priceCursor.getString(7);

            float price;

            if (which == 0 && isGold && isBd) price = price_k22;
            else if (which == 1 && isGold && isBd) price = k21_price;
            else if (which == 2 && isGold && isBd) price = k18_price;
            else if (which == 0 && !isGold && isBd) price = price_k22S;
            else if (which == 1 && !isGold && isBd) price = k21_priceS;
            else if(which == 2 && !isGold && isBd) price = k18_priceS;
            else if (which == 0 && isGold && !isBd) price = price_k22Saudi;
            else if (which == 1 && isGold && !isBd) price = k21_priceSaudi;
            else if (which == 2 && isGold && !isBd) price = k18_priceSaudi;
            else price = 0;

            price+= 0.00F;


            float k22_vori, k22_roti, k22_ana, k22_point;
            k22_vori = (float) (price* 11.664);
            k22_ana = k22_vori / 16;
            k22_roti = k22_ana / 6;
            k22_point = k22_roti / 10;

            binding.tvDisplay.setText(Html.fromHtml(String.format(
                    "<b>প্রতি ভরি:</b> %s ৳<br>" +
                            "<b>প্রতি আনা:</b> %s ৳<br>" +
                            "<b>প্রতি রতি:</b> %s ৳<br>" +
                            "<b>প্রতি পয়েন্ট:</b> %s ৳<br>" +
                            "<b>প্রতি গ্রাম:</b> %s ৳",
                    floatToBengali(k22_vori),
                    floatToBengali(k22_ana),
                    floatToBengali(k22_roti),
                    floatToBengali(k22_point),
                    floatToBengali(price)
            )));



            binding.tvDate.setText(String.format("তারিখ: %s\nসোর্স: বাংলাদেশ জুয়েলারি সমিতি", date));
            MainActivity.PRICE = k22_vori;

            priceCursor.close();
        }
    }

    private void implementGraph() {
        Cursor cursor = sqLiteHelper.getData();
        ArrayList<com.github.mikephil.charting.data.Entry> entries1 = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        ArrayList<com.github.mikephil.charting.data.Entry> entries2 = new ArrayList<>();

        int index = 0;
        int chartIndex = 0;
        while (cursor.moveToNext()) {
            if (index % 7 == 0) {
                // First line (e.g., price from column 1)
                float price1 = Float.parseFloat(cursor.getString(1));
                entries1.add(new com.github.mikephil.charting.data.Entry(chartIndex, price1));

                // Second line (e.g., another value from column 2)
                float price2 = Float.parseFloat(cursor.getString(2));
                entries2.add(new com.github.mikephil.charting.data.Entry(chartIndex, price2));

                // Dates for X axis
                dates.add(fixDate(cursor.getString(3)));

                chartIndex++;
            }
            index++;
            if (index > 35) break;
        }
        cursor.close();

        // First dataset
        com.github.mikephil.charting.data.LineDataSet dataSet1 =
                new com.github.mikephil.charting.data.LineDataSet(entries1, "২২ ক্যারেট স্বর্ণ");
        dataSet1.setColor(android.graphics.Color.BLUE);
        dataSet1.setDrawCircles(false);
        dataSet1.setDrawValues(false);

        // Second dataset
        com.github.mikephil.charting.data.LineDataSet dataSet2 =
                new com.github.mikephil.charting.data.LineDataSet(entries2, "২১ ক্যারেট স্বর্ণ");
        dataSet2.setColor(android.graphics.Color.RED);
        dataSet2.setDrawCircles(false);
        dataSet2.setDrawValues(false);

        // Combine datasets
        com.github.mikephil.charting.data.LineData lineData = new com.github.mikephil.charting.data.LineData();
        lineData.addDataSet(dataSet1);
        lineData.addDataSet(dataSet2);

        binding.chart.setData(lineData);

        // X axis setup
        binding.chart.getXAxis().setValueFormatter(
                new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(dates));
        binding.chart.getXAxis().setGranularity(1f);
        binding.chart.getXAxis().setPosition(com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM);

        binding.chart.getDescription().setEnabled(false);
        binding.chart.getAxisRight().setEnabled(false);

        binding.chart.invalidate();
    }




    private String floatToBengali(float value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setGroupingSeparator(',');

        // Pattern: #,##,###.## → 2 decimal max
        DecimalFormat df = new DecimalFormat("#,##,###.##", symbols);
        df.setGroupingUsed(true);

        String formatted = df.format(value);

        // Convert digits to Bengali
        char[] banglaDigits = {'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
        StringBuilder result = new StringBuilder();
        for (char c : formatted.toCharArray()) {
            if (c >= '0' && c <= '9') {
                result.append(banglaDigits[c - '0']);
            } else {
                result.append(c); // keep . or ,
            }
        }
        return result.toString();
    }

    public static String fixDate(String date) {
        String formatted = date; // fallback in case SDK < O

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                LocalDate date1 = LocalDate.parse(date); // parse string to LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM");
                formatted = date1.format(formatter).toUpperCase(); // 9 OCT
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return formatted;
    }
    Boolean first1 = true;
    Boolean first2 = true;
    private void handleSpinner(){


        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (first1){
                    first1 = false;
                    return;
                }
                startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        endLoading();
                    }
                },250);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: handle nothing selected
            }
        });
        binding.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (first2){
                    first2 = false;
                    return;
                }
                startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        endLoading();
                    }
                },250);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: handle nothing selected
            }
        });
        binding.spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (first2){
                    first2 = false;
                    return;
                }
                startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        endLoading();
                    }
                },250);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: handle nothing selected
            }
        });
    }

    private void refresh(){
        Boolean isGold, isBd;
        if (binding.spinner3.getSelectedItemId() == 0) isBd = true;
        else isBd = false;
        if (binding.spinner2.getSelectedItemId() == 0 ) isGold = true;
        else isGold = false;
        showInformation((int) binding.spinner.getSelectedItemId(),isGold, isBd);

    }

}