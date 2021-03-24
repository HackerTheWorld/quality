package aacoptics;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.types.Row;

public class QualityFilter implements FilterFunction<Row>{

    /**
     *
     */
    private static final long serialVersionUID = -9016910691275295442L;

    @Override
    public boolean filter(Row raw) throws Exception {
        String zero = "0";
        String lensType = String.valueOf(raw.getField(4));
        String mouldRev = String.valueOf(raw.getField(1));
        if(raw.getField(1) == null ||"null".equals(mouldRev)){
            return false;
        }
        String paramGroupNameStr = String.valueOf(raw.getField(8)).trim();
        boolean paramGroupName = true;
        return !zero.equals(lensType) || !paramGroupName;
    }
    
}
