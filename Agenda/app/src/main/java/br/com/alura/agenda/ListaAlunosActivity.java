package br.com.alura.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.adapter.AlunosAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private Button  button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        listaAlunos = findViewById(R.id.lista_alunos);


        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Toast.makeText(ListaAlunosActivity.this, "Aluno " +  aluno.getNome(), Toast.LENGTH_SHORT ).show();
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno",aluno);
                startActivity(intentVaiProFormulario);


            }
        });


        button = findViewById(R.id.novo_aluno);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();

    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();

        AlunosAdapter listaAdapater = new AlunosAdapter(this,alunos);
        listaAlunos.setAdapter(listaAdapater);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        String site = aluno.getSite();


        MenuItem itemLigacao = menu.add("Ligar");
        itemLigacao.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},123);
                }else {

                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }

                return false;
            }
        });



        MenuItem itemTelefone = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemTelefone.setIntent(intentSMS);


        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);


        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        if(!site.startsWith("https://")){
            site = "https://" + site;
        }


        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(ListaAlunosActivity.this, "Deletar o aluno " + aluno.getNome(), Toast.LENGTH_SHORT ).show();

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();

                carregaLista();

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:

                new EnviaAlunosTask(this).execute();

                break;

            case R.id.menu_baixar_provas:
                final Intent vaiParaProvas = new Intent(this, ProvasActivity.class);
                startActivity(vaiParaProvas);
                break;

            case R.id.menu_mapa:
                Intent vaiParaMapa = new Intent(this, MapaActivity.class);
                startActivity(vaiParaMapa);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
