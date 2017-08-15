package com.kingskull.lolapplication.models.database.mappers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Esta clase es una clase que administra la creacion de la base de datos y sus procesos.
 *
 * @author Carlos Herrera
 *
 * @version 1.50, 15/02/2015
 *
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "synergylol.sqlite";
    private static final int DB_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS summoners (id INTEGER PRIMARY KEY, summonername TEXT, region TEXT)");
        //db.execSQL("INSERT INTO summoners(id, summonername, region) VALUES (2033911, 'I Legion', 'lan')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS summoners");
    }
}
