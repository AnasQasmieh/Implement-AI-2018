package com.example.dkim.implementai2018.api.plantDisease;

import com.example.dkim.implementai2018.api.plantDisease.Response.PlantClassificationResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ICustomVisionService {
    final static String PREDICTION_ENDPOINT = "https://southcentralus.api.cognitive.microsoft.com/customvision/v2.0/Prediction/8f634f8f-e13f-42cf-86f3-3255c57afdf1/image?iterationId=776e1a59-1941-4779-b98e-0014b2bff325";
    final static String PREDICTION_KEY = "4b5441cfcd4e459ebcdf1cab26b1adb6";
    final static String CONTENT_TYPE = "application/octet-stream";

    @Headers({
            "Prediction-Key: " + PREDICTION_KEY,
            "Content-Type: " + CONTENT_TYPE
    })
    @GET(".")
    Observable<PlantClassificationResponse> getPrediction();
}
