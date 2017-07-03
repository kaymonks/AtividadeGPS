package com.example.von.prova2.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(Context context){
        super(context, "food", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE food (id INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT, imagem TEXT, rating INT, tipo TEXT, geoLatitude REAL, geoLongitude REAL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2){
            db.execSQL(
                    "ALTER TABLE food ADD COLUMN geoLatitude REAL"
            );
            db.execSQL(
                    "ALTER TABLE food ADD COLUMN geoLongitude REAL"
            );
        }
    }
}
