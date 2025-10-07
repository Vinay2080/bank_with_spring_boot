package org.example.bankwithspringboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Slf4j
@Service
public class JwtServices {

    private final SecretKey secretKey = Jwts.SIG
}
