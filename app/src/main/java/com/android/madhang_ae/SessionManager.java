package com.android.madhang_ae;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.madhang_ae.Pembeli.NavigationPembeli;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name =  "crudpref";
    private static final String is_login = "isLogin";
    public static final  String kunci_id = "keyid";
    public static final  String kunci_pass = "keypass";
    public static final  String kunci_idKec = "keyidKec";
    public static final  String kunci_otp = "keyotp";
    public SessionManager(Context context) {
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
        this.context = context;
    }
    public void createSession(String id,String password,String idKec,String otp){
        editor.putBoolean(is_login,true);
        editor.putString(kunci_id,id);
        editor.putString(kunci_pass,password);
        editor.putString(kunci_idKec,idKec);

        editor.putString(kunci_otp,otp);
        editor.commit();
    }
    public void checkLogin(){
        if(!this.is_login()){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, NavigationPembeli.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(kunci_id, pref.getString(kunci_id, null));
        user.put(kunci_pass, pref.getString(kunci_pass, null));
        user.put(kunci_idKec, pref.getString(kunci_idKec, null));
        user.put(kunci_otp, pref.getString(kunci_otp, null));

        return user;
    }
}
