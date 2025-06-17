package com.test.work.test4work.service.impl;

import com.lowagie.text.pdf.BaseFont;
import com.test.work.test4work.service.PDFConverterService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PDFConverterServiceImpl implements PDFConverterService {

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void convertHtmlToPDF() {
        Context context = new Context();
        context.setVariables(assembleParameters());
        System.out.println("Processing template...");
        String htmlContent = templateEngine.process("mail", context);

        Document doc = Jsoup.parse(htmlContent, "utf-8");
        //默认是以 html 的方式
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        //需改用 xml 的方式格式，否则用 openPdf 转化时 PDF 时可能排版错乱
        byte[] pdfBytes = html2PDF(doc.outerHtml());

        //文件保存本地路径
        String filePath = "output.pdf";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            //将 byte[] 数据写入文件
            fos.write(pdfBytes);
            log.info("PDF 文件保存成功：{}", filePath);
        } catch (IOException e) {
            log.info("保存 PDF 文件时出现错误：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    public Map<String, Object> assembleParameters() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name", "Tom");
//        map.put("posName", "software");
//        map.put("jobLevelName", "高级");
//        map.put("departmentName", "软件部");
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(i + 1));
            map.put("no", "11111111111111111111");
            map.put("name", "人工智能");
            map.put("mode", "混合模式");
            map.put("detail", "人工智能................");
            list.add(map);
        }
        Map<String, Object> mode = new HashMap<>();
        mode.put("list", list);
        return mode;
    }

    public byte[] html2PDF(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            // 加载字体文件（SimHei为示例，请根据实际字体文件替换路径）
            String fontFile = "/fonts/simhei.ttf";
            renderer.getFontResolver().addFont(fontFile, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
