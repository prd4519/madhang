package com.example.madhang_ae.Penjual;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.Pembeli.NavigationPembeli;
import com.example.madhang_ae.R;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.makeramen.roundedimageview.RoundedImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class MenuFragment extends Fragment {

    SessionManager sessionManager;
    private BottomSheetBehavior bsMenu;
    private LinearLayout linearLayoutbs;
    RoundedImageView imageItem;
    EditText etNama,etHarga,etDesa;
    String idKategori;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        sessionManager = new SessionManager(getContext());
        linearLayoutbs = v.findViewById(R.id.bottomSheetMenu);
        bsMenu = BottomSheetBehavior.from(linearLayoutbs);
        bsMenu.setState(BottomSheetBehavior.STATE_EXPANDED);

        return v;
    }

}