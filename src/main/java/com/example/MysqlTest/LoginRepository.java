package com.example.MysqlTest;

import com.example.MysqlTest.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {

}
