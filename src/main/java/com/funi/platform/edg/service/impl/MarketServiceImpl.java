package com.funi.platform.edg.service.impl;

import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.MarketDao;
import com.funi.platform.edg.query.MarketQuery;
import com.funi.platform.edg.service.MarketService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/4/6 0006 10:47]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/4/6 0006 10:47，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
@Service
public class MarketServiceImpl implements MarketService {
    @Resource
    private MarketDao marketDao;
    @Override
    public List<MarketList> findMarketList() {
        return marketDao.findMarketList();
    }
    @Override
    public List findSupplyDeal(MarketQuery marketQuery) {
        List result = new ArrayList<>();
        List x = new ArrayList<>();
        List y1 = new ArrayList<>();
        List y2 = new ArrayList<>();
        List y3 = new ArrayList<>();
        if ("年".equals(marketQuery.getFlagYM())){
            List<MarketSupplyDeal> list = marketDao.findSupplyDealY(marketQuery);
            for (MarketSupplyDeal marketSupplyDeal : list) {
                x.add(marketSupplyDeal.getY());
                y1.add(marketSupplyDeal.getSupply_area());
                y2.add(marketSupplyDeal.getDeal_area());
                y3.add(marketSupplyDeal.getRate());
            }
            result.add(x);
            result.add(y1);
            result.add(y2);
            result.add(y3);
            return result;
        }
        List<MarketSupplyDeal> list = marketDao.findSupplyDealYM(marketQuery);
        for (MarketSupplyDeal marketSupplyDeal : list) {
            x.add(marketSupplyDeal.getYm());
            y1.add(marketSupplyDeal.getSupply_area());
            y2.add(marketSupplyDeal.getDeal_area());
            y3.add(marketSupplyDeal.getRate());
        }
        result.add(x);
        result.add(y1);
        result.add(y2);
        result.add(y3);
        return result;
    }
    @Override
    public List findStockCycle(MarketQuery marketQuery) {
        List result = new ArrayList<>();
        List x = new ArrayList<>();
        List y1 = new ArrayList<>();
        List y2 = new ArrayList<>();
        List<MarketStockCycle> list = marketDao.findStockCycle(marketQuery);
        for (MarketStockCycle marketStockCycle : list) {
            x.add(marketStockCycle.getYm());
            y1.add(marketStockCycle.getSupply_area());
            y2.add(marketStockCycle.getRate());
        }
        result.add(x);
        result.add(y1);
        result.add(y2);
        return result;
    }
    @Override
    public List findSupplyPrice(MarketQuery marketQuery) {
        List result = new ArrayList<>();
        List x = new ArrayList<>();
        List y = new ArrayList<>();
        List y1 = new ArrayList<>();
        if ("年".equals(marketQuery.getFlagYM())){
            List<MarketSupplyDeal> list = marketDao.findSupplyPriceY(marketQuery);
            for (MarketSupplyDeal marketSupplyDeal : list) {
                x.add(marketSupplyDeal.getY());
                y.add(marketSupplyDeal.getAverage_price());
                y1.add(marketSupplyDeal.getDeal_average_decprice());
            }
            result.add(x);
            result.add(y);
            result.add(y1);
            return result;
        }
        List<MarketSupplyDeal> list = marketDao.findSupplyPriceYM(marketQuery);
        for (MarketSupplyDeal marketSupplyDeal : list) {
            x.add(marketSupplyDeal.getYm());
            y.add(marketSupplyDeal.getAverage_price());
            y1.add(marketSupplyDeal.getDeal_average_decprice());
        }
        result.add(x);
        result.add(y);
        result.add(y1);
        return result;

    }
    @Override
    public List findPompAllArea(MarketQuery marketQuery) {
        List result = new ArrayList<>();
        //行政区域统计
        List<MarketPompRegion> listRegion = marketDao.findPompAllArearRegion(marketQuery);
        //项目排行
        List<MarketPompObj> listObj = marketDao.findPompAllAreaObj(marketQuery);
        result.add(listRegion);
        result.add(listObj);
        return result;
    }
    @Override
    public List findPompAllpresell(MarketQuery marketQuery) {
        List result = new ArrayList<>();
        //行政区域统计
        List<MarketPompRegion> listRegion = marketDao.findPompAllpresellRegion(marketQuery);
        //项目排行
        List<MarketPompObj> listObj = marketDao.findPompAllpresellObj(marketQuery);
        result.add(listRegion);
        result.add(listObj);
        return result;
    }
    @Override
    public List findPompAllsell(MarketQuery marketQuery) {
        List result = new ArrayList<>();
        //行政区域统计
        List<MarketPompRegion> listRegion = marketDao.findPompAllsellRegion(marketQuery);
        //项目排行
        List<MarketPompObj> listObj = marketDao.findPompAllsellObj(marketQuery);
        result.add(listRegion);
        result.add(listObj);
        return result;
    }
}
