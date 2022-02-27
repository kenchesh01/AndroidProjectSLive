package com.transposesolutions.loantrackerproject.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {LoanTrackerEntity.class,AmortizationEntity.class},version = 5)
public abstract class LoanTrackerDatabase extends RoomDatabase {
    public abstract  LoanTrackerDao loanTrackerDao();

    public static LoanTrackerDatabase INSTANCE;

    public static LoanTrackerDatabase getDBinstance(Context context) {
        if(INSTANCE == null ) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LoanTrackerDatabase.class, "AppDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }


}
