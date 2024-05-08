package bee.corp.bcorder.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import bee.corp.bcorder.R;
import bee.corp.bcorder.model.RequestCodeConstants;

public class ForegroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification n = createForegroundServiceNotification(this, "ForegroundService", "BCorder");
        startForeground(RequestCodeConstants.START_FOREGROUND_ID, n);
        return super.onStartCommand(intent, flags, startId);
    }
    private Notification createForegroundServiceNotification(Context context, String CHANNEL_ID, String CHANNEL_NAME) {
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = null;
        Intent closeIntent = new Intent(this, NotificationAppClose.class);
        closeIntent.setAction("CloseApp");
        PendingIntent closePendingIntent = PendingIntent.getBroadcast(this, RequestCodeConstants.GET_BROADCAST_REQUEST_CODE, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder.setSmallIcon(R.mipmap.ic_launcher_adaptive_fore);
            builder.setContentTitle("BCorder");
            builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Exit", closePendingIntent);
            return builder.build();
        } else {
            Notification notification;
            builder.setSmallIcon(R.mipmap.ic_launcher_adaptive_fore);
            builder.setContentTitle("BCorder");
            builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Exit", closePendingIntent);
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
            notification = builder.build();
            return notification;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}
