package com.suven.framework.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zlt
 * @date 2020/6/26
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@RestController
public class WebController {

//    @DubboReference
    @Autowired
    private DemoService demoService;

    private Logger logger = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/test/{p}")
    public String test(@PathVariable("p") String param) {
        String logback_trace_id = GlobalLogbackThread.initGlobalLogbackTraceId("WebController");
        logger.info("gc server global logback info logback_trace_id [{}]", logback_trace_id);
        return demoService.sayHello("spring mvc "+param);
    }
}
