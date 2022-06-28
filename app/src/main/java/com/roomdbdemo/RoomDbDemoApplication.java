package com.roomdbdemo;

import android.app.Application;
import com.roomdbdemo.db.EmployeeRoomDb;
import androidx.room.Room;

public class RoomDbDemoApplication extends Application {

    public static RoomDbDemoApplication instance;
    public  EmployeeRoomDb myDatabase;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        myDatabase = Room.databaseBuilder(getApplicationContext(),
                EmployeeRoomDb.class, "Emp.db")
                .addMigrations(EmployeeRoomDb.MIGRATION_1_2)
                .build();
    }

    public static RoomDbDemoApplication getInstance() {
        return instance;
    }

    public EmployeeRoomDb getDb() {
        return myDatabase;
    }
}
