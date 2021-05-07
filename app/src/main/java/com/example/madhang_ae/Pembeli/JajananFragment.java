package com.example.madhang_ae.Pembeli;

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

import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.Penjual.NavigationPenjual;
import com.example.madhang_ae.R;
import com.example.madhang_ae.SessionManager;

import de.hdodenhof.circleimageview.CircleImageView;


public class JajananFragment extends Fragment {
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    SessionManager sessionManager;
    CircleImageView fabPop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_jajanan, container, false);
        fabPop = v.findViewById(R.id.popup3);
        fabPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp3();
            }
        });

        return v;
    }

    private void showPopUp3(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        View layoutView = getLayoutInflater().inflate(R.layout.popup_pembeli, null);
        Button edtProfile = layoutView.findViewById(R.id.edtProfile);
        Button dashboardpnjl = layoutView.findViewById(R.id.dshbrPenjual);
        Button logout = layoutView.findViewById(R.id.Logout);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        edtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfile.class);
                startActivity(intent);
                getActivity().finish();
                alertDialog.dismiss();
            }
        });
        dashboardpnjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NavigationPenjual.class);
                startActivity(intent);
                getActivity().finish();
                alertDialog.dismiss();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });
    }
}