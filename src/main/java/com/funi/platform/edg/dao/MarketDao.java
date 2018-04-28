package com.funi.platform.edg.dao;

import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.query.MarketQuery;

import java.util.List;

/**
 * @Package: [com.funi.platform.edg.dao]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/16 0016 13:04]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/16 0016 13:04，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public interface MarketDao {

    List<MarketList> findMarketList();

    List<MarketSupplyDeal> findSupplyDealY(MarketQuery marketQuery);

    List<MarketSupplyDeal> findSupplyDealYM(MarketQuery marketQuery);

    List<MarketStockCycle> findStockCycle(MarketQuery marketQuery);

    List<MarketSupplyDeal> findSupplyPriceY(MarketQuery marketQuery);

    List<MarketSupplyDeal> findSupplyPriceYM(MarketQuery marketQuery);

    List<MarketPompObj> findPompAllAreaObj(MarketQuery marketQuery);

    List<MarketPompRegion> findPompAllArearRegion(MarketQuery marketQuery);

    List<MarketPompRegion> findPompAllpresellRegion(MarketQuery marketQuery);

    List<MarketPompObj> findPompAllpresellObj(MarketQuery marketQuery);

    List<MarketPompRegion> findPompAllsellRegion(MarketQuery marketQuery);

    List<MarketPompObj> findPompAllsellObj(MarketQuery marketQuery);

}
