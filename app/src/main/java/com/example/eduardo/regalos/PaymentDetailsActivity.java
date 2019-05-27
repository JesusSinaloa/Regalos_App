package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PaymentDetailsActivity extends AppCompatActivity {
    private TextView txtId, txtStatus;
    private ImageView ImgStatusPay;
    private Button btnClose;
    UserSessionManager session;
    //RETROFIT
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        setContentView(R.layout.activity_payment_details);

        //VINCULACION
        txtId = findViewById(R.id.txtId);
        txtStatus = findViewById(R.id.txtStatus);
        ImgStatusPay = findViewById(R.id.imageViewStatusPay);
        btnClose = findViewById(R.id.btnClose);

        //GET INTENT
        Intent inRecuperar = getIntent();
        //SESSION USUARIO
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDEtails();
        String stremail;
        stremail = user.get(UserSessionManager.KEY_EMAIL);

        //INICIAR API DE RETROFIT
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        try{
            JSONObject jsonObject = new JSONObject(inRecuperar.getStringExtra("PaymentDetails"));
            JSONObject response = jsonObject.getJSONObject("response");
            String idCompra = response.getString("id");


            //RECUPERAR DATOS
            for (Item p : Cart.contents()) {//RECORREMOS LA LISTA
                /*Toast.makeText(getApplicationContext(), "nombre : " + p.getProduct().getNombre(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "precio : " + p.getProduct().getPrecio(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "id producto :" + p.getProduct().getIdProducto(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "cantidad : " + p.getCantidad(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "id compra : " + response.getString("id"), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "usuario : " + stremail, Toast.LENGTH_SHORT).show();*/


                String nombre = p.getProduct().getNombre();
                String imagen = p.getProduct().getImage();
                Double precio = Double.parseDouble(p.getProduct().getPrecio());
                int idProducto = p.getProduct().getIdProducto();
                int cantidad = p.getCantidad();

                orden(idCompra, nombre, precio, idProducto, cantidad, imagen);
            }
            compra(idCompra, stremail);
            limpiar_carrito();

            showDetails(jsonObject.getJSONObject("response"), inRecuperar.getStringExtra("PaymentAmount").toString());
        }catch (JSONException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent inHome = new Intent(PaymentDetailsActivity.this, Principal.class);
                startActivity(inHome);

            }
        });
    }

    private void orden(String idCompra, String nombre, Double precio, int idProducto, int cantidad, String imagen) {
        compositeDisposable.add(myAPI.orden(idCompra, idProducto, nombre, precio, cantidad, imagen)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //Toast.makeText(getApplicationContext(), " " + s, Toast.LENGTH_SHORT).show();

                    }
                })
        );
    }

    private void compra (String idCompra, String usuario){
        //Toast.makeText(getApplicationContext(), "compra: " + idCompra, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "usuario: " + usuario, Toast.LENGTH_SHORT).show();

        //AGREGAR COMPRA
        compositeDisposable.add(myAPI.compra(idCompra, usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //Toast.makeText(getApplicationContext(), " " + s, Toast.LENGTH_SHORT).show();

                    }
                })

        );
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try{
            if(response.getString("state").equals("approved")){
                ImgStatusPay.setImageResource(R.drawable.enable);
            }else{
                ImgStatusPay.setImageResource(R.drawable.disabled);
            }
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));

        }catch (JSONException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void limpiar_carrito(){
        Cart.clean();
    }

}
