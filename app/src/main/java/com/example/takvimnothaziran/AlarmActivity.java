package com.example.takvimnothaziran;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    private TextView selectedTimeTextView;
    private Button selectTimeBtn, setAlarmBtn, cancelAlarmBtn;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Gerekli bileşenlerin tanımlanması
        selectedTimeTextView = findViewById(R.id.selectedTime);
        selectTimeBtn = findViewById(R.id.selectTimeBtn);
        setAlarmBtn = findViewById(R.id.setAlarmBtn);
        cancelAlarmBtn = findViewById(R.id.cancelAlarmBtn);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Zaman seçici düğmesine tıklama işlemi
        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        // Alarm ayarla düğmesine tıklama işlemi
        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        // Alarm iptal et düğmesine tıklama işlemi
        cancelAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    // Zaman seçiciyi gösteren metod
    private void showTimePicker() {
        final Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                AlarmActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        selectedTimeTextView.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }

    // Alarmı ayarlayan metod
    private void setAlarm() {
        if (calendar != null) {
            long alarmTime = calendar.getTimeInMillis();

            Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);

            Toast.makeText(AlarmActivity.this, "Alarm kuruldu!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AlarmActivity.this, "Lütfen önce bir zaman seçin.", Toast.LENGTH_SHORT).show();
        }
    }

    // Alarmı iptal eden metod
    private void cancelAlarm() {
        if (alarmManager != null && pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(AlarmActivity.this, "Alarm iptal edildi!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AlarmActivity.this, "Hiçbir alarm ayarlı değil.", Toast.LENGTH_SHORT).show();
        }
    }
}
