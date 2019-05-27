package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DetalleProductoActivity extends AppCompatActivity {
    //DECLARACION DE WIDGETS
    CarouselView carouselView;
    //ImageView imageViewProdcuto;
    TextView nombre, precio, espc1, espc2, espc3, espc4, espc5, espc6, espc7, descripcion;
    Button btnAddCart;
    private String[] Images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

      //VINCULACION DE GIDGETS
      //imageViewProdcuto = findViewById(R.id.imageViewProductoAD);
      nombre = findViewById(R.id.textViewNombreAD);
      precio = findViewById(R.id.textViewPrecioAD);
        espc1 = findViewById(R.id.textViewespc1AD);
        espc2 = findViewById(R.id.textViewespc2AD);
        espc3 = findViewById(R.id.textViewespc3AD);
        espc4 = findViewById(R.id.textViewespc4AD);
        espc5 = findViewById(R.id.textViewespc5AD);
        espc6 = findViewById(R.id.textViewespc6AD);
        espc7 = findViewById(R.id.textViewespc7AD);
        descripcion = findViewById(R.id.textViewDescripcionAD);
        btnAddCart = findViewById(R.id.btnAddCartDetalle);

        //RECUPERA LA ARRAY LIST ENVIADA EN EL EXTRA DESDE LA ACTIVIDAD PRINCIPAL
        final Producto objetoProducto = (Producto) getIntent().getSerializableExtra("producto");

    if (objetoProducto != null){

        String URL = "https://tetragex.com/Regalos/";
        String nombre_imagen = objetoProducto.getImage().toString();
        final String Uri = URL + nombre_imagen;
        Images = new String[]{Uri, Uri, Uri, Uri};
        //Carousel
        carouselView = (CarouselView) findViewById(R.id.imageViewProductoAD);
        carouselView.setPageCount(Images.length);
        carouselView.setImageListener(imageListener);

        descripcion.setText(objetoProducto.getDescripcion());
        nombre.setText(objetoProducto.getNombre().toString());
        precio.setText("$" + NumberFormat.getInstance().format(Double.parseDouble(objetoProducto.getPrecio().toString())));
        espc1.setText(Integer.toString(objetoProducto.getCantidad()));
        espc2.setText(objetoProducto.getEspc2().toString());
        espc3.setText(objetoProducto.getEspc3().toString());
        espc4.setText(objetoProducto.getEspc4().toString());
        espc5.setText(objetoProducto.getEspc5().toString());
        espc6.setText(objetoProducto.getEspc6().toString());
        espc7.setText(objetoProducto.getEspc7().toString());

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inCart = new Intent(getApplicationContext(), CartActivity.class);
                inCart.putExtra("producto", (Serializable)objetoProducto);
                inCart.putExtra("status", "preparacion");
                startActivity(inCart);
            }
        });
    }else{
        Toast.makeText(getApplicationContext(), "No se puede obtener el objeto", Toast.LENGTH_SHORT).show();
        }
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            //PICASSO IMAGEN
            //LENAMOS LA VISTA
            Picasso.with(getApplicationContext())//IMAGEN CON PICASSO
                    .load(Images[position])
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }
    };
}
