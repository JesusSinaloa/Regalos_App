package com.example.eduardo.regalos;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment {


    private TextInputEditText email, password;
    private Button singup, singin;
    private LinearLayout viewLogin;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserSessionManager session;

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


    public Perfil() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        //INICIAR API DE RETROFIT
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        email = (TextInputEditText) rootView.findViewById(R.id.emailPerfil);
        password = (TextInputEditText) rootView.findViewById(R.id.passwordPerfil);
        singin = (Button) rootView.findViewById(R.id.btnSingIn);
        singup = (Button) rootView.findViewById(R.id.btnSingUp);
        viewLogin = rootView.findViewById(R.id.LoginView);

        //SESSIONES
        session = new UserSessionManager(getActivity().getApplicationContext());
        Toast.makeText(getActivity().getApplicationContext(), "User Login Status: " + session.isUserLoggedIn(), Toast.LENGTH_SHORT).show();
        if(!session.checkLogin()){
            //ESTABLECER DATOS DE SESSION

            Intent inPerfil = new Intent(getActivity().getApplicationContext(), PerfilActivity.class);
            startActivity(inPerfil);
        }

        //EVENTOS
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user(email.getText().toString(), password.getText().toString());

            }
        });
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_user();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private void login_user(final String stremail, String password) {
        compositeDisposable.add(myAPI.loginUser(stremail, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (s.contains("Success")){
                    Toast.makeText(getActivity().getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    establecer_session(stremail);
                    //session.createUserLoginSession(stremail);
                    //Intent inPerfil = new Intent(getActivity().getApplicationContext(), PerfilActivity.class);
                    //startActivity(inPerfil);

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), ""+s, Toast.LENGTH_SHORT).show();
                }
             }
        }));

    }

    private void establecer_session(final String stremail) {
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
                                int idUser = jresponse.getInt("idUsuario");
                                //Toast.makeText(getActivity().getApplicationContext(), "Datos: " + nombre + apellidos + String.valueOf(idUser).toString(), Toast.LENGTH_SHORT).show();
                                session.createUserLoginSession(stremail, nombre, apellidos, String.valueOf(idUser).toString());
                                Intent inPerfil = new Intent(getActivity().getApplicationContext(), PerfilActivity.class);
                                startActivity(inPerfil);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
    }

    private void register_user(){

           Intent inRegister = new Intent(getActivity().getApplicationContext(), RegisterActivity.class);
           startActivity(inRegister);


    }

}
