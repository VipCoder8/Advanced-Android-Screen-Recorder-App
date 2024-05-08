package bee.corp.bcorder.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

public class DialogCreator {
    private static EditText currentEditText;
    public static AlertDialog CreateAssuranceDialog(Context c, String title,
                                             String message, String positiveButtonText,
                                             String negativeButtonText, DialogInterface.OnClickListener positiveButtonClickListener,
                                             DialogInterface.OnClickListener negativeButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveButtonText, positiveButtonClickListener);
        builder.setNegativeButton(negativeButtonText, negativeButtonClickListener);
        return builder.create();
    }
    public static AlertDialog CreateEditTextDialog(Context c, String title, String positiveButtonText,
                                                   String negativeButtonText,
                                                   DialogInterface.OnClickListener positiveButtonClickListener,
                                                   DialogInterface.OnClickListener negativeButtonClickListener,
                                                   int editTextWidth, int editTextHeight) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setNegativeButton(negativeButtonText, negativeButtonClickListener);
        builder.setPositiveButton(positiveButtonText, positiveButtonClickListener);
        EditText editText = new EditText(c);
        editText.setLayoutParams(new ConstraintLayout.LayoutParams(editTextWidth, editTextHeight));
        currentEditText = editText;
        builder.setView(editText);
        return builder.create();
    }
    public static EditText getEditText() {
        return currentEditText;
    }
}
