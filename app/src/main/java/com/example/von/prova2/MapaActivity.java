package com.example.von.prova2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.example.von.prova2.modelo.Food;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Von on 04/07/2016.
 */
public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private Food a;
    public Marker marcador;
    private GoogleMap mapa;
    private boolean temCoordenada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        ArrayList<Food> a = Food.list(this.getApplicationContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        temCoordenada = this.getIntent().getBooleanExtra("temCordenada", false);
        if(!temCoordenada){
            mapFragment.getView().setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        if(temCoordenada){
            //tem coordenada
            LatLng local = new LatLng(a.getGeoLatitude(), a.getGeoLongitude());
            if (a.getTipo() == getResources().getString(R.string.entrada)) {
                marcador = mapa.addMarker(new MarkerOptions().position(local).title(a.getDesc()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
            if (a.getTipo() == getResources().getString(R.string.pratoprincipal)) {
                marcador = mapa.addMarker(new MarkerOptions().position(local).title(a.getDesc()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            if (a.getTipo() == getResources().getString(R.string.sobremesa)) {
                marcador = mapa.addMarker(new MarkerOptions().position(local).title(a.getDesc()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            if (a.getTipo() == getResources().getString(R.string.lanche)) {
                marcador = mapa.addMarker(new MarkerOptions().position(local).title(a.getDesc()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }

            mapa.moveCamera(CameraUpdateFactory.newLatLng(local));

            mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    marcador.setPosition(latLng);
                    a.setGeoLatitude(latLng.latitude);
                    a.setGeoLongitude(latLng.longitude);
                }
            });
        }
    }

}
