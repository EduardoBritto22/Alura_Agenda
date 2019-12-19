package br.com.alura.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class MapaFragment extends Fragment implements OnMapReadyCallback {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        SupportMapFragment.newInstance().getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng posicaoDaEscola = pegaCoordenadaDoEndereco("Rua José Araújo Fernandes 618, Salgado Filho, Belo Horizonte");

        if (posicaoDaEscola!=null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoDaEscola,17);
            googleMap.moveCamera(update);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()){
            LatLng latLng = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if (latLng!=null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(latLng);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }

        alunoDAO.close();

        Localizador localizador = new Localizador(getContext(), googleMap);


    }

    private LatLng pegaCoordenadaDoEndereco(String endereco) {
        Geocoder geocoder = new Geocoder(getContext());
        double longitude = 0;
        double latitude = 0;
        LatLng latLng = null;

        try {
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);

            if (!resultados.isEmpty()){
                longitude = resultados.get(0).getLongitude();
                latitude = resultados.get(0).getLatitude();
                latLng = new LatLng(latitude, longitude);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLng;
    }
}
