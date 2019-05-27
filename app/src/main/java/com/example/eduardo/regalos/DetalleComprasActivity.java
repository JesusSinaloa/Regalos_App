package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DetalleComprasActivity extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView idPay, fechaPay;
    ListView lstViewProductos;
    ArrayList<Producto> lstProductos;
    ArrayList<String> images = new ArrayList<String>();

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
        setContentView(R.layout.activity_detalle_compras);
        //VINCULACION
        idPay = findViewById(R.id.textViewIDPayDetail);
        fechaPay = findViewById(R.id.textViewFechaPayDetail);
        lstViewProductos = findViewById(R.id.lstViewProductosPayDetail);

        //INICIAR API DE RETROFIT
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //RECUPERA LA ARRAY LIST ENVIADA EN EL EXTRA DESDE LA ACTIVIDAD PRINCIPAL
        final Pay objetoPay = (Pay) getIntent().getSerializableExtra("compra");

        idPay.setText(objetoPay.getIdPay());
        fechaPay.setText(objetoPay.getFechaPago());

        CargarAdapter(recuperarDatos(objetoPay.getIdPay()));

        //Toast.makeText(getApplicationContext(), ""+ objetoPay.getIdPay() + objetoPay.getFechaPago(), Toast.LENGTH_SHORT).show();
    }

    private void CargarAdapter(ArrayList<Producto> lista) {

        //CREO ADAPTADOR
        Adapter_Pay_Details miAdaptador = new Adapter_Pay_Details(getApplicationContext(),R.layout.adapter_detaills_pay,lista);//LE MANDAMOS AL CONTRUCTOR DE LA CLASE EL CONTEXTO; LA LISTA Y ILAYOUT
        lstViewProductos.setAdapter(miAdaptador);

    }

    private ArrayList<Producto> recuperarDatos(String compra) {
        lstProductos = new ArrayList<>();
        compositeDisposable.add(myAPI.ordenes_compra(compra)
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

                                lstProductos.add(new Producto(jresponse.getString("imagen"), 0, jresponse.getString("nombre_producto").toString(), jresponse.getString("precioProducto").toString(), jresponse.getInt("cantidadProducto"), "", "", "", "", "", "", ""));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
        return lstProductos;
    }


}
