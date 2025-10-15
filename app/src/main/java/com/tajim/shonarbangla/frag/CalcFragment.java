package com.tajim.shonarbangla.frag;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tajim.shonarbangla.MainActivity;
import com.tajim.shonarbangla.databinding.FragmentCalcBinding;

public class CalcFragment extends Fragment {
    FragmentCalcBinding binding;
    float vori, ana, roti, point ;

    int voriCount, anaCount, rotiCount, pointCount, mojuriCount;

    float voriFinal, anaFinal, rotiFinal, pointFinal, totalFinal, mojuriFinal, priceTotalFinal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalcBinding.inflate(inflater, container, false);
        setup();



        return binding.getRoot();
    }

    private void setup(){

        binding.edPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String ss = s.toString();
                if (ss.isEmpty()) vori = 0;
                else vori = Float.parseFloat(ss);


                ana = vori / 16;
                roti = ana / 6 ;
                point = roti / 10 ;

                binding.tvDorVori.setText(""+vori);
                binding.tvDorAna.setText(""+ana);
                binding.tvDorRoti.setText(""+roti);
                binding.tvDorPoint.setText(""+point);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        binding.edPrice.setText(""+ MainActivity.PRICE);


        binding.edVori.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        binding.edAna.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        binding.edRoti.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        binding.edPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        binding.edMojuri.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });





        binding.btnReset.setOnClickListener(v->{
            binding.edPrice.setText(""+MainActivity.PRICE);
            binding.edVori.setText("");
            binding.edAna.setText("");
            binding.edRoti.setText("");
            binding.edPoint.setText("");
            binding.edMojuri.setText("");
            binding.btnReset.setVisibility(View.INVISIBLE);

        });
    }

    private void calculate(){
        binding.btnReset.setVisibility(View.VISIBLE);
        voriCount = getIntFromEd(binding.edVori);
        anaCount = getIntFromEd(binding.edAna);
        rotiCount = getIntFromEd(binding.edRoti);
        pointCount = getIntFromEd(binding.edPoint);
        mojuriCount = getIntFromEd(binding.edMojuri);

        binding.tvVori.setText(voriCount + " ভরি");
        binding.tvAna.setText(anaCount + " আনা");
        binding.tvRoti.setText(rotiCount + " রতি");
        binding.tvPoint.setText(pointCount + " পয়েন্ট");

        binding.tvDorVori.setText(vori+ " * "+voriCount);
        binding.tvDorAna.setText(ana+ " * "+anaCount);
        binding.tvDorRoti.setText(roti + " * "+rotiCount);
        binding.tvDorPoint.setText(point + " * "+pointCount);

        voriFinal = vori * voriCount;
        anaFinal = ana * anaCount;
        rotiFinal = roti * rotiCount;
        pointFinal = point * pointCount;
        totalFinal = voriFinal + anaFinal + rotiFinal + pointFinal;
        mojuriFinal = totalFinal * ((float) mojuriCount /100);
        priceTotalFinal = totalFinal + mojuriFinal;

        binding.tvDamVori.setText(""+ voriFinal);
        binding.tvDamAna.setText(""+ anaFinal);
        binding.tvDamRoti.setText(""+ rotiFinal);
        binding.tvDamPoint.setText(""+ pointFinal);
        binding.tvDam.setText(""+ totalFinal);
        binding.tvMojuri.setText(""+ mojuriFinal);
        binding.tvFinalDam.setText(""+ priceTotalFinal);





    }

    public static int getIntFromEd(EditText editText){
        String a = editText.getText().toString();
        if (a.isEmpty()) return 0;
        else return Integer.parseInt(a);

    }





}