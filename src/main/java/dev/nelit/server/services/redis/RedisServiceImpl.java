//package dev.nelit.server.services.redis;
//
//import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;

//@Service
//public class RedisServiceImpl implements RedisService {
//
//    private final ReactiveStringRedisTemplate redisTemplate;
//
//    public RedisServiceImpl(ReactiveStringRedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public Mono<Boolean> save(String key, String value) {
//        return redisTemplate.opsForValue().set(key, value);
//    }
//
//    @Override
//    public Mono<String> get(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//}
