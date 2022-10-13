package com.example.MysqlTest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String pw;

    public Login(Long id, String username, String pw) {
        this.id = id;
        this.username = username;
        this.pw = pw;
    }
}
