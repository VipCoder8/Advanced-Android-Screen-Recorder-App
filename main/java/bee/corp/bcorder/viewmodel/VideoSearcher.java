package bee.corp.bcorder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import bee.corp.bcorder.utility.VideoAdapter;
import bee.corp.bcorder.view.VideoTab;

public class VideoSearcher extends AndroidViewModel {
    private MutableLiveData<ArrayList<VideoTab>> mutableLiveFoundTabsData;
    private int foundTabsCount;
    public VideoSearcher(@NonNull Application application) {
        super(application);
        mutableLiveFoundTabsData = new MutableLiveData<>();
        foundTabsCount = 0;
    }
    public void search(VideoAdapter va, String query) {
        foundTabsCount = 0;
        ArrayList<VideoTab> resultTabs = new ArrayList<>();
        for(VideoTab tab : va.videoTabs) {
            if(tab.getTitle().toLowerCase().contains(query.toLowerCase())) {
                foundTabsCount++;
                resultTabs.add(tab);
            }
        }
        if(foundTabsCount == 0) {
            resultTabs.clear();
        }
        mutableLiveFoundTabsData.setValue(resultTabs);
        if(query.trim().isEmpty()) {
            va.restoreList();
        }
    }

    public MutableLiveData<ArrayList<VideoTab>> getMutableLiveFoundTabsData() {
        return mutableLiveFoundTabsData;
    }
}
