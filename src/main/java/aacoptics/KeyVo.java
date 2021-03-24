package aacoptics;

public class KeyVo {

    public String mouldNoSys;
    public String mouldRev;

    public KeyVo(){

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mouldNoSys == null) ? 0 : mouldNoSys.hashCode());
        result = prime * result + ((mouldRev == null) ? 0 : mouldRev.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyVo other = (KeyVo) obj;
        if (mouldNoSys == null) {
            if (other.mouldNoSys != null)
                return false;
        } else if (!mouldNoSys.equals(other.mouldNoSys))
            return false;
        if (mouldRev == null) {
            if (other.mouldRev != null)
                return false;
        } else if (!mouldRev.equals(other.mouldRev))
            return false;
        return true;
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
