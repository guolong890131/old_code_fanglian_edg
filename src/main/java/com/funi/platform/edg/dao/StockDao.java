package com.funi.platform.edg.dao;

import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.query.StockQuery;

import java.util.List;

/**
 * @Package: [com.funi.platform.edg.dao]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/17 0017 15:27]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/17 0017 15:27，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public interface StockDao {
    List<StockList> findStockList();

    List<StockLayer> findStockLayer(StockQuery stockQuery);

    List<StockTrend> findStockTrendY(StockQuery stockQuery);

    List<StockTrend> findStockTrendYM(StockQuery stockQuery);

    List<StockStageUsage> findStockStageUsage(StockQuery stockQuery);

    List<StockFullCycleRegion> findFullCycleRegionStock(StockQuery stockQuery);

    List<StockFieldLayoutRegion> findFieldLayoutRegionStock(StockQuery stockQuery);

    List<StockEstateRegion> findEstateRegionStock(StockQuery stockQuery);

    List<StockCanuseArea> findEdgCanuseStock(StockQuery stockQuery);

    List<StockCanuseReta> findEdgCanuseRate(StockQuery stockQuery);

    List<StockPompRegion> findPompRegion(StockQuery stockQuery);

    List<StockStageUsagePie> findRegionPie(StockQuery stockQuery);

    List<StockLsRegion> findRegion4ls(StockQuery stockQuery);

    List<StockTrend> findStockTrendY4ls(StockQuery stockQuery);

    List<StockTrend> findStockTrendYM4ls(StockQuery stockQuery);
}
