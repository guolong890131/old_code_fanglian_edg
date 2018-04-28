package com.funi.platform.edg.service;

import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.StockDao;
import com.funi.platform.edg.query.StockQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/17 0017 15:26]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/17 0017 15:26，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */

public interface StockService {

    List<StockList> findStockList();

    List<StockLayer> findStockLayer(StockQuery stockQuery);

    List findStockTrend(StockQuery stockQuery);

    List<StockStageUsage> findStockStageUsage(StockQuery stockQuery);

    List<StockFullCycleRegion> findFullCycleRegionStock(StockQuery stockQuery);

    List<StockFieldLayoutRegion> findFieldLayoutRegionStock(StockQuery stockQuery);

    List<StockEstateRegion> findEstateRegionStock(StockQuery stockQuery);

    List<StockCanuseObj> findEdgCanuseStock(StockQuery stockQuery);

    List findEdgCanuseRate(StockQuery stockQuery);

    List<StockPompRegion> findPompRegion(StockQuery stockQuery);

    List<StockStageUsagePie> findRegionPie(StockQuery stockQuery);

    List<StockLsRegion> findStockTrend4lsRegion(StockQuery stockQuery);
}
