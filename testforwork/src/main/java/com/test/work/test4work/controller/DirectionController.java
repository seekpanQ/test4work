package com.test.work.test4work.controller;

import com.test.work.test4work.utils.DefaultUnixCodec;
import lombok.extern.slf4j.Slf4j;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;
import org.owasp.esapi.reference.DefaultEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class DirectionController {

    @GetMapping("/list")
    public String getAllDirList(@RequestParam String path) throws IOException, InterruptedException {

        return testEncodeForOS(path);
    }

    /**
     * 测试encodeForOS
     *
     * @param path
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private String testEncodeForOS(String path) throws IOException, InterruptedException {

        path = DefaultEncoder.getInstance().encodeForOS(new DefaultUnixCodec(), path);
        log.info("secure command -> " + path);

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(new String[]{"/bin/sh", "-c", "ls " + path + " -l | grep ^d | awk '{print $9}'"});
//        Process process = runtime.exec(new String[]{"cmd.exe", "/c", "dir " + path});
        // 获取流
        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();

        InputStreamReader errorReader = new InputStreamReader(errorStream, Charset.forName("gbk"));
        InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("gbk"));
        // 读取
        int len;
        char[] buffer = new char[1024];
        // rs
        StringBuffer result = new StringBuffer();
        List<String> jsonResult = new ArrayList<>();
        while ((len = errorReader.read(buffer)) != -1) {
            String b = new String(buffer, 0, len);
            result.append(b);
            log.info(b);
            jsonResult.add(b);
        }

        // 读取
        buffer = new char[1024];

        while ((len = reader.read(buffer)) != -1) {
            String b = new String(buffer, 0, len);
            result.append(b);
            log.info(b);
            jsonResult.add(b);
        }
        inputStream.close();
        // 等待执行完成
        int exitVal = process.waitFor();
        log.info("process exit value is " + exitVal);

        return "success";
    }

    /**
     * 测试getValidDirectoryPath
     *
     * @param path
     * @return
     * @throws ValidationException
     */
    private String testGetValidDirectoryPath(String path) throws ValidationException {

        File file = new File(path);
        if (file.exists()) {
            System.out.println("origin path 存在");
        } else {
            System.out.println("origin path 不存在");
        }

        String validDirectoryPath = ESAPI.validator().getValidDirectoryPath(path, path,
                new File("/"), false);

        System.out.println(validDirectoryPath);

        File file2 = new File(validDirectoryPath);
        if (file2.exists()) {
            System.out.println("valid path 存在");
        } else {
            System.out.println("valid path 不存在");
        }

        return "success";
    }
}
