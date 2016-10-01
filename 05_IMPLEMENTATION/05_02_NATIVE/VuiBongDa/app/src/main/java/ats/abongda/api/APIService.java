package ats.abongda.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ats.abongda.config.APILink;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NienLe on 12-Jul-16.
 */
public class APIService {
    static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APILink.APIENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static APIEndpoint build(){
        return retrofit.create(APIEndpoint.class);
    }



}
