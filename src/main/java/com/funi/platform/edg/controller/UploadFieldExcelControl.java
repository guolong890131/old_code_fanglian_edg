package com.funi.platform.edg.controller;/**
 * Created by as on 2017/2/22 0022.
 */

import com.alibaba.fastjson.JSON;
import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.bo.*;
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
@RequestMapping("/uploadFieldExcel")
public class UploadFieldExcelControl {

    @Autowired
    private loginService service;
    @Resource
    private UploadFieldExcelService uploadFieldExcelService;

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


    @RequestMapping("/importField")
    public String importFill(HttpSession session)  {
        return "/pages/importField";
    }

    @RequestMapping("/importFieldList")
    public String importFillList(HttpSession session)  {
        String uuid = (String)session.getAttribute("uuid");
        //清空该UUID下的所有_IMP表数据
        uploadFieldExcelService.cleanAll(uuid);
        return "/pages/importFieldList";
    }
    @RequestMapping("/importFieldListEtl")
    public String importFieldListEtl(HttpSession session)  {
        String uuid = (String)session.getAttribute("uuid");
        //清空该UUID下的所有_IMP表数据
        uploadFieldExcelService.cleanAll(uuid);
        return "/pages/importFieldListEtl";
    }

    /**
     * 土地导入----------------------------------------------------------------------------------------------
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
        if(check(session,"field")){
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
        List<FieldVo> list=null;
        try {
            MyExcelImporter<FieldVo> importer = new MyExcelImporter<FieldVo>();
            list = importer.setStartRows(startRows).setKeyIndex(keyIndex)
                    .setHeadRows(headRows).setTitleRows(titleRows)
                    .setItemClass(FieldVo.class)
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
            fieldToIMP(list,request,session);
        }else {
            rv.setSuccess(false);
            rv.setMessage("Excel表中无数据！");
            return rv;
        }
        return rv;
    }
    @RequestMapping(value = "fileuploadEtl")
    @ResponseBody
    public ResultView uploadFileExcel( HttpServletRequest request, HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("土地导入");
        String mydate = request.getParameter("mydate");
//        JSONArray array = JSONArray.parseArray(mydate);
//        ArrayList<FieldVo> fieldVos = new ArrayList<>();
//        for(int i=0;i<array.size();i++){
//
//        }
        List<FieldVo> list = JSON.parseArray(mydate, FieldVo.class);

        //重置标识
        request.getSession().setAttribute("s_num", 0);//成功条数标识
        request.getSession().setAttribute("f_num", 0);//失败条数标识
        request.getSession().setAttribute("ipmState", false);//结束标识

        if(list.size()>0){
            fieldToIMP(list,request,session);
        }else {
            rv.setSuccess(false);
            rv.setMessage("没有选中数据");
            return rv;
        }

        return rv;
    }

    @RequestMapping(value="fileUploadState")
    @ResponseBody
    public ResultView fileUploadState(HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("土地导入状态");
        Integer s = (Integer)session.getAttribute("s_num");
        Integer f = (Integer)session.getAttribute("f_num");
        Object ipmState = session.getAttribute("ipmState");
        rv.addAttribute("s",s);
        rv.addAttribute("f",f);
        rv.addAttribute("ipmState",ipmState);
        return rv;
    }

    @Async
    public void fieldToIMP(List<FieldVo> list,HttpServletRequest request,HttpSession session){
        String uuid = (String)session.getAttribute("uuid");
        for (FieldVo field : list) {

            if(null== field.getFno_new() || "" == field.getFno_new()){
                Map map = new HashMap<>();
                map.put("fno_new","NULL");
                map.put("inputer_id",uuid);
                map.put("import_state","宗地号为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFctrno_new() || "" == field.getFctrno_new()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","土地合同证号为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getRegion() || "" == field.getRegion()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","区域为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFtradetype() || "" == field.getFtradetype()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","出让方式为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFaddress() || "" == field.getFaddress()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","宗地位置为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFowner() || "" == field.getFowner()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","竞得者为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbbprice()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","起拍楼面地价为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbprice_mu()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","起拍单价为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFtrprice_mu()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","成交单价为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFtrbprice()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","成交楼面地价为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFtrtime()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","出让时间为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFfarea()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","净用地面积为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFusage_type()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","土地用途为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFplanusg()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","规划用地性质为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbtarea_down()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容总建筑面积下限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbtarea_up()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容总建筑面积上限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFhouse_down()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容住宅建筑面积下限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFhouse_up()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容住宅建筑面积上限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbusiness_down()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容商服建筑面积下限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbusiness_up()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容商服建筑面积上限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbself_down()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容自持商服建筑面积下限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbself_up()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容自持商服建筑面积上限为空");
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
                uploadFieldExcelService.insertFieldErr(map);
            }else  if(null== field.getFbuse_down()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容自用商服建筑面积下限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFbuse_up()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","计容自用商服建筑面积上限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFrate_dow()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","容积率下限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else  if(null== field.getFrate_up()){
                Map map = new HashMap<>();
                map.put("fno_new",field.getFno_new());
                map.put("inputer_id",uuid);
                map.put("import_state","容积率上限为空");
                uploadFieldExcelService.insertFieldErr(map);
                Integer f = (Integer)session.getAttribute("f_num");
                request.getSession().setAttribute("f_num", f+1);//失败条数标识
            }else{
                try {
                    field.setInputer_id(uuid);
                    uploadFieldExcelService.insertFieldToIMP(field);
                    Integer s = (Integer)session.getAttribute("s_num");
                    request.getSession().setAttribute("s_num", s+1);//成功条数标识
                }catch (Exception e){
                    Map map = new HashMap<>();
                    map.put("fno_new",field.getFno_new());
                    map.put("inputer_id",uuid);
                    if(e.getMessage()!=null){
                        String[] message= e.getMessage().split(";");
                        map.put("import_state",message[0]);
                    }else {
                        map.put("import_state",e.getMessage());
                    };
                    uploadFieldExcelService.insertFieldErr(map);

                    Integer f = (Integer)session.getAttribute("f_num");
                    request.getSession().setAttribute("f_num", f+1);//失败条数标识
                    System.out.println(e);
                }
            }

        }

        request.getSession().setAttribute("ipmState", true);//插入结束标识
    }

    //查询导入临时表
    @RequestMapping("/queryFieldIMP")
    @ResponseBody
    public PageResult<Field> queryFieldIMP(SearchField searchField,HttpSession session)  {
        if(check(session,"field")){
            return null;
        }
        String uuid = (String)session.getAttribute("uuid");
        PageResult<Field> result = uploadFieldExcelService.queryFieldIMP(searchField,uuid);
        return result;
    }


    @RequestMapping(value="transferTable")
    @ResponseBody
    public ResultView transferTable(HttpSession session) throws Exception {
        ResultView rv = ResultView.newResult("土地_b_imp导入_b");
//        if(check(session,"field")){
//            rv.setSuccess(false);
//            rv.setMessage("无操作权限");
//            return rv;
//        }
        String uuid = (String)session.getAttribute("uuid");
        //检查是否有数据可以导入到正式表
        int i = uploadFieldExcelService.checkNum(uuid);
        if(i<=0){
            rv.setSuccess(false);
            rv.setMessage("无可导入的数据！");
            return rv;
        }
        try {
            uploadFieldExcelService.transferTable(uuid);

            //清空该UUID下的所有_IMP表数据
            uploadFieldExcelService.cleanAll(uuid);
        }catch (Exception e){
            rv.setSuccess(false);
            rv.setMessage(e.getMessage());
            return rv;
        }
        return rv;
    }

}
