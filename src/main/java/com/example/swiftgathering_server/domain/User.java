package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MEMBER")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String loginPassword;

//    @Builder(builderMethodName = "createBuilder")
//    public User(String loginId, String loginPassword) {
//        this.loginId = loginId;
//        this.loginPassword = loginPassword;
//    }
}
