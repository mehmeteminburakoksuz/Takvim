package com.example.takvimnothaziran;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.takvimnothaziran.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    // Veritabanı
    public static veritabani veritabanim;
    ListView list_View;

    // Gösterilecek resimler
    int images[] = {R.drawable.note};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list_View = findViewById(R.id.list_view);

        // Veritabanını oluştur
        veritabanim = Room.databaseBuilder(getApplicationContext(), veritabani.class, "notdb")
                .allowMainThreadQueries().build();

        Button btn_notEkle = findViewById(R.id.btnnotEkle);
        Button btn_alarmEkle = findViewById(R.id.btnalarmEkle);

        // Not ekleme butonuna tıklandığında AddNote aktivitesini başlat
        btn_notEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNote.class);
                startActivity(intent);
            }
        });

        // Alarm ekleme butonuna tıklandığında AlarmActivity aktivitesini başlat
        btn_alarmEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        // Notları listeleme
        List<Not> notlar = MainActivity.veritabanim.dao().getNot();
        ArrayList arrayList = new ArrayList();

        ArrayList n = new ArrayList();
        ArrayList s = new ArrayList();
        ArrayList t = new ArrayList();

        String veriler = "";

        for (Not nt : notlar) {
            int id = nt.getId();
            String ikinciNotSaat = nt.getNotSaat();
            String ikinciNotTarih = nt.getNotTarih();
            String ikinciNot = nt.getNot();

            veriler = veriler + ikinciNot + ikinciNotSaat + ikinciNotTarih;
            arrayList.add(veriler);
            veriler = "";

            n.add(ikinciNot);
            s.add(ikinciNotSaat);
            t.add(ikinciNotTarih);
        }

        // Özel adapter oluşturma
        MyAdapter adapter = new MyAdapter(this, n, s, t, images);
        list_View.setAdapter(adapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        // Listeye tıklanınca notu silmek için bir metin gösterme
        list_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder diyalogOlustur = new AlertDialog.Builder(MainActivity.this);

                long veriId = arrayAdapter.getItemId(position);
                String listeId = String.valueOf(veriId);

                diyalogOlustur.setMessage("Notu silmek istediğinize emin misiniz ?").setCancelable(false).setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VeriSil(listeId);
                    }
                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                diyalogOlustur.create().show();
            }
        });
    }

    // Notu veritabanından silme
    private void VeriSil(String listeId) {
        List<Not> notlariSil = MainActivity.veritabanim.dao().getNot();
        ArrayList arrayListSil = new ArrayList();

        int veriler;

        for (Not silListe : notlariSil) {
            int id = silListe.getId();
            veriler = id;
            arrayListSil.add(veriler);
            veriler = 0;
        }

        String sid = listeId;
        int id = Integer.valueOf(sid);
        int eleman = (int) arrayListSil.get(id);

        Not not = new Not();
        not.setId(eleman);

        MainActivity.veritabanim.dao().notSil(not);

        Toast.makeText(getApplicationContext(), "Not silindi", Toast.LENGTH_SHORT).show();

        finish();
        startActivity(getIntent());
    }

    // Özel adapter sınıfı
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList rNot;
        ArrayList rSaat;
        ArrayList rTarih;
        int rImgs[];

        MyAdapter(Context c, ArrayList not, ArrayList saat, ArrayList tarih, int imgs[]) {
            super(c, R.layout.custom_view, R.id.txt_not, not);
            this.context = c;
            this.rNot = not;
            this.rSaat = saat;
            this.rTarih = tarih;
            this.rImgs = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.custom_view, parent, false);
            ImageView images = row.findViewById(R.id.resim);
            TextView myNot = row.findViewById(R.id.txt_not);
            TextView mySaat = row.findViewById(R.id.txt_saat);
            TextView myTarih = row.findViewById(R.id.txt_tarih);

            images.setImageResource(rImgs[0]);
            myNot.setText((CharSequence) rNot.get(position));
            mySaat.setText((CharSequence) rSaat.get(position));
            myTarih.setText((CharSequence) rTarih.get(position));

            return row;
        }
    }
}
