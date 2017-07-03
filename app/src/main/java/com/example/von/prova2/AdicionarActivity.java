package com.example.von.prova2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.von.prova2.modelo.Food;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Von on 04/05/2016.
 */
public class AdicionarActivity extends AppCompatActivity {

    AdapterFood adaptador;
    private double geoLatitude;
    private double geoLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);
    }

    public void onClickSalvar(View v) {
        EditText txtDesc = (EditText) findViewById(R.id.txtDesc);
        RadioGroup rGrpTipo= (RadioGroup) findViewById(R.id.rGrpTipo);
        int radioSelectedId = rGrpTipo.getCheckedRadioButtonId();

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        int rating = ratingBar.getNumStars();
        if( txtDesc.getText().toString().equals("") ){
            txtDesc.setError(getResources().getString(R.string.campo_obrigatorio, getResources().getString(R.string.descricao)) );
            return;
        }
        if( radioSelectedId == -1 ){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.campo_obrigatorio, getResources().getString(R.string.tipo)), Toast.LENGTH_SHORT).show();
            return;
        }
        if(rating == -1){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.campo_obrigatorio, getResources().getString(R.string.rating)), Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rGrpTipoRadioButtonSelected = (RadioButton) findViewById(radioSelectedId);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                geoLatitude=location.getLatitude();
                geoLongitude=location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        Intent intent = new Intent(this, MainActivity.class);

        Food a = new Food();

        a.setDesc(txtDesc.getText().toString());
        a.setTipo(rGrpTipoRadioButtonSelected.getText().toString());
        a.setRate(rating);
        a.setImagem(caminhoImagemSalva);
        a.setGeoLatitude(geoLatitude);
        a.setGeoLongitude(geoLongitude);

        ArrayList<Food> lista = Food.list(this.getApplicationContext());

        adaptador = new AdapterFood(lista);

        if(a.save(this.getApplicationContext()) == -1){
            Toast.makeText(AdicionarActivity.this, "Erro ao Salvar", Toast.LENGTH_SHORT).show();
        }else{
            intent.putExtra("salvo", "sim");
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                setResult(resultCode);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 3){
            if (resultCode == RESULT_OK) {
                ImageView imgFotografia = (ImageView) findViewById(R.id.imageView);
                imgFotografia.setImageURI(uriImagem);
                caminhoImagemSalva = uriImagem.getPath();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String caminhoImagemSalva = null;
    private Uri uriImagem;

    public void onClickTirarFoto(View v){
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File pastaImagem = new File(Environment.getExternalStorageDirectory(), "Food");
        if(!pastaImagem.isDirectory()){
            pastaImagem.mkdirs();
        }
        long milissegundos = System.currentTimeMillis();
        File arquivoImagem = new File(pastaImagem, milissegundos+".jpg");
        uriImagem = Uri.fromFile(arquivoImagem);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagem);
        startActivityForResult(imageIntent,3);
    }
}