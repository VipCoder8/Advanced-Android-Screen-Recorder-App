package bee.corp.bcorder.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import bee.corp.bcorder.R;
import bee.corp.bcorder.utility.VideoUtils;
import bee.corp.bcorder.view.VideoTab;

public class VideoLoader extends AndroidViewModel {

    private MutableLiveData<ArrayList<VideoTab>> mutableLiveVideoTabs;
    private ArrayList<VideoTab> videoTabs;
    private Application app;

    public VideoLoader(Application app) {
        super(app);
        this.app = app;
        mutableLiveVideoTabs = new MutableLiveData<>();
        videoTabs = new ArrayList<>();
    }

    public void loadVideos(String pathToFolder, View view) {
        File folder = new File(pathToFolder);
        for(File videoFile : Objects.requireNonNull(folder.listFiles())) {
            VideoTab videoTab = new VideoTab(getApplication().getApplicationContext(), R.layout.video_item);
            videoTab.itemView.setTag(videoFile.getAbsolutePath());
            videoTab.setVideoPath(videoFile.getPath());
            videoTab.setTitle(videoFile.getName());
            videoTab.setVideoPreview(VideoUtils.getVideoPreviewImage(view.getContext(), videoFile.getAbsolutePath(),
                    R.drawable.video_thumbnail_placeholder));
            videoTab.setDuration(VideoUtils.getVideoDuration(videoFile.getAbsolutePath(), view.getContext()));
            videoTabs.add(videoTab);
        }
        mutableLiveVideoTabs.setValue(videoTabs);
    }

    public MutableLiveData<ArrayList<VideoTab>> getLiveVideoTabs() {return mutableLiveVideoTabs;}
}