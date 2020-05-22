package fun.gengzi.codecopy.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;

/** 
 * 返回客户端包装数
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class ReturnData {

	//操作状态码
	@ApiModelProperty(value = "状态码", example = "200表示成功,0表示失败,1000表示错误")
	private int status; 
	
	//返回数据
	@ApiModelProperty(value = "返回数据", example = "{ \"ID\"：\"新提交数据的ID\" },"
			+ "如果是数组 为[{ \"ID\"：\"新提交数据的ID\" },{ \"ID\"：\"新提交数据的ID\" }]"
			+ "或更复杂的消息类型")
	private Object info; 
	
	//错误信息
	@ApiModelProperty(value = "错误信息", example = "失败！")
	private Object message;

	public ReturnData(){}
	
	public static ReturnData newInstance(){
		return new ReturnData();
	}
	
	public ReturnData(int status, Object info, Object message) {
		super();
		this.status = status;
		this.info = info;
		this.message = message;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public void setFailure(Object message) {
		this.status = CommonConstants.RETURN_CODE_FAIL;
		this.message = message;
		this.info = new HashMap<String,String>();
	}
	
	public void setSuccess(){
		this.status = CommonConstants.RETURN_CODE_SUCCESS;
		this.message = "操作成功";
		this.info = new HashMap<String,String>();
	}
	

	public void setSuccess(Object data) {
		this.status = CommonConstants.RETURN_CODE_SUCCESS;
		this.message = "操作成功";
		this.info = data == null ? new HashMap<String, Object>() : data;
	}
	
}

