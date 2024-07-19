package com.social.Hizam.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq",sequenceName = "user_id_seq",allocationSize = 1)
    private long id;

    private boolean deleted;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password1;

    @Column(nullable = false)
    private String password2;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String captchaCode;

    @Column(nullable = false)
    private String captchaSecret;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


}
