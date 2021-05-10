package com.example.madhang_ae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));

        setContentView(R.layout.activity_edit_profile);

        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.btn_kembaliEdit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button editpassword = (Button) findViewById(R.id.btn_EditPassword);
        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfile.this,EditPassword.class);
                startActivity(i);
            }
        });
    }
}