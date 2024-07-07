package ru.dggz.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dggz.models.SignUpForm;
import ru.dggz.services.CandidateRegisterService;
import ru.dggz.services.GetRolesService;


@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final CandidateRegisterService candidateRegisterService;
    private final GetRolesService getRolesService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<String> register(@RequestBody SignUpForm signUpForm) {
        return candidateRegisterService.registerCandidate(signUpForm);
    }

    @GetMapping(value = "/get-roles")
    public ResponseEntity<String> getRoles(){
        return getRolesService.getRoles();
    }
}
