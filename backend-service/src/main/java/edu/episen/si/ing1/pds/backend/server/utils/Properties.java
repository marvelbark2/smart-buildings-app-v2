package edu.episen.si.ing1.pds.backend.server.utils;

import edu.episen.si.ing1.pds.backend.server.pool.DataSource;

import java.util.concurrent.ExecutorService;

public class Properties {
    public static String configVar = "SMARTBUILDCONFIG";
    public static boolean testMode = false;
    public static ExecutorService executor;
}
