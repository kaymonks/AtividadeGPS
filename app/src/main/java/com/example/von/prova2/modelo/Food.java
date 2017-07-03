package com.example.von.prova2.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.von.prova2.helper.MyDbHelper;

import java.util.ArrayList;

public class Food implements Parcelable{

    private String desc;
    private String imagem;
    private int rate;
    private String tipo;
    private int id;
    private double geoLatitude;
    private double geoLongitude;

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Integer getRate() {
        return rate;
    }
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String desc) {
        this.tipo = tipo;
    }

    public double getGeoLatitude() {
        return geoLatitude;
    }
    public void setGeoLatitude(double geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public double getGeoLongitude() {
        return geoLongitude;
    }
    public void setGeoLongitude(double geoLongitude) {
        this.geoLongitude = geoLongitude;
    }

    public Food(){

    }

    public Food(Parcel in){
        this.desc = in.readString();
        this.imagem = in.readString();
        this.id = in.readInt();
        this.rate = in.readInt();
        this.tipo = in.readString();
        this.setGeoLatitude(in.readDouble());
        this.setGeoLongitude(in .readDouble());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(desc);
        dest.writeString(imagem);
        dest.writeInt(id);
        dest.writeInt(rate);
        dest.writeString(tipo);
        dest.writeDouble(this.getGeoLatitude());
        dest.writeDouble(this.getGeoLongitude());
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public long save(Context context){
        MyDbHelper dbHelper = new MyDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues conteudo = new ContentValues();
        conteudo.put("descricao", this.getDesc());
        conteudo.put("imagem", this.getImagem());
        conteudo.put("rate", this.getRate());
        conteudo.put("tipo", this.getTipo());
        conteudo.put("geoLatitude", this.getGeoLatitude());
        conteudo.put("geoLongitude", this.getGeoLongitude());
        long insertId = db.insert("food", null, conteudo);
        this.setId((int) insertId);
        return insertId;
    }

    public static ArrayList<Food> list(Context context){
        ArrayList<Food> lista = new ArrayList<>();

        MyDbHelper dbHelper = new MyDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] selectedColumns = {"id", "descricao", "imagem", "rating", "tipo", "geoLatitude", "geoLongitude"};
        Cursor cursor = db.query("food", selectedColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Food p = new Food();
            p.setId(cursor.getInt(0));
            p.setDesc(cursor.getString(1));
            p.setImagem(cursor.getString(2));
            p.setRate(cursor.getInt(3));
            p.setTipo(cursor.getString(4));
            p.setGeoLatitude(cursor.getDouble(5));
            p.setGeoLongitude(cursor.getDouble(6));
            lista.add(p);
            cursor.moveToNext();
        }

        return lista;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
