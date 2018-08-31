package com.snxy.common.util;

import java.util.UUID;


public class UUIDUtil {

    private UUIDUtil(){

    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static void main(String[] args) {
        System.out.println(getUUID()
        );
    }
}
