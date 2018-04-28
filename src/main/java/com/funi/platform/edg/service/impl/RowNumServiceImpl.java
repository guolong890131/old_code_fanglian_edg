package com.funi.platform.edg.service.impl;/**
 * Created by as on 2017/2/17 0017.
 */

import com.funi.platform.edg.bo.RowNum;
import com.funi.platform.edg.dao.rowNumber.RowNumDao;
import com.funi.platform.edg.service.RowNumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/17 0017 9:16]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/17 0017 9:16，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Service
@Transactional(readOnly = true,propagation = Propagation.NEVER)
public class RowNumServiceImpl implements RowNumService {

    @Resource()
    private RowNumDao rowNumDao;

    @Override
    public Map queryByRank(String rankno, String rankdate) throws ParseException {
        HashMap map = new HashMap();
        HashMap rMap=new HashMap();
        String s = aleph(rankno);
        map.put("H_initial",s);
        map.put("H_cometime",rankdate);
        RowNum rn=rowNumDao.query(map);
        if(rn==null||"".equals(rn)) {
            rn.setTopNo("00000");
            rMap.put("rn",rn);
            rMap.put("waitNo",0);
            return  rMap;
        }
        //等候人数
        int waitNum=stringToInt(rankno,s)-stringToInt(rn.getTopNo(),s);
        rMap.put("rn",rn);
        rMap.put("waitNo",waitNum);
        return rMap;
    }



    public String aleph(String s){
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c >= 48 && c <= 58) {
                return  s.substring(0,i);
            }
        }
        return "";
    }

    public int stringToInt(String s,String initail){
        String substring = s.substring(initail.length());
        int i = Integer.parseInt(substring);
        return i;
    }
}
