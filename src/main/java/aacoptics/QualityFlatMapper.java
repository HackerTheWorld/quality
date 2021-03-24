package aacoptics;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

public class QualityFlatMapper implements FlatMapFunction<Row,QualityMouldNoSys>{

    /**
     *
     */
    private static final long serialVersionUID = -4778823105470305736L;

    @Override
    public void flatMap(Row row, Collector<QualityMouldNoSys> out) throws Exception {
        String mouldNoSys = String.valueOf(row.getField(0));
        String mouldRev = String.valueOf(row.getField(1));
        String hole = String.valueOf(row.getField(2));
        String mouldDate = String.valueOf(row.getField(3));
        String testValue = String.valueOf(row.getField(5));
        LocalDateTime testTime = ((Date)row.getField(6)).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String paramId = String.valueOf(row.getField(7));
        String paramName = String.valueOf(row.getField(9));
        String paramGroup = String.valueOf(row.getField(10));

        QualityMouldNoSys qualityMouldNoSys = new QualityMouldNoSys();
        qualityMouldNoSys.setMouldNoSys(mouldNoSys);
        qualityMouldNoSys.setMouldRev(mouldRev);
        qualityMouldNoSys.setHole(hole);
        qualityMouldNoSys.setMouldDate(mouldDate);
        qualityMouldNoSys.setTestValue(Double.parseDouble(testValue));
        qualityMouldNoSys.setTestTime(testTime);
        qualityMouldNoSys.setParamId(paramId);
        qualityMouldNoSys.setParamName(paramName);
        qualityMouldNoSys.setParamGroup(paramGroup);

        out.collect(qualityMouldNoSys);

    }
    
}
