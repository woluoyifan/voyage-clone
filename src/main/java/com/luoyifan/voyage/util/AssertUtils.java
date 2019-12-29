package com.luoyifan.voyage.util;

import com.luoyifan.voyage.exception.BizException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author EvanLuo
  */
public class AssertUtils {

    public static void isNull(Object obj, String msg) {
        if (obj != null) {
            fail(msg);
        }
    }

    public static void nonNull(Object obj, String msg) {
        if (obj == null) {
            fail(msg);
        }
    }

    public static void nonBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            fail(msg);
        }
    }

    public static void isTrue(boolean expression, String msg){
        if(!expression){
            fail(msg);
        }
    }

    public static void fail(String msg) {
        throw new BizException(msg);
    }
}
