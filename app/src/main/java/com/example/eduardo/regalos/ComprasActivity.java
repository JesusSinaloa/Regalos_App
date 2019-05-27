package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ComprasActivity extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserSessionManager session;
    ArrayList<Pay> lstPay;
    ListView lstPayView;


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
        setContentView(R.layout.activity_compras);

        //INICIAR API DE RETROFIT
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        //SESSIONES
        session = new UserSessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDEtails();
        String stremail;
        stremail = user.get(UserSessionManager.KEY_EMAIL);


        lstPayView = findViewById(R.id.lstViewCompras);

        CargarAdapter(recuperarDatos(stremail));

        lstPayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent inOrdenes = new Intent(ComprasActivity.this, DetalleComprasActivity.class);
                inOrdenes.putExtra("compra", (Serializable)lstPay.get(position));
                startActivity(inOrdenes);

            }
        });
    }

    private void CargarAdapter(ArrayList<Pay> lista) {

        //CREO ADAPTADOR
        Adapter_Pay miAdaptador = new Adapter_Pay(getApplicationContext(),R.layout.adapter_payments,lista);//LE MANDAMOS AL CONTRUCTOR DE LA CLASE EL CONTEXTO; LA LISTA Y ILAYOUT
        lstPayView.setAdapter(miAdaptador);




    }

    private ArrayList<Pay> recuperarDatos(String user) {
        lstPay = new ArrayList<>();
        compositeDisposable.add(myAPI.compras_data(user)
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
                                lstPay.add(new Pay(jresponse.getString("idCompra"), jresponse.getString("fechaCompra")));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
        return lstPay;
    }
}
