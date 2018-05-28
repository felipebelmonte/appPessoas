package net.atos.pessoas.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import net.atos.pessoas.converter.PessoaConverter;
import net.atos.pessoas.dao.PessoaDAO;
import net.atos.pessoas.modelo.Pessoa;

import java.util.List;

public class EnviaPessoaTask extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviaPessoaTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando Pessoas", true, true);
    }

    @Override
    protected String doInBackground(String... params) {
        PessoaDAO dao = new PessoaDAO(context);
        List<Pessoa> pessoas = dao.buscaPessoas();
        dao.close();

        PessoaConverter converter = new PessoaConverter();
        String json = converter.converterParaJSON(pessoas);

        WebClient client = new WebClient();
        String resposta = client.post(json, params[0]);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
