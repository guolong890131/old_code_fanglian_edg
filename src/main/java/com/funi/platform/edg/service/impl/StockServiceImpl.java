package com.funi.platform.edg.service.impl;

import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.StockDao;
import com.funi.platform.edg.query.StockQuery;
import com.funi.platform.edg.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/4/6 0006 10:49]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/4/6 0006 10:49，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
@Service
public class StockServiceImpl implements StockService {
    @Resource
    private StockDao stockDao;

    public List<StockList> findStockList() {
        return stockDao.findStockList();
    }

    public List<StockLayer> findStockLayer(StockQuery stockQuery) {
        return stockDao.findStockLayer(stockQuery);
    }

    public List findStockTrend(StockQuery stockQuery) {
        List result = new ArrayList<>();
        List x = new ArrayList<>();
        List y = new ArrayList<>();
        List<StockTrend> list = null;
        if ("年".equals(stockQuery.getFlagYM())){
            if("".equals(stockQuery.getRegion_code())|| null==stockQuery.getRegion_code()){
                list = stockDao.findStockTrendY(stockQuery);
            }else {
                list = stockDao.findStockTrendY4ls(stockQuery);
            }
            for (StockTrend stockTrend : list) {
                x.add(stockTrend.getY());
                y.add(stockTrend.getStock_area());
            }
            result.add(x);
            result.add(y);
            return result;
        }
        if("".equals(stockQuery.getRegion_code())|| null==stockQuery.getRegion_code()){
            list = stockDao.findStockTrendYM(stockQuery);
        }else {
            list = stockDao.findStockTrendYM4ls(stockQuery);
        }
        for (StockTrend stockTrend : list) {
            x.add(stockTrend.getYm());
            y.add(stockTrend.getStock_area());
        }
        result.add(x);
        result.add(y);
        return result;
    }

    public List<StockStageUsage> findStockStageUsage(StockQuery stockQuery) {
        return stockDao.findStockStageUsage(stockQuery);
    }

    public List<StockFullCycleRegion> findFullCycleRegionStock(StockQuery stockQuery) {
        return stockDao.findFullCycleRegionStock(stockQuery);
    }

    public List<StockFieldLayoutRegion> findFieldLayoutRegionStock(StockQuery stockQuery) {
        return stockDao.findFieldLayoutRegionStock(stockQuery);
    }

    public List<StockEstateRegion> findEstateRegionStock(StockQuery stockQuery) {
        return stockDao.findEstateRegionStock(stockQuery);
    }

    public List<StockCanuseObj> findEdgCanuseStock(StockQuery stockQuery) {
        List<StockCanuseObj> result = new ArrayList<>();
        //usage_code=1001 1002 1003 1004
        stockQuery.setUsage_code("1001");
        List<StockCanuseArea> list1001 = stockDao.findEdgCanuseStock(stockQuery);
        stockQuery.setUsage_code("1002");
        List<StockCanuseArea> list1002 = stockDao.findEdgCanuseStock(stockQuery);
        stockQuery.setUsage_code("1003");
        List<StockCanuseArea> list1003 = stockDao.findEdgCanuseStock(stockQuery);
        stockQuery.setUsage_code("1004");
        List<StockCanuseArea> list1004 = stockDao.findEdgCanuseStock(stockQuery);
        int len = list1001.size();
        for(int i=0;i<len;i++){
            StockCanuseObj obj = new StockCanuseObj();
            obj.setRegin(list1001.get(i).getRegion_name());
            obj.setHouse(list1001.get(i).getArea());
            obj.setBusiness(list1002.get(i).getArea());
            obj.setOffice(list1003.get(i).getArea());
            obj.setStall(list1004.get(i).getArea());
            result.add(obj);
        }
        return result;
    }

    public List findEdgCanuseRate(StockQuery stockQuery) {
        List result = new ArrayList<>();
        //usage_code=1001 1002 1003 1004
        stockQuery.setUsage_code("1001");
        List<StockCanuseReta> list1001 = stockDao.findEdgCanuseRate(stockQuery);
        stockQuery.setUsage_code("1002");
        List<StockCanuseReta> list1002 = stockDao.findEdgCanuseRate(stockQuery);
        stockQuery.setUsage_code("1003");
        List<StockCanuseReta> list1003 = stockDao.findEdgCanuseRate(stockQuery);
        int len = list1001.size();
        for(int i=0;i<len;i++){
            StockCanuseObj obj = new StockCanuseObj();
            obj.setRegin(list1001.get(i).getRegion_name());
            obj.setHouse(list1001.get(i).getRate());
            obj.setBusiness(list1002.get(i).getRate());
            obj.setOffice(list1003.get(i).getRate());
            result.add(obj);
        }
        return result;
    }

    public List<StockPompRegion> findPompRegion(StockQuery stockQuery) {
        return stockDao.findPompRegion(stockQuery);
    }

    public List<StockStageUsagePie> findRegionPie(StockQuery stockQuery) {
        return stockDao.findRegionPie(stockQuery);
    }



    @Override
    public List<StockLsRegion> findStockTrend4lsRegion(StockQuery stockQuery) {
        return stockDao.findRegion4ls(stockQuery);
    }
}
