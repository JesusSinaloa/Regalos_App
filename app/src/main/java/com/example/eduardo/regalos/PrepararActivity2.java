package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PrepararActivity2 extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ArrayList<Producto> lstProductos;
    ListView lstViewPreparar2;
    FloatingActionButton pass2;


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
        setContentView(R.layout.activity_preparar2);

        //VINVULACION
        lstViewPreparar2 = findViewById(R.id.lstViewPreparar2);
        pass2 = findViewById(R.id.floatingActionButtonPass2);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        CargarAdapter(recuperarDatos("1"));

        lstViewPreparar2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent inCart = new Intent(PrepararActivity2.this, CartActivity.class);
                inCart.putExtra("producto", (Serializable)lstProductos.get(position));
                inCart.putExtra("status", "listo");
                startActivity(inCart);


            }
        });
        pass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inCart = new Intent(PrepararActivity2.this, CartActivity.class);
                inCart.putExtra("status", "listo");
                startActivity(inCart);
            }
        });
    }

    private void CargarAdapter(ArrayList<Producto> lista) {

        //CREO ADAPTADOR
        Adapter_Ver_Todos miAdaptador = new Adapter_Ver_Todos(getApplicationContext(),R.layout.ver_todos,lista);//LE MANDAMOS AL CONTRUCTOR DE LA CLASE EL CONTEXTO; LA LISTA Y ILAYOUT
        lstViewPreparar2.setAdapter(miAdaptador);




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
