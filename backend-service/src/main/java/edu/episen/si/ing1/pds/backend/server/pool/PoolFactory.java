package edu.episen.si.ing1.pds.backend.server.pool;

public enum PoolFactory {
    Instance;

    public BlockingPool pool;
    boolean isNotReturnable;
    int delayTime;
    PoolFactory() {
        pool = new ConnectionPoolManager();
    }

    BlockingPool newPool(int n) {
        pool.init(n);
        return pool;
    }
    public boolean isNotReturnable() {
        return isNotReturnable;
    }

    public void setNotReturnable(boolean notReturnable) {
        isNotReturnable = notReturnable;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }


}
