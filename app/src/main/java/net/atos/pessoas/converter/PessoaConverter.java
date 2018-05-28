package net.atos.pessoas.converter;

import net.atos.pessoas.modelo.Pessoa;

import org.json.JSONException;
import org.json.JSONStringer;
import java.util.List;

public class PessoaConverter {

    public String converterParaJSON(List<Pessoa> pessoas) {
        JSONStringer json = new JSONStringer();
        try {
            json.object().key("list").array().object().key("pessoa").array();
            for (Pessoa pessoa : pessoas) {
                json.object();
                json.key("nome").value(pessoa.getNome());
                json.key("idade").value(pessoa.getIdade());
                json.key("sexo").value(pessoa.getSexo());
                json.endObject();
            }
            json.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
