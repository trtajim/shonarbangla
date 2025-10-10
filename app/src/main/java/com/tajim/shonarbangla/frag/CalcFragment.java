package com.tajim.shonarbangla.frag;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tajim.shonarbangla.databinding.FragmentCalcBinding;

public class CalcFragment extends Fragment {
    FragmentCalcBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalcBinding.inflate(inflater, container, false);




        return binding.getRoot();
    }



}