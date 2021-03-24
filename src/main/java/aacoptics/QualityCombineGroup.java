package aacoptics;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.flink.api.common.functions.GroupCombineFunction;
import org.apache.flink.util.Collector;

public class QualityCombineGroup implements GroupCombineFunction<QualityMouldNoSys,HoleDateVo>{

    /**
     *
     */
    private static final long serialVersionUID = -6332375222852316936L;

    @Override
    public void combine(Iterable<QualityMouldNoSys> in, Collector<HoleDateVo> out) throws Exception {
        HashMap<String,LocalDateTime> mouldSet = new HashMap<>();
        HashMap<String,HoleDateVo> outMap = new HashMap<>();
        Iterator<QualityMouldNoSys> iterator = in.iterator();
        while(iterator.hasNext()){
            QualityMouldNoSys qualityMouldNoSys = iterator.next();
            String key = qualityMouldNoSys.getHole()+qualityMouldNoSys.getMouldDate()+qualityMouldNoSys.getParamId();
            boolean flag = true;
            if(mouldSet.containsKey(key)){
                if(mouldSet.get(key).isAfter(qualityMouldNoSys.getTestTime())){
                    mouldSet.put(key, qualityMouldNoSys.getTestTime());
                    flag = false;
                }
            }
            if(flag){
                HoleDateVo holeDateVo = new HoleDateVo();
                holeDateVo.setMouldNoSys(qualityMouldNoSys.getMouldNoSys());
                holeDateVo.setMouldRev(qualityMouldNoSys.getMouldRev());
                holeDateVo.setHole(qualityMouldNoSys.getHole());
                holeDateVo.setMouldDate(qualityMouldNoSys.getMouldDate());
                holeDateVo.setParamId(qualityMouldNoSys.getParamId());
                holeDateVo.setTestValue(qualityMouldNoSys.getTestValue());
                holeDateVo.setParamGroup(qualityMouldNoSys.getParamGroup());
                holeDateVo.setParamName(qualityMouldNoSys.getParamName());
                holeDateVo.setTestTime(qualityMouldNoSys.getTestTime());
                outMap.put(key, holeDateVo);
            }
        }
        
        for(Entry<String,HoleDateVo> entry:outMap.entrySet()){
            out.collect(entry.getValue());
        }
    }
    
}
