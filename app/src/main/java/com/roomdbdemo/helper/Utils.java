package com.roomdbdemo.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import com.roomdbdemo.EmployeeDetailActivity;
import com.roomdbdemo.MainActivity;
import com.roomdbdemo.R;

import androidx.appcompat.app.AlertDialog;

public class Utils {

    public void showEditDialog(final Activity activity) {

        AlertDialog.Builder alert =  new AlertDialog.Builder(activity, R.style.AppTheme);
        final EditText edittext = new EditText(activity);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        edittext.setSingleLine(true);
        alert.setMessage("Enter Employee Id , you want to update!");
        alert.setTitle("Update");
        alert.setView(edittext);
        alert.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!TextUtils.isEmpty(edittext.getText())) {
                    int emp_id = Integer.parseInt(edittext.getText().toString());
                    Intent intent = new Intent(activity, EmployeeDetailActivity.class);
                    intent.putExtra("comes_from", "update");
                    intent.putExtra("emp_id", emp_id);
                    activity.startActivity(intent);
                }
            }
        });

        alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();
    }
}
