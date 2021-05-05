package com.example.madhang_ae.Pembeli;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.madhang_ae.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MakananFragment extends Fragment {
    BottomSheetBehavior bottomSheetBehaviorMakanan;
    LinearLayout linearLayoutBsMakanan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_makanan, container, false);
        linearLayoutBsMakanan = v.findViewById(R.id.bottomSheetMakanan);
        bottomSheetBehaviorMakanan = BottomSheetBehavior.from(linearLayoutBsMakanan);
        bottomSheetBehaviorMakanan.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        return v;
    }
}