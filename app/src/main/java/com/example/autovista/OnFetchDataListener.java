package com.example.autovista;

import com.example.autovista.models.CarAPIResponse;

public interface OnFetchDataListener <CarAPIResponse>
{
    void onFetchCarAPIResponse(CarAPIResponse response, String message);
    void onError(String message);
}
