package com.test.work.test4work.test;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试PDFBox生成pdf报表文件
 */
public class TableInPDFBox {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        PDDocument document = null;
        String[][] stuData = new String[101][5];
        stuData[0][0] = "序号";
        stuData[0][1] = "编号";
        stuData[0][2] = "名称";
        stuData[0][3] = "模式";
        stuData[0][4] = "详情";
        for (int i = 1; i < stuData.length; i++) {
            stuData[i][0] = String.valueOf(i);
            stuData[i][1] = "11111111111111111111";
            stuData[i][2] = "人工智能";
            stuData[i][3] = "混合模式";
            stuData[i][4] = "人工智能................";
        }
        try {
            document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true, false);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateFormat = sdf.format(date);

            // 表格参数
            int rows = stuData.length;
            int cols = 5;
            float xPos = 50; // 表格左上角的x坐标
            float yPos = 700; // 表格左上角的y坐标（注意：PDF坐标系统的原点在左下角）
            float cellWidth1 = 50, cellWidth2 = 150;
            float cellHeight = 50;

            // 绘制表格线 x轴
            for (int i = 0; i <= rows; i++) {
                if (i % 10 == 0) {//没有把内容分页放进去
                    document.addPage(page);
                    xPos = 50; // 表格左上角的x坐标
                    yPos = 700; // 表格左上角的y坐标（注意：PDF坐标系统的原点在左下角）
                }
                float rowY = yPos - (i % 10) * cellHeight;
                contentStream.moveTo(xPos, rowY);
                contentStream.lineTo(xPos + cellWidth1 * 3 + cellWidth2 * 2, rowY);
                contentStream.stroke();
                //System.out.println("第"+(i+1)+"条x轴的位置是：（"+xPos+","+rowY+")->("+(xPos+cellWidth1*3+cellWidth2*2)+","+rowY+")");
            }
            // 绘制表格线 y轴
            for (int j = 0; j <= cols; j++) {
                float colX;
                if (j > 3) {
                    colX = xPos + 3 * cellWidth1 + (j - 3) * cellWidth2;
                } else {
                    colX = xPos + (j) * cellWidth1;
                }
                contentStream.moveTo(colX, yPos);
                contentStream.lineTo(colX, yPos - rows * cellHeight);
                contentStream.stroke();
                //System.out.println("第"+(j+1)+"条y轴的位置是：（"+colX+","+yPos+")->("+colX+","+(yPos - rows * cellHeight)+")");
            }

            // 写入表格内容
            File fontFile = new File("C:\\Windows\\Fonts\\simhei.ttf"); // 字体文件路径
            PDType0Font font = PDType0Font.load(document, fontFile);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    String text = stuData[i][j];
                    float startX = 0f;
                    if (j > 3) {
                        startX = xPos + 3 * cellWidth1 + (j - 3) * cellWidth2;
                    } else {
                        startX = xPos + (j) * cellWidth1;
                    }
                    contentStream.beginText();
                    contentStream.setFont(font, 10);
                    contentStream.newLineAtOffset(startX + 3, yPos - (i % 10) * cellHeight - 15);  //定点写入
                    contentStream.showText(text);
                    contentStream.endText();
                }
            }

            contentStream.close(); // 不要忘记关闭内容流

            // 保存PDF
            document.save("E:\\" + dateFormat + " table.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                try {
                    document.close(); // 关闭文档
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("用时：" + (end - start));
    }
}
