package com.example.pw;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 教科書だとこれ
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
// API名考えるとこれ

public class GenPassword {
	public String hashpw(String pw) {
		String hashedpw = new Pbkdf2PasswordEncoder().encode(pw);
		return hashedpw;
	}
}
