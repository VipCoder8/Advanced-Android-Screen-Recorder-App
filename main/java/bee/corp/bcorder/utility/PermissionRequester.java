package bee.corp.bcorder.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import bee.corp.bcorder.model.RequestCodeConstants;

public class PermissionRequester {
    public static void checkAndRequestPermissions(Activity a) {
        List<String> permissionsNeeded = new ArrayList<>();

        final String[] permissions = new String[]{
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.FOREGROUND_SERVICE,
        };

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(a, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        if (!Settings.canDrawOverlays(a)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + a.getPackageName()));
            a.startActivityForResult(intent, RequestCodeConstants.ALLOW_OVER_OTHER_APPS_REQUEST_CODE);
        }

        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(a, permissionsNeeded.toArray(new String[0]), RequestCodeConstants.PERMISSIONS_REQUEST_CODE);
        }
    }
}
