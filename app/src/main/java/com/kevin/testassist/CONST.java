package com.kevin.testassist;

import android.os.Environment;

import com.kevin.testassist.utils.FileUtils;

import org.json.JSONObject;

import java.io.File;

public final class CONST {
     private CONST() {}
     public static final String AUTOTEST= "autotest";
     public static final String REPORT= "report";
     public static final String TESTCASES = "testcases";
     public static final String MONKEY = "monkey";
     public static final String INPUT = "input.json";
     public static final String REFLECT = "reflect.json";
     public static final String LOGPATH = Environment.getExternalStorageDirectory().getPath() + File.separator + AUTOTEST + File.separator;
     public static final String TESTCASES_PATH = LOGPATH + TESTCASES + File.separator;
     public static final String REPORT_PATH = LOGPATH + REPORT + File.separator;
     public static final String DUMP_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "window_dump.xml";
     public static final String CONFIG_FILE = LOGPATH + "config.json";

     public static final String INPUT_FILE = LOGPATH + INPUT;
     public static final String REFLECT_FILE = LOGPATH + REFLECT;

     public static final String SHUTTLE_RECEIVER = "com.kevin.testool.test.shuttle";
     public static final String DRAG_RECEIVER = "com.kevin.testool.test.drag";



    public static JSONObject CONFIG;

    static {
        try {
            if (new File(CONFIG_FILE).exists()) {
                CONFIG = new JSONObject(FileUtils.readJsonFile(CONFIG_FILE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            CONFIG = new JSONObject();
        }
    }
}

