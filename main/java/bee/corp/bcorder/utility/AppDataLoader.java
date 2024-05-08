package bee.corp.bcorder.utility;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bee.corp.bcorder.model.RecorderSettings;

public class AppDataLoader {
    public AppDataLoader(Context c) {
        try {
            loadVideoSettings(c);
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }
    private void loadVideoSettings(Context c) throws FileNotFoundException {
        File fileParent = new File(c.getExternalFilesDir(null).getAbsolutePath() + "/data/");
        if(!fileParent.exists()) {
            fileParent.mkdir();
        }
        File[] filesSettings = fileParent.listFiles();
        for(File file : filesSettings) {
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNextLine()) {
                if(file.getName().equals("OutputDirectory.inf")) {
                    RecorderSettings.vOutputDirectory = fileScanner.next();
                } else if (file.getName().equals("OutputFormat.inf")) {
                    RecorderSettings.vOutputFormat = Integer.parseInt(fileScanner.next());
                } else if (file.getName().equals("VideoEncoder.inf")) {
                    RecorderSettings.vEncoder = Integer.parseInt(fileScanner.next());
                } else if (file.getName().equals("VideoFps.inf")) {
                    RecorderSettings.vFPS = Integer.parseInt(fileScanner.next());
                } else if (file.getName().equals("VideoBitrate.inf")) {
                    RecorderSettings.vEncodingBitrate = Integer.parseInt(fileScanner.next());
                }
            }
        }
    }
}
