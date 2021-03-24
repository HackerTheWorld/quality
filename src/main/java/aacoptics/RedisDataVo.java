package aacoptics;

public class RedisDataVo {

    private String key;
    private String redisValue;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getRedisValue() {
        return redisValue;
    }
    public void setRedisValue(String redisValue) {
        this.redisValue = redisValue;
    }
    
    @Override
    public String toString(){
        return key+":"+redisValue;
    }
    
}
