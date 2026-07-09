package com.banking.creditservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class InfrastructureConfigTest {

    private InfrastructureConfig config;

    @BeforeEach
    void setUp() {
        config = new InfrastructureConfig();

        ReflectionTestUtils.setField(
                config,
                "kafkaBootstrapServers",
                "localhost:9092");

        ReflectionTestUtils.setField(
                config,
                "kafkaConsumerGroupId",
                "credit-group");

        ReflectionTestUtils.setField(
                config,
                "jwtSecret",
                "12345678901234567890123456789012");
    }

    @Test
    void shouldCreateJwtDecoder() {

        JwtDecoder decoder = config.jwtDecoder();

        assertNotNull(decoder);
    }

    @Test
    void shouldCreateRedisConnectionFactory() {

        LettuceConnectionFactory factory =
                config.redisConnectionFactory("localhost", 6379);

        assertNotNull(factory);
    }

    @Test
    void shouldCreateReactiveRedisTemplate() {

        LettuceConnectionFactory factory =
                config.redisConnectionFactory("localhost", 6379);

        ReactiveRedisTemplate<String, Object> template =
                config.reactiveRedisTemplate(factory);

        assertNotNull(template);
    }

    @Test
    void shouldCreateCacheManager() {

        LettuceConnectionFactory factory =
                config.redisConnectionFactory("localhost", 6379);

        CacheManager cacheManager =
                config.cacheManager(factory);

        assertNotNull(cacheManager);
    }

    @Test
    void shouldCreateProducerFactory() {

        ProducerFactory<String, String> producerFactory =
                config.producerFactory();

        assertNotNull(producerFactory);
    }

    @Test
    void shouldCreateKafkaTemplate() {

        ProducerFactory<String, String> producerFactory =
                config.producerFactory();

        KafkaTemplate<String, String> template =
                config.kafkaTemplate(producerFactory);

        assertNotNull(template);
    }

    @Test
    void shouldCreateConsumerFactory() {

        ConsumerFactory<String, String> consumerFactory =
                config.consumerFactory();

        assertNotNull(consumerFactory);
    }

    @Test
    void shouldCreateKafkaListenerContainerFactory() {

        ConsumerFactory<String, String> consumerFactory =
                config.consumerFactory();

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                config.kafkaListenerContainerFactory(consumerFactory);

        assertNotNull(factory);
    }
}