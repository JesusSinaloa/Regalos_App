package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PerfilActivity extends AppCompatActivity {
    UserSessionManager session;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView username, txtnombre, txtapellidos, txtcorreo, txtcompra0, txtcompra1, txtcompra2;
    private ImageButton btnLogout;
    private Button btneditPersonal, btnviewPays;


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
        setContentView(R.layout.activity_perfil);
        session = new UserSessionManager(getApplicationContext());
        if(!session.checkLogin()) {
            //VINCULAR WIDGETS
            btnLogout = findViewById(R.id.btnLogout);
            username = findViewById(R.id.textViewNameUser);
            txtnombre = findViewById(R.id.textViewNombresDP);
            txtapellidos = findViewById(R.id.textViewApellidosDP);
            txtcorreo = findViewById(R.id.textViewCorreoDP);
            txtcompra0 = findViewById(R.id.textViewCompra1DP);
            txtcompra1 = findViewById(R.id.textViewCompra2DP);
            txtcompra2 = findViewById(R.id.textViewCompra3DP);
            btneditPersonal = findViewById(R.id.buttonEditPersonalesDP);
            btnviewPays = findViewById(R.id.buttonViewPays);
            //SESSION USUARIO
            HashMap<String, String> user = session.getUserDEtails();
            String stremail, nombre, apellidos, iduser;
            stremail = user.get(UserSessionManager.KEY_EMAIL);
            nombre = user.get(UserSessionManager.KEY_NAME);
            apellidos = user.get(UserSessionManager.KEY_APELLIDO);
            iduser = user.get(UserSessionManager.KEY_IDUSER);
            username.setText(nombre.toString() + " " + apellidos.toString());

            //INICIAR API DE RETROFIT
            Retrofit retrofit = RetrofitClient.getInstance();
            myAPI = retrofit.create(INodeJS.class);


            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    session.logoutUser();
                    finish();
                }
            });

            datos_usuario(stremail);
            datos_compra(stremail);

            btneditPersonal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inDatos = new Intent(PerfilActivity.this, PersonalDataActivity.class);
                    HashMap<String, String> userData = session.getUserDEtails();
                    inDatos.putExtra("nombre", userData.get(UserSessionManager.KEY_NAME));
                    inDatos.putExtra("apellido", userData.get(UserSessionManager.KEY_APELLIDO));
                    inDatos.putExtra("email", userData.get(UserSessionManager.KEY_EMAIL));
                    startActivity(inDatos);
                }
            });

            btnviewPays.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inPays = new Intent(PerfilActivity.this, ComprasActivity.class);
                    startActivity(inPays);
                }
            });
        }else{
            finish();
        }

    }

    private void datos_usuario(final String stremail) {
        compositeDisposable.add(myAPI.user_data(stremail)
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
                                String nombre = jresponse.getString("nombres");
                                String apellidos = jresponse.getString("apellidos");
                                String correo = jresponse.getString("correo");
                                int idUser = jresponse.getInt("idUsuario");
                                txtnombre.setText(nombre);
                                txtapellidos.setText(apellidos);
                                txtcorreo.setText(correo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
    }

    private void datos_compra(final String user){
        compositeDisposable.add(myAPI.compras_data(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        if(s.isEmpty()){
                            txtcompra0.setText("No hay compras");
                            txtcompra1.setText("No hay compras");
                            txtcompra2.setText("No hay compras");
                            btnviewPays.setVisibility(View.INVISIBLE);
                        }else{
                            JSONArray jsonArray = new JSONArray(s);
                            String[] array = new String[3];
                            for (int i = 0; i < 3; i++) {
                                JSONObject jresponse = null;
                                try {
                                    jresponse = jsonArray.getJSONObject(i);
                                    String idCompra = jresponse.getString("idCompra");
                                    //Toast.makeText(getApplicationContext(), ""+idCompra, Toast.LENGTH_SHORT).show();
                                    array[i] = idCompra;

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }

                            txtcompra0.setText(array[0]);
                            txtcompra1.setText(array[1]);
                            txtcompra2.setText(array[2]);
                        }

                    }
                }));
    }
}
