package bee.corp.bcorder.view;


import android.health.connect.datatypes.Record;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import bee.corp.bcorder.R;
import bee.corp.bcorder.model.RecorderSettings;
import bee.corp.bcorder.utility.VideoAdapter;
import bee.corp.bcorder.viewmodel.VideoLoader;
import bee.corp.bcorder.viewmodel.VideoSearcher;

public class FilesFragment extends Fragment {

    RecyclerView videoTabsView;
    SearchView searchText;

    VideoAdapter videoTabsAdapter;
    VideoLoader videoLoader;
    VideoSearcher videoSearcher;

    LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View mainView = inflater.inflate(R.layout.fragment_files, container, false);
        initializeViews((ViewGroup) mainView);
        return mainView;
    }

    private void initializeViews(ViewGroup v) {
        searchText = v.findViewById(R.id.search_text);
        searchText.clearFocus();
        videoTabsView = v.findViewById(R.id.video_tabs_view);
        videoTabsView.setHasFixedSize(true);
        videoTabsView.addItemDecoration(new ItemDividerDecoration(25));
        videoLoader = new ViewModelProvider(this).get(VideoLoader.class);
        videoSearcher = new ViewModelProvider(this).get(VideoSearcher.class);
        loadVideos(inflater.inflate(R.layout.video_item, null, false));
        videoLoader.getLiveVideoTabs().observe(getViewLifecycleOwner(), videoTabs -> {
            videoTabsAdapter = new VideoAdapter(videoTabs, getContext(), getActivity());
            this.videoTabsView.setAdapter(videoTabsAdapter);
            searchVideos();
        });
    }
    private void searchVideos() {
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {return true;}
            @Override
            public boolean onQueryTextChange(String newText) {
                videoSearcher.search(videoTabsAdapter, newText);
                return true;
            }
        });
        videoSearcher.getMutableLiveFoundTabsData().observe(getViewLifecycleOwner(), videoTabs -> {
            videoTabsAdapter.setFilteredList(videoTabs);
        });
    }
    private void loadVideos(View view) {
        if(RecorderSettings.vOutputDirectory == null) {
            RecorderSettings.vOutputDirectory = getContext().getExternalFilesDir(null).getAbsolutePath() + "/videos/";
        }
        videoLoader.loadVideos(RecorderSettings.vOutputDirectory, view);
    }
}