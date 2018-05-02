package com.matheustadeu.aula25;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Matheus on 16/02/2018.
 */

public class Exercicio extends RealmObject {

    @PrimaryKey private String id;

    private String nome;
    private Integer repeticoes;

    public Exercicio() {



    }

    public Exercicio(String id, String nome, Integer repetições) {

        this.id = id;
        this.nome = nome;
        this.repeticoes = repetições;

    }

    public String getId() {

        return id;

    }

    public void setId(String id) {

        this.id = id;

    }

    public String getNome() {

        return nome;

    }

    public void setNome(String nome) {

        this.nome = nome;

    }

    public Integer getRepeticoes() {

        return repeticoes;

    }

    public void setRepeticoes(Integer repeticoes) {

        this.repeticoes = repeticoes;

    }

    @Override
    public String toString() {

        return getNome() + " " + getRepeticoes() + " repeticoes ! ";

    }
}
