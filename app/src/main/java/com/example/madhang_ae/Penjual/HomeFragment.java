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
import android.widget.LinearLayout;

import com.example.madhang_ae.EditProfile;
import com.example.madhang_ae.Pembeli.NavigationPembeli;
import com.example.madhang_ae.R;
import com.example.madhang_ae.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    SessionManager sessionManager;
    private BottomSheetBehavior bsHome;
    private LinearLayout linearLayoutbs;
//    CircleImageView fabPop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        fabPop = v.findViewById(R.id.popupp);
        linearLayoutbs = v.findViewById(R.id.bottomSheetPenjual);
        bsHome = BottomSheetBehavior.from(linearLayoutbs);
        bsHome.setState(BottomSheetBehavior.STATE_EXPANDED);
        sessionManager = new SessionManager(getContext());

//        fabPop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopUpp();
//            }
//        });
        return v;
    }

    private void showPopUpp(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        View layoutView = getLayoutInflater().inflate(R.layout.popup_penjual, null);
        Button edtProfile = layoutView.findViewById(R.id.edtProfileJual);
        Button dashboardpembeli = layoutView.findViewById(R.id.dshbrPembeli);
        Button logout = layoutView.findViewById(R.id.LogoutJual);
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

                alertDialog.dismiss();
            }
        });
        dashboardpembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NavigationPembeli.class);
                startActivity(intent);
                getActivity().finish();
                alertDialog.dismiss();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                sessionManager.logout();

            }
        });
    }
}