package com.proximate.evaluacion.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proximate.evaluacion.network.DataItem;
import com.proximate.evaluacion.network.ResponseUser;
import com.proximate.evaluacion.network.SeccionesItem;

/**
 * Created by G1L21088 on 22/12/2017.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void crateTables() {
        try {
            open();
            for (String create : DBController.createTables()) {
                database.execSQL(create);
            }
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dropTables() {
        open();
        for (String drop : DBController.dropTables()) {
            database.execSQL(drop);
        }
        close();
    }

    public void insertUser(ResponseUser responseUser) {
        open();
        for (DataItem dataItem : responseUser.getData()) {
            database.execSQL(DBController.insertData(dataItem));
            for (SeccionesItem seccionesItem : dataItem.getSecciones()) {
                database.execSQL(DBController.insertSeccion(seccionesItem, dataItem.getId()));
            }
        }
        close();
    }

    public String selectUser() {
        String string = null;
        try {
            string = "";
            open();
            Cursor cursor = database.rawQuery(DBController.selectData(), null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    string = string
                            .concat(cursor.getColumnName(i))
                            .concat(" ")
                            .concat(cursor.getString(i))
                            .concat("\n")
                    ;
                }
                cursor.moveToNext();
            }
            cursor.close();
            close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return string;
        }

    }


}
