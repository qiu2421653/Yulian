package com.naga.common.util;

public class CommonUserData {

    private static String stfNum;
    private static String stfNm;
    private static String posNum;
    private static String subDep;

    public static String getPosNum() {
        return posNum;
    }

    public static void setPosNum(String posNum) {
        CommonUserData.posNum = posNum;
    }

    public static String getStfNum() {
        return stfNum;
    }

    public static void setStfNum(String stfNum) {
        CommonUserData.stfNum = stfNum;
    }

    public static String getStfNm() {
        return stfNm;
    }

    public static void setStfNm(String stfNm) {
        CommonUserData.stfNm = stfNm;
    }

    public static String getSubDep() {
        return subDep;
    }

    public static void setSubDep(String subDep) {
        CommonUserData.subDep = subDep;
    }
}
