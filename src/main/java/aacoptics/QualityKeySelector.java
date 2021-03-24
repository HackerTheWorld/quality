package aacoptics;

import org.apache.flink.api.java.functions.KeySelector;

public class QualityKeySelector implements KeySelector<QualityMouldNoSys,KeyVo>{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public KeyVo getKey(QualityMouldNoSys mould) throws Exception {
        KeyVo keyVo = new KeyVo();
        keyVo.setMouldNoSys(mould.getMouldNoSys());
        keyVo.setMouldRev(mould.getMouldRev());
        return keyVo;
    }
    
}
