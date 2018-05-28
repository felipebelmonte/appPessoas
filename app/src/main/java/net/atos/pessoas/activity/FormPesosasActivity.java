package net.atos.pessoas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import net.atos.pessoas.R;
import net.atos.pessoas.dao.PessoaDAO;
import net.atos.pessoas.helper.FormPessoasHelper;
import net.atos.pessoas.modelo.Pessoa;

public class FormPesosasActivity extends AppCompatActivity {

    private FormPessoasHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pesosas);
        FloatingActionButton botaoSalvar = (FloatingActionButton) findViewById(R.id.nova_pessoa);
        helper = new FormPessoasHelper(this);

        Intent intent = getIntent();
        Pessoa pessoa = (Pessoa) intent.getSerializableExtra("pessoa");
        if (pessoa != null) {
            helper.preencheFormulario(pessoa);
        }
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (helper.validaForm()) {
                    Pessoa pessoa = helper.getPessoa();
                    PessoaDAO dao = new PessoaDAO(FormPesosasActivity.this);
                    if (pessoa.getId() != null) {
                        dao.altera(pessoa);
                    } else {
                        dao.insere(pessoa);
                    }
                    dao.close();
                    Toast.makeText(FormPesosasActivity.this, "Pessoa Salva", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

}
