package com.example.eduardo.regalos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Adapter_Card extends PagerAdapter {
    //LSITA DE OBJETOS DE LA CLASE PRODUCTOS
    private List<Producto> productos;
    //LAYOUT CON EL QUE VA CONTRUIR (ITEM)
    private LayoutInflater layoutInflater;
    //CONTEXTO DE LA APLICACION
    private Context context;

    public Adapter_Card(List<Producto> productos, Context context) {
        this.productos = productos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productos.size();//RETORNO EL TAMAÑO DE LA LISTA
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        //NOS PERMITE CREAR LA LISTA EN BASE ALA VISTA
        layoutInflater = LayoutInflater.from(context);
        //OBTENEMOS LA VISTA PARA TRABAJAR SOBRE ELLA
        View view = layoutInflater.inflate(R.layout.item, container, false);

        //DECLARACION DE WIDGETS Y VARIABLES
        ImageView imagen;
        TextView nombre, precio, descripcion;
        Button btnAddCart;
        String nombre_imagen = "";
        String URL = "https://tetragex.com/Regalos/";
        //VINCULACION DE WIDGETS Y ASIGNACION DE VTIABLES
        imagen = view.findViewById(R.id.imgProductAll);
        nombre = view.findViewById(R.id.textViewNombreAll);
        precio = view.findViewById(R.id.textViewPrecioAll);
        descripcion = view.findViewById(R.id.description);
        btnAddCart = view.findViewById(R.id.btnAddCart);
        //NOMBRE DE LA IMAGEN PARA PEDIRLA AL SERVIDOR
        nombre_imagen = productos.get(position).getImage().toString();
        String Uri = URL + nombre_imagen;
        //LENAMOS LA VISTA
        Picasso.with(context)//IMAGEN CON PICASSO
                .load(Uri)
                .into(imagen);
        nombre.setText(productos.get(position).getNombre());
        precio.setText(productos.get(position).getPrecio());
        descripcion.setText(productos.get(position).getDescripcion());
        //EVENTO CLICK PARA EL DETALLE DEL PRODUCTO
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inDetalles = new Intent(context, DetalleProductoActivity.class);
                inDetalles.putExtra("producto", (Serializable)productos.get(position));
                context.startActivity(inDetalles);
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inCart = new Intent(context, CartActivity.class);
                inCart.putExtra("producto", (Serializable)productos.get(position));
                inCart.putExtra("status", "preparacion");
                context.startActivity(inCart);
            }
        });

        //AÑADIMOS LA VISTA y LA REGRESAMOS
        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public void refreshEvents(List<Producto> listPapeles) {
        listPapeles.clear();
        listPapeles.addAll(listPapeles);
        notifyDataSetChanged();
    }
}
