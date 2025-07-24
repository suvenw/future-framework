package com.suven.framework.core.config;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration()
@ConfigurationProperties(prefix = GlobalConfigConstants.TOP_SPIDER_CONFIG)
@ConditionalOnProperty(name = GlobalConfigConstants.TOP_SPIDER_CONFIG_ENABLED,  matchIfMissing = true)
//@NacosConfigurationProperties( groupId= GlobalConfigConstants.SERVICE_NAME, dataId = GlobalConfigConstants.TOP_SPIDER_CONFIG_NAME, prefix= GlobalConfigConstants.TOP_SPIDER_CONFIG, type = ConfigType.PROPERTIES,autoRefreshed = true)

public class SpiderConfigSetting {

    private Movie movie = new Movie();

    public static class  Movie{
        private String thunderSeed = "";
        private String source  = "";
        private String ffmpeg  = "";

        public String getThunderSeed() {
            return thunderSeed;
        }

        public void setThunderSeed(String thunderSeed) {
            this.thunderSeed = thunderSeed;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getFfmpeg() {
            return ffmpeg;
        }

        public void setFfmpeg(String ffmpeg) {
            this.ffmpeg = ffmpeg;
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
