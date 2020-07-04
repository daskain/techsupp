package ru.rfibank.techsupport.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class CalculateMac {

    @Value("#{hostApiParamNotRequired}")
    private List<String> hostApiParamNotRequired;

    @Value("#{hostApiParamNotUse}")
    private List<String> hostApiParamNotUse;

    @Getter
    public String data;

    public String mac(LinkedHashMap<String, String> params, String secret_key) throws InvalidKeyException, NoSuchAlgorithmException {
        
        //строка для подписи
        data = params.keySet().stream()
                .map(key -> {

                    String buf;
                    //не включать в строку пустые параметры, если это указано в доке
                    if (!hostApiParamNotRequired.stream().anyMatch(s -> s.contains(params.get(key)))) {

                        if (params.get(key).equals("")) {
                            buf = "";
                        } else {
                            buf = params.get(key).length() + params.get(key);
                        }
                    } else {

                        if (params.get(key).equals("")) {
                            buf = "-";
                        } else {
                            buf = params.get(key).length() + params.get(key);
                        }
                    }

                    return buf;
                })
                .collect(Collectors.joining(""));

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        sha256_HMAC.init(new SecretKeySpec(secret_key.getBytes(), "HmacSHA256"));
        byte[] result = sha256_HMAC.doFinal(data.getBytes());
        return DatatypeConverter.printHexBinary(result).toLowerCase();

    }

}
