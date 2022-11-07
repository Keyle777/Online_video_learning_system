package top.keyle.online_video_learning_system.tool;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author TMJIE5200
 * @date 2022-09-28 15:03:57
 * @week 星期三
 * 200:表示成功
 * 500:表示错误，错误信息在msg信息中
 * 501:bean验证错误，无论多少个错误都以map形式返回
 * 502:拦截器拦截到用户token出错
 * 503:异常抛出信息
 * // TODO  fastjson - 原API 一览
 * // public static final Object parse(String text);                                // 把JSON文本parse为JSONObject或者JSONArray
 * // public static final JSONObject parseObject(String text)；                     // 把JSON文本parse成JSONObject
 * // public static final <T> T parseObject(String text, Class<T> clazz);           // 把JSON文本parse为JavaBean
 * // public static final JSONArray parseArray(String text);                        // 把JSON文本parse成JSONArray
 * // public static final <T> List<T> parseArray(String text, Class<T> clazz);      // 把JSON文本parse成JavaBean集合
 * // public static final String toJSONString(Object object);                       // 将JavaBean 序列化为JSON文本
 * // public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean 序列化为带格式的JSON文本
 * // public static final Object toJSON(Object javaObject);                         // 将JavaBean转换为JSONObject或者JSONArray。
 */

@SuppressWarnings("all")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JSONResult{
    //定义
    private Integer code;
    private String msg;
    private Object data;
    public JSONResult(Object data) {
        this.code=200;
        this.msg="success";
        this.data = data;
    }

    public static JSONResult build(Integer status,String msg,Object data){
        return new JSONResult(status,msg,data);
    }

    public static JSONResult success(Object data){
        return new JSONResult(data);
    }

    public static JSONResult success(){
        return new JSONResult(null);
    }
    public static JSONResult errorMsg(String msg){
        return new JSONResult(500,msg,null);
    }

    public static JSONResult errorMap(Object data){
        return new JSONResult(501,"error",data);
    }
    public static JSONResult errorTokenMsg(String msg){
        return new JSONResult(502,msg,null);
    }

    public static JSONResult errorException(String msg){
        return new JSONResult(503,msg,null);
    }

    //===================================================================================================
    //===================================================================================================
    //======================================= 转 Json字符串 ==============================================
    //===================================================================================================
    //===================================================================================================

    /**
     * TODO 将任意对象转为json字符串, 并忽略值为 null的属性（默认）
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * TODO 将任意对象转为json字符串, 并忽略值为null的属性
     */
    public static String toJSONStringNoNull(Object object) {
        return JSON.toJSONString(object, String.valueOf(SerializerFeature.PrettyFormat));
    }

    /**
     * TODO 将任意对象转为json字符串转为json字符串, 并保留值为null的属性
     */
    public static String toJSONStringIsNull(Object object) {
        return JSON.toJSONString(object);
    }


    //===================================================================================================
    //===================================================================================================
    //========================================== 转 Object ==============================================
    //===================================================================================================
    //===================================================================================================

    /**
     * TODO 把 JSON字符串转为 Object （接收时可强转任意javaBand）
     */
    public static Object parse(String text) {
        return JSON.parse(text);
    }

    /**
     * TODO 将JavaBean(任意对象) 转换为 Object, （接收时可强转任意javaBand）
     */
    public static Object toJSON(Object javaObject) {
        return JSON.toJSON(javaObject);
    }

    //===================================================================================================
    //===================================================================================================
    //=========================================  转 JSONObject ==========================================
    //==================================   JSONObject的数据是用 {  } 来表示的）  == ========================
    //===================================================================================================

    /**
     * TODO 把JSON字符串 转为 JSONObject
     */
    public static JSONObject parseObject(String text) {
        return JSON.parseObject(text);
    }

    //===================================================================================================
    //===================================================================================================
    //========================================== 转 JSONArray ===========================================
    //================== JSONArray，顾名思义是由JSONObject构成的数组，用  [ { } , { } , ......  , { } ]  ====
    //===================================================================================================

    /**
     * TODO 把JSON字符串 转为 JSONArray
     */
    public static JSONArray parseArray(String text) {
        return JSON.parseArray(text);
    }


    //===================================================================================================
    //===================================================================================================
    //=========================================== 转 javaBean ===========================================
    //===================================================================================================
    //===================================================================================================

    /**
     * TODO 把JSON字符串转为JavaBean
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }


    //===================================================================================================
    //===================================================================================================
    //============================================= 转实体类 ==============================================
    //===================================================================================================
    //===================================================================================================

    /**
     * TODO 把JSON 字符串转为实体类
     */
    public static <T> T parseEntity(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * TODO 将Map 转换为 实体类
     */
    public static <T> T parseEntity(Map map, Class<T> t) {
        String jsons = toJSONStringIsNull(map);
        T t1 = JSON.parseObject(jsons, t);
        return t1;
    }


    //===================================================================================================
    //===================================================================================================
    //============================================= 转Map ==============================================
    //===================================================================================================
    //===================================================================================================

    /**
     * TODO 把JSON字符串转为 Map
     */
    public static Map parseMap(String text) {
        return JSON.parseObject(text, Map.class);
    }

    /**
     * TODO 将实体类 转换为  Map
     */
    public static <T> Map parseMap(T t) {
        String jsons = toJSONStringIsNull(t);
        Map paramMap = (Map) JSONObject.parseObject(jsons, t.getClass());
        return paramMap;
    }


    //===================================================================================================
    //===================================================================================================
    //============================================= 转List ==============================================
    //===================================================================================================
    //===================================================================================================

    /**
     * TODO 把JSON字符串转为 List
     *
     * @return
     */
    public static List parseList(String text) {
        return JSON.parseObject(text, List.class);
    }

    /**
     * TODO 把JSON 字符串转为 List<Entity/Map/List>  ==>  List<任意对象>
     */
    public static <T> List<T> parseList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }
}
