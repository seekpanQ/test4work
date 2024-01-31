package com.test.work.test4work.controller;

import com.test.work.test4work.utils.DefaultUnixCodec;
import com.test.work.test4work.utils.DefaultWindowCodec;
import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.codecs.WindowsCodec;
import org.owasp.esapi.errors.EncodingException;
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

    public static void main(String[] args) throws EncodingException {
//        String path = "/com/test_?/..";
//        UnixCodec unixCodec = new UnixCodec();
//        char[] chars = path.toCharArray();
//        String result = "";
//        for (Character c : chars) {
//            String s = unixCodec.encodeCharacter(new char[]{'*'}, c);
//            result += s;
//        }
//        System.out.println("去除*:" + result);

//        path = DefaultEncoder.getInstance().encodeForOS(new DefaultUnixCodec(), path);
//        System.out.println(path);

        String filePath = "D:" + File.separator + "sofaware" + File.separator + "acrobat_225409.rar";
        filePath = DefaultEncoder.getInstance().encodeForURL(filePath);

        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("存在");
        } else {
            System.out.println("不存在");
        }
    }
}

