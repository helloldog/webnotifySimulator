package mine.BaseLib;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;


public class Zxing {
    public static void main(String[] args) {
        Zxing zxing = new Zxing();

//        // 传参：二维码内容和生成路径
//        if (zxing.orCode("http://fxh5.platformcenter.net/inParking.html?parkingId=P5lu82bu3c18549&channelId=C5lu82idb378528", "F:\\3.jpg")) {
//            System.out.println("ok,成功");
//        } else {
//            System.out.println("no,失败");
//        }
String parkingId="P6a0pcr826376ff";
String channelId1out2a="C6a0phr5425938a";
String channelIda2b="C6a0pj6s81f3a0e";
String channelIdb2a="C6a0pjsddf975e1";
String St1="https://h5carown.platformcenter.net/inParking.html?parkingId="+parkingId+"&channelId="+channelId1out2a;
String St2="https://h5carown.platformcenter.net/inParking.html?parkingId="+parkingId+"&channelId="+channelIda2b;
String St3="https://h5carown.platformcenter.net/inParking.html?parkingId="+parkingId+"&channelId="+channelIdb2a;
String St4="https://h5carown.platformcenter.net/pay.html?parkingId="+parkingId+"&payTimeType=OutParkingPay&sn=a2out";
String St5="https://h5carown.platformcenter.net/pay.html?parkingId="+parkingId+"&payTimeType=OutParkingPay&sn=b2out";

        zxing.orCode(St1, "F:\\无牌车入A.jpg");
        zxing.orCode(St2, "F:\\a-b.jpg");
        zxing.orCode(St3, "F:\\b-a.jpg");
        zxing.orCode(St4, "F:\\从A出场.jpg");
        zxing.orCode(St5, "F:\\从B出场.jpg");
        System.out.println(St1);
      //  https://h5carown.platformcenter.net/inParking.html?parkingId=P6a0pcr826376ff&channelId=C6a0phr5425938a&sn=PTR-200-LS18010007&picName=
        //https://h5carown.platformcenter.net/inParking.html?parkingId=P6a0pcr826376ff&channelId=C6a0phr5425938a
    }



    public static boolean orCode(String content, String path) {
        /*
         * 图片的宽度和高度
         */
        int width = 300;
        int height = 300;
        // 图片的格式
        String format = "png";
        // 二维码内容
        // String content = "hello,word";

        // 定义二维码的参数
        HashMap hints = new HashMap();
        // 定义字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 纠错的等级 L > M > Q > H 纠错的能力越高可存储的越少，一般使用M
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置图片边距
        hints.put(EncodeHintType.MARGIN, 2);

        try {
            // 最终生成 参数列表 （1.内容 2.格式 3.宽度 4.高度 5.二维码参数）
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 写入到本地
            Path file = new File(path).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param srcImgPath 源图片路径
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     * @param markContentColor 水印颜色
     * @param font 水印字体
     */



}