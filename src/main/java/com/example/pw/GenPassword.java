package com.example.pw;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 教科書だとこれ
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
// API名考えるとこれ

public class GenPassword {
	public static void main(String[] args){
		System.out.printf(new Pbkdf2PasswordEncoder().encode("root"));
	}
}
