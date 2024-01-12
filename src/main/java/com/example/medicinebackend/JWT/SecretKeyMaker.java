package com.example.medicinebackend.JWT;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.security.Key;

public class SecretKeyMaker {
    public static Key generateKey() {
        // HS512 알고리즘을 위한 512비트 키 생성
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public static void main(String[] args) {
        Key key = generateKey();
        // 키를 Base64 인코딩된 문자열로 변환하여 출력
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("JWT Secret Key: " + encodedKey);
    }

}
