package net.atos.pessoas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import net.atos.pessoas.R;
import net.atos.pessoas.dao.PessoaDAO;
import net.atos.pessoas.modelo.Pessoa;
import net.atos.pessoas.util.EnviaPessoaTask;

import java.util.List;

public class ListaPessoasActivity extends AppCompatActivity {

    private ListView listaPessoas;
    private EditText server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);
        carregaLista();
        listaPessoas = (ListView) findViewById(R.id.lista_pessoas);
        listaPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int i, long l) {
                Pessoa pessoa = (Pessoa) listaPessoas.getItemAtPosition(i);
                Intent intentFormPessoa = new Intent(ListaPessoasActivity.this, FormPesosasActivity.class);
                intentFormPessoa.putExtra("pessoa", pessoa);
                startActivity(intentFormPessoa);
            }
        });
        //server = (EditText) findViewById(R.id.lista_server);
        //server.setText("http://189.103.143.21:8090/pessoas");
        Button novaPessoa = (Button) findViewById(R.id.nova_pessoa);
        novaPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFormPessoa = new Intent(ListaPessoasActivity.this, FormPesosasActivity.class);
                startActivity(intentFormPessoa);
            }
        });
        registerForContextMenu(listaPessoas);
    }

    private void carregaLista() {
        PessoaDAO dao = new PessoaDAO(this);
        List<Pessoa> pessoas = dao.buscaPessoas();
        dao.close();

        listaPessoas = (ListView) findViewById(R.id.lista_pessoas);
        ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1, pessoas);
        listaPessoas.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pessoas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_pessoas:
                new EnviaPessoaTask(this).execute(server.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem remover = menu.add("Remover");
        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Pessoa pessoa = (Pessoa) listaPessoas.getItemAtPosition(info.position);

                PessoaDAO dao = new PessoaDAO(ListaPessoasActivity.this);
                dao.remove(pessoa);
                dao.close();

                carregaLista();
                return false;
            }
        });
    }
}
