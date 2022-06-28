package com.roomdbdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.roomdbdemo.entity.EmpEntity;
import com.roomdbdemo.preference.Preference;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class EmployeeDetailActivity extends AppCompatActivity {

    private EditText edttxtName, edttxtDepart, edttxtDesig, edttxtExp;
    private Button btnSave;
    private  String comes_from ;
    private int emp_id;
    private ProgressBar progressBar;
    private Preference preference;
    private int empIdStored = 0, empIdForNew; //we will save it in shared preference , will increase by 1 everytime


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emp_detail_layout);
        inItViews();
        Intent in =  getIntent();
        comes_from = in.getStringExtra("comes_from");
        emp_id = in.getIntExtra("emp_id",0);

        if (comes_from.equalsIgnoreCase("update")){
            new getEmployeeInfo(emp_id).execute();
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comes_from.equalsIgnoreCase("insert")){
                    insertEmployeeDetail();
                }
                else if (comes_from.equalsIgnoreCase("update")){
                    updateEmployeeDetail(emp_id);
                }
            }
        });
    }

    private void inItViews() {
        preference =  Preference.getInstance(this);
        edttxtName = (EditText)findViewById(R.id.edttxt_name);
        edttxtDepart = (EditText)findViewById(R.id.edttxt_dept);
        edttxtDesig = (EditText)findViewById(R.id.edttxt_desig);
        edttxtExp = (EditText)findViewById(R.id.edttxt_exp);
        btnSave = (Button)findViewById(R.id.btn_save);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    private void updateEmployeeDetail(int emp_id) {
        // fetch & insert the emp detail
        if(!(TextUtils.isEmpty(edttxtName.getText()))&&
                !(TextUtils.isEmpty(edttxtDepart.getText()))&&
                !(TextUtils.isEmpty(edttxtDesig.getText()))&&
                !(TextUtils.isEmpty(edttxtExp.getText()))){
            // insert in db
            EmpEntity empEntity =  new EmpEntity(emp_id,edttxtName.getText().toString()
                    ,edttxtDepart.getText().toString()
                    ,edttxtDesig.getText().toString()
                    ,edttxtExp.getText().toString());
            new insertupdateEmpDetail(empEntity).execute();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please fill all the fields!",Toast.LENGTH_LONG).show();
        }
    }

    private void insertEmployeeDetail() {

        empIdStored =  preference.getEmployeeId();
        empIdForNew = empIdStored+1;

        if(!(TextUtils.isEmpty(edttxtName.getText()))&&
                !(TextUtils.isEmpty(edttxtDepart.getText()))&&
                !(TextUtils.isEmpty(edttxtDesig.getText()))&&
                !(TextUtils.isEmpty(edttxtExp.getText()))){
            // insert in db
            EmpEntity empEntity =  new EmpEntity(empIdForNew,edttxtName.getText().toString()
                    ,edttxtDepart.getText().toString()
                    ,edttxtDesig.getText().toString()
                    ,edttxtExp.getText().toString());
            new insertupdateEmpDetail(empEntity).execute();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please fill all the fields!",Toast.LENGTH_LONG).show();
        }
    }

    private class insertupdateEmpDetail extends AsyncTask<String, String, String> {

        EmpEntity empEntity ;

        public insertupdateEmpDetail(EmpEntity empEntity) {
            this.empEntity = empEntity;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            if (comes_from.equalsIgnoreCase("insert")){
                RoomDbDemoApplication.getInstance().getDb().employeeDbDao().insertEmpInfo(empEntity);
            }
            else{
                RoomDbDemoApplication.getInstance().getDb().employeeDbDao().updateEmpInfo(empEntity);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            edttxtName.getText().clear();
            edttxtDepart.getText().clear();
            edttxtDesig.getText().clear();
            edttxtExp.getText().clear();
            if (comes_from.equalsIgnoreCase("insert")){
                preference.setEmployeeId(empEntity.getEmp_id());
                Toast.makeText(getApplicationContext(),"Employee record saved successfully!",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Employee record updated successfully!",Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

    private class getEmployeeInfo extends AsyncTask<String, String, String>{
        int empId ;
        EmpEntity empEntity;
        public getEmployeeInfo(int emp_id) {
            this.empId = emp_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
           empEntity =  RoomDbDemoApplication.getInstance().getDb().employeeDbDao().fetchEmpDetail(empId);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            if (empEntity!=null){
                edttxtName.setText(empEntity.getName());
                edttxtDepart.setText(empEntity.getDept());
                edttxtDesig.setText(empEntity.getDesign());
                edttxtExp.setText(empEntity.getExp());
            }

            else{
                Toast.makeText(getApplicationContext(),"No Employee found with this employee-id!",Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }
}
