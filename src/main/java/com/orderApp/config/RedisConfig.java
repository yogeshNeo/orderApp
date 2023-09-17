package com.orderApp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${redis.ip}")
    private String redisIp;

   /* @Bean(name = "redisClient")
    public JedisCluster getConnection() {
        String[] split = redisIp.split(",");
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        List<String> stringList = Arrays.asList(split);
        stringList.forEach(ip -> {
            String[] split1 = ip.trim().split(":");
            HostAndPort hostAndPort = new HostAndPort(split1[0].trim(), Integer.parseInt(split1[1].trim()));
            hostAndPortSet.add(hostAndPort);
        });

        return new JedisCluster(hostAndPortSet, new GenericObjectPoolConfig<>());
    }*/
}
