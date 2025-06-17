package com.test.work.test4work.utils.pdf;

import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.IOException;

import static com.lowagie.text.Font.BOLD;

/**
 * PDF工具类
 */
public class PdfUtils {

    public static BaseFont MSYH;
    public static BaseFont MSYHL;
    public static Font MSYHL_TEXT_BOLD;
    public static Font MSYHL_TEXT;

    static {
        try {
//            MSYH = BaseFont.createFont("fonts/msyh.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            MSYHL = BaseFont.createFont("fonts/msyhl.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            MSYH = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            MSYHL = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

//            Font docFont = new Font(bfChinese, 10, Font.UNDEFINED, Color.BLACK);


//            MSYHL_TEXT_BOLD = new Font(MSYHL, 10, BOLD);
//            MSYHL_TEXT = new Font(MSYHL, 10f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //为页面设置水印
    public static void addWaterMark(Document document, PdfWriter writer) {
        writer.setPageEvent(new PdfPageEventHelper(){
            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte waterMar = writer.getDirectContentUnder();
                String text = "XXXXXXXXXXXXX";
                addTextFullWaterMark(waterMar, text, MSYH);
            }
        });
    }

    //为页面设置页脚
    public static void addFooter(Document document) {
        HeaderFooter footer = new HeaderFooter(true);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorder(Rectangle.NO_BORDER);
        document.setFooter(footer);
    }

    //设置表格样式
    public static void setTableStyle(Table table, int[] widths) {
        // 设置表格宽度为100%
        table.setWidth(100);

        // 表格在页面水平方向的对齐方式
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // 设置表格边框（仅最外层边框为Table的边框，内部边框属于Cell），同样用四个bit位分别表示上(0001)、下(0010)、左(0100)、右(1000)四个边
        table.setBorder(Rectangle.NO_BORDER);

        // 表格边框宽度
//        table.setBorderWidth(1);

        // 设置该值可以让文字与底部表格线留出一定空间
        table.setPadding(0.6f);

        if (widths != null){
            //设置宽度
            table.setWidths(widths);
        }

        // 以下两行一般同时使用：设置默认Cell，使用默认Cell填充空的Cell
        table.setDefaultCell(createCell(""));
        table.setAutoFillEmptyCells(true);

        //单元格设置不分页
        table.setCellsFitPage(true);
    }

    public static void addTextFullWaterMark(PdfContentByte waterMar, String text, BaseFont bfChinese) {
        waterMar.beginText();

        PdfGState gs = new PdfGState();
        // 设置填充字体不透明度为0.2f
        gs.setFillOpacity(0.2f);
        waterMar.setFontAndSize(bfChinese, 12);
        // 设置透明度
        waterMar.setGState(gs);
        // 设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
        float height = waterMar.getPdfWriter().getPageSize().getHeight();
        float width = waterMar.getPdfWriter().getPageSize().getWidth();
        for (int x = -80; x <= width; x += 300) {
            for (int y = -90; y <= height; y += 300) {
                waterMar.showTextAligned(Element.ALIGN_RIGHT, text, x, y, 35);
            }
        }
        // 设置水印颜色
        waterMar.setColorFill(Color.GRAY);

        //结束设置
        waterMar.endText();
        waterMar.stroke();
    }

    /**
     * 设置表格的数据单元格样式
     *
     * @param text 单元格内容
     */
    public static Cell createCell(String text) {
        return createCell(text, 1);
    }

    /**
     * 设置表格的数据单元格样式
     *
     * @param text 文本
     * @param colSpan colSpan
     * @return Cell
     */
    public static Cell createCell(String text, int colSpan) {
        return createCell(text, colSpan, HorizontalAlignment.LEFT);
    }

    /**
     * 设置表格的数据单元格样式
     *
     * @param text 文本
     * @param colSpan colSpan
     * @param font 字体
     * @return Cell
     */
    public static Cell createCell(String text, int colSpan, Font font) {
        return createCell(text, colSpan, HorizontalAlignment.LEFT, true, font);
    }

    /**
     * 设置表格的数据单元格样式
     *
     * @param text 文本
     * @param isBold 是否粗体
     * @return Cell
     */
    public static Cell createCell(String text, boolean isBold) {
        return createCell(text, 1, HorizontalAlignment.LEFT, isBold, null);
    }

    /**
     * 设置表格的数据单元格样式
     *
     * @param text 文本
     * @param colSpan colSpan
     * @param horizontalAlign 水平对齐方式
     * @return Cell
     */
    public static Cell createCell(String text, int colSpan, HorizontalAlignment horizontalAlign) {
        return createCell(text, colSpan, horizontalAlign, true, null);
    }

    /**
     * 设置表格的数据单元格样式
     *
     * @param text 文本
     * @param colSpan  colSpan
     * @param horizontalAlign 水平对齐方式
     * @param isBold 是否粗体
     * @param font 字体
     * @return Cell
     */
    public static Cell createCell(String text, int colSpan, HorizontalAlignment horizontalAlign, boolean isBold, Font font) {
        Font realFont = isBold ? new Font(MSYHL, 10f, BOLD) : new Font(MSYHL, 10f);
        if(font != null){
            realFont = font;
        }
        Paragraph paragraph = new Paragraph(text, realFont);
        paragraph.setLeading(18, 18);
        Cell cell = new Cell(paragraph);
        cell.setColspan(colSpan);

        // 设置水平对齐方式
        cell.setHorizontalAlignment(horizontalAlign);

        // 设置垂直对齐方式
        cell.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置单元格边框颜色，同样用四个bit位分别表示上(0001)、下(0010)、左(0100)、右(1000)四个边
        cell.setBorder(Rectangle.BOX);
        cell.setBorderWidth(1);
        return cell;
    }

}
