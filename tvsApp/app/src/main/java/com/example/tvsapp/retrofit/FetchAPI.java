package com.example.tvsapp.retrofit;

import com.example.tvsapp.model.LoginObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FetchAPI {


	@POST("gettabledata.php")
	Call<ResponseBody> postAPI(@Body LoginObject loginObject);


}
