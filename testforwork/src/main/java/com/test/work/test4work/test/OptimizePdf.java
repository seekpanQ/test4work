package com.test.work.test4work.test;

import com.aspose.pdf.Document;
import com.aspose.pdf.optimization.OptimizationOptions;

/**
 * 压缩PDF文件
 */
public class OptimizePdf {

    public static void optimize(String source, String target) {
        Document doc = new Document(source);
        //设置压缩属性
        OptimizationOptions opt = new OptimizationOptions();
        //删除PDF不必要的对象
        opt.setRemoveUnusedObjects(true);
        //链接重复流
        opt.setLinkDuplcateStreams(false);
        //删除未使用的流
        opt.setRemoveUnusedStreams(false);
        //删除不必要的字体
        opt.setUnembedFonts(true);
        //压缩PDF中的图片
        opt.getImageCompressionOptions().setCompressImages(true);
        //图片压缩比， 0 到100可选，越低压缩比越大
        opt.getImageCompressionOptions().setImageQuality(50);
        doc.optimizeResources(opt);
        //优化web的PDF文档
        doc.optimize();
        doc.save(target);
    }

    public static void main(String[] args) {
        String source = "E:\\TableExample.pdf";
        String target = "E:\\TableExampleOpt.pdf";
//        optimize(source, target);
        for (int i = 0; i < 100000; i++) {
            System.out.println("<tr><td>1</td><td>11111111111111111111</td><td>人工智能</td><td>混合模式</td><td>人工智能................</td></tr>");
        }
    }

}
