package com.test.work.test4work.controller;

import com.test.work.test4work.service.PDFConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/htp")
public class HtmlToPDFController {
    @Autowired
    private PDFConverterService pdfConverterService;

    @PostMapping("/converter")
    public String htmlToPDF() {
        pdfConverterService.convertHtmlToPDF();
        return "success";
    }

    @GetMapping("/mail")
    public ModelAndView mail() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(i + 1));
            map.put("no", "11111111111111111111");
            map.put("name", "人工智能");
            map.put("mode", "混合模式");
            map.put("detail", "人工智能................");
            list.add(map);
        }
        Map<String, List<Map<String, String>>> mode = new HashMap<>();
        mode.put("list", list);
        return new ModelAndView("mail", mode);
    }
}
