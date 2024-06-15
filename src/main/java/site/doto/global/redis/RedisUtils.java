package site.doto.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import site.doto.domain.record.entity.Record;
import site.doto.domain.record.entity.RecordPK;
import site.doto.domain.record.repository.RecordRepository;

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

    public void updateRecordToRedis(Long memberId, int year, int month, String field, int amount) {
        String key = "record:" + memberId + ":" + year + ":" + month + "::" + field;

        Integer value = (Integer) getData(key);

        if (value == null) {
            setData(key, amount);
        } else {
            setData(key, (Integer) getData(key) + amount);
        }
    }

    public void updateRecordToDB(Long memberId, int year, int month) {
        RecordPK recordPK = new RecordPK(memberId, year, month);

        Optional<Record> optional = recordRepository.findByRecordPK(recordPK);

        if (optional.isEmpty()) {
            recordRepository.save(new Record(recordPK, 0, 0, 0, 0, 0, 0, 0));
        }

        Set<String> keys = findKeys("record:" + memberId + ":" + year + ":" + month + "*");

        if (keys.isEmpty()) return;

        List<String> fields = new ArrayList<>();
        List<Integer> variances = new ArrayList<>();

        for (String key : keys) {
            fields.add(key.split("::")[1]);
            variances.add((Integer) getData(key));
        }

        recordRepository.updateRecord(memberId, year, month, fields, variances);
    }

    public void flushRedis() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }
}
