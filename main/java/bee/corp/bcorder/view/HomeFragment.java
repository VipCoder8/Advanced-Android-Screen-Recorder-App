package bee.corp.bcorder.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import bee.corp.bcorder.R;
import bee.corp.bcorder.utility.PhoneRecorder;
import bee.corp.bcorder.viewmodel.PhoneRecorderController;

public class HomeFragment extends Fragment {
    ImageButton recordVideoButton;
    ActivityResultLauncher<Intent> onActivityResultLauncher;
    VideoRecordingController videoController;
    PhoneRecorderController phoneRecorderController;
    private boolean initializedViewModels = false;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(!initializedViewModels) {
            setupViewModels();
        }
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActivityResultLauncher();
        setupViews(view);
    }

    private void setupViewModels() {
        phoneRecorderController = new ViewModelProvider(this).get(PhoneRecorderController.class);
        phoneRecorderController.initScreenSize(getActivity().getWindowManager().getDefaultDisplay().getWidth(), getActivity().getWindowManager().getDefaultDisplay().getHeight());
        phoneRecorderController.setupPhoneRecorder();
        phoneRecorderController.getPhoneRecorderMutableLiveData().observe(getViewLifecycleOwner(), this::setupVideoController);
        initializedViewModels = true;
    }

    private void initActivityResultLauncher() {
        this.onActivityResultLauncher = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if(o.getResultCode()== Activity.RESULT_OK) {
                videoController.initActivityCaptureScreenResults(o.getData(), o.getResultCode());
                videoController.open();
            }
        });
    }

    private void setupVideoController(PhoneRecorder pr) {
        videoController = new VideoRecordingController(getContext(), pr, phoneRecorderController);
    }

    private void setupViews(View view) {
        recordVideoButton = view.findViewById(R.id.record_screen_button);
        recordVideoButton.setOnClickListener(v -> {
            this.onActivityResultLauncher.launch(phoneRecorderController.getPhoneRecorderMutableLiveData().getValue().getMediaProjectionManager().createScreenCaptureIntent());
        });
    }
}