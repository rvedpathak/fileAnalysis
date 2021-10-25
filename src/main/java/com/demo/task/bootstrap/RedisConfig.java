/**
 * Connection Factory for Redis Class;
 */

package com.demo.task.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${demo.task.redis.host}")
    private String host;
    @Value("${demo.task.redis.port}")
    private String port;
    /*@Bean
    public Jedis jedis(){
        //System.out.println("Host :"+ host+", port :"+ port);
        Jedis jedis = new Jedis(host, Integer.parseInt(port));
        jedis.connect();
        //System.out.println("loaded redis config, connected :"+ jedis.isConnected());
        return jedis;
    }*/
    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(16);
        poolConfig.setMaxIdle(6);
        poolConfig.setFairness(true);
        JedisPool jedisPool = new JedisPool(poolConfig, host, Integer.parseInt(port));
        return jedisPool;
    }
}
