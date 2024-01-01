package com.example.medicinebackend.OpenAPI.Response;

public enum ResultType {
    SUCCESS, ERROR;

    public enum ErrorType{
        DATA_NOT_FOUND;
    }
}
