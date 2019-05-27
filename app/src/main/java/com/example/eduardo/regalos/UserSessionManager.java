package com.example.eduardo.regalos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

public class UserSessionManager {
    //VARAIBLE DE PREFERENCIA COMPARTIDA
    SharedPreferences pref;
    //EDITOR
    SharedPreferences.Editor editor;
    //CONTEXTO
    Context _context;
    //MODO
    int PRIVATE_MODE = 0;

    //NOMBRE DEL ARCHIVO
    private static final String PREFER_NAME = "AndroidExamplePref";

    //SESION USUARIO
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    //DATOS USUARIO
        //NOMBRE USUARIO
        public static final String KEY_NAME = "name";
        //EMAIL USUARIO
        public static final String KEY_EMAIL = "email";

        public static final String KEY_APELLIDO = "apellidos";

        public static  final String KEY_IDUSER = "iduser";

    //COSNTRUCTOR
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    //METODO PARA INICIAR SESSION
    public  void createUserLoginSession (String email, String nombres, String apellidos, String iduser){
        //PONER EL VALOR DE LA SESSION EN TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        //GUARDAR NOMBRE
        editor.putString(KEY_NAME, nombres);

        //GUARDAR EMAIL
        editor.putString(KEY_EMAIL, email);

        //GUARDAR APELLIDOS
        editor.putString(KEY_APELLIDO, apellidos);
        //GUARDAR ID USUARIO
        editor.putString(KEY_IDUSER, iduser);
        //GUARDAR CAMBIOS
        editor.commit();
    }
    //METODO PARA CHECAR SESSION
    public  boolean checkLogin (){
        //VERIFICAR EL ESTATUS DE LA SESSION
        if (!this.isUserLoggedIn()){
            //SI NO ESTA LOGGEADO SE REDIRIJE AL INICIO DE SESSION
            //Intent inIniciar = new Intent(_context, Perfil.class);

            //SE CIERRAN TODAS LAS ACTIVIDADES DE LA PILA
            //inIniciar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //NUEVA BANDERA APRA NUEVA ACTIVIDAD
            //inIniciar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //INICIAR LA ACTIVIDAD
            //_context.startActivity(inIniciar);

            return true;
        }
        return false;
    }
    //METODO QUE VERIFICA LA SESSION
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    //METODO QUE DEVUELVE LOS DATOS DEL USUARIO
    public HashMap<String, String> getUserDEtails(){

        //USAMOS UN HASMAP PARA LAS CREDENCIALES
        HashMap<String, String> user = new HashMap<>();

        //NOMBRE USUARIO
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        //APELLIDOS
        user.put(KEY_APELLIDO, pref.getString(KEY_APELLIDO, null));

        //IDUSUARIO
        user.put(KEY_IDUSER, pref.getString(KEY_IDUSER, null));

        //EMIAL
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        return user;
    }

    //CERRAR SESSION
    public  void logoutUser(){
        //LIMPIAR TODOS LOS DATOS
        editor.clear();
        editor.commit();

        //REDIRECCIONAR A OTRA ACTIVIDAD
        //Intent inOut = new Intent(_context, Perfil.class);

        //CERRAR TODAS LAS DEMAS ACTIVIDADES
        //inOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //AÑADIR NUEVA BANDERA PAR LA NUEVA ACTIVIDAD
        //inOut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //INICAR ACTIVIDAD
        //_context.startActivity(inOut);
    }

}
