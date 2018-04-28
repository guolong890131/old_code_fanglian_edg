package com.funi.platform.edg.service;

import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.MarketDao;
import com.funi.platform.edg.query.MarketQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/16 0016 13:06]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/16 0016 13:06，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */

public interface MarketService {

    List<MarketList> findMarketList();

    List findSupplyDeal(MarketQuery marketQuery);

    List findStockCycle(MarketQuery marketQuery);

    List findSupplyPrice(MarketQuery marketQuery);

    List findPompAllArea(MarketQuery marketQuery);

    List findPompAllpresell(MarketQuery marketQuery);

    List findPompAllsell(MarketQuery marketQuery);
}
