package fun.gengzi.codecopy.business.tomcat;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.net.InternetDomainName;

public class RequestUtils {


    /**
     * 获取httq请求的类型
     * @param httpReqInfo
     * @return
     */
    public static String getType(String httpReqInfo){
        // 1.根据换行进行分割
        // 2，获取第一行，空格进行分割
        // 3，获取请求方式
        String[] infoLine = httpReqInfo.split("\n");
        if(ArrayUtil.isNotEmpty(infoLine)){
            String one = infoLine[0];
            String[] ones = one.split(" ");
            return ones[0];
        }
        return "";
    }


    /**
     * 获取httq请求的Url
     * @param httpReqInfo
     * @return
     */
    public static String getUrl(String httpReqInfo){
        // 1.根据换行进行分割
        // 2，获取第一行，空格进行分割
        // 3，获取请求方式
        String[] infoLine = httpReqInfo.split("\n");
        if(ArrayUtil.isNotEmpty(infoLine)){
            String one = infoLine[0];
            String[] ones = one.split(" ");
            return ones[1];
        }
        return "";
    }







}
