package com.kingskull.lolapplication.models.database.mappers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kingskull.lolapplication.models.database.entities.Entitie;

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
public class DBManaer {

    private DBHelper helper;
    private SQLiteDatabase db;

    private final long NO_INSERTED_ENTITIE = -1;

    public DBManaer(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }


    public long insert (Entitie entitie){
        long id = NO_INSERTED_ENTITIE;

        getWritableDb();

        if (entitie.getId() == NO_INSERTED_ENTITIE) {
            id = db.insert(entitie.getTableName(), null, entitie.getContentValues());
            entitie.setId( (int) id );
        }

        return id;
    }


    public List multipleInsert (ArrayList<Entitie> entities){
        ArrayList ids = new ArrayList();
        long tempId;

        getWritableDb();

        for (Entitie entitie : entities){
            tempId = this.insert(entitie);
            ids.add(tempId);
        }

        return ids;
    }

    public void update (Entitie entitie){
        getWritableDb();

        db.update(entitie.getTableName(), entitie.getContentValues(), "id = ?", new String[]{String.valueOf(entitie.getId())});
    }

    public Entitie getById (Entitie dummyEntitie) {
        getReadableDb();

        Cursor cursor = db.query(dummyEntitie.getTableName(), dummyEntitie.getColumnNames(), "id = ?", new String[]{ String.valueOf(dummyEntitie.getId())}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        dummyEntitie.setContentValues(cursor);

        return dummyEntitie;
    }


    public List<Entitie> getAll(Entitie dumEntitiie){
        List<Entitie> entities = new ArrayList<Entitie>();

        String selectQuery = "SELECT * FROM " + dumEntitiie.getTableName();

        getWritableDb();

        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping trught all rows and adding to the list
        if (cursor.moveToFirst()){
            do {

                dumEntitiie = dumEntitiie.getNewInstance();
                dumEntitiie.setContentValues(cursor);
                entities.add(dumEntitiie);

            } while ( cursor.moveToFirst() );
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
