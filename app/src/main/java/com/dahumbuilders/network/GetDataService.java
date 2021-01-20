package com.dahumbuilders.network;

import com.dahumbuilders.model.ResponseSummary;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("/forex/dahum.php")
    Call<ResponseSummary> getAllPhotos();
}
