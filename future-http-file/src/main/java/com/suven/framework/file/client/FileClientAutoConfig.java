package com.suven.framework.file.client;

import com.suven.framework.file.client.local.LocalFileClient;
import com.suven.framework.file.client.local.LocalFileClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("fileOSSConfig")
public class FileClientAutoConfig {

    @Bean
    public LocalFileClient createLocalFileClient(){
        return new LocalFileClient(10L,new LocalFileClientConfig());
    }

}
