package com.funi.platform.edg.controller;/**
 * Created by as on 2017/2/22 0022.
 */

import com.funi.framework.data.migrate.excel.ExcelImporter;
import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.bo.Layout;
import com.funi.platform.edg.bo.Menu;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchLayout;
import com.funi.platform.edg.service.UploadLayoutExcelService;
import com.funi.platform.edg.service.loginService;
import com.funi.platform.edg.utils.MyExcelImporter;
import com.funi.platform.edg.utils.PropertiesUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/22 0022 10:09]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/22 0022 10:09，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/uploadLayoutExcel")
public class UploadLayoutExcelControl {

    @Autowired
    private loginService service;
    @Resource
    private UploadLayoutExcelService uploadLayoutExcelService;

    private int titleRows=1;//Excel标题行数
    private int headRows=1;//Excel表头行数
    private int startRows=3;//Excel数据起始行
    private Integer keyIndex=0;//数据列主键位置

    /**
     * 读取Excel参数配置
     */
    private void reload(){
        PropertiesUtil prop =  new PropertiesUtil();
        this.titleRows=Integer.parseInt(prop.getPropertiesMsg("excel","titleRows4Layout").trim());
        this.headRows=Integer.parseInt(prop.getPropertiesMsg("excel","headRows4Layout").trim());
        this.startRows=Integer.parseInt(prop.getPropertiesMsg("excel","startRows4Layout").trim());
        String key = prop.getPropertiesMsg("excel","keyIndex4Layout").trim();
        if("NULL".equals(key)){
            this.keyIndex=null;
        }else {
            this.keyIndex=Integer.parseInt(key);
        }
    }

    /**
     * 操作权限检查
     * @param session
     * @param type  角色类型 Layout，layout，build
     * @return
     */
    public boolean check(HttpSession session,String type){
        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        String role = (String)session.getAttribute("role");
        if(account==null && password==null){
            return true;
        }
        List<Menu> listMenu = service.findMenu(role);
        boolean flag = true;
        for (Menu menu : listMenu) {
            if(menu.getMenu_code().equals(type)){
                flag=false;
            }
        }
        return flag;
    }

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    private File multipartToFile(MultipartFile multfile) throws IOException {
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;
        //这个myfile是MultipartFile的
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        //手动创建临时文件
        if(file.length() < 2048){
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                    file.getName());
            multfile.transferTo(tmpFile);
            return tmpFile;
        }
        return file;
    }


    @RequestMapping("/importLayout")
    public String importFill(HttpSession session)  {
        return "/pages/importLayout";
    }

    @RequestMapping("/importLayoutList")
    public String importFillList(HttpSession session) {
        String uuid = (String)session.getAttribute("uuid");
        //清空该UUID下的所有_IMP表数据
        uploadLayoutExcelService.cleanAll(uuid);
        return "/pages/importLayoutList";
    }

    /**
     * 规划导入----------------------------------------------------------------------------------------------
     * @param n_file
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value="fileupload")
    @ResponseBody
    public ResultView uploadFileExcel(MultipartFile n_file, HttpServletRequest request, HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("土地导入");
        if(check(session,"layout")){
            rv.setSuccess(false);
            rv.setMessage("无操作权限");
            return rv;
        }

        File file = multipartToFile(n_file);
        String fileName=n_file.getOriginalFilename();
        if(!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")){
            rv.setSuccess(false);
            rv.setMessage("文件类型错误");
            return rv;
        }
        long len = file.length()/10240000;
        if(len>1){
            rv.setSuccess(false);
            rv.setMessage("文件过大");
            return rv;
        }
        reload();
        List<Layout> list=null;
        try {
            MyExcelImporter<Layout> importer = new MyExcelImporter<Layout>();
            list = importer.setStartRows(startRows).setKeyIndex(keyIndex)
                    .setHeadRows(headRows).setTitleRows(titleRows)
                    .setItemClass(Layout.class)
                    .execute(new FileInputStream(file));
        }catch (Exception e){
            rv.setSuccess(false);
            rv.setMessage(e.getMessage());
            return rv;
        }
        //重置标识
        request.getSession().setAttribute("s_num", 0);//成功条数标识
        request.getSession().setAttribute("f_num", 0);//失败条数标识
        request.getSession().setAttribute("ipmState", false);//结束标识
        if(list.size()>0){
            LayoutToIMP(list,request,session);
        }else {
            rv.setSuccess(false);
            rv.setMessage("Excel表中无数据！");
            return rv;
        }
        return rv;
    }

    @RequestMapping(value="fileUploadState")
    @ResponseBody
    public ResultView fileUploadState(HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("规划导入状态");
        Integer s = (Integer)session.getAttribute("s_num");
        Integer f = (Integer)session.getAttribute("f_num");
        Object ipmState = session.getAttribute("ipmState");
        rv.addAttribute("s",s);
        rv.addAttribute("f",f);
        rv.addAttribute("ipmState",ipmState);
        return rv;
    }

    @Async
    public void LayoutToIMP(List<Layout> list,HttpServletRequest request,HttpSession session){
        String uuid = (String)session.getAttribute("uuid");
        for (Layout layout : list) {
            if(null == layout.getLayout_no() || "" == layout.getLayout_no()){
                Map map = new HashMap<>();
                map.put("layout_no","NULL");
                map.put("inputer_id",uuid);
                map.put("import_state","规划证号为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getFctrno() || "" == layout.getFctrno()){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","土地合同编号为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getRegion() || "" == layout.getRegion()){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","区域为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getConstruction_project_name() || "" == layout.getConstruction_project_name()){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","项目名称为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getAddress() || "" == layout.getAddress()){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","项目地址为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getConstruction_unit() || "" == layout.getConstruction_unit()){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设单位为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null==layout.getPrint_date()||""==layout.getPrint_date()){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","发证日期为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识

            }else if(null == layout.getConstruction_scale()){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","规划总建筑面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getC2()){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","其中计容积率住宅面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getC3() ){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","其中计容商业面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getC4() ){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","其中计容办公面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getC5() ){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","其中计容酒店面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getC6() ){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","其中计容其他面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getC7() ){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","其中计容机动车位面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getC8() ){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","其中不计容机动车位面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null == layout.getC9() ){
                Map map = new HashMap<>();
                map.put("layout_no",layout.getLayout_no());
                map.put("inputer_id",uuid);
                map.put("import_state","其中不计容其他面积为空");
                uploadLayoutExcelService.insertLayoutErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else{
                try {
                    layout.setInputer_id(uuid);
                    uploadLayoutExcelService.insertLayoutToIMP(layout);
                    Integer s = (Integer)session.getAttribute("s_num");
                    request.getSession().setAttribute("s_num", s+1);//成功条数标识
                }catch (Exception e){
                    Map map = new HashMap<>();
                    map.put("layout_no",layout.getLayout_no());
                    map.put("inputer_id",uuid);
                    if(e.getMessage()!=null){
                        String[] message= e.getMessage().split(";");
                        map.put("import_state",message[0]);
                    }else {
                        map.put("import_state",e.getMessage());
                    }
                    uploadLayoutExcelService.insertLayoutErr(map);

                    Integer f = (Integer)session.getAttribute("f_num");
                    request.getSession().setAttribute("f_num", f+1);//失败条数标识
                    System.out.println(e);
                }
            }
        }
        request.getSession().setAttribute("ipmState", true);//插入结束标识
    }

    //查询导入临时表
    @RequestMapping("/queryLayoutIMP")
    @ResponseBody
    public PageResult<Layout> queryLayoutIMP(SearchLayout searchLayout,HttpSession session)  {
        if(check(session,"layout")){
            return null;
        }
        String uuid = (String)session.getAttribute("uuid");
        PageResult<Layout> result = uploadLayoutExcelService.queryLayoutIMP(searchLayout,uuid);
        return result;
    }


    @RequestMapping(value="transferTable")
    @ResponseBody
    public ResultView transferTable(HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("规划_b_imp导入_b");
        if(check(session,"layout")){
            rv.setSuccess(false);
            rv.setMessage("无操作权限");
            return rv;
        }
        String uuid = (String)session.getAttribute("uuid");

        //检查是否有数据可以导入到正式表
        int i = uploadLayoutExcelService.checkNum(uuid);
        if(i<=0){
            rv.setSuccess(false);
            rv.setMessage("无可导入的数据！");
            return rv;
        }
        try {
            uploadLayoutExcelService.transferTable(uuid);

            //清空该UUID下的所有_IMP表数据
            uploadLayoutExcelService.cleanAll(uuid);
        }catch (Exception e){
            rv.setSuccess(false);
            rv.setMessage(e.getMessage());
            return rv;
        }
        return rv;
    }

}
