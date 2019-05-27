package com.example.eduardo.regalos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;

public class CartActivity extends AppCompatActivity {
    private ListView lstCartProducts;
    FloatingActionButton btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //VINCULACION
        lstCartProducts = findViewById(R.id.lstViewCart);
        btnPay = findViewById(R.id.floatingActionButtonPay);
        final String status = getIntent().getStringExtra("status");
        if(status.equals("preparacion")) {
            btnPay.setImageResource(R.drawable.present2);
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inPay = new Intent(CartActivity.this, PrepararActivity.class);
                    startActivity(inPay);
                }
            });
        }else{
            if(status.equals("listo")){
                btnPay.setImageResource(R.drawable.paypal);
                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inPay = new Intent(CartActivity.this, PayActivity.class);
                        startActivity(inPay);
                    }
                });
            }
        }

        addCart();
        cargarAdaptador();

    }

    private void addCart(){
        try{
            //RECUPERA LA ARRAY LIST ENVIADA CUAANDO AÑADEN AL CARRITO
            final Producto objetoProducto = (Producto) getIntent().getSerializableExtra("producto");
            if(objetoProducto != null){
                //Toast.makeText(getApplicationContext(), "" + objetoProducto.getNombre(), Toast.LENGTH_SHORT).show();
                if (!Cart.isExist(objetoProducto)) {
                    Cart.insert(new Item(objetoProducto, 1));
                    //Toast.makeText(getApplicationContext(), "Se agrego", Toast.LENGTH_SHORT).show();
                } else {
                    Cart.update(objetoProducto);
                }
            }else{

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void cargarAdaptador() {
        Adapter_Cart miAdaptador = new Adapter_Cart(getApplicationContext(),R.layout.item_cart,Cart.contents());//LE MANDAMOS AL CONTRUCTOR DE LA CLASE EL CONTEXTO; LA LISTA Y ILAYOUT
        lstCartProducts.setAdapter(miAdaptador);
    }


}
