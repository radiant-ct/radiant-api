package dev.pepecoral.radiant.modules.common.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.pepecoral.radiant.modules.common.config.RedisProperties;
import redis.clients.jedis.*;
import tools.jackson.databind.ObjectMapper;

@Service
public class RedisService {

    private final RedisProperties redisProperties;
    private Jedis jedis;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisService(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());
    }

    public void enqueueDatasetTask(UUID datasetId) {
        try {
            Map<String, Object> payload = Map.of(
                    "task", "register_dataset",
                    "dataset_id", datasetId.toString());

            String json = objectMapper.writeValueAsString(payload);
            jedis.rpush(redisProperties.getQueueName(), json);

        } catch (Exception e) {
            throw new RuntimeException("Failed to enqueue task", e);
        }
    }

}