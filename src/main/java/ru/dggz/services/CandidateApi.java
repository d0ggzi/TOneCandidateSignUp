package ru.dggz.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.dggz.models.SignUpForm;
import ru.dggz.models.StatusForm;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class CandidateApi {
    @Value("${config.url}")
    private String url;

    private final ObjectMapper objectMapper;
    private final HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).
            version(HttpClient.Version.HTTP_1_1).build();

    public String callGetRoles(){
        HttpRequest request = null;
        request = HttpRequest.newBuilder()
                .uri(URI.create(this.url + "/api/get-roles"))
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200){
            throw new ResponseStatusException(HttpStatusCode.valueOf(response.statusCode()));
        }

        return response.body();
    }

    public void callSignUp(SignUpForm signUpForm) {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(this.url + "/api/sign-up"))
                    .header("Content-Type", "application/json")
                    .header("accept", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(signUpForm)))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200){
            throw new ResponseStatusException(HttpStatusCode.valueOf(response.statusCode()));
        }
    }

    public String callGetCode(String email){
        HttpRequest request = null;
        request = HttpRequest.newBuilder()
                .uri(URI.create(this.url + "/api/get-code?email=" + email))
                .header("accept", "text/plain")
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200){
            throw new ResponseStatusException(HttpStatusCode.valueOf(response.statusCode()));
        }
        String code = response.body();
        return code.substring(1, code.length() - 1);
    }


    public void callSetStatus(String token){
        HttpRequest request = null;
        StatusForm statusForm = new StatusForm();
        statusForm.setToken(token);
        statusForm.setStatus("increased");
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(this.url + "/api/set-status"))
                    .header("Content-Type", "application/json")
                    .header("accept", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(statusForm)))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200){
            throw new ResponseStatusException(HttpStatusCode.valueOf(response.statusCode()));
        }
    }
}
