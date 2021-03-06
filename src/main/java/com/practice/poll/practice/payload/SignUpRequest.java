package com.practice.poll.practice.payload;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   @NotBlank
   @Size(min = 4,max = 40)
    private String name;

   @NotBlank
   @Size(max = 40)
   @Email
    private String email;

   @NotBlank
   @Size(min = 2,max = 15)
    private String username;

   @NotBlank
   @Size(min = 6,max = 10)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }
}
