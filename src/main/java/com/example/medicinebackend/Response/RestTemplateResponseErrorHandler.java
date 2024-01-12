package com.example.medicinebackend.Response;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws  IOException {
        if (httpResponse.getStatusCode().is4xxClientError()) {
            return true;
        }

        if (httpResponse.getStatusCode().is5xxServerError()) {
            return true;
        }

        return false;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode().is4xxClientError()) {
            // handle SERVER_ERROR
            String responseString = new String(httpResponse.getBody().readAllBytes());
            System.out.println("responseString = " + responseString);
        } else if (httpResponse.getStatusCode().is5xxServerError()) {
            // handle CLIENT_ERROR
            String responseString = new String(httpResponse.getBody().readAllBytes());
            System.out.println("responseString = " + responseString);
    }
    }
}