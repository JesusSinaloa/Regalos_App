package com.example.eduardo.regalos;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    //LISTAS GLOBALES
    List<Producto> listPapeles, listCajas, listProductos; //LISTA DE CLASE PRODUCTO
    //DECLARACION DE WIDGETS
    ViewPager viewPagerPapel, viewPagerCaja, viewPagerProducto; //VISTA PARA CADA SLIDER
    Adapter_Card adapterPapeles, adapterCajas, adapterProductos; //ADAPTADOR
    Button btnVerTodosProductos, btnVerTodosCasja, btnVerTodos;


    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //VISTA DEL FRAGMENTO
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //btnVerTodosProductos = rootView.findViewById(R.id.btnViewAllProducto);
        //btnVerTodosCasja = rootView.findViewById(R.id.btnViewAllCajas);
        btnVerTodos = rootView.findViewById(R.id.btnViewAll);




        //MANDAR LLAMAR AL METODO QUE RECUPERA LOS DATOS
        recuperar_datos(rootView);

       /* btnVerTodosProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inAllProducts = new Intent(getActivity().getApplicationContext(), Ver_Todos.class);
                inAllProducts.putExtra("type", "producto");
                startActivity(inAllProducts);
            }
        });
        btnVerTodosEnvolturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inAllEnvolturas = new Intent(getActivity().getApplicationContext(), Ver_Todos.class);
                inAllEnvolturas.putExtra("type", "envoltura");
                startActivity(inAllEnvolturas);
            }
        });*/
        btnVerTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent inAllCajas = new Intent(getActivity().getApplicationContext(), Ver_Todos.class);
                inAllCajas.putExtra("type", "caja");
                startActivity(inAllCajas);*/
                Intent grid_subcat = new Intent(getActivity().getApplicationContext(), GridSubcat.class);
                startActivity(grid_subcat);
            }
        });



        // RETORNO LA VISTA YA CON EL ADAPTADOR EN ELLA
        return rootView;
    }

    private void recuperar_datos(final View rootView) {
        listPapeles = new ArrayList<>();
        listCajas = new ArrayList<>();
        listProductos = new ArrayList<>();
        //RECUPARAR DATOS DEL SERVIDOR
        final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());//INSTANCIA
        final String url = "http://10.8.17.249:3000/productos";//URL
        queue.start();

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){
                    JSONObject jresponse = null;
                    try {
                        jresponse = response.getJSONObject(i);
                        String categoria = jresponse.getString("idSubcategoria").toString();
                        //Toast.makeText(getActivity().getApplicationContext(), "Categoria: "+ jresponse.getString("idCategoria"), Toast.LENGTH_LONG).show();

                        if (categoria.equals("4") || categoria.equals("7")){
                            //Toast.makeText(getActivity().getApplicationContext(), "entro a 1", Toast.LENGTH_LONG).show();
                            listPapeles.add(new Producto(jresponse.getString("imagen"), jresponse.getInt("idProducto"), jresponse.getString("nombre").toString(), jresponse.getString("precio").toString(), jresponse.getInt("cantidad"), jresponse.getString("espc2").toString(), jresponse.getString("espc3").toString(), jresponse.getString("espc4").toString(), jresponse.getString("espc5").toString(), jresponse.getString("espc6").toString(), jresponse.getString("espc7").toString(), jresponse.getString("descripcion").toString()));
                            //Toast.makeText(getActivity().getApplicationContext(), "Agregando a papeles"+ jresponse.getString("nombre"), Toast.LENGTH_LONG).show();
                        }
                        if (categoria.equals("8") || categoria.equals("9") || categoria.equals("10")){
                            //Toast.makeText(getActivity().getApplicationContext(), "entro a 2", Toast.LENGTH_LONG).show();
                            listCajas.add(new Producto(jresponse.getString("imagen"), jresponse.getInt("idProducto"), jresponse.getString("nombre"), jresponse.getString("precio"), jresponse.getInt("cantidad"), jresponse.getString("espc2"), jresponse.getString("espc3"), jresponse.getString("espc4"), jresponse.getString("espc5"), jresponse.getString("espc6"), jresponse.getString("espc7"), jresponse.getString("descripcion")));
                            //Toast.makeText(getActivity().getApplicationContext(), "Agregando a cajas"+ jresponse.getString("nombre"), Toast.LENGTH_LONG).show();

                        }if (categoria.equals("11") || categoria.equals("3") || categoria.equals("5") || categoria.equals("6")) {
                            //Toast.makeText(getActivity().getApplicationContext(), "entro a 3", Toast.LENGTH_LONG).show();
                            listProductos.add(new Producto(jresponse.getString("imagen"), jresponse.getInt("idProducto"), jresponse.getString("nombre"), jresponse.getString("precio"), jresponse.getInt("cantidad"), jresponse.getString("espc2"), jresponse.getString("espc3"), jresponse.getString("espc4"), jresponse.getString("espc5"), jresponse.getString("espc6"), jresponse.getString("espc7"), jresponse.getString("descripcion")));
                            //Toast.makeText(getActivity().getApplicationContext(), "Agregando a productos"+ jresponse.getString("nombre"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                //CARGAR ADAPTADORES
                cargar_adaptador(rootView, listPapeles, listProductos, listCajas);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                for (int j=0;j<5 ;j++){
                    Toast.makeText(getActivity().getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                }
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void cargar_adaptador(View rootView, List<Producto> listPapeles, List<Producto> listProductos, List<Producto> listCajas) {
        //--------------------------------------PAPELES----------------------//
        //DECLARAMOS NUESTRO ADAPTADOR Y LE ENVIAMOS LA LISTA Y EL CONTEXTO

        adapterPapeles = new Adapter_Card(listPapeles, getContext());

        //VINCULAMOS LA VIEWPAGER Y LE COLOCAMOS EL ADAPTADOR
        viewPagerPapel = (ViewPager) rootView.findViewById(R.id.viewPagerPapel);
        viewPagerPapel.setAdapter(adapterPapeles);
        viewPagerPapel.setPadding(50, 0, 50, 0);//PADDING PARA QUE NO SE GUNTEN LAS TARJETAS
        //EVENTO PARA HACER EL SLIDER
        viewPagerPapel.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });



        //---------------------------------------CAJAS-------------------------------------//
        //DECLARAMOS NUESTRO ADAPTADOR Y LE ENVIAMOS LA LISTA Y EL CONTEXTO
        adapterCajas = new Adapter_Card(listCajas, getContext());

        //VINCULAMOS LA VIEWPAGER Y LE COLOCAMOS EL ADAPTADOR
        viewPagerCaja = (ViewPager)rootView.findViewById(R.id.viewPagerCajas);
        viewPagerCaja.setAdapter(adapterCajas);
        viewPagerCaja.setPadding(50, 0, 50, 0);//PADDING PARA QUE NO SE GUNTEN LAS TARJETAS

        //EVENTO PARA HACER EL SLIDER
        viewPagerCaja.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //--------------------------------------------PRODUCTO-------------------------------//

        //DECLARAMOS NUESTRO ADAPTADOR Y LE ENVIAMOS LA LISTA Y EL CONTEXTO
        adapterProductos = new Adapter_Card(listProductos, getContext());

        //VINCULAMOS LA VIEWPAGER Y LE COLOCAMOS EL ADAPTADOR
        viewPagerProducto = (ViewPager)rootView.findViewById(R.id.viewPagerProducto);
        viewPagerProducto.setAdapter(adapterProductos);
        viewPagerProducto.setPadding(50, 0, 50, 0);//PADDING PARA QUE NO SE GUNTEN LAS TARJETAS

        //EVENTO PARA HACER EL SLIDER
        viewPagerProducto.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //----------------------------------------------------FIN MOSTRAR TARJETAS-------------------------------------------//
    }
}


