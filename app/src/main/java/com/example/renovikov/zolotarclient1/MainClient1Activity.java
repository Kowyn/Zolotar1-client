package com.example.renovikov.zolotarclient1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.zip.Inflater;

public class MainClient1Activity extends AppCompatActivity implements View.OnClickListener{

    int requestCode;
    TextView tv_user_settings;

    String textUserSettings="";
    StringBuilder stringBuilder = new StringBuilder(textUserSettings);

    SharedPreferences user_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client1);

        tv_user_settings = (TextView) findViewById(R.id.tv_user_settings);
        setTextUserGreetings();
    }

    @Override
    protected void onRestart() {
        setTextUserGreetings();

        super.onRestart();
    }

    //создаем главное меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return super.onCreateOptionsMenu(menu);
    }

//обработка нажатия на главное меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mi_profile:
            //UserSettings();  //вызов окна настроек пользователя
                Intent intent = new Intent(this, MenuProfileActivity.class);
                startActivityForResult(intent,requestCode);
            return true;
            case R.id.mi_update:
                //UpdateApp();   //обновление приложения
            return true;
            case R.id.mi_options:
                //options();   //вызов справки по работе приложения
            return true;
            default:
            return super.onOptionsItemSelected(item); }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case RESULT_OK:
                user_pref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = user_pref.edit();
                ed.putString("name",data.getStringExtra("name"));
                ed.putString("district",data.getStringExtra("district"));
                ed.putString("adress",data.getStringExtra("adress"));
                ed.putString("phone",data.getStringExtra("phone"));
                ed.putString("gps_lat",data.getStringExtra("latitude"));
                ed.putString("gps_long",data.getStringExtra("longitude"));
                ed.apply();
                break;
            case RESULT_CANCELED:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

    }

    public void setTextUserGreetings(){
        user_pref = getPreferences(MODE_PRIVATE);
        tv_user_settings.setText("");
        stringBuilder.append("Здравствуйте, "+user_pref.getString("name","Незнакомец")+"!");
        stringBuilder.append("/n"+"Район: "+user_pref.getString("district","Незнакомец")+"!");
        stringBuilder.append("/n"+"Адрес: "+user_pref.getString("adress","Незнакомец")+"!");
        stringBuilder.append("/n"+"Телефон: "+user_pref.getString("phone","Незнакомец")+"!");
        stringBuilder.append("/n"+"GPS-lat: "+user_pref.getString("gps_lat","Незнакомец")+"!");
        stringBuilder.append("/n"+"GPS-long: "+user_pref.getString("gps_long","Незнакомец")+"!");

        tv_user_settings.setText(stringBuilder.toString().split("/n").toString());
    }

}



