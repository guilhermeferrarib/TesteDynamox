package com.ferrariapps.testedynamox.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_NOTAS";
    public static String TABELA_USUARIO = "usuario";
    public static String ID = "id";
    public static String NOME = "nome";
    public static String NOTA = "nota";

    private SQLiteDatabase db;

    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_usuario = "";

        sql_usuario  = "CREATE TABLE IF NOT EXISTS "+TABELA_USUARIO+" ( ";
        sql_usuario += ID+" INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql_usuario += NOME+" TEXT NOT NULL, ";
        sql_usuario += NOTA+" INTEGER NOT NULL );";




        try {
            db.execSQL(sql_usuario);
            Log.i("INFO DB", "Sucesso ao criar a tabela usuário");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar tabela "+e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
