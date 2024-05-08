package bee.corp.bcorder.model;
import android.media.MediaRecorder;
public class RecorderSettings {
    public static int vEncoder = MediaRecorder.VideoEncoder.H264;
    public static int vEncodingBitrate = 512*1000;
    public static int vOutputFormat = MediaRecorder.OutputFormat.MPEG_4;
    public static String vOutputDirectory = null;
    public static int vFPS = 30;
}