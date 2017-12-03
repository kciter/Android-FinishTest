package kciter.famuosfood.network.service;

import java.util.HashMap;

import kciter.famuosfood.network.response.AuthCheckResponse;
import kciter.famuosfood.network.response.LoginResponse;
import kciter.famuosfood.network.response.MessageResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by kciter on 2017. 12. 1..
 */

public interface AuthService {
    @Multipart
    @Headers("Accept: application/json")
    @POST("/api/auth/register")
    Call<MessageResponse> register(@Part MultipartBody.Part image, @PartMap HashMap<String, RequestBody> fields);

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/api/auth/login")
    Call<LoginResponse> login(@FieldMap HashMap<String, String> fields);

    @Headers("Accept: application/json")
    @GET("/api/auth/check")
    Call<AuthCheckResponse> check(@Query("token") String token);
}
