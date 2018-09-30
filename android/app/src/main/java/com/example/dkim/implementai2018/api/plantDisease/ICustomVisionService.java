package com.example.dkim.implementai2018.api.plantDisease;

import com.example.dkim.implementai2018.api.plantDisease.Response.PlantClassificationResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ICustomVisionService {
    final static String PREDICTION_ENDPOINT = "https://southcentralus.api.cognitive.microsoft.com/customvision/v2.0/";
    final static String PREDICTION_KEY = "4b5441cfcd4e459ebcdf1cab26b1adb6";
    final static String CONTENT_TYPE = "application/octet-stream";

    @Headers({
            "Prediction-Key: " + PREDICTION_KEY,
            "Content-Type: " + CONTENT_TYPE
    })
    @POST("Prediction/8f634f8f-e13f-42cf-86f3-3255c57afdf1/image?iterationId=54afa4b5-3553-4a5f-89c1-ec44298ec5c1")
    Observable<PlantClassificationResponse> getPrediction(@Body RequestBody file);
}
