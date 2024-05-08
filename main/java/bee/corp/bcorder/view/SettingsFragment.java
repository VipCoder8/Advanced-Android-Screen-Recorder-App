package bee.corp.bcorder.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import bee.corp.bcorder.R;
import bee.corp.bcorder.model.RecorderSettings;
import bee.corp.bcorder.model.SettingsPreferencesController;
import bee.corp.bcorder.utility.AppDataSaver;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SettingsPreferencesController settingsPreferencesController;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Preference outputDirectoryPreference;
    private AppDataSaver dataSaver;
    public SettingsFragment(AppDataSaver a) {
        dataSaver = a;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityResultLauncher();
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey);
        settingsPreferencesController = new SettingsPreferencesController(getContext());
        outputDirectoryPreference = findPreference("output_directory");
        outputDirectoryPreference.setOnPreferenceClickListener(onDirectoryPreferenceChangeListener);
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);
        initPreferenceSummary();
    }

    private void initPreferenceSummary() {
        if(RecorderSettings.vOutputDirectory != null) {
            outputDirectoryPreference.setSummary("Current: " + RecorderSettings.vOutputDirectory);
        }
    }

    private void initActivityResultLauncher() {
        this.activityResultLauncher = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            settingsPreferencesController.changeVideoOutputDirectory(o.getData().getData(), getContext());
            outputDirectoryPreference.setSummary("Current: " + RecorderSettings.vOutputDirectory);
            dataSaver.saveVideoDirectory(RecorderSettings.vOutputDirectory);
        });
    }

    Preference.OnPreferenceClickListener onDirectoryPreferenceChangeListener = preference -> {
        if(preference.getKey().equals("output_directory")) {
            OpenFileDialog.Show(SettingsFragment.this.activityResultLauncher, Intent.ACTION_OPEN_DOCUMENT_TREE);
        }
        return true;
    };

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        int result = Integer.parseInt(sharedPreferences.getString(key,"0"));
        assert key != null;
        switch (key) {
            case "output_formats":
                settingsPreferencesController.changeVideoOutputFormat(result);
                dataSaver.saveVideoFormat(sharedPreferences.getString(key,"0"));
                break;
            case "video_encoders":
                settingsPreferencesController.changeVideoEncoder(result);
                dataSaver.saveVideoEncoder(sharedPreferences.getString(key,"0"));
                break;
            case "video_fps":
                settingsPreferencesController.changeVideoFPS(result);
                dataSaver.saveVideoFps(sharedPreferences.getString(key,"0"));
                break;
            case "video_bitrate":
                settingsPreferencesController.changeVideoEncodingBitrate(result);
                dataSaver.saveVideoBitrate(sharedPreferences.getString(key,"0"));
                break;
        }
    }
}