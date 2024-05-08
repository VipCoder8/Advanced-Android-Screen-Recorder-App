package bee.corp.bcorder.view;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

import bee.corp.bcorder.model.RequestCodeConstants;

public class OpenFileDialog {
    public static void Show(Activity a, String i) {
        Intent intent = new Intent(i);
        a.startActivityForResult(intent, RequestCodeConstants.OPEN_FILE_DIALOG_REQUEST_CODE);
    }
    public static void Show(ActivityResultLauncher<Intent> a, String i) {
        Intent intent = new Intent(i);
        a.launch(intent);
    }
}
