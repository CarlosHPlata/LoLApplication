package com.kingskull.lolapplication.models.database.mappers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kingskull.lolapplication.models.database.entities.Entitie;
import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de las conexiones a bases de datos, generales, genera una estructura con los metodos basicos, y provee metodos que devuelven base de datos, para crear metodos propios y agenos que cumplan con caracteristicas distintas.
 *
 * @author Carlos Herrera
 *
 * @version 2.00, 18/09/2015
 *
 */
public class DBManaer<T extends Entitie> {

    private DBHelper helper;
    private SQLiteDatabase db;

    private final long NO_INSERTED_ENTITIE = -1;

    public DBManaer(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }


    public long insert (T entitie){
        long id = NO_INSERTED_ENTITIE;

        getWritableDb();

        if (entitie.getId() == NO_INSERTED_ENTITIE) {
            id = db.insert(entitie.getTableName(), null, entitie.getContentValues());
            entitie.setId( (int) id );
        }
        db.replace(entitie.getTableName(), null, entitie.getContentValues());
        return id;
    }

    public long insertOrUpdate(T entitie, ContentValues contentValues){
        long id = NO_INSERTED_ENTITIE;

        getWritableDb();

        id = db.replace(entitie.getTableName(), null, contentValues);

        return id;
    }


    public List multipleInsert (ArrayList<T> entities){
        ArrayList ids = new ArrayList();
        long tempId;

        getWritableDb();

        for (T entitie : entities){
            tempId = this.insert(entitie);
            ids.add(tempId);
        }

        return ids;
    }

    public void update (T entitie){
        getWritableDb();

        db.update(entitie.getTableName(), entitie.getContentValues(), "id = ?", new String[]{String.valueOf(entitie.getId())});
    }

    public T getById (T dummyEntitie) {
        getReadableDb();

        Cursor cursor = db.query(dummyEntitie.getTableName(), dummyEntitie.getColumnNames(), "id = ?", new String[]{ String.valueOf(dummyEntitie.getId())}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getColumnCount() > 0)
            dummyEntitie.setContentValues(cursor);

        return dummyEntitie;
    }

    public void delete(T entitie){
        getWritableDb();

        db.delete(entitie.getTableName(), "id = ?", new String[]{ String.valueOf(entitie.getId()) });
    }

    public SummonerEntitie getBySummonerName (SummonerEntitie entitie){
        getReadableDb();

        Cursor cursor = db.query(entitie.getTableName(), entitie.getColumnNames(), "summonername = ? AND region = ?", new String[]{entitie.getSummonername(), entitie.getRegion() }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        int count  = cursor.getCount();

        if (count > 0)
            entitie.setContentValues(cursor);

        return entitie;
    }




    public List<T> getAll(T dumEntitiie){
        List<T> entities = new ArrayList<T>();

        String selectQuery = "SELECT * FROM " + dumEntitiie.getTableName();

        getWritableDb();

        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping trught all rows and adding to the list
        if (cursor.moveToFirst()){
            do {

                dumEntitiie = (T) dumEntitiie.getNewInstance();
                dumEntitiie.setContentValues(cursor);
                entities.add(dumEntitiie);

            } while ( cursor.moveToNext() );
        }

        return entities;
    }


    public SQLiteDatabase getReadableDb() {
        this.db = helper.getReadableDatabase();
        return this.db;
    }


    public SQLiteDatabase getWritableDb() {
        this.db = helper.getWritableDatabase();
        return this.db;
    }



}
