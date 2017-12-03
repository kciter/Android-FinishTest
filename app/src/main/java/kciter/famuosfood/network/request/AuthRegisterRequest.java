package kciter.famuosfood.network.request;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class AuthRegisterRequest {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String gender;
    private int age;
    private HashMap<String, RequestBody> params;

    public AuthRegisterRequest(String username, String password, String name, String phone, String gender, int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
    }

    public HashMap<String, RequestBody> getParams() {
        params = new HashMap<>();
        params.put("username", RequestBody.create(MediaType.parse("text/plain"), username));
        params.put("password", RequestBody.create(MediaType.parse("text/plain"), password));
        params.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
        params.put("phone", RequestBody.create(MediaType.parse("text/plain"), phone));
        params.put("gender", RequestBody.create(MediaType.parse("text/plain"), gender));
        params.put("age", RequestBody.create(MediaType.parse("text/plain"), age + ""));

        return params;
    }
}
