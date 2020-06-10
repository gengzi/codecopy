/**  
 * All rights Reserved, Designed By Android_Robot   
 * @Title:  Base64Util.java   
 * @Package com.foriseland.fsoa.social.consumer.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: Android_Robot     
 * @date:   2018年4月8日 下午12:59:34   
 * @version V1.0     
 */
package fun.gengzi.codecopy.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**   
 * @ClassName:  Base64Util   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: zhaofucai  
 * @date:   2018年4月8日 下午12:59:34      
 */
public class Base64Util {
    final static Base64.Decoder DECODER = Base64.getDecoder();
    final static Base64.Encoder ENCODER = Base64.getEncoder();
    
    //编码
    public static String encode(String text) throws UnsupportedEncodingException
    {
        byte[] textByte = text.getBytes("UTF-8");
        return ENCODER.encodeToString(textByte);
    }
    //解码
    public static String decode(String encodeText) throws UnsupportedEncodingException
    {
       return new String(DECODER.decode(encodeText), "UTF-8");
    }
}
