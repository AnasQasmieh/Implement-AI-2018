package com.example.dkim.implementai2018.api;

import com.example.dkim.implementai2018.api.plantDisease.ICustomVisionService;

public interface IMyRetrofitFactory {
    ICustomVisionService getCustomVisionService();
}
