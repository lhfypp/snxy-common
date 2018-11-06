package com.snxy;

import com.snxy.common.util.StringUtil;

public class TestIsChinese {
    public static void main(String[] args) {
        String a = "大家好";

        System.out.println(StringUtil.isChinese(a));
    }
}
