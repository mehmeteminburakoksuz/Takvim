package com.example.takvimnothaziran;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Bildirime tıklanınca açılacak olan aktiviteyi belirlemek için bir Intent oluşturuyoruz
        Intent nextActivity = new Intent(context, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nextActivity, 0);

        // Bildirim özelliklerini belirleyen bir NotificationCompat.Builder oluşturuyoruz
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Dene")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Hatırlatıcı")
                .setContentText("Alarm Mesajı")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        // Bildirimi göstermek için bir NotificationManagerCompat kullanıyoruz
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
