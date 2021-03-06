package br.com.alura.agenda;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.alura.agenda.modelo.Aluno;

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;

    private Aluno aluno;

    public FormularioHelper(Activity activity){

        campoNome = activity.findViewById(R.id.formulario_nome);

        campoEndereco = activity.findViewById(R.id.formulario_endereco);

        campoTelefone = activity.findViewById(R.id.formulario_telefone);

        campoSite = activity.findViewById(R.id.formulario_site);

        campoNota = activity.findViewById(R.id.formulario_nota);

        campoFoto = activity.findViewById(R.id.formulario_foto);

        aluno = new Aluno();

    }

    public Aluno pegaAluno() {

        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota((double) campoNota.getProgress());
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {

        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        campoFoto.setImageBitmap(BitmapFactory.decodeFile(aluno.getCaminhoFoto()));
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto) {

        if(caminhoFoto == null)
            return;

        Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
//        Bitmap bitmapreduzido = Bitmap.createScaledBitmap(bitmap,300,300,true);
  //      campoFoto.setImageBitmap(bitmapreduzido);
        campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        campoFoto.setTag(caminhoFoto);

    }
}
