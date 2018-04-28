package com.funi.platform.edg.controller;

import com.funi.platform.edg.utils.DownLoadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/12/26.
 */
@Controller
@RequestMapping("/downLoad")
public class DownLoadController {
    @RequestMapping("/index")
    public ModelAndView downLoad(HttpServletRequest request,HttpServletResponse response,String FilenameSign) throws Exception{
        String path=request.getSession().getServletContext().getRealPath("/download");
        String fileName="";
        if("1".equals(FilenameSign)){
             fileName="Manual(GT).pdf";
        }else if("2".equals(FilenameSign)){
             fileName="Manual(GH).pdf";
        }else{
             fileName="Manual(JW).pdf";
        }
        String filePath=path+"//"+fileName;
        DownLoadUtil.downLoadFile(filePath,response,fileName,"pdf");
        return  null;
    }

    @RequestMapping("/excel")
    public ModelAndView downLoadExcel(HttpServletRequest request,HttpServletResponse response,String FilenameSign) throws Exception{
        String path=request.getSession().getServletContext().getRealPath("/download");
        String fileName="";
        if("1".equals(FilenameSign)){
            fileName="template_GT.xls";
        }else if("2".equals(FilenameSign)){
            fileName="template_GH.xls";
        }else if("3".equals(FilenameSign)){
            fileName="template_SG.xls";
        }else if("4".equals(FilenameSign)){
            fileName="template_JG.xls";
        }
        String filePath=path+"//"+fileName;
        DownLoadUtil.downLoadFile(filePath,response,fileName,"xls");
        return  null;
    }
}
