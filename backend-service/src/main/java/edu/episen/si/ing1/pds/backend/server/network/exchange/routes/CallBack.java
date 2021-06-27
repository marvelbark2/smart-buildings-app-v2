package edu.episen.si.ing1.pds.backend.server.network.exchange.routes;

public interface CallBack {
    default Object callBack()  {return null; }
    default Object callBack(Object... t) { return null; }
}
