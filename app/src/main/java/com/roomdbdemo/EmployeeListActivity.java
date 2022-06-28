package com.roomdbdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.roomdbdemo.adapter.EmpListAdapter;
import com.roomdbdemo.entity.EmpEntity;
import com.roomdbdemo.helper.ClickListener;
import com.roomdbdemo.helper.RecyclerTouchListener;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeListActivity extends AppCompatActivity {

    ArrayList<EmpEntity> empList =  new ArrayList<>();
    ProgressBar progressBar;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emp_list_layout);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setVisibility(View.GONE);
        loadEmpList();

    }

    private void loadEmpList() {
        //fetch emp list from DB
        new LoadEmpListAsyncTask().execute();

    }

    private void showEmpList(List<EmpEntity> empEntities) {

        EmpListAdapter mAdapter = new EmpListAdapter(EmployeeListActivity.this,empEntities);
        recyclerView.setLayoutManager(new LinearLayoutManager(EmployeeListActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(EmployeeListActivity.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //clicked show the emp detail
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private class LoadEmpListAsyncTask extends AsyncTask<String,String,List<EmpEntity>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected  List<EmpEntity>  doInBackground(String... strings) {
            List<EmpEntity> empEntities =  RoomDbDemoApplication.getInstance().getDb().employeeDbDao().showAll();
            return empEntities;
        }

        @Override
        protected void onPostExecute( List<EmpEntity> empEntities) {
            super.onPostExecute(empEntities);
            progressBar.setVisibility(View.GONE);
            if (empEntities.size()>0){
                recyclerView.setVisibility(View.VISIBLE);
                showEmpList(empEntities);
            }else{
                Toast.makeText(getApplicationContext(), "No employee has been stored!!",Toast.LENGTH_LONG).show();
            }

        }
    }
}
