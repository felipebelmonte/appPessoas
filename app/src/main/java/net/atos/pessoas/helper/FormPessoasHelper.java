package net.atos.pessoas.helper;

import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.atos.pessoas.R;
import net.atos.pessoas.activity.FormPesosasActivity;
import net.atos.pessoas.modelo.Pessoa;

public class FormPessoasHelper {

    private final EditText nome;
    private final EditText idade;
    private final Spinner sexo;
    private Pessoa pessoa;
    private ArrayAdapter<CharSequence> adapter;

    public FormPessoasHelper(FormPesosasActivity formPesosasActivity) {
        pessoa = new Pessoa();
        nome = (EditText) formPesosasActivity.findViewById(R.id.formpessoas_nome);
        nome.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(150)});
        idade = (EditText) formPesosasActivity.findViewById(R.id.formpessoas_idade);
        idade.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(2)});
        sexo = (Spinner) formPesosasActivity.findViewById(R.id.formpessoas_sexo);
        adapter = ArrayAdapter.createFromResource(formPesosasActivity, R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(adapter);
        sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                pessoa.setSexo(parent.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public Pessoa getPessoa() {
        pessoa.setNome(nome.getText().toString());
        pessoa.setIdade(Integer.valueOf(idade.getText().toString()));
        return pessoa;
    }

    public void preencheFormulario(Pessoa pessoa) {
        nome.setText(pessoa.getNome().toString());
        idade.setText(pessoa.getIdade().toString());
        if (pessoa.getSexo() != null) {
            int spinnerPosition = adapter.getPosition(pessoa.getSexo());
            sexo.setSelection(spinnerPosition);
        }
        this.pessoa = pessoa;
    }

    public boolean validaForm() {
        boolean isSucesso = true;
        if (nome != null && nome.getText().toString().length() == 0) {
            nome.setError("Informe o nome");
            if (isSucesso) {
                nome.requestFocus();
            }
            isSucesso = false;
        }
        if (idade != null && idade.getText().toString().length() == 0) {
            idade.setError("Informe a idade");
            if (isSucesso) {
                idade.requestFocus();
            }
            isSucesso = false;
        }
        if (sexo.getSelectedItemPosition() == 0) {
            if (isSucesso) {
                ((TextView) sexo.getSelectedView()).setError("Informe o sexo");
            }
            isSucesso = false;
        }
        return isSucesso;
    }
}

