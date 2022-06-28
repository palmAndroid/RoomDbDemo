package com.roomdbdemo.db;



import com.roomdbdemo.entity.EmpEntity;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {EmpEntity.class},version = 2, exportSchema = false)
public  abstract class EmployeeRoomDb extends RoomDatabase {
    public static EmployeeRoomDb INSTANCE;

    public abstract EmployeeDbDao employeeDbDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };
}