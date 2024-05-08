package bee.corp.bcorder.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationAppClose extends BroadcastReceiver {
    public static String CLOSE_APP = "CloseApp";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(CLOSE_APP)) {
            System.exit(0);
        }
    }
}
