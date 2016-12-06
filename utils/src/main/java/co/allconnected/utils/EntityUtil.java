
package co.allconnected.utils;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 实体转换类
 *
 * @author michael
 * @time 16/12/5 下午9:16
 */
public class EntityUtil {

    /**
     * hashmap转换成JSONObject
     *
     * @param entity
     * @return
     * @author zhengyx 2015-4-13
     */
    public static JSONObject map2JsonObject(HashMap<String, Object> entity) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (entity != null && entity.keySet() != null && entity.keySet().size() > 0) {
                Set<String> keySet = entity.keySet();
                for (String key : keySet) {
                    Object value = entity.get(key);
                    if (value instanceof String) {
                        jsonObject.put(key, (String) value);
                    } else if (value instanceof Integer) {
                        jsonObject.put(key, (Integer) value);
                    } else if (value instanceof Boolean) {
                        jsonObject.put(key, (Boolean) value);
                    } else if (value instanceof Long) {
                        jsonObject.put(key, (Long) value);
                    } else {
                        jsonObject.put(key, (String) value);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 实体类转换成JSONObject
     *
     * @param entity
     * @return
     * @author zhengyx 2015-4-13
     */
    public static JSONObject entity2JsonObject(Object entity) {
        Class<?> userClass = entity.getClass();
        Field[] declaredFields = userClass.getDeclaredFields();
        JSONObject jsonObject = new JSONObject();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                String key = field.getName();
                Method getMethod = getFieldGetMethod(userClass, key);
                try {
                    Object value = getMethod.invoke(entity);
                    if (value instanceof String) {
                        jsonObject.put(key, (String) value);
                    } else if (value instanceof Integer) {
                        jsonObject.put(key, (Integer) value);
                    } else if (value instanceof Boolean) {
                        jsonObject.put(key, (Boolean) value);
                    } else if (value instanceof Long) {
                        jsonObject.put(key, (Long) value);
                    } else if (value instanceof Map) {
                        JSONObject jsonObject1 = map2JsonObject((HashMap<String, Object>) value);
                        jsonObject.put(key, jsonObject1);
                    } else {
                        jsonObject.put(key, (String) value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    /**
     * 实体类转换成ContentValues
     *
     * @param entity
     * @return
     * @author zhengyx 2015-4-13
     */
    public static ContentValues entity2ContentValues(Object entity) {
        Class<?> userClass = entity.getClass();
        Field[] declaredFields = userClass.getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                String key = field.getName();
                Method getMethod = getFieldGetMethod(userClass, key);
                try {
                    Object value = getMethod.invoke(entity);
                    if (value instanceof String) {
                        contentValues.put(key, (String) value);
                    } else if (value instanceof Integer) {
                        contentValues.put(key, (Integer) value);
                    } else if (value instanceof Boolean) {
                        contentValues.put(key, (Boolean) value);
                    } else if (value instanceof Long) {
                        contentValues.put(key, (Long) value);
                    } else {
                        contentValues.put(key, (String) value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return contentValues;
    }

    /**
     * 实体类转换成map
     *
     * @param entity
     * @return
     * @author zhengyx 2015-4-13
     */
    public static HashMap<String, Object> entity2Map(Object entity) {
        Class<?> userClass = entity.getClass();
        Field[] declaredFields = userClass.getDeclaredFields();
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                String name = field.getName();
                Method getMethod = getFieldGetMethod(userClass, name);
                try {
                    Object value = getMethod.invoke(entity);
                    map.put(name, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }


    /**
     * 实体类转换成logstring
     *
     * @param entity
     * @author zhengyx 2015-4-13
     */
    public static void entity2LogString(Object entity) {
        Map<String, Object> logMap = entity2Map(entity);
        if (logMap != null && logMap.entrySet().size() > 0) {
            StringBuilder sbBuilder = new StringBuilder();
            Set<Entry<String, Object>> entrySet = logMap.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object value = entry.getValue();
                sbBuilder.append(key + "='" + value + "',");// 在此处修改你的log拼接字符
            }
            String result = sbBuilder.toString();
            result = result.substring(0, result.length() - 1);
            System.out.println(result);
        }
    }

    /**
     * 获取实体类的get方法
     *
     * @param clazz
     * @param fieldName
     * @return
     * @author zhengyx 2015-4-13
     */
    public static Method getFieldGetMethod(Class<?> clazz, String fieldName) {
        String mn = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            return clazz.getDeclaredMethod(mn);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
