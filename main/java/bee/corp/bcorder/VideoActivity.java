package bee.corp.bcorder;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController videoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String pathToVideo = getIntent().getStringExtra("video_path");
        initViews();
        playVideo(pathToVideo);
    }

    private void playVideo(String path) {
        videoView.setVideoPath(path);
        videoView.start();
    }

    private void initViews() {
        videoView = findViewById(R.id.video_view);
        videoController = new MediaController(this);
        videoController.setMediaPlayer(videoView);
        videoController.setAnchorView(videoView);
        videoView.setMediaController(videoController);
    }

}
