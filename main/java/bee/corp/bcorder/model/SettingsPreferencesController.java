package bee.corp.bcorder.model;
import android.content.Context;
import android.net.Uri;
import androidx.documentfile.provider.DocumentFile;
import bee.corp.bcorder.utility.UriParser;
public class SettingsPreferencesController {
    public SettingsPreferencesController(Context c) {
        if(RecorderSettings.vOutputDirectory == null) {
            RecorderSettings.vOutputDirectory = c.getExternalFilesDir(null).getAbsolutePath() + "/videos/";
        }
    }
    public void changeVideoEncoder(int ve) {RecorderSettings.vEncoder=ve;}
    public void changeVideoEncodingBitrate(int bt) {RecorderSettings.vEncodingBitrate=bt;}
    public void changeVideoOutputFormat(int ft) {
        RecorderSettings.vOutputFormat=ft;
    }
    public void changeVideoFPS(int fps) {RecorderSettings.vFPS=fps;}
    public void changeVideoOutputDirectory(Uri uri, Context c) {
        DocumentFile df = DocumentFile.fromTreeUri(c, uri);
        RecorderSettings.vOutputDirectory = UriParser.getRealPathFromURI(c, df.getUri());
    }
}