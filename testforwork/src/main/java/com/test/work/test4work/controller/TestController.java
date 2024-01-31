package com.test.work.test4work.controller;

import com.test.work.test4work.utils.DefaultUnixCodec;
import com.test.work.test4work.utils.DefaultWindowCodec;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.codecs.WindowsCodec;
import org.owasp.esapi.errors.EncodingException;
import org.owasp.esapi.errors.ValidationException;
import org.owasp.esapi.reference.DefaultEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    public static void main(String[] args) throws ValidationException {

        String filePath = "D:" + File.separator + "sofaware";
        String validDirectoryPath = ESAPI.validator().getValidDirectoryPath(filePath, filePath,
                new File("D:\\sofaware"), false);

        File file = new File(validDirectoryPath);
        if (file.exists()) {
            System.out.println("存在");
        } else {
            System.out.println("不存在");
        }
    }
}

