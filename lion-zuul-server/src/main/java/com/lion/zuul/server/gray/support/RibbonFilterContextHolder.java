package com.lion.zuul.server.gray.support;

import com.lion.zuul.server.gray.api.RibbonFilterContext;

/**
 * RibbonFilterContextHolder
 * 灰度负载过滤上下文持有类
 *
 * @author Yanzheng https://github.com/micyo202
 * @date 2019/08/06
 * Copyright 2019 Yanzheng. All rights reserved.
 */
public class RibbonFilterContextHolder {

    private static final ThreadLocal<RibbonFilterContext> contextHolder = new InheritableThreadLocal<RibbonFilterContext>() {
        @Override
        protected RibbonFilterContext initialValue() {
            return new DefaultRibbonFilterContext();
        }
    };

    public static RibbonFilterContext getCurrentContext() {
        return contextHolder.get();
    }

    public static void clearCurrentContext() {
        contextHolder.remove();
    }

}
