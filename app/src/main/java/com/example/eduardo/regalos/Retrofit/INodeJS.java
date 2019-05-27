package com.example.eduardo.regalos.Retrofit;

import java.util.Observable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJS {
    @POST("register")
    @FormUrlEncoded
    io.reactivex.Observable<String> registerUser(@Field("email") String email,
                                                 @Field("name") String name,
                                                 @Field("password") String password,
                                                 @Field("apellidos") String apellidos

                                                    );


    @POST("login")
    @FormUrlEncoded
    io.reactivex.Observable<String> loginUser(@Field("email") String email,
                                              @Field("password") String password);

    @POST("producto-by-category")
    @FormUrlEncoded
    io.reactivex.Observable<String> productos(@Field("categoria") String categoria);

    @POST("producto-by-subcat")
    @FormUrlEncoded
    io.reactivex.Observable<String> productossubcat(@Field("subcat") String subcat);

    @POST("productos")
    @FormUrlEncoded
    io.reactivex.Observable<String> AllProducts(@Field("key") String key);

    @POST("user-data")
    @FormUrlEncoded
    io.reactivex.Observable<String> user_data(@Field("email") String email
                                              );

    @POST("orden")
    @FormUrlEncoded
    io.reactivex.Observable<String> orden(@Field("idcompra") String idcompra,
                                                 @Field("idproducto") int idproducto,
                                                 @Field("nombreproducto") String nombreproducto,
                                                 @Field("precio") Double precio,
                                                 @Field("cantidad") int cantidad,
                                                 @Field("imagen") String imagen


    );

    @POST("compra")
    @FormUrlEncoded
    io.reactivex.Observable<String> compra(@Field("idcompra") String idcompra,
                                                 @Field("usuario") String usuario


    );

    @POST("compras_data")
    @FormUrlEncoded
    io.reactivex.Observable<String> compras_data(@Field("email") String email
    );

    @POST("update-user-data")
    @FormUrlEncoded
    io.reactivex.Observable<String> updateUserData(@Field("email") String email,
                                                 @Field("name") String name,
                                                 @Field("apellidos") String apellidos,
                                                   @Field("id") String id

    );

    @POST("ordenes-compra")
    @FormUrlEncoded
    io.reactivex.Observable<String> ordenes_compra(@Field("compra") String compra
    );

    @POST("get-image")
    @FormUrlEncoded
    io.reactivex.Observable<String> get_image(@Field("producto") String producto
    );

}
