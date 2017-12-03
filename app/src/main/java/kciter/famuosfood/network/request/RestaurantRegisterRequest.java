package kciter.famuosfood.network.request;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class RestaurantRegisterRequest {
    private String title;
    private String phone;
    private String address;
    private String description;
    private HashMap<String, RequestBody> params;

    public RestaurantRegisterRequest(String title, String phone, String address, String description) {
        this.title = title;
        this.phone = phone;
        this.address = address;
        this.description = description;
    }

    public HashMap<String, RequestBody> getParams() {
        params = new HashMap<>();
        params.put("title", RequestBody.create(MediaType.parse("text/plain"), title));
        params.put("phone", RequestBody.create(MediaType.parse("text/plain"), phone));
        params.put("address", RequestBody.create(MediaType.parse("text/plain"), address));
        params.put("description", RequestBody.create(MediaType.parse("text/plain"), description));

        return params;
    }
}
