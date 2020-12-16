package com.github.xsi640.common;

import org.apache.commons.lang3.SystemUtils;

/**
 * 操作系统工具类
 */
public class OSUtils {
    /**
     * 当前操作系统名称
     */
    public static final String OS_NAME = System.getProperty("os.name");
    /**
     * 当前操作系统架构
     */
    public static final String PROCESSOR_BIT = System.getProperty("os.arch");
    /**
     * 当前使用的java运行时环境供应商
     */
    public static final String JAVA_VENDOR_NAME = System.getProperty("java.vendor");

    /**
     * 操作系统是否是64bit
     *
     * @return true-64位系统，false-不是64位系统
     */
    public static boolean is64Bit() {
        return OSUtils.PROCESSOR_BIT.contains("64");
    }

    /**
     * 是否是Windows系统
     *
     * @return true-windows系统，false-不是windows系统;
     */
    public static boolean isWindows() {
        return SystemUtils.IS_OS_WINDOWS;
    }

    /**
     * 是否是Mac系统
     *
     * @return true-mac系统，false-不是mac系统
     */
    public static boolean isMacOS() {
        return SystemUtils.IS_OS_MAC_OSX;
    }

    /**
     * 是否是Linux系统
     *
     * @return true-linux系统，false-不是linux系统
     */
    public static boolean isLinux() {
        return SystemUtils.IS_OS_LINUX;
    }

    /**
     * 是否是AIX系统
     *
     * @return true-aix系统，false不是aix系统
     */
    public static boolean isAIX() {
        return OSUtils.OS_NAME.equals("AIX");
    }
}
