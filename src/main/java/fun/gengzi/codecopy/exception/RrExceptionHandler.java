package fun.gengzi.codecopy.exception;

import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.vo.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理器
 */
@RestControllerAdvice
public class RrExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     */
    @ExceptionHandler(RrException.class)
    public ReturnData handleRRException(RrException e) {
        ReturnData returnData = ReturnData.newInstance();
        returnData.setFailure(e.getMessage());
        return returnData;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ReturnData handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        ReturnData returnData = ReturnData.newInstance();
        returnData.setFailure("数据库中已存在该记录！");
        return returnData;
    }

    @ExceptionHandler(Exception.class)
    public ReturnData handleException(Exception e) {
        logger.error(e.getMessage(), e);
        ReturnData returnData = ReturnData.newInstance();
        returnData.setFailure("未知异常，请稍等再试！");
        returnData.setStatus(RspCodeEnum.ERROR_SYSTEM.getCode());
        return returnData;
    }
}
