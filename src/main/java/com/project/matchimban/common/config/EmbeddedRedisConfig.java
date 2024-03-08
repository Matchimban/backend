package com.project.matchimban.common.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Profile({"embedded", "test"})
@Configuration
public class EmbeddedRedisConfig {

    private RedisServer redisServer;

    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.host}")
    private String redisHost;

    private static final String REDISSON_HOST_PREFIX = "redis://";
    private static final String REDIS_SERVER_MAX_MEMORY = "maxmemory 512M";
    private static final String CHECK_PORT_IS_AVAILABLE_WIN = "netstat -nao | find \"LISTEN\" | find \"%d\"";
    private static final String CHECK_PORT_IS_AVAILABLE_ANOTHER = "netstat -nat | grep LISTEN|grep %d";

    @PostConstruct
    public void startRedis() throws IOException {
        int port = isRedisRunning() ? findAvailablePort() : redisPort;
        redisServer = RedisServer.builder()
                .port(port)
                .setting(REDIS_SERVER_MAX_MEMORY)
                .build();
        try {
            redisServer.start();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private boolean isRedisRunning() throws IOException {
        return isRunning(executeGrepProcessCommand(redisPort));
    }

    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }

        } catch (Exception e) {
        }

        return StringUtils.hasText(pidInfo.toString());
    }

    private Process executeGrepProcessCommand(int port) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        String[] shell;
        if (os.contains("win")) {
            String command = String.format(CHECK_PORT_IS_AVAILABLE_WIN, port);
            shell = new String[] {"cmd.exe", "/y", "/c", command};
        } else {
            String command = String.format(CHECK_PORT_IS_AVAILABLE_ANOTHER, port);
            shell = new String[] {"/bin/sh", "-c", command};
        }

        return Runtime.getRuntime().exec(shell);
    }

    private int findAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(redisHost);
        redisConfiguration.setPort(redisPort);
        return new LettuceConnectionFactory(redisConfiguration);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        // RedisTemplate을 이용하여 Redis에 CRUD 작업 수행
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();

        // Redis와 연결 설정
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // Hash값을 String형으로 직렬화
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        // 이외의 경우 (default)
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public RedissonClient redissonClient() {
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer()
                .setAddress(REDISSON_HOST_PREFIX + redisHost + ":" + redisPort);
        redisson = Redisson.create(config);
        return redisson;
    }
}