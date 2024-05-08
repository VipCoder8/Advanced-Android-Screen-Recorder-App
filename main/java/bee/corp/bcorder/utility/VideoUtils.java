package bee.corp.bcorder.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class VideoUtils {
    public static Bitmap getVideoPreviewImage(Context context, String videoPath, int videoThumbnailPlaceholder) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(context, Uri.parse(videoPath));
            return retriever.getFrameAtTime();
        } catch (Exception e) {e.printStackTrace();return BitmapFactory.decodeResource(context.getResources(), videoThumbnailPlaceholder);
        } finally {
            try {
                retriever.release();
            } catch (IOException e) {throw new RuntimeException(e);}
        }
    }
    @SuppressLint("Range")
    public static String getVideoDuration(String videoPath, Context c) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(videoPath);
            long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            return TimeFormatter.FormatMilliseconds(duration);
        } catch (Exception e) {
            // Log the exception
            Log.e("VideoUtils", "Error retrieving video duration", e);
            // Return default value
            return "00:00:00";
        } finally {
            try {
                retriever.release();
            } catch (IOException e) {
                // Log the exception
                Log.e("VideoUtils", "Error releasing MediaMetadataRetriever", e);
            }
        }
    }
}
