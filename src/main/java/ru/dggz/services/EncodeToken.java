package ru.dggz.services;

import org.springframework.stereotype.Component;

import java.util.Base64;


@Component
public class EncodeToken {
    public static String encode(String email, String code){
        String toConvert = email + ":" + code;
        return Base64.getEncoder().encodeToString(toConvert.getBytes());
    }
}
