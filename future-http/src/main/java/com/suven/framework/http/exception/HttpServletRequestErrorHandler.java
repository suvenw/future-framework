package com.suven.framework.http.exception;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.handler.IResponseResultVoHandler;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.ParameterMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;




/**
 * @Title: HttpServletRequestErrorHandler.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http ErrorController 接口 实现异常全局统一处理返回错误信息的统一封装对象类;
 *
 *
 */

@Controller
public class HttpServletRequestErrorHandler extends GlobalExceptionErrorResponse implements IResponseResultVoHandler, ErrorController {

    private final static String ERROR_PATH = GlobalConfigConstants.TOP_SERVER_ERROR_URL;


    @Override
    public IResponseResult getResultVo() {
        return (IResponseResult) ResponseResultVo.build();
    }
    /**
     * Supports the HTML Error View
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public Object errorHtml(HttpServletRequest request, HttpServletResponse response) {
       String url =  ParameterMessage.getRequestMessage().getUri();
        IResponseResult result = getResultVo();
        IResultCodeEnum msgEnumType = SysResultCodeEnum.SYS_REQUEST_URL_NOT_FOUND.format(url);
        return new ResponseEntity<>(this.write(result,msgEnumType, response), HttpStatus.NOT_FOUND);
    }

    /**
     * Supports other formats like JSON, XML
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH,produces = "application/*")
    @ResponseBody
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        String url =  ParameterMessage.getRequestMessage().getUri();
        IResponseResult result = getResultVo();
        IResultCodeEnum msgEnumType = SysResultCodeEnum.SYS_REQUEST_URL_NOT_FOUND.format(url);
        return new ResponseEntity<>(this.write(result,msgEnumType, response), HttpStatus.NOT_FOUND);
    }




    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
//    @Override
    public String getErrorPath() {
        String url =  ParameterMessage.getRequestMessage().getUri();
        return url;
//        return ERROR_PATH;
    }
}