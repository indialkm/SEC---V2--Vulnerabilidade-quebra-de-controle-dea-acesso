package com.app.notaFlow.utils;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Base64;
import com.app.notaFlow.config.RsaKeyProperties;

@Configuration
public class RsaKeyConfig {

    @Bean
    public RsaKeyProperties rsaKeyProperties() throws Exception {
        RsaKeyProperties keys = new RsaKeyProperties();
        keys.setPublicKey(loadPublicKey());
        keys.setPrivateKey(loadPrivateKey());
        return keys;
    }

    private RSAPrivateKey loadPrivateKey() throws Exception {
        ClassPathResource resource = new ClassPathResource("keypair.pem");
        String key = new String(resource.getInputStream().readAllBytes());

        String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----", "")
                                  .replaceAll("\\r?\\n", "")
                                  .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }

    private RSAPublicKey loadPublicKey() throws Exception {
        ClassPathResource resource = new ClassPathResource("public.pem");
        String key = new String(resource.getInputStream().readAllBytes());

        String publicKeyPEM = key.replace("-----BEGIN PUBLIC KEY-----", "")
                                 .replaceAll("\\r?\\n", "")
                                 .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
    }
}