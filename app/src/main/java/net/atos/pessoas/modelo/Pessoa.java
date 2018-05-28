package net.atos.pessoas.modelo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pessoa implements Serializable {
    private Long id;
    private String nome;
    private Integer idade;
    private String sexo;

    @Override
    public String toString() {
        return getId() + " - " + getNome();
    }
}
