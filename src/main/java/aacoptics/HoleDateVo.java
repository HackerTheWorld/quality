package aacoptics;

import java.time.LocalDateTime;

public class HoleDateVo {
    
    private String mouldNoSys;
    private String mouldRev;
    private String paramId;
    private String paramGroup;
    private String paramName;
    private String hole;
    private String mouldDate;
    private double testValue;
    private LocalDateTime testTime;

    
    public String getHole() {
        return hole;
    }
    public void setHole(String hole) {
        this.hole = hole;
    }
    public String getMouldDate() {
        return mouldDate;
    }
    public void setMouldDate(String mouldDate) {
        this.mouldDate = mouldDate;
    }
    public double getTestValue() {
        return testValue;
    }
    public void setTestValue(double testValue) {
        this.testValue = testValue;
    }
    public LocalDateTime getTestTime() {
        return testTime;
    }
    public void setTestTime(LocalDateTime testTime) {
        this.testTime = testTime;
    }
    public String getParamId() {
        return paramId;
    }
    public void setParamId(String paramId) {
        this.paramId = paramId;
    }
    public String getParamGroup() {
        return paramGroup;
    }
    public void setParamGroup(String paramGroup) {
        this.paramGroup = paramGroup;
    }
    public String getParamName() {
        return paramName;
    }
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
    public String getMouldNoSys() {
        return mouldNoSys;
    }
    public void setMouldNoSys(String mouldNoSys) {
        this.mouldNoSys = mouldNoSys;
    }
    public String getMouldRev() {
        return mouldRev;
    }
    public void setMouldRev(String mouldRev) {
        this.mouldRev = mouldRev;
    }

    

}
