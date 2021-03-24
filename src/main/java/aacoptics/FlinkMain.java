package aacoptics;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.operators.GroupCombineOperator;
import org.apache.flink.api.java.operators.GroupReduceOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.types.Row;

public class FlinkMain {
    
    public static void main(String[] args) {

        //sql查询结果列类型
        TypeInformation[] fieldTypes = new TypeInformation[] {
            BasicTypeInfo.STRING_TYPE_INFO,     //mouldNoSys
            BasicTypeInfo.STRING_TYPE_INFO,     //mouldRev
            BasicTypeInfo.STRING_TYPE_INFO,     //hole
            BasicTypeInfo.STRING_TYPE_INFO,     //mouldDate
            BasicTypeInfo.SHORT_TYPE_INFO,      //lensType
            BasicTypeInfo.STRING_TYPE_INFO,     //testValue
            BasicTypeInfo.DATE_TYPE_INFO,       //testTime
            BasicTypeInfo.STRING_TYPE_INFO,     //paramId
            BasicTypeInfo.STRING_TYPE_INFO,     //paramGroupName
            BasicTypeInfo.STRING_TYPE_INFO,     //paramName
            BasicTypeInfo.STRING_TYPE_INFO      //paramGroup
        };
        
        String qualitySql = "SELECT  * from table";

        RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
        JDBCInputFormat jdbcInputFormat = JDBCInputFormat.buildJDBCInputFormat()
                //数据库连接信息
                .setDrivername("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .setDBUrl("jdbc:sqlserver://XXXXX")
                .setUsername("XXXX")
                .setPassword("XXXXXX")
                .setQuery(qualitySql)//查询sql
                .setRowTypeInfo(rowTypeInfo)
                .finish();
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //获取数据源
        DataSet<Row> qualitySet = env.createInput(jdbcInputFormat); // datasource
        qualitySet = qualitySet.filter(new QualityFilter());
        DataSet<QualityMouldNoSys> qualityMouldSet = qualitySet.flatMap(new QualityFlatMapper());
        UnsortedGrouping<QualityMouldNoSys> qualityMouldGroupSet = qualityMouldSet.groupBy(new QualityKeySelector());
        GroupCombineOperator<QualityMouldNoSys,HoleDateVo> holeSet = qualityMouldGroupSet.combineGroup(new QualityCombineGroup());
        GroupReduceOperator<HoleDateVo, RedisDataVo> qualityMouldGroupO = holeSet.reduceGroup(new QualityGroupReduceFunction());
        DataSet<RedisDataVo> quSet = qualityMouldGroupO.mapPartition(new QualityMapPartitionFunction());
        String filepath="D:\\log";
        //默认写到文件
        quSet.writeAsText(filepath,FileSystem.WriteMode.OVERWRITE);
        try {
            env.execute();
            quSet.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
