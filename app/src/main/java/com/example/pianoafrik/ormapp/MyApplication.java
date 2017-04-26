package com.example.pianoafrik.ormapp;

import android.app.Application;
import com.example.pianoafrik.ormapp.model.DaoMaster;
import com.example.pianoafrik.ormapp.model.DaoSession;
import org.greenrobot.greendao.database.Database;


public class MyApplication extends Application {

    //Create a session
    private DaoSession daoSession;

    //get a helper
    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "phoneNumber-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();



    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
