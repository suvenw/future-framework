//package com.suven.framework.http.interceptor;
//
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//
//public interface IHandlerValidator {
//
//
//    /**
//     * request Method 如果为post 返回true, 否则返回false
//     * @return
//     */
//   default boolean isPostRequest(HttpServletRequest request){
//       boolean isPost = request.getMethod().equals(RequestMethod.POST.name());
//       return isPost;
//   }
//
//    /**
//     * 验证结果数据,成功返回御前, 失败返回false
//     * @return
//     */
//    boolean validatorData();
//
//}
