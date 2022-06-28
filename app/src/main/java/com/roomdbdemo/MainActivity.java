package com.roomdbdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.roomdbdemo.helper.Utils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_insert = (Button)findViewById(R.id.btn_insert);
        Button btn_update = (Button)findViewById(R.id.btn_update);
        Button btn_show = (Button)findViewById(R.id.btn_show);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployeeDetailActivity.class);
                intent.putExtra("comes_from","insert");
                intent.putExtra("emp_id","");
                startActivity(intent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.showEditDialog(MainActivity.this);
            }
        });

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployeeListActivity.class);
                startActivity(intent);
            }
        });

    }



    private void showAlertDialog() {

        AlertDialog.Builder alert =  new AlertDialog.Builder(MainActivity.this, R.style.AppTheme);
        final EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        edittext.setSingleLine(true);
        alert.setMessage("Enter Employee Id , you want to update!");
        alert.setTitle("Update");
        alert.setView(edittext);
        alert.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!TextUtils.isEmpty(edittext.getText())) {
                    int emp_id = Integer.parseInt(edittext.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), EmployeeDetailActivity.class);
                    intent.putExtra("comes_from", "update");
                    intent.putExtra("emp_id", emp_id);
                    startActivity(intent);
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
