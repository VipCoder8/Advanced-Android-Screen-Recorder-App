package bee.corp.bcorder.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import bee.corp.bcorder.model.RecorderSettings;
import bee.corp.bcorder.model.RequestCodeConstants;
import bee.corp.bcorder.utility.ForegroundService;
import bee.corp.bcorder.utility.PhoneRecorder;

public class PhoneRecorderController extends AndroidViewModel {

    private final MutableLiveData<PhoneRecorder> phoneRecorderMutableLiveData;

    private final Application app;
    private PhoneRecorder phoneRecorder;
    private Size screenSize;

    public PhoneRecorderController(@NonNull Application application) {
        super(application);
        this.app = application;
        this.phoneRecorderMutableLiveData = new MutableLiveData<>();
    }

    public void initScreenSize(int width, int height) {
        screenSize = new Size(width, height);
    }
    public void setupPhoneRecorder() {
        if(android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.N && android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            if(app.checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE)== PackageManager.PERMISSION_GRANTED) {
                Intent serviceIntent = new Intent(app, ForegroundService.class);
                app.startService(serviceIntent);
                phoneRecorder = new PhoneRecorder(screenSize.getWidth(),screenSize.getHeight());
                phoneRecorder.initMediaProjectionManager((MediaProjectionManager)app.getApplicationContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE));
            }
        } else {
            Intent serviceIntent = new Intent(app, ForegroundService.class);
            app.startService(serviceIntent);
            phoneRecorder = new PhoneRecorder(screenSize.getWidth(),screenSize.getHeight());
            phoneRecorder.initMediaProjectionManager((MediaProjectionManager)app.getApplicationContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE));
        }
        phoneRecorderMutableLiveData.setValue(phoneRecorder);
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodeConstants.DISPLAY_INIT_REQUEST_CODE && resultCode==Activity.RESULT_OK) {
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.N
                    && android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                if (app.checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED) {
                    phoneRecorder.prepareVideoRecorder(app.getApplicationContext(), RecorderSettings.vOutputDirectory, RecorderSettings.vFPS, RecorderSettings.vEncodingBitrate, MediaRecorder.VideoSource.SURFACE, RecorderSettings.vOutputFormat, RecorderSettings.vEncoder);
                    phoneRecorder.initDisplayNMediaProjection(resultCode, data, (int) app.getResources().getDisplayMetrics().density);
                    phoneRecorder.startRecording();
                }
            } else {
                phoneRecorder.prepareVideoRecorder(app.getApplicationContext(), RecorderSettings.vOutputDirectory, RecorderSettings.vFPS, RecorderSettings.vEncodingBitrate, MediaRecorder.VideoSource.SURFACE, RecorderSettings.vOutputFormat, RecorderSettings.vEncoder);
                phoneRecorder.initDisplayNMediaProjection(resultCode, data, (int) app.getResources().getDisplayMetrics().density);
                phoneRecorder.startRecording();

            }
        }
    }

    public MutableLiveData<PhoneRecorder> getPhoneRecorderMutableLiveData() {
        return phoneRecorderMutableLiveData;
    }

}