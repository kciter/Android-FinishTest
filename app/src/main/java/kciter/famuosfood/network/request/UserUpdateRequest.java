package kciter.famuosfood.network.request;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by kciter on 2017. 12. 3..
 */

public class UserUpdateRequest {
    private String name;
    private String phone;
    private String gender;
    private int age;
    private HashMap<String, RequestBody> params;

    public UserUpdateRequest(String name, String phone, String gender, int age) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
    }

    public HashMap<String, RequestBody> getParams() {
        params = new HashMap<>();
        params.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
        params.put("phone", RequestBody.create(MediaType.parse("text/plain"), phone));
        params.put("gender", RequestBody.create(MediaType.parse("text/plain"), gender));
        params.put("age", RequestBody.create(MediaType.parse("text/plain"), age + ""));

        return params;
    }
}
