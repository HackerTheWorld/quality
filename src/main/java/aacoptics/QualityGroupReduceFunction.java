package aacoptics;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import org.apache.flink.api.common.functions.GroupCombineFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;
import org.json.JSONObject;


public class QualityGroupReduceFunction implements GroupReduceFunction<HoleDateVo,RedisDataVo>,GroupCombineFunction<RedisDataVo, RedisDataVo>{

    /**
     *
     */
    private static final long serialVersionUID = 6214286476448508743L;

    @Override
    public void reduce(Iterable<HoleDateVo> iterable, Collector<RedisDataVo> out)
            throws Exception {
                Map<String,Map<String,Map<String,Map<String,Object>>>> map = new HashMap<String,Map<String,Map<String,Map<String,Object>>>>(16);
                Iterator<HoleDateVo> iter = iterable.iterator();
                while(iter.hasNext()){
                    HoleDateVo qualityMouldNoSys = iter.next();
                    //获取穴位数据结构
                    Map<String,Map<String,Map<String,Object>>> holeMap = new HashMap<>();
                    if(map.containsKey(qualityMouldNoSys.getHole())){
                        holeMap = map.get(qualityMouldNoSys.getHole());
                    }
                    //获取穴位对应的参数的结构
                    Map<String,Map<String,Object>> paramMap = new HashMap<>();
                    if(holeMap.containsKey(qualityMouldNoSys.getParamId())){
                        paramMap = holeMap.get(qualityMouldNoSys.getParamId());
                    }
                    Map<String,Object> mapEle = new HashMap<>();
                    if(paramMap.containsKey(qualityMouldNoSys.getMouldDate())){
                        mapEle = paramMap.get(qualityMouldNoSys.getMouldDate());
                        LocalDateTime mapLTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(String.valueOf(mapEle.get("testTime")))),TimeZone.getDefault().toZoneId());
                        if(qualityMouldNoSys.getTestTime().isAfter(mapLTime)){
                            mapEle.put("testValue", qualityMouldNoSys.getTestValue());
                            mapEle.put("testTime", qualityMouldNoSys.getTestTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
                        }
                    }else{
                        mapEle.put("testValue", qualityMouldNoSys.getTestValue());
                        mapEle.put("testTime", qualityMouldNoSys.getTestTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
                        mapEle.put("paramName", qualityMouldNoSys.getParamName());
                        mapEle.put("paramGroup", qualityMouldNoSys.getParamGroup());
                    }
                    paramMap.put(qualityMouldNoSys.getMouldDate(), mapEle);
                    holeMap.put(qualityMouldNoSys.getParamId(), paramMap);
                    map.put(qualityMouldNoSys.getHole(), holeMap);
                    RedisDataVo redisDataVo = new RedisDataVo();
                    redisDataVo.setKey(qualityMouldNoSys.getMouldNoSys()+"="+qualityMouldNoSys.getMouldRev());
                    JSONObject jsonObject = new JSONObject(map);
                    redisDataVo.setRedisValue(jsonObject.toString());
                    out.collect(redisDataVo);
                }
        
    }

    //将分组函数合并成一个或多个元素
    @Override
    public void combine(Iterable<RedisDataVo> iterable,Collector<RedisDataVo> out) throws Exception {
            Iterator<RedisDataVo> in = iterable.iterator();
            while(in.hasNext()){
                out.collect(in.next());
            }
    }
    
}
