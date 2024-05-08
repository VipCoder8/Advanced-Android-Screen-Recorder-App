package bee.corp.bcorder.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import bee.corp.bcorder.R;
import bee.corp.bcorder.model.RequestCodeConstants;
import bee.corp.bcorder.utility.PhoneRecorder;
import bee.corp.bcorder.viewmodel.PhoneRecorderController;

public class VideoRecordingController {
    private Context context;
    private final View mainView;
    private final WindowManager.LayoutParams wParams;
    private final WindowManager windowManager;
    private final LayoutInflater inflater;
    private final PhoneRecorderController phoneRecorderController;
    private Intent activityResultCaptureScreenIntent;
    private int activityResultCaptureScreenCode;

    public VideoRecordingController(Context c, PhoneRecorder pr, PhoneRecorderController pcler) {
        this.context = c;
        this.phoneRecorderController = pcler;
        if(android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            wParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            wParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }
        wParams.gravity = Gravity.TOP | Gravity.START;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(R.layout.video_controller, null);
        addButtonListeners(pr);
        windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
    }
    public void initActivityCaptureScreenResults(Intent i, int resultCode) {
        this.activityResultCaptureScreenIntent = i;
        this.activityResultCaptureScreenCode = resultCode;
    }
    private void addButtonListeners(PhoneRecorder pr) {
        mainView.findViewById(R.id.camera_controller_opener_button).setOnTouchListener(new View.OnTouchListener() {
            float initialX, initialY;
            float initialTouchX, initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if(mainView.findViewById(R.id.controller_layout).getVisibility()==View.INVISIBLE) {
                            mainView.findViewById(R.id.controller_layout).setVisibility(View.VISIBLE);
                        } else {
                            mainView.findViewById(R.id.controller_layout).setVisibility(View.INVISIBLE);
                        }
                        return true;
                    case MotionEvent.ACTION_DOWN:
                        initialX = wParams.x;
                        initialY = wParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        wParams.x = (int) (initialX + (int) (event.getRawX() - initialTouchX));
                        wParams.y = (int) (initialY + (int) (event.getRawY() - initialTouchY));
                        windowManager.updateViewLayout(mainView, wParams);
                        return true;
                }
                return true;
            }
        });
        mainView.findViewById(R.id.pause).setOnClickListener(v -> {
            if(!pr.isPaused()) {
                pr.pauseRecording();
                mainView.findViewById(R.id.resume).setEnabled(true);
            } else {
                pr.resumeRecording();
            }
        });
        mainView.findViewById(R.id.resume).setOnClickListener(v -> {
            mainView.findViewById(R.id.stop).setEnabled(true);
            pr.resetRecorder();
            if(pr.isRecordingRightNow()) {
                pr.resumeRecording();
            } else {
                phoneRecorderController.handleActivityResult(RequestCodeConstants.DISPLAY_INIT_REQUEST_CODE, this.activityResultCaptureScreenCode, this.activityResultCaptureScreenIntent);
            }
        });
        mainView.findViewById(R.id.stop).setOnClickListener(v -> {
            mainView.findViewById(R.id.stop).setEnabled(false);
            pr.stopRecording();
        });
    }
    public void open() {
        try {
            if(mainView.getWindowToken()==null) {
                if(mainView.getParent()==null) {
                    windowManager.addView(mainView, wParams);
                }
            }
        } catch (Exception e) {
            Log.d("Error1",e.toString());
        }
    }
}
