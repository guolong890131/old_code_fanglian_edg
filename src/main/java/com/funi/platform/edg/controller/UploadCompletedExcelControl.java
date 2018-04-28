package com.funi.platform.edg.controller;

import com.funi.framework.data.migrate.excel.ExcelImporter;
import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.service.UploadCompletedExcelService;
import com.funi.platform.edg.service.UploadFieldExcelService;
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
 * Created by Administrator on 2017/2/25.
 */
@Controller
@RequestMapping("/uploadCompletedExcel")
public class UploadCompletedExcelControl {

    @Autowired
    private loginService service;
    @Resource
    private UploadCompletedExcelService uploadCompletedExcelService;

    private int titleRows=1;//Excel标题行数
    private int headRows=1;//Excel表头行数
    private int startRows=3;//Excel数据起始行
    private Integer keyIndex=0;//数据列主键位置


    /**
     * 读取Excel参数配置
     */
    private void reload(){
        PropertiesUtil prop =  new PropertiesUtil();
        this.titleRows=Integer.parseInt(prop.getPropertiesMsg("excel","titleRows4Completed").trim());
        this.headRows=Integer.parseInt(prop.getPropertiesMsg("excel","headRows4Completed").trim());
        this.startRows=Integer.parseInt(prop.getPropertiesMsg("excel","startRows4Completed").trim());
        String key = prop.getPropertiesMsg("excel","keyIndex4Completed").trim();
        if("NULL".equals(key)){
            this.keyIndex=null;
        }else {
            this.keyIndex=Integer.parseInt(key);
        }

    }

    /**
     * 操作权限检查
     * @param session
     * @param type  角色类型 field，layout，build
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
     * @throws java.io.IOException
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


    @RequestMapping("/importCompleted")
    public String importFill(HttpSession session)  {
        return "/pages/importCompleted";
    }

    @RequestMapping("/importCompletedList")
    public String importFillList(HttpSession session)  {
        String uuid = (String)session.getAttribute("uuid");
        //清空该UUID下的所有_IMP表数据
        uploadCompletedExcelService.clearAll(uuid);
        return "/pages/importCompletedList";
    }

    /**
     * 竣工导入----------------------------------------------------------------------------------------------
     * @param n_file
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value="fileupload")
    @ResponseBody
    public ResultView uploadFileExcel(MultipartFile n_file, HttpServletRequest request, HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("竣工导入");
        if(check(session,"build")){
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
        List<Completed> list=null;
        try {
            MyExcelImporter<Completed> importer = new MyExcelImporter<Completed>();
            list = importer.setStartRows(startRows).setKeyIndex(keyIndex)
                    .setHeadRows(headRows).setTitleRows(titleRows)
                    .setItemClass(Completed.class)
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
            CompletedToIMP(list,request,session);
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
        ResultView rv = ResultView.newResult("竣工导入状态");
        Integer s = (Integer)session.getAttribute("s_num");
        Integer f = (Integer)session.getAttribute("f_num");
        Object ipmState = session.getAttribute("ipmState");
        rv.addAttribute("s",s);
        rv.addAttribute("f",f);
        rv.addAttribute("ipmState",ipmState);
        return rv;
    }

    @Async
    public void CompletedToIMP(List<Completed> list,HttpServletRequest request,HttpSession session) {
        String uuid = (String) session.getAttribute("uuid");
        for (Completed completed : list) {
            if(null== completed.getCompleted_no() || "" == completed.getCompleted_no()){
                Map map = new HashMap<>();
                map.put("completed_no","NULL");
                map.put("inputer_id",uuid);
                map.put("import_state","竣工证号为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getLayout_no() || "" == completed.getLayout_no()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设工程规划许可证号为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getBuild_no() || "" == completed.getBuild_no()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","施工许可证书编号为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getRegion() || "" == completed.getRegion()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","区域为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getCompleted_date() || "" == completed.getCompleted_date()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","竣工备案时间为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getCompleted_scale() || "" == completed.getCompleted_scale()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","竣工规模为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getJ1() || "" == completed.getJ1()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","竣工规模其中住宅竣工面积为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getJ2() || "" == completed.getJ2()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","竣工规模其中商业竣工面积为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getJ3() || "" == completed.getJ3()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","竣工规模其中办公竣工面积为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getJ4() || "" == completed.getJ4()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","竣工规模其中酒店竣工面积为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== completed.getJ5() || "" == completed.getJ5()){
                Map map = new HashMap<>();
                map.put("completed_no",completed.getCompleted_no());
                map.put("inputer_id",uuid);
                map.put("import_state","竣工规模其中车位竣工面积为空");
                uploadCompletedExcelService.insertCompletedErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else{
                try {
                    completed.setInputer_id(uuid);
                    uploadCompletedExcelService.insertCompletedToIMP(completed);
                    Integer s = (Integer) session.getAttribute("s_num");
                    request.getSession().setAttribute("s_num", s + 1);//成功条数标识
                } catch (Exception e) {
                    Map map = new HashMap<>();
                    map.put("completed_no", completed.getCompleted_no());
                    map.put("inputer_id", uuid);
                    if(e.getMessage()!=null){
                        String[] message = e.getMessage().split(";");
                        map.put("import_state", message[0]);
                    }else {
                        map.put("import_state",e.getMessage());
                    }
                    uploadCompletedExcelService.insertCompletedErr(map);

                    Integer f = (Integer) session.getAttribute("f_num");
                    request.getSession().setAttribute("f_num", f + 1);//失败条数标识
                    System.out.println(e);
                }
            }

        }
        request.getSession().setAttribute("ipmState", true);//插入结束标识
    }

    //查询导入临时表
    @RequestMapping("/queryCompletedIMP")
    @ResponseBody
    public PageResult<Completed> queryFieldIMP(SearchField searchField,HttpSession session)  {
        if(check(session,"build")){
            return null;
        }
        String uuid = (String)session.getAttribute("uuid");
        PageResult<Completed> result = uploadCompletedExcelService.queryFieldIMP(searchField,uuid);
        return result;
    }


    @RequestMapping(value="transferTable")
    @ResponseBody
    public ResultView transferTable(HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("竣工_b_imp导入_b");
        if(check(session,"build")){
            rv.setSuccess(false);
            rv.setMessage("无操作权限");
            return rv;
        }
        String uuid = (String)session.getAttribute("uuid");

        //检查是否有数据可以导入到正式表
        int i = uploadCompletedExcelService.checkNum(uuid);
        if(i<=0){
            rv.setSuccess(false);
            rv.setMessage("无可导入的数据！");
            return rv;
        }

        try {
            uploadCompletedExcelService.transferTable(uuid);

            //清空该UUID下的所有_IMP表数据
            uploadCompletedExcelService.clearAll(uuid);
        }catch (Exception e){
            rv.setSuccess(false);
            rv.setMessage(e.getMessage());
            return rv;
        }
        return rv;
    }
}
