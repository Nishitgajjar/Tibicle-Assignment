package com.example.tibicleassignment.mvvm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tibicleassignment.BuildConfig;
import com.example.tibicleassignment.models.Device;
import com.example.tibicleassignment.models.DeviceImage;
import com.example.tibicleassignment.mvvm.dao.DeviceDao;

@Database(entities = {Device.class, DeviceImage.class}, version = BuildConfig.VERSION_CODE, exportSchema = false)
public abstract class DeviceDatabase extends RoomDatabase {

    private static DeviceDatabase instance;
    public abstract DeviceDao deviceDao();

    public static synchronized DeviceDatabase getInstance(Context ctx) {
        if (instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(),
                    DeviceDatabase.class, "device_Database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
