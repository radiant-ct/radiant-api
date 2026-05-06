package dev.pepecoral.radiant.modules.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private String queueDir = "/queue";

    public String getQueueDir() {
        return queueDir;
    }

    public void setQueueDir(String uploadDir) {
        this.queueDir = uploadDir;
    }
}