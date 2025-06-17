package com.test.work.test4work.test;

import com.lowagie.text.*;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.PdfWriter;
import com.test.work.test4work.utils.pdf.PdfUtils;

import java.io.FileOutputStream;

import static com.lowagie.text.Font.BOLD;

/**
 * 测试使用Openpdf生成pdf文件
 */
public class GeneratePDFTest {

    public static void main(String[] args) throws Exception {
        exportPdf();
    }

    public static void exportPdf() throws Exception {
        long start = System.currentTimeMillis();
        String pdfFile = "E:\\TableExample10000.pdf";

        // 设置第一页为A3大小
        Document document = new Document(PageSize.A3);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

        //添加水印
        PdfUtils.addWaterMark(document, writer);

        // 添加页脚
        PdfUtils.addFooter(document);

        // 打开文档
        document.open();

        // 绘制第一页的表格内容
        addTable(document);

        // 关闭文档流
        document.close();

        System.out.println("=========done=========");
        long end = System.currentTimeMillis();
        System.out.println("用时：" + (end - start));
    }

    private static void addTable(Document document) {
        Paragraph title = new Paragraph("大标题", new Font(PdfUtils.MSYH, 16, BOLD));
        title.setLeading(60);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph tableTopText = new Paragraph("其余标题", new Font(PdfUtils.MSYH, 13, BOLD));
        tableTopText.setSpacingBefore(45);
        tableTopText.setAlignment(Element.ALIGN_LEFT);
        document.add(tableTopText);

        Table table = new Table(5);
        int[] widths = new int[]{11, 20, 20, 20, 29};
        PdfUtils.setTableStyle(table, widths);
        table.addCell(PdfUtils.createCell("序号", 1, HorizontalAlignment.CENTER));
        table.addCell(PdfUtils.createCell("编号", 1, HorizontalAlignment.CENTER));
        table.addCell(PdfUtils.createCell("名称", 1, HorizontalAlignment.CENTER));
        table.addCell(PdfUtils.createCell("模式", 1, HorizontalAlignment.CENTER));
        table.addCell(PdfUtils.createCell("详情", 1, HorizontalAlignment.CENTER));

        for (int i = 0; i < 100000; i++) {
            table.addCell(PdfUtils.createCell(String.valueOf(i + 1), false));
            table.addCell(PdfUtils.createCell("11111111111111111111", false));
            table.addCell(PdfUtils.createCell("人工智能", false));
            table.addCell(PdfUtils.createCell("混合模式", false));
            table.addCell(PdfUtils.createCell("人工智能................", false));
        }
        document.add(table);
    }


}
