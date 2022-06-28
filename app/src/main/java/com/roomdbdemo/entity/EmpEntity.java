package com.roomdbdemo.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee")
public class EmpEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "emp_id")
    int emp_id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "dept")
    String dept;

    @ColumnInfo(name = "designation")
    String design;

    @ColumnInfo(name = "exp")
    String exp;

    public EmpEntity(@NonNull int emp_id, String name, String dept, String design, String exp) {
        this.emp_id = emp_id;
        this.name = name;
        this.dept = dept;
        this.design = design;
        this.exp = exp;
    }

    @NonNull
    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(@NonNull int emp_id) {
        this.emp_id = emp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
