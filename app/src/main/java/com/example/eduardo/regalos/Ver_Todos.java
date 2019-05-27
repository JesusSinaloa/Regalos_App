package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Ver_Todos extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ArrayList<Producto> lstProductos;
    ListView lstProductosView;


    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_todos);

        //INICIAR API DE RETROFIT
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        lstProductosView = findViewById(R.id.ListViewTodos);



        String type = getIntent().getStringExtra("type");
        if (type.equals("Libros")){
            CargarAdapter(recuperarDatos("4"));
        }
        if (type.equals("Zapatos")){
            CargarAdapter(recuperarDatos("3"));
        }
        if (type.equals("Belleza")){
            CargarAdapter(recuperarDatos("5"));
        }
        if (type.equals("Moda")){
            CargarAdapter(recuperarDatos("6"));
        }
        if (type.equals("Fotografia")){
            CargarAdapter(recuperarDatos("8"));
        }
        if (type.equals("Celulares")){
            CargarAdapter(recuperarDatos("9"));
        }
        if (type.equals("Computadoras")){
            CargarAdapter(recuperarDatos("10"));
        }
        if (type.equals("Ropa")){
            CargarAdapter(recuperarDatos("11"));
        }
        if (type.equals("Otros")){
            CargarAdapter(recuperarDatos("2"));
        }
        if (type.equals("Videojuegos")){
            CargarAdapter(recuperarDatos("7"));
        }


        lstProductosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idProducto = Long.toString(lstProductosView.getItemIdAtPosition(position));
                Intent inProducto = new Intent(Ver_Todos.this, DetalleProductoActivity.class);
                inProducto.putExtra("producto", (Serializable)lstProductos.get(position));
                //inProducto.putExtra("idProducto", idProducto);
                startActivity(inProducto);

            }
        });

    }

    private void CargarAdapter(ArrayList<Producto> lista) {

        //CREO ADAPTADOR
        Adapter_Ver_Todos miAdaptador = new Adapter_Ver_Todos(getApplicationContext(),R.layout.ver_todos,lista);//LE MANDAMOS AL CONTRUCTOR DE LA CLASE EL CONTEXTO; LA LISTA Y ILAYOUT
        lstProductosView.setAdapter(miAdaptador);




    }

    private ArrayList<Producto> recuperarDatos(String categoria) {
        lstProductos = new ArrayList<>();
        compositeDisposable.add(myAPI.productossubcat(categoria)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        JSONArray jsonArray = new JSONArray(s);
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jresponse = null;
                            try {
                                jresponse = jsonArray.getJSONObject(i);
                                lstProductos.add(new Producto(jresponse.getString("imagen"), jresponse.getInt("idProducto"), jresponse.getString("nombre").toString(), jresponse.getString("precio").toString(), jresponse.getInt("cantidad"), jresponse.getString("espc2").toString(), jresponse.getString("espc3").toString(), jresponse.getString("espc4").toString(), jresponse.getString("espc5").toString(), jresponse.getString("espc6").toString(), jresponse.getString("espc7").toString(), jresponse.getString("descripcion").toString()));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
        return lstProductos;
    }


}
