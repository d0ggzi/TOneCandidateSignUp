package ru.dggz.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dggz.models.SignUpForm;


@RequiredArgsConstructor
@Service
public class CandidateRegisterService {
    private final CandidateApi candidateApi;

    public ResponseEntity<String> registerCandidate(SignUpForm signUpForm){
        String email = signUpForm.getEmail();

        candidateApi.callSignUp(signUpForm);
        String code = candidateApi.callGetCode(email);
        String token = EncodeToken.encode(email, code);
        candidateApi.callSetStatus(token);

        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
