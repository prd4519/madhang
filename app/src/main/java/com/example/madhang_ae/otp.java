package com.example.madhang_ae;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.madhang_ae.API.BaseApiService;
import com.example.madhang_ae.API.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otp extends AppCompatActivity {
    String sEmail,sPassword,email,Codeotp;
    EditText etOtp;
    Button btnVerifikasi;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    TextView emailOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ColorButton));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        etOtp = findViewById(R.id.et_otp);
        btnVerifikasi = findViewById(R.id.btn_verifikasiOTP);
        emailOtp = findViewById(R.id.txt_email_otp);
        email = getIntent().getStringExtra("emailVerifikasi");
        Codeotp = getIntent().getStringExtra("otpVerifikasi");
        sEmail = "aemadhang@gmail.com";
        sPassword = "mboh4519";
        emailOtp.setText("Masukkan kode OTP yang telah dikirimkan ke alamat email "+email);
        sendVerification();
        btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitVerifikasi();
            }
        });
    }

    private void SubmitVerifikasi() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> verif = mApiService.verifikasi(email,Integer.parseInt(etOtp.getText().toString()));
        verif.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("error").equals("false")) {
                            showAlertDialog();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(otp.this);
//                            builder.setCancelable(false);
//                            builder.setTitle(Html.fromHtml("<font color='#509324'>Verifikasi Success</font>"));
//                            builder.setMessage("Silahkan login kembali");
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            });
//                            builder.show();

                        } else {
                            String error_msg = jsonResult.getString("error_msg");
                            Toast.makeText(otp.this, error_msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlertDialogWrong();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(otp.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showAlertDialog(){
        dialogBuilder = new AlertDialog.Builder(otp.this);
        View layoutView = getLayoutInflater().inflate(R.layout.layout_verification_success, null);
        Button dialogButton = layoutView.findViewById(R.id.btnDialog);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                alertDialog.dismiss();
            }
        });
    }
    private void showAlertDialogWrong(){
        dialogBuilder = new AlertDialog.Builder(otp.this);
        View layoutView = getLayoutInflater().inflate(R.layout.layout_verification_wrong, null);
        Button dialogButtonWrong = layoutView.findViewById(R.id.btnDialogWrong);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        dialogButtonWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }
    public void sendVerification() {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail,sPassword);
            }
        });
        try {
            String to = email;
            Message message =new MimeMessage(session);
            message.setFrom(new InternetAddress(sEmail));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Verifikasi Email");
            message.setText("Masukkan kode OTP : "+Codeotp);

            new SendMail().execute(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    private class SendMail extends AsyncTask<Message,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("Success")){
                Toast.makeText(otp.this, "Kode OTP telah terkirim, mohon cek email anda", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),
                        "Something Went Wrong?", Toast.LENGTH_SHORT).show();
            }
        }
    }

}