package com.matheustadeu.aula25;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Matheus on 16/02/2018.
 */

public class Database extends AppCompatActivity {

    private EditText edtExercicio, edtRepeticoes;
    private Exercicio exercicio;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtExercicio = (EditText) findViewById(R.id.edtExercicio);
        edtRepeticoes = (EditText) findViewById(R.id.edtRepeticoes);

        // - Recuperando Bundle

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            id = bundle.getString("id");

            if(bundle.getString("delete") != null) {

                if (bundle.getString("delete").equalsIgnoreCase("excluir")) {

                    Realm.init(Database.this);

                    Realm realm = Realm.getDefaultInstance();

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<Exercicio> result = realm.where(Exercicio.class).equalTo("id", id).findAll();
                            result.deleteAllFromRealm();
                        }
                    });

                    Toast.makeText(this, "O exercício foi excluído ! ", Toast.LENGTH_SHORT).show();

                }

            }

            else {



            }

        }

        if(id != null) {

            exercicio = new Exercicio();
            exercicio.setId(id);
            exercicio.setNome(bundle.getString("exercicio"));
            exercicio.setRepeticoes(bundle.getInt("repeticoes"));

            edtExercicio.setText(exercicio.getNome());
            edtRepeticoes.setText(String.valueOf(exercicio.getRepeticoes()));

        }

    }

    public void atualizar(View view) {

        // - Capturando os dados da tela

        if(exercicio == null) {

            exercicio = new Exercicio();
            UUID uuid = UUID.randomUUID();
            exercicio.setId(uuid.toString());

        }

        exercicio.setNome(edtExercicio.getEditableText().toString().trim());
        exercicio.setRepeticoes(Integer.parseInt(edtRepeticoes.getEditableText().toString().trim()));

        // -- Gravando dados com Realm

        // -- Obter o banco de dados

        Realm.init(Database.this);

        Realm realm = Realm.getDefaultInstance();

        // -- Iniciar uma transação

        realm.beginTransaction();

        // -- Gravar ou atualizar o Exercicio

        realm.copyToRealmOrUpdate(exercicio);

        // -- Finalizando transação

        realm.commitTransaction();

        Toast.makeText(this, "Exercício Salvo ! ", Toast.LENGTH_SHORT).show();

        exercicio = null;

        edtExercicio.setText("");
        edtRepeticoes.setText("");

    }

    public void dashboard(View view) {

        Intent intent = new Intent(Database.this, MainActivity.class);
        startActivity(intent);

    }

}
