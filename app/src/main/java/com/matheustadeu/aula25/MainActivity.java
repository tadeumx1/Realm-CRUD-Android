package com.matheustadeu.aula25;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ListView lsvExercicios;
    private RealmResults<Exercicio> exercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        lsvExercicios = (ListView) findViewById(R.id.lsvExercicios);

        Realm.init(MainActivity.this);
        Realm realm = Realm.getDefaultInstance();

        exercicios = realm.where(Exercicio.class).findAll();

        ArrayAdapter<Exercicio> adapter = new ArrayAdapter<Exercicio>
                (MainActivity.this, android.R.layout.simple_list_item_1, exercicios);

        lsvExercicios.setAdapter(adapter);

        lsvExercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button

                                atualizar(position);

                            }
                        })
                        .setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog

                                excluir(position);

                            }
                        });

                builder.setMessage("O que deseja fazer ?")
                        .setTitle("Tarefa");

                builder.show();

            }
        });

    }

    public void novoExercicio (View view) {

        Intent intent = new Intent(MainActivity.this, Database.class);
        startActivity(intent);

    }

    public void atualizar (int i) {

        Exercicio exercicio = exercicios.get(i);

        Intent intent = new Intent(MainActivity.this, Database.class);
        intent.putExtra("id", exercicio.getId());
        intent.putExtra("exercicio", exercicio.getNome());
        intent.putExtra("repeticoes", exercicio.getRepeticoes());
        startActivity(intent);

    }

    public void excluir (int i) {

        Exercicio exercicio = exercicios.get(i);

        Intent intent = new Intent(MainActivity.this, Database.class);
        intent.putExtra("delete", "excluir");
        intent.putExtra("id", exercicio.getId());
        intent.putExtra("exercicio", exercicio.getNome());
        intent.putExtra("repeticoes", exercicio.getRepeticoes());
        startActivity(intent);

    }

}
