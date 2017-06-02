package com.naga.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

/**
 * BeanCopy处理用缓冲Copier
 */
public class CachedBeanCopier {

	/** BeanCopy缓存 */
    static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<String, BeanCopier>();
    
    /**
     * beanCopy处理
     * @param srcObj Copy元Bean
     * @param destObj Copy目标Bean
     */
    public static void copy(Object srcObj, Object destObj) {
    	copy(srcObj, destObj, new DefaultBeanCopierConverter());
    }
    
    /**
     * beanCopy处理
     * @param srcObj Copy元Bean
     * @param destObj Copy目标Bean
     * @param converter 转换器
     */
    public static void copy(Object srcObj, Object destObj, Converter converter) {
        String key = genKey(srcObj.getClass(), destObj.getClass(), converter);
        BeanCopier copier = null;
        if (!BEAN_COPIERS.containsKey(key)) {
        	if (converter == null) {
                copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
        	} else {
                copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), true);
        	}
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        copier.copy(srcObj, destObj, converter);
    }
  
    /**
     * 获取缓冲Key
     * @param srcClazz 源类
     * @param destClazz 目标类
     * @param converter 转换器
     * @return
     */
    private static String genKey(Class<?> srcClazz, Class<?> destClazz, Converter converter) {
    	if (converter == null) {
            return srcClazz.getName() + destClazz.getName();
    	} else {
    		return srcClazz.getName() + destClazz.getName() + converter.getClass().getName();
    	}
    }
    
	/**
	 * 默认的转换器
	 * 对下列简单类型和包装类型进行转换
	 *  int Integer  
	 *  long Long
	 *  boolean Boolean
	 *  short Short
	 *  float Float
	 *  double Double
	 */
	public static class DefaultBeanCopierConverter implements Converter {

		@SuppressWarnings("rawtypes")
		@Override
		public Object convert(Object value, Class target, Object context) {
			if (value == null) {
				return null;
			} else if (target == value.getClass()) {
				return value;
			} else if (target == int.class) {
				if (value instanceof Integer) {
					return ((Integer)value).intValue();
				}
			} else if (target == long.class) {
				if (value instanceof Long) {
					return ((Long)value).longValue();
				}
			} else if (target == boolean.class) {
				if (value instanceof Boolean) {
					return ((Boolean)value).booleanValue();
				}
			} else if (target == short.class) {
				if (value instanceof Short) {
					return ((Short)value).shortValue();
				}
			} else if (target == float.class) {
				if (value instanceof Float) {
					return ((Float)value).floatValue();
				}
			} else if (target == double.class) {
				if (value instanceof Double) {
					return ((Double)value).doubleValue();
				}
			} 
			return null;
		}
	}

}  