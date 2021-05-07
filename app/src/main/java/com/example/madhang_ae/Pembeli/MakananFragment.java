package com.example.madhang_ae.Pembeli;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.madhang_ae.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MakananFragment extends Fragment  {
    private BottomSheetBehavior bsMakanan;
    private LinearLayout linearLayoutbs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_makanan, container, false);
        linearLayoutbs = v.findViewById(R.id.bottomSheetMakanan);
        bsMakanan = BottomSheetBehavior.from(linearLayoutbs);
        bsMakanan.setState(BottomSheetBehavior.STATE_EXPANDED);

        return v;
    }

}