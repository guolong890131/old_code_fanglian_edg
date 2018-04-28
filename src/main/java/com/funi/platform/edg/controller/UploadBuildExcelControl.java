package com.funi.platform.edg.controller;

import com.funi.framework.data.migrate.excel.ExcelImporter;
import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.service.UploadBuildExcelService;
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
@RequestMapping("/uploadBuildExcel")
public class UploadBuildExcelControl {
    @Autowired
    private loginService service;
    @Resource
    private UploadBuildExcelService uploadBuildExcelService;

    private int titleRows=1;//Excel标题行数
    private int headRows=1;//Excel表头行数
    private int startRows=3;//Excel数据起始行
    private Integer keyIndex=0;//数据列主键位置


    /**
     * 读取Excel参数配置
     */
    private void reload(){
        PropertiesUtil prop =  new PropertiesUtil();
        titleRows=Integer.parseInt(prop.getPropertiesMsg("excel","titleRows4Field").trim());
        headRows=Integer.parseInt(prop.getPropertiesMsg("excel","headRows4Field").trim());
        startRows=Integer.parseInt(prop.getPropertiesMsg("excel","startRows4Field").trim());
        String key = prop.getPropertiesMsg("excel","keyIndex4Field").trim();
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


    @RequestMapping("/importBuild")
    public String importFill(HttpSession session)  {
        return "/pages/importBuild";
    }

    @RequestMapping("/importBuildList")
    public String importFillList(HttpSession session)  {
        String uuid = (String)session.getAttribute("uuid");
        //清空该UUID下的所有_IMP表数据
        uploadBuildExcelService.clearAll(uuid);
        return "/pages/importBuildList";
    }

    /**
     * 施工导入----------------------------------------------------------------------------------------------
     * @param n_file
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value="fileupload")
    @ResponseBody
    public ResultView uploadFileExcel(MultipartFile n_file, HttpServletRequest request, HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("施工导入");
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

        List<Build> list=null;
        try {
            MyExcelImporter<Build> importer = new MyExcelImporter<Build>();
            list = importer.setStartRows(startRows).setKeyIndex(keyIndex)
                    .setHeadRows(headRows).setTitleRows(titleRows)
                    .setItemClass(Build.class)
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
            buildToIMP(list, request, session);
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
        ResultView rv = ResultView.newResult("施工导入状态");
        Integer s = (Integer)session.getAttribute("s_num");
        Integer f = (Integer)session.getAttribute("f_num");
        Object ipmState = session.getAttribute("ipmState");
        rv.addAttribute("s",s);
        rv.addAttribute("f",f);
        rv.addAttribute("ipmState",ipmState);
        return rv;
    }

    @Async
    public void buildToIMP(List<Build> list,HttpServletRequest request,HttpSession session){
        String uuid = (String)session.getAttribute("uuid");
        for (Build build : list) {
            if(null== build.getBuild_no() || "" == build.getBuild_no()){
                Map map = new HashMap<>();
                map.put("build_no","NULL");
                map.put("inputer_id",uuid);
                map.put("import_state","施工证号为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getLayout_no() || "" == build.getLayout_no()) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设工程规划许可证号为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getRegion() || "" == build.getRegion()) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","区域为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getBuild_name() || "" == build.getBuild_name()) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","工程名称为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getConstruction_unit() || "" == build.getConstruction_unit()) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设单位为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getAddress() || "" == build.getAddress()) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设地址为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getPrint_date() || "" == build.getPrint_date()) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","发证日期为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getBuild_date() || "" == build.getBuild_date()) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","合同开工日期为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getCompulete_date() || "" == build.getCompulete_date()) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","合同竣工日期为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getConstruction_scale() ) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设规模为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getS1() ) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设规模其中住宅施工面积为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getS2() ) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设规模其中商业施工面积为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getS3() ) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设规模其中办公施工面积为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getS4() ) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设规模其中酒店施工面积为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else if(null== build.getS5() ) {
                Map map = new HashMap<>();
                map.put("build_no",build.getBuild_no());
                map.put("inputer_id",uuid);
                map.put("import_state","建设规模其中车位施工面积为空");
                uploadBuildExcelService.insertBuildErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else{
                try {
                    build.setInputer_id(uuid);
                    uploadBuildExcelService.insertBuildToIMP(build);
                    Integer s = (Integer)session.getAttribute("s_num");
                    request.getSession().setAttribute("s_num", s+1);//成功条数标识
                }catch (Exception e){
                    Map map = new HashMap<>();
                    map.put("build_no",build.getBuild_no());
                    map.put("inputer_id",uuid);
                    if(e.getMessage()!=null){
                        String[] message= e.getMessage().split(";");
                        map.put("import_state",message[0]);
                    }else {
                        map.put("import_state",e.getMessage());
                    }
                    uploadBuildExcelService.insertBuildErr(map);

                    Integer f = (Integer)session.getAttribute("f_num");
                    request.getSession().setAttribute("f_num", f+1);//失败条数标识
                    System.out.println(e);
                }
            }

        }
        request.getSession().setAttribute("ipmState", true);//插入结束标识
    }

    //查询导入临时表
    @RequestMapping("/queryBuildIMP")
    @ResponseBody
    public PageResult<Build> queryFieldIMP(SearchField searchField,HttpSession session)  {
        if(check(session,"build")){
            return null;
        }
        String uuid = (String)session.getAttribute("uuid");
        PageResult<Build> result = uploadBuildExcelService.queryFieldIMP(searchField,uuid);
        return result;
    }


    @RequestMapping(value="transferTable")
    @ResponseBody
    public ResultView transferTable(HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("施工_b_imp导入_b");
        if(check(session,"build")){
            rv.setSuccess(false);
            rv.setMessage("无操作权限");
            return rv;
        }
        String uuid = (String)session.getAttribute("uuid");

        //检查是否有数据可以导入到正式表
        int i = uploadBuildExcelService.checkNum(uuid);
        if(i<=0){
            rv.setSuccess(false);
            rv.setMessage("无可导入的数据！");
            return rv;
        }

        try {
            uploadBuildExcelService.transferTable(uuid);

            //清空该UUID下的所有_IMP表数据
            uploadBuildExcelService.clearAll(uuid);
        }catch (Exception e){
            rv.setSuccess(false);
            rv.setMessage(e.getMessage());
            return rv;
        }
        return rv;
    }


}
