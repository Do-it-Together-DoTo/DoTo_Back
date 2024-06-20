package site.doto.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.record.dto.RecordUpdateDto;
import site.doto.domain.record.entity.Record;
import site.doto.domain.record.entity.RecordPK;
import site.doto.domain.record.repository.RecordRepository;
import site.doto.domain.todo.dto.TodoRedisDto;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.redis.core.ScanOptions.scanOptions;

@Component
@RequiredArgsConstructor
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RecordRepository recordRepository;

    public void setData(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void setDataWithExpiration(String key, Object value,Long expiredTime){
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.SECONDS);
    }

    public Object getData(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

    public Set<String> findKeys(String pattern) {

        Set<String> result = new HashSet<>();

        ScanOptions scanOptions = scanOptions().match(pattern).count(100).build();

        Cursor<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);

        while (keys.hasNext()) {
            result.add(new String(keys.next()));
        }

        return result;
    }

    public void flushRedis() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }

    public void updateRecordToRedis(Long memberId, int year, int month, String field, int amount) {
        String key = "record:" + memberId + ":" + year + ":" + month + "::" + field;

        Integer value = (Integer) getData(key);

        if (value == null) {
            setData(key, amount);
        } else {
            setData(key, (Integer) getData(key) + amount);
        }
    }

    @Transactional
    public void updateRecordToDB() {
        Set<String> keys = findKeys("record*");

        Map<String, RecordUpdateDto> map = new HashMap<>();

        for (String key : keys) {
            String[] split = key.split("::");

            String pk = split[0];
            String field = split[1];
            Integer variance = (Integer) getData(key);

            if (!map.containsKey(pk)) {
                map.put(pk, new RecordUpdateDto(pk.split(":")));
            }

            RecordUpdateDto update = map.get(pk);

            update.addData(field, variance);
        }

        for (RecordUpdateDto update : map.values()) {
            RecordPK recordPK = update.getRecordPK();

            if (!recordRepository.existsByRecordPK(recordPK)) {
                recordRepository.save(new Record(recordPK, 0, 0, 0, 0, 0, 0, 0));
            }

            recordRepository.updateRecord(update);
        }
    }

    public void saveTodo(TodoRedisDto todo) {
        redisTemplate.opsForHash().put("todo:", longToString(todo.getBettingId()), todo);
    }

    public TodoRedisDto findTodo(Long id) {
        return (TodoRedisDto) redisTemplate.opsForHash().get("todo:", longToString(id));
    }

    public void deleteTodo(Long id) {
        redisTemplate.opsForHash().delete("todo:", longToString(id));
    }

    private String longToString(Long number) {
        return String.valueOf(number);
    }
}
