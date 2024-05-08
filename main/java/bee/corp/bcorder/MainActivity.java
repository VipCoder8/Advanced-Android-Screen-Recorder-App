package bee.corp.bcorder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import bee.corp.bcorder.utility.AppDataLoader;
import bee.corp.bcorder.utility.AppDataSaver;
import bee.corp.bcorder.utility.PermissionRequester;
import bee.corp.bcorder.view.FilesFragment;
import bee.corp.bcorder.view.HomeFragment;
import bee.corp.bcorder.view.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    FilesFragment filesFragment;
    SettingsFragment settingsFragment;
    AppDataLoader appDataLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDataLoader = new AppDataLoader(getApplicationContext());
        PermissionRequester.checkAndRequestPermissions(this);
        setupViews();
    }

    private void setupViews() {
        homeFragment = new HomeFragment();
        filesFragment = new FilesFragment();
        settingsFragment = new SettingsFragment(new AppDataSaver(getApplicationContext()));
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
                if(item.getItemId()==R.id.home_button) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                } else if(item.getItemId()==R.id.files_button) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,filesFragment).commit();
                } else if(item.getItemId()==R.id.settings_button) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                }
            return true;
        });
    }
}