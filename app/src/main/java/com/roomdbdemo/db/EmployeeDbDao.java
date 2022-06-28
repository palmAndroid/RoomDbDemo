package com.roomdbdemo.db;


import com.roomdbdemo.entity.EmpEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EmployeeDbDao {

    @Insert
    void insertEmpInfo(EmpEntity myBooksEntity);

    @Update
    void updateEmpInfo(EmpEntity myBooksEntity);

    @Delete
    void deleteEmpInfo(EmpEntity myBooksEntity);

   // with query
    @Query("SELECT * FROM employee")
    List<EmpEntity> showAll();

    // with query
    @Query("SELECT * FROM employee WHERE emp_id = :emp_id")
    EmpEntity fetchEmpDetail(int emp_id);

}
