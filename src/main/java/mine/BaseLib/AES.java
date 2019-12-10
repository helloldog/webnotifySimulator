package mine.BaseLib;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by weijl on 2019-08-23.
 */
public class AES {
    /**
     * AES对称加密和解密，有偏移量
     *
     * @author tyg
     * @date 2018年6月28日下午12:48:01
     */
    @SuppressWarnings("restriction")

//
//        // 密匙
//        private static final String KEY = "f4k9f5w7f8g4er26";
//        // 偏移量
//        private static final String OFFSET = "5e8y6w45ju8w9jq8";
//        // 编码
//        private static final String ENCODING = "UTF-8";
//        //算法
//        private static final String ALGORITHM = "AES";
//        // 默认的加密算法
//        private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";


        /**
         * 加密
         *
         * @param data
         * @return String
         * @throws Exception
         * @author tyg
         * @date 2018年6月28日下午2:50:35
         */
        public  String encrypt(String data,String vi,String key) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ASCII"), "AES");
            IvParameterSpec iv = new IvParameterSpec(vi.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
        }
    public static String VI() throws Exception {
        String VI = null;
        try{
            Date date =new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddyyyyMMdd");
            String nowDate = sf.format(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sf.parse(nowDate));
            // cal.add(Calendar.DAY_OF_YEAR,-70);//HOUR_OF_DAY加减小时，DAY_OF_YEAR加减天，MINUTE加减分钟，SECOND加减秒，+、-决定时间的加减
            VI = sf.format(cal.getTime());
            //   vars.put("key",orderDate);//输出到jmeter变量
        }
        catch(Exception e){

        }
        return  VI;
    }


        /**
         * 解密
         *
         * @param
         * @return String
         * @throws Exception
         * @author tyg
         * @date 2018年6月28日下午2:50:43
         */
        public static String decrypt(String data,String vi,String key) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ASCII"), "AES");
            IvParameterSpec iv = new IvParameterSpec(vi.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] buffer = new BASE64Decoder().decodeBuffer(data);
            byte[] encrypted = cipher.doFinal(buffer);
            return new String(encrypted, "GBK");//此处使用BASE64做转码。
        }

    /**
     * 加密
     * @param sSrc              需要加密字符串
     * @param sKey              密钥
     * @param ivParameter       加密向量
     * @return                  加密结果
     */
    public static String encryptAES7(String sSrc, String sKey, String ivParameter) {
        try {
            byte[] iv = ivParameter.getBytes();//因为要求IV为16byte，而此处aiv串为32位字符串，所以将32位字符串转为16byte
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = sSrc.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(sKey.getBytes("utf-8"), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            String EncStr = new Base64().encodeAsString(encrypted);//此处使用BASE64做转码。
            return  new String(EncStr.getBytes(), "UTF-8") ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 解密
     * @param sSrc              已经加密字符串
     * @param sKey              密钥
     * @param ivParameter       加密向量
     * @return                  解密结果
     */
    public static   String decryptAES7(String sSrc, String sKey,String ivParameter) {
        try {
            byte[] encrypted1 = new Base64().decode(sSrc);
            byte[] iv = ivParameter.getBytes();
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(sKey.getBytes("utf-8"), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,"UTF-8");
            return originalString.trim();//此处添加trim（）是为了去除多余的填充字符，就不用去填充了，具体有什么问题我还没有遇到，有强迫症的同学可以自己写一个PKCS7UnPadding函数
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String ForAES7(String carNO,String carNOC,String triggT,String picN,String confid,String memo) throws Exception {
        String afterAes;
        String json= "{\n" +
                "\"carNO\":\""+carNO+"\",\n" +
                "\"carNOC\":\""+carNOC+"\",\n" +
                "\"triggT\":\""+triggT+"\",\n" +
                "\"picN\":\""+picN+"\",\n" +
                "\"confid\":"+confid+",\n" +
                "\"memo\":\""+memo+"\"\n" +
                "}";

        afterAes = encryptAES7(json,"F7A0B971B199FD2A",VI());
       return afterAes;
    }




    public static void main(String[] args) throws Exception {


        String json3="ag99D36CZDi1jvLYdO2ZFuKZ2AhDBTKK1aiyrGzK/MVCOpOvVJJrc2ayYJw3hAC6exg1B+MXg1UAvSx5m3zGPetq1lJb+iaqFdBGwEbTCyVU5CFLGJGltfsOlHePd1q/XZfy4utpzKx/x3w0RlxUDvexWj4dUmxad4FdBTz783/Uww4RYYxsQf4xhV9JK8GjTNMOeTdzP54xqQssOp5ydeJaS30RL0Wkz51wM4FUCTLo2MtV+FDx2wtxGJqTvhwn4vOcPOFBna5mUTXedr3MZ7wCVzFum8z8V1wb50bfA4ucH6rDLzXGIoqVW8MJz6J5";
        System.out.println(decryptAES7(json3,"F7A0B971B199FD2A","2019091120190911"));


    }



}
