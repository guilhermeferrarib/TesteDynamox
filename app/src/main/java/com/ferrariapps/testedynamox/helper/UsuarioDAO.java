package com.ferrariapps.testedynamox.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ferrariapps.testedynamox.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public UsuarioDAO(Context context){
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Usuario usuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NOME,usuario.getNome());
        contentValues.put(DbHelper.NOTA,usuario.getNota());

        try {
            escreve.insert(DbHelper.TABELA_USUARIO,null, contentValues);
            Log.i("INFO", "Usuário salvo com sucesso!");
        }catch (Exception e){
            Log.e("ERRO", "Erro ao salvar usuário "+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Usuario usuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ID,1);
        contentValues.put(DbHelper.NOME,usuario.getNome());
        contentValues.put(DbHelper.NOTA,usuario.getNota());

        try {
            String[] args = {usuario.getId().toString()};
            escreve.update(DbHelper.TABELA_USUARIO,contentValues,"id=?",args);
            Log.i("INFO", "Usuário atualizado com sucesso!");
        }catch (Exception e){
            Log.e("ERRO", "Erro ao atualizar usuário "+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarioList = new ArrayList<>();

        String sql = "SELECT * FROM "+DbHelper.TABELA_USUARIO+" ;";
        Cursor c = le.rawQuery(sql,null);

        while (c.moveToNext()){

            Usuario usuario = new Usuario();

            Long id = c.getLong(c.getColumnIndex(DbHelper.ID));
            String nome = c.getString(c.getColumnIndex(DbHelper.NOME));
            Integer nota = c.getInt(c.getColumnIndex(DbHelper.NOTA));

            usuario.setId(id);
            usuario.setNome(nome);
            usuario.setNota(nota);

            usuarioList.add(usuario);

        }

        c.close();
        return usuarioList;
    }
}
