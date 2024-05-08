package bee.corp.bcorder.utility;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneRecorder {
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private VirtualDisplay virtualDisplay;
    private MediaRecorder videoRecorder;
    private int screenWidth;
    private int screenHeight;
    private boolean isRecording;
    private boolean isPaused;
    private boolean isStopped;
    private boolean isPrepared;

    public PhoneRecorder(int screenWidth, int screenHeight) {
        setupScreenSize(screenWidth, screenHeight);
        initRecorder();
    }

    private void initRecorder() {
        videoRecorder = new MediaRecorder();
    }

    private void setupScreenSize(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void initMediaProjectionManager(MediaProjectionManager mpm) {
        mediaProjectionManager = mpm;
    }
    public MediaProjectionManager getMediaProjectionManager() {
        return mediaProjectionManager;
    }

    //Should be called before prepareVideoRecorder() method
    public void initDisplayNMediaProjection(int resultCode, Intent data, int screenDensity) {
        // Get MediaProjection
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);

        // Start screen capture
        virtualDisplay = mediaProjection.createVirtualDisplay("ScreenRecorder",
                screenWidth, screenHeight, screenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                videoRecorder.getSurface(), null, null);
    }
    public void prepareVideoRecorder(Context c, String outputDirectory, int fps, int videoEncodingBitrate, int videoSource, int outputFormat, int videoEncoder) {
        videoRecorder.setVideoSource(videoSource);
        videoRecorder.setOutputFormat(outputFormat);
        videoRecorder.setVideoEncoder(videoEncoder);
        videoRecorder.setVideoEncodingBitRate(videoEncodingBitrate);
        videoRecorder.setVideoFrameRate(fps);
        videoRecorder.setVideoSize(this.screenWidth,this.screenHeight);
        if(outputDirectory == null) {
            videoRecorder.setOutputFile(getOutputVideoFile(c));
        } else {
            videoRecorder.setOutputFile(outputDirectory + getVideoFileName());
        }
        try {
            videoRecorder.prepare();
            isPrepared = true;
        } catch (IOException e) {
            isPrepared = false;
            e.printStackTrace();
        }
    }
    private String getVideoFileName() {
        return File.separator + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";
    }
    private String getOutputVideoFile(Context c) {
        File mediaStorageDir = new File(c.getExternalFilesDir(null).getAbsolutePath() + "/videos/");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4";
    }
    public void resetRecorder() {
        if(videoRecorder != null) {
            if(isPrepared) {
                videoRecorder.reset();
            }
        }
    }
    public void resumeRecording() {videoRecorder.resume();}
    public void startRecording() {isRecording = true; videoRecorder.start();}
    public boolean isRecordingRightNow() {return isRecording;}
    public boolean isPaused() {return isPaused;}
    public boolean isStopped() {return isStopped;}
    public void stopRecording() {isRecording = false; videoRecorder.stop();}
    public void pauseRecording() {videoRecorder.pause();}
}
