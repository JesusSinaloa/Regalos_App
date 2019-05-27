package com.example.eduardo.regalos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Pay_Details extends ArrayAdapter {
    Context contexto;//CONTEXTO DE LA APLICACION
    int iLayout;
    List<Producto> ListProductos;//LISTA DE OBJETOS DE LA CLASE PRODUCTO
    String nombre_imagen = "";
    String URL = "https://tetragex.com/Regalos/";


    //CONTRUCTOR QUE RECIBIRA LOS DATOS DE LA LISTA
    public Adapter_Pay_Details(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        contexto = context;
        iLayout = resource;
        ListProductos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vFila = convertView;
        //DECLARACION DE WIDGETS
        TextView nombre, precio, cantidad;
        ImageView imgProducto;


        if (vFila == null){//SI NO EXISTE LA FILA HAY QUE CREARLA
            //ESTE OBJETO NOS PERMITE CREAR LA LISTA
            vFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detaills_pay, null, false);

        }
        //VINCULAR WIDGETS
        nombre= vFila.findViewById(R.id.textViewNombreADP);
        precio = vFila.findViewById(R.id.textViewPrecioADP);
        cantidad = vFila.findViewById(R.id.textViewCantidadADP);
        imgProducto = vFila.findViewById(R.id.imageViewImagenADP);

        //NOMBRE DE LA IMAGEN PARA PEDIRLA AL SERVIDOR
        nombre_imagen = ListProductos.get(position).getImage().toString();
        final String Uri = URL + nombre_imagen;
        //LENAMOS LA VISTA
        Picasso.with(contexto)//IMAGEN CON PICASSO
                .load(Uri)
                .into(imgProducto);




        //LLENAR DATOS
        nombre.setText(ListProductos.get(position).getNombre());
        precio.setText(ListProductos.get(position).getPrecio());
        cantidad.setText(String.valueOf(ListProductos.get(position).getCantidad()));


        return vFila;
    }
}
