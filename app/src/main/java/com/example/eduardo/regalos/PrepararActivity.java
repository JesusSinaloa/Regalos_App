package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class PrepararActivity extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ArrayList<Producto> lstProductos;
    ListView lstViewPreparar;
    FloatingActionButton pass;

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
        setContentView(R.layout.activity_preparar);
        //VINVULACION
        lstViewPreparar = findViewById(R.id.lstViewPreparar);
        pass = findViewById(R.id.floatingActionButtonPass);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        CargarAdapter(recuperarDatos("2"));

        lstViewPreparar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cart.insert(new Item(lstProductos.get(position), 1));
                Intent inPrep = new Intent(PrepararActivity.this, PrepararActivity2.class);
                startActivity(inPrep);


            }
        });
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inPrep = new Intent(PrepararActivity.this, PrepararActivity2.class);
                startActivity(inPrep);
            }
        });
    }

    private void CargarAdapter(ArrayList<Producto> lista) {

        //CREO ADAPTADOR
        Adapter_Ver_Todos miAdaptador = new Adapter_Ver_Todos(getApplicationContext(),R.layout.ver_todos,lista);//LE MANDAMOS AL CONTRUCTOR DE LA CLASE EL CONTEXTO; LA LISTA Y ILAYOUT
        lstViewPreparar.setAdapter(miAdaptador);




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
