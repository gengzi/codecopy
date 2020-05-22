package fun.gengzi.codecopy.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
 * map工具类
 * @author sjm
 *
 */
public class CheckMapUtils {
	static final List<String> paramsList = new ArrayList<>();
	/**
	 * 添加非负数校验集合单独添加
	 * 需要校验参数(正整数)
	 */
	static {
		paramsList.add("count");
		paramsList.add("pageindex");
	}

	/**
	 * 传入的参数列表中是否包含非负数校验
	 * @param param
	 * @return
	 */
	private static boolean hasNonPositive(String param){
		return paramsList.contains(param);
	}

	/**
	 * null检测通过
	 * 检测是否有未传参数
	 * @param jsonPara
	 * @param params
	 * @return
	 */
	public static Map<String, Object> checkMapParIsNotNull(Map<String, Object> jsonPara, String ...params) {
		Map<String, Object> map = new HashMap<>();
		try{
			if(null==params||0== params.length){
				return null;
			}else{
				for(int i = 0;i<params.length;i++){
					if (StringUtils.isEmpty(params[i]))
						continue;
					if (jsonPara.get(params[i])==null || StringUtils.isBlank(jsonPara.get(params[i]).toString())) {
						map.put("param", params[i]+"不能为空！");
						return map;
					}else{
						if (hasNonPositive(params[i])){
							if (0>Integer.valueOf(jsonPara.get(params[i]).toString())){
								map.put("param", params[i]+"不能小于0！");
								return map;
							}
						}
						if (params[i].contains("Time")){
							if (!jsonPara.get(params[i]).toString().matches("^\\d{13}$")){
								map.put("param", params[i]+"时间格式不对！");
								return map;
							}
						}
					}
				}
				return null;
			}
		}catch (Exception e){
			map.put("param","请输入正确的参数格式!");
			return map;
		}
	}

	/**
	 * 参数转化成为map(K,V,K,V,K,V,K,V)--->map(k,v)(k,v)(k,v)(k,v)
	 * @param params
	 * @return
	 */
	public static Map<String,Object> paramsToMap(String ...params){
		Map<String, Object> map = new HashMap<>();
		for (int i = 0;i<params.length;i+=2){
			map.put(params[i],params[i+1]);
		}
		return map;
	}

	public static Map<String,Object> checkObjParIsNotNull(Class clazz) {

		return null;
	}
}