package bee.corp.bcorder.utility;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AppDataSaver {
    private File fileVideoSettingsParent;
    private File fileVideosParent;
    public AppDataSaver(Context c) {
        fileVideoSettingsParent = new File(c.getExternalFilesDir(null).getAbsolutePath() + "/data/");
        fileVideosParent = new File(c.getExternalFilesDir(null).getAbsolutePath() + "/videos/");
        if(!fileVideoSettingsParent.exists()) {fileVideoSettingsParent.mkdir();}
        if(!fileVideosParent.exists()) {fileVideosParent.mkdir();}
    }
    public void saveVideoDirectory(String output) {
        try {
            FileOutputStream fos = new FileOutputStream(fileVideoSettingsParent.getAbsolutePath() + "/OutputDirectory.inf");
            fos.write(output.getBytes());
            fos.flush();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    public void saveVideoFormat(String output) {
        try {
            FileOutputStream fos = new FileOutputStream(fileVideoSettingsParent.getAbsolutePath() + "/OutputFormat.inf");
            fos.write(output.getBytes());
            fos.flush();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    public void saveVideoEncoder(String output) {
        try {
            FileOutputStream fos = new FileOutputStream(fileVideoSettingsParent.getAbsolutePath() + "/VideoEncoder.inf");
            fos.write(output.getBytes());
            fos.flush();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    public void saveVideoFps(String output) {
        try {
            FileOutputStream fos = new FileOutputStream(fileVideoSettingsParent.getAbsolutePath() + "/VideoFps.inf");
            fos.write(output.getBytes());
            fos.flush();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    public void saveVideoBitrate(String output) {
        try {
            FileOutputStream fos = new FileOutputStream(fileVideoSettingsParent.getAbsolutePath() + "/VideoBitrate.inf");
            fos.write(output.getBytes());
            fos.flush();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}
