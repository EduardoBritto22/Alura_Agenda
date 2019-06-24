package br.com.alura.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        String[] alunos = {"Daniel", "Carla", "João", "Gisele"};

        listaAlunos = findViewById(R.id.lista_alunos);
        ArrayAdapter<String> listaAdapater = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alunos);
        listaAlunos.setAdapter(listaAdapater);

    }
}
