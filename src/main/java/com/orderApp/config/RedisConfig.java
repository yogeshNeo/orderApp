package com.orderApp.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${redis.ip}")
    private String redisIp;

    @Bean(name = "redisClient")
    public JedisCluster getConnection() {
        System.out.println("redisIp :: " + redisIp);
        String[] split = redisIp.split(",");
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        List<String> stringList = Arrays.asList(split);
        stringList.forEach(ip -> {
            String[] split1 = ip.trim().split(":");
            HostAndPort hostAndPort = new HostAndPort(split1[0].trim(), Integer.parseInt(split1[1].trim()));
            hostAndPortSet.add(hostAndPort);
        });
        return new JedisCluster(hostAndPortSet, new GenericObjectPoolConfig<>());
    }
}
