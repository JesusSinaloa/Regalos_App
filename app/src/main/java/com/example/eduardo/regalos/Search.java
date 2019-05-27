package com.example.eduardo.regalos;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {
    List<Producto> datos;
    ListView listView;
    Adapter_Search miAdaptadorProductos;
    private TextInputEditText search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    //VISTA DEL FRAGMENTO
    View ViewSearch = inflater.inflate(R.layout.fragment_search, container, false);
    //VINCULAR WIDGETS
    search = ViewSearch.findViewById(R.id.searchText);
    listView = ViewSearch.findViewById(R.id.lstViewSearch);
    //MANDAR LLAMAR AL METODO QUE RECUPERA LOS DATOS
    recuperar_datos();

    search.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().equals("")){
                //RESET LIST
               miAdaptadorProductos.filter("");
               listView.clearTextFilter();

            }else{
               miAdaptadorProductos.filter(String.valueOf(s));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idProducto = Long.toString(listView.getItemIdAtPosition(position));
                Intent inProducto = new Intent(getActivity().getApplicationContext(), DetalleProductoActivity.class);
                inProducto.putExtra("producto", (Serializable)datos.get(position));
                //inProducto.putExtra("idProducto", idProducto);
                startActivity(inProducto);

            }
        });

    return ViewSearch;
    }

    private void searchItem(String textsearch) {
      int index = 0;
        for (Producto p : datos) {//RECORREMOS LA LISTA
            if(!p.getNombre().toString().equals(textsearch)){
                datos.remove(index);
            }
            index++;
        }
       miAdaptadorProductos.notifyDataSetChanged();
    }

    private void recuperar_datos() {
        datos = new ArrayList<>();
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
                        datos.add(new Producto(jresponse.getString("imagen"), jresponse.getInt("idProducto"), jresponse.getString("nombre").toString(), jresponse.getString("precio").toString(), jresponse.getInt("cantidad"), jresponse.getString("espc2").toString(), jresponse.getString("espc3").toString(), jresponse.getString("espc4").toString(), jresponse.getString("espc5").toString(), jresponse.getString("espc6").toString(), jresponse.getString("espc7").toString(), jresponse.getString("descripcion").toString()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //CARGAR ADAPTADORES
                cargar_adaptador(datos);
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

    private void cargar_adaptador(List<Producto> datos) {
        //DECLARAMOS NUESTRO ADAPTADOR Y LE ENVIAMOS LA LISTA Y EL CONTEXTO
         miAdaptadorProductos = new Adapter_Search(getActivity().getApplicationContext(),R.layout.fragment_search,datos);//LE MANDAMOS AL CONTRUCTOR DE LA CLASE EL CONTEXTO; LA LISTA Y ILAYOUT
        listView.setAdapter(miAdaptadorProductos);


            /*for (Producto p : datos) {//RECORREMOS LA LISTA
                Toast.makeText(getApplicationContext(), "Nombre producto" + p.getEan(), Toast.LENGTH_SHORT).show();
            }*/

    }

}
