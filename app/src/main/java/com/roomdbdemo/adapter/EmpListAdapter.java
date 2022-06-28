package com.roomdbdemo.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.roomdbdemo.EmployeeDetailActivity;
import com.roomdbdemo.R;
import com.roomdbdemo.RoomDbDemoApplication;
import com.roomdbdemo.entity.EmpEntity;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class EmpListAdapter extends RecyclerView.Adapter<EmpListAdapter.MyViewHolder>{

    Activity activity;
    List<EmpEntity> empList ;
    public EmpListAdapter(Activity mActivity, List<EmpEntity> empList) {
        this.activity = mActivity;
        this.empList = empList;
    }

    @Override
    public EmpListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emp_item_layout, parent, false);
        return new EmpListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(EmpListAdapter.MyViewHolder holder, int position) {
        final EmpEntity empEntity = empList.get(position);
        holder.textName.setText("Name: "+empEntity.getName() );
        holder.textDesignation.setText(empEntity.getDesign()+" ("+empEntity.getDept()+") ");
        holder.textExp.setText("Experience(in months): "+empEntity.getExp());
        holder.textEmpId.setText( "EmployeeId: "+empEntity.getEmp_id());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete the employee record
                showDeleteDialog(activity,empEntity);
            }
        });
        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edit the employee record
                Intent intent = new Intent(activity, EmployeeDetailActivity.class);
                intent.putExtra("comes_from", "update");
                intent.putExtra("emp_id", empEntity.getEmp_id());
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }
    class  MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView, imageEdit;
        TextView textName, textDesignation, textExp, textEmpId;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView2);
            textName = (TextView)itemView.findViewById(R.id.textView);
            textDesignation = (TextView)itemView.findViewById(R.id.textView2);
            textExp = (TextView)itemView.findViewById(R.id.textView3);
            textEmpId = (TextView)itemView.findViewById(R.id.textView4);
            imageEdit = (ImageView)itemView.findViewById(R.id.imageView3);
        }

    }

    private void showDeleteDialog(Activity activity, final EmpEntity empEntity) {

        AlertDialog.Builder alert =  new AlertDialog.Builder(activity, R.style.AppTheme);
        alert.setMessage("Are you sure , to delete this employee record?");
        alert.setTitle("Delete");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               deleteEmployeeRecord(empEntity);
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void deleteEmployeeRecord(EmpEntity empEntity) {
        new deleteTask(empEntity).execute();
    }

    private class deleteTask extends AsyncTask<String,String,String> {
        EmpEntity empEntity ;
        public deleteTask(EmpEntity empEntity) {
            this.empEntity = empEntity;
        }

        @Override
        protected String doInBackground(String... strings) {
            RoomDbDemoApplication.getInstance().getDb().employeeDbDao().deleteEmpInfo(empEntity);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            empList.remove(empEntity);
            notifyDataSetChanged();
        }
    }
}
