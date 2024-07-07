package ru.dggz.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class GetRolesService {
    private final CandidateApi candidateApi;

    public ResponseEntity<String> getRoles(){
        String roles = candidateApi.callGetRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}

