package org.example.bankwithspringboot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class userRequest {
    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
