package com.example.takvimnothaziran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Locale;

public class notEkle extends AppCompatActivity {


    CalendarView takvim;
    EditText edt_aktivite;
    Button btn_saat,btn_kaydet;

    String secilenSaat,secilenTarih;


    int saat,dakika;
    int y,g,a,secilentarihid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_ekle);

        takvim = findViewById(R.id.takvim);
        edt_aktivite = findViewById(R.id.edt_aktivite);
        btn_kaydet= findViewById(R.id.btn_kaydet);
        btn_saat = findViewById(R.id.btn_saat);

        takvim.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int yil, int ay, int gun) {

                y=yil;
                g=gun;
                a=ay+1;

                String birlesik =String.valueOf(g)+ String.valueOf(a)+ String.valueOf(y);
                secilentarihid = Integer.valueOf(birlesik);
                secilenTarih = g+"/"+a+"/"+y;




            }
        });
    }

    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                saat = selectedHour;
                dakika = selectedMinute;
                btn_saat.setText(String.format(Locale.getDefault(),"%02d:%02d",saat,dakika));
                secilenSaat=btn_saat.getText().toString();

            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style,onTimeSetListener,saat,dakika,true);
        timePickerDialog.setTitle("Saat Seçiniz");
        timePickerDialog.show();
    }

    public void Kaydet(View view){

        int notId = Integer.valueOf(secilentarihid);
        String notSaat = secilenSaat;
        String notTarih = secilenTarih;
        String aktivite = edt_aktivite.getText().toString();

        if(TextUtils.isEmpty(aktivite)){
            Toast.makeText(notEkle.this,"Lütfen Bir Plan Giriniz" ,Toast.LENGTH_SHORT).show();

        }else {
            Not not = new Not();
            not.setId(notId);
            not.setNotTarih(notTarih);
            not.setNotSaat(notSaat);

            not.setNot(aktivite);


            MainActivity.veritabanim.dao().notEkle(not);
            Toast.makeText(notEkle.this,"Not Kaydedildi" ,Toast.LENGTH_SHORT).show();
            edt_aktivite.setText("");
            btn_saat.setText("");


            Intent intent = new Intent(notEkle.this,MainActivity.class);
            startActivity(intent);


        }


    }

}