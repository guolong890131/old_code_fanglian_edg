package com.funi.platform.edg.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Package: [com.funi.platform.cdfg.utils]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/4/19 0019 15:30]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/4/19 0019 15:30，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class RequestMapUtils {
    public  static Map<String,Object> getRequestMap(HttpServletRequest request) {

        Map<String,Object>  result = new HashMap<String,Object>();
        Map  map =  request.getParameterMap() ;
        Set set = map.keySet() ;
        for(Object key : set) {
            Object value = map.get(key) ;
            if(value instanceof String[]) {
                String _value = ((String[]) value)[0] ;
                result.put(String.valueOf(key).trim(), _value.trim()) ;
            }
        }
        //处理分页
        Integer page = result.get("page")!=null ? Integer.parseInt(String.valueOf(result.get("page"))):null;
        Integer rows = result.get("rows")!=null ? Integer.parseInt(String.valueOf(result.get("rows"))):null;
        if(page!=null && rows !=null) {
            int start = (page-1) * rows + 1;
            int end = page*rows ;
            result.put("start", start) ;
            result.put("end" ,  end) ;
        }
        //处理排序
        String sort = result.get("sort")!=null ? String.valueOf(result.get("sort")) : null ;
        String column = result.get("order")!=null ? String.valueOf(result.get("order")) : null ;
        if(sort!=null && column!=null) {
            String sortColumn = sort + " " + column ;
            result.put("sortColumn", sortColumn) ;
        }
        return result ;
    }

//    public  static Map<String,Object> getRequestMapSO(HttpServletRequest request) throws UnsupportedEncodingException {
//        Map<String,Object>  result = new HashMap<String,Object>();
//        Map  map =  request.getParameterMap() ;
//        Set set = map.keySet() ;
//        for(Object key : set) {
//            Object value = map.get(key) ;
//            if(value instanceof String[]) {
//                String _value = ((String[]) value)[0] ;
//                String encoding =Tools.getEncoding(_value);
//                if("ISO-8859-1".equals(encoding)){
//                    String ss=new String(_value.getBytes("ISO-8859-1"),"utf-8");
//                    result.put(String.valueOf(key).trim(), ss.trim()) ;
//                }else {
//                    result.put(String.valueOf(key).trim(), _value.trim()) ;
//                }
//            }
//        }
//        return result ;
//    }
    /**
     *
     * @Title: getRequestMaps
     * @Description: TODO(获取页面字符串参数)
     * @param request
     * @return Map<String,String>
     * @throws
     */
    public static Map<String,String> getRequestMaps(HttpServletRequest request) {
        Map<String,String>  result = new HashMap<String,String>();
        Map  map =  request.getParameterMap() ;
        Set  set = map.keySet() ;
        for(Object key : set) {
            Object value = map.get(key) ;
            if(value instanceof String[]) {
                String _value = ((String[]) value)[0] ;
                result.put(String.valueOf(key).trim(), _value.trim()) ;
            }
        }
        return result ;
    }

    /**
     *
     * @Title: getRequestListMap
     * @Description: TODO(参数名对应多个值时使用)
     * 默认获取get请求时的参数
     * @param @param request
     * @param @return    设定文件
     * @return Map<String,List<Object>>    返回类型
     * @throws
     */
    public static Map<String, List<Object>> getRequestListMap(
            HttpServletRequest request) {
        Map<String,List<Object>> map = new HashMap<String, List<Object>>();
        Enumeration enumeration = request.getParameterNames();
        Object item;
        while(enumeration.hasMoreElements()){
            String name = (String)enumeration.nextElement();
            String []values = null;
            values = request.getParameterValues(name);
            List<Object> list = new ArrayList<Object>();
            for(int i=0;i<values.length;i++){
                list.add(values[i]);
            }
            map.put(name,list);
        }
        return map;
    }
}
