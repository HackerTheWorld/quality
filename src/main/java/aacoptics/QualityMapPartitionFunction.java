package aacoptics;

import java.util.Iterator;

import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.util.Collector;

import redis.clients.jedis.Jedis;

public class QualityMapPartitionFunction implements MapPartitionFunction<RedisDataVo,RedisDataVo>{

    /**
     *
     */
    private static final long serialVersionUID = -6921584068374015587L;

    @Override
    public void mapPartition(Iterable<RedisDataVo> values, Collector<RedisDataVo> out) throws Exception {
         try(Jedis jedis = new Jedis("XXXXXX",30000)){
            jedis.select(1);
            Iterator<RedisDataVo> iterator = values.iterator();
            while(iterator.hasNext()){
                RedisDataVo redisDataVo = iterator.next();
                jedis.set(redisDataVo.getKey(), redisDataVo.getRedisValue());
            }
         }
        
    }
    
}
