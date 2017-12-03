package kciter.famuosfood.network.service;

import java.util.HashMap;

import kciter.famuosfood.model.User;
import kciter.famuosfood.network.response.MessageResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by kciter on 2017. 12. 3..
 */

public interface UserService {
    @Multipart
    @Headers("Accept: application/json")
    @PUT("/api/user")
    Call<User> update(@Header("x-access-token") String token, @Part MultipartBody.Part image, @PartMap HashMap<String, RequestBody> fields);

    @Headers("Accept: application/json")
    @GET("/api/user")
    Call<User> get(@Header("x-access-token") String token);
}
