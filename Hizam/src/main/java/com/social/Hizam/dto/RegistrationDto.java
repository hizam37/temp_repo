package com.social.Hizam.dto;


import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class RegistrationDto {
    private String id;
    private boolean deleted;
    private String email;
    private String password1;
    private String password2;
    private String firstName;
    private String lastName;
    private String captchaCode;
    private String captchaSecret;

    public RegistrationDto(
            String id,

            boolean deleted,

            String email,

            String password1,

            String password2,

            String firstName,

            String lastName,

            String captchaCode,

            String captchaSecret) {
        this.id = UUID.randomUUID().toString();
        this.deleted = deleted;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.firstName = firstName;
        this.lastName = lastName;
        this.captchaCode = captchaCode;
        this.captchaSecret = captchaSecret;
    }

    public String id() {
        return id;
    }

    public boolean deleted() {
        return deleted;
    }

    public String email() {
        return email;
    }

    public String password1() {
        return password1;
    }

    public String password2() {
        return password2;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String captchaCode() {
        return captchaCode;
    }

    public String captchaSecret() {
        return captchaSecret;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RegistrationDto) obj;
        return this.id == that.id &&
                this.deleted == that.deleted &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.password1, that.password1) &&
                Objects.equals(this.password2, that.password2) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.captchaCode, that.captchaCode) &&
                Objects.equals(this.captchaSecret, that.captchaSecret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deleted, email, password1, password2, firstName, lastName, captchaCode, captchaSecret);
    }

    @Override
    public String toString() {
        return "RegistrationDto[" +
                "id=" + id + ", " +
                "deleted=" + deleted + ", " +
                "email=" + email + ", " +
                "password1=" + password1 + ", " +
                "password2=" + password2 + ", " +
                "firstName=" + firstName + ", " +
                "lastName=" + lastName + ", " +
                "captchaCode=" + captchaCode + ", " +
                "captchaSecret=" + captchaSecret + ']';
    }
}