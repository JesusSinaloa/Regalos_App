package com.example.eduardo.regalos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Adapter_Cart extends ArrayAdapter {
    Context contexto;//CONTEXTO DE LA APLICACION
    int iLayout;
    List<Item> ListProductos;//LISTA DE OBJETOS DE LA CLASE PRODUCTO
    String nombre_imagen = "";
    String URL = "https://tetragex.com/Regalos/";


    public Adapter_Cart(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        contexto = context;
        iLayout = resource;
        ListProductos = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vFila = convertView;
        //DECLARACION DE WIDGETS
        TextView nombre, precio, cantidad;
        ImageView imgProducto;
        ImageButton imgViewDec, imgViewInc, imgViewDelete;

        if (vFila == null){//SI NO EXISTE LA FILA HAY QUE CREARLA
            //ESTE OBJETO NOS PERMITE CREAR LA LISTA
            vFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, null, false);

        }
        //VINCULAR WIDGETS
        nombre = vFila.findViewById(R.id.textViewNombreCart);
        precio = vFila.findViewById(R.id.textViewPrecioCart);
        cantidad = vFila.findViewById(R.id.textViewCantidadCart);
        imgProducto = vFila.findViewById(R.id.imageViewCart);
        imgViewDec = vFila.findViewById(R.id.imageButtonDecCart);
        imgViewInc = vFila.findViewById(R.id.imageButtonIncCart);
        imgViewDelete = vFila.findViewById(R.id.imageButtonDeleteCart);

        //NOMBRE DE LA IMAGEN PARA PEDIRLA AL SERVIDOR
        nombre_imagen = ListProductos.get(position).getProduct().getImage().toString();
        final String Uri = URL + nombre_imagen;
        //LENAMOS LA VISTA
        Picasso.with(contexto)//IMAGEN CON PICASSO
                .load(Uri)
                .into(imgProducto);
        //LLENAR DATOS
        String strnombre = ListProductos.get(position).getProduct().getNombre();
        if(strnombre.length() > 25){
            nombre.setText(strnombre.substring(0,25));
        }else{
            nombre.setText(strnombre);
        }
        precio.setText(ListProductos.get(position).getProduct().getPrecio());
        String strcantidad = (String.valueOf(ListProductos.get(position).getCantidad()));
        cantidad.setText(strcantidad);

        imgViewDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(contexto, "Decrementando cantidad", Toast.LENGTH_SHORT).show();
                Cart.rest(ListProductos.get(position).getProduct());
                notifyDataSetChanged();
            }
        });

        imgViewInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(contexto, "Incrementando cantidad", Toast.LENGTH_SHORT).show();
                Cart.add(ListProductos.get(position).getProduct());
                notifyDataSetChanged();
            }
        });

        imgViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.remove(ListProductos.get(position).getProduct());
                notifyDataSetChanged();

            }
        });



        return vFila;
    }
}
