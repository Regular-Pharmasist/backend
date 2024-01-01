package com.example.medicinebackend.OpenAPI.Response;

import com.example.medicinebackend.OpenAPI.Response.ResultType.ErrorType;

public class ApiResponse<S> {
    private final ResultType resultType;
    private final String resultMessage;

    private final S data;


    private ApiResponse(ResultType resultType, String result, S data) {
        this.resultType = resultType;
        this.resultMessage = result;
        this.data = data;
    }

    public static ApiResponse<?> success(){
        return new ApiResponse<>(ResultType.SUCCESS, ResultMessage.success,null);
    }

    public static <S> ApiResponse<S> success(S data){
        return new ApiResponse<>(ResultType.SUCCESS,ResultMessage.success,null);
    }

    public static ApiResponse<?> error(ErrorType errorType){
        return new ApiResponse<>(ResultType.ERROR,null,null);
    }
}
