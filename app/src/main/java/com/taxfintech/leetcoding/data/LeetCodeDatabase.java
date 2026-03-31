package com.taxfintech.leetcoding.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LeetCodeEntry.class}, version = 1, exportSchema = false)
public abstract class LeetCodeDatabase extends RoomDatabase {

    private static volatile LeetCodeDatabase instance;

    public abstract LeetCodeDao leetCodeDao();

    @NonNull
    public static LeetCodeDatabase getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (LeetCodeDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    LeetCodeDatabase.class,
                                    "leetcoding_database"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
