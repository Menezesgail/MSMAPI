package com.sony.msm.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MSMUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Map<String, Object>> convertListMap(String jsonString) throws Exception {

        try {

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> dataList = mapper.readValue(jsonString, ArrayList.class);
            if (dataList == null) {
                return Collections.emptyList();
            }

            return dataList;

        } catch (IOException e) {
        //    new CsxException("ECSX0015", e);
        	System.out.println("Error"+e);
        }
        return null;
    }

    public static Map<String, Object> convertMap(String jsonString) throws Exception {

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> dataMap = mapper.readValue(jsonString, HashMap.class);
            if (dataMap == null) {
                return Collections.emptyMap();
            }

            return dataMap;

        } catch (IOException e) {
          //  new CsxException("ECSX0015", e);
        	System.out.println("Error"+e);
        }
        return null;
    }

    /**
     * Map<String, Object>ã‚’Map<String, String>ã?«å¤‰æ?›ã?™ã‚‹
     *
     * @param Map<String, Object>
     * @return Map<String, String>
     */
    public static Map<String, String> convertStringMap(Map<String, Object> map) {

        Map<String, String> dataMap = new HashMap<String, String>();

        for (String key : map.keySet()) {
            dataMap.put(key, String.valueOf(map.get(key)));
        }
        return dataMap;
    }

    /**
     * Jsonã?®Stringã?‹ã‚‰List<String>ã?«å¤‰æ?›ã?™ã‚‹
     *
     * @param Map<String, Object>
     * @return Map<String, String>
     * @throws Exception 
     */
    public static List<String> convertList(String jsonString) throws Exception {

        try {

            @SuppressWarnings("unchecked")
            List<String> dataList = mapper.readValue(jsonString, ArrayList.class);
            if (dataList == null) {
                return Collections.emptyList();
            }

            return dataList;

        } catch (IOException e) {
          //  new CsxException("ECSX0015", e);
        	System.out.println("Error"+e);
        }
        return null;
    }

    public static String timeCalculator(String deuTime) {

        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";

        SimpleDateFormat formatUtc = new SimpleDateFormat(dateFormat);
        formatUtc.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatDeu = new SimpleDateFormat(dateFormat);
        formatDeu.setTimeZone(TimeZone.getTimeZone("CET"));

        try {
            Date date = formatDeu.parse(deuTime);
            return formatUtc.format(date);
        } catch (Exception e) {
            return null;
        }
    }
}