package net.atos.pessoas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.atos.pessoas.modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDAO extends SQLiteOpenHelper {

    public PessoaDAO(Context context) {
        super(context, "Pessoas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder("CREATE TABLE Pessoas ( ");
        sql.append("id INTEGER PRIMARY KEY, ");
        sql.append("nome TEXT NOT NULL, ");
        sql.append("idade TEXT, ");
        sql.append("sexo TEXT );");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Pessoas;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insere(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getDadosDaPessoa(pessoa);
        db.insert("Pessoas", null, dados);
    }

    private ContentValues getDadosDaPessoa(Pessoa pessoa) {
        ContentValues dados = new ContentValues();
        dados.put("nome", pessoa.getNome());
        dados.put("idade", pessoa.getIdade());
        dados.put("sexo", pessoa.getSexo());

        return dados;
    }

    public List<Pessoa> buscaPessoas() {
        String sql = "SELECT * FROM Pessoas";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Pessoa> pessoas = new ArrayList<>();
        while (c.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setId(c.getLong(c.getColumnIndex("id")));
            pessoa.setNome(c.getString(c.getColumnIndex("nome")));
            pessoa.setIdade(c.getInt(c.getColumnIndex("idade")));
            pessoa.setSexo(c.getString(c.getColumnIndex("sexo")));

            pessoas.add(pessoa);
        }
        c.close();

        return pessoas;
    }

    public void remove(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();
        String[] param = {pessoa.getId().toString()};
        db.delete("Pessoas", "id = ?", param);
    }

    public void altera(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getDadosDaPessoa(pessoa);
        String[] params = {pessoa.getId().toString()};
        db.update("Pessoas", dados, "id = ?", params);
    }
}
