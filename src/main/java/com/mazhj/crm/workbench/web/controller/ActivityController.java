package com.mazhj.crm.workbench.web.controller;

import com.mazhj.crm.commons.constant.Constant;
import com.mazhj.crm.commons.json.JsonReturn;
import com.mazhj.crm.commons.utils.DateUtil;
import com.mazhj.crm.commons.utils.HSSFUtil;
import com.mazhj.crm.commons.utils.UUIDUtil;
import com.mazhj.crm.settings.pojo.User;
import com.mazhj.crm.settings.service.UserService;
import com.mazhj.crm.workbench.pojo.Activity;
import com.mazhj.crm.workbench.pojo.ActivityRemark;
import com.mazhj.crm.workbench.service.ActivityRemarkService;
import com.mazhj.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
public class ActivityController {

    private UserService userService;

    private ActivityService activityService;

    private ActivityRemarkService activityRemarkService;

    public ActivityController(UserService userService, ActivityService activityService,ActivityRemarkService activityRemarkService) {
        this.userService = userService;
        this.activityService = activityService;
        this.activityRemarkService = activityRemarkService;
    }

    /**
     * 跳转市场活动主页
     * @return
     */
    @RequestMapping("/workbench/activity/index.do")
    public ModelAndView index(){
        //获取合法用户列表
        List<User> userList = userService.queryUsers();
        //创建视图
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/pages/workbench/activity/index.jsp");
        //将合法用户列表传给视图
        modelAndView.addObject("users",userList);

        return modelAndView;

    }

    /**
     * 保存创建市场活动
     * @param activity
     * @param session
     * @return
     */
    @RequestMapping("/workbench/activity/save.do")
    public @ResponseBody Object saveActivity(@RequestBody Activity activity, HttpSession session){
        //获取当前用户
        User user = (User) session.getAttribute(Constant.USER_SESSION);
        //填充属性
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateBy(user.getId());
        activity.setEditTime(DateUtil.formDateTime(new Date()));
        activity.setCreateTime(DateUtil.formDateTime(new Date()));

        //返回的Json对象
        JsonReturn jsonReturn = new JsonReturn();
        //插入活动
        try {
            int res = activityService.save(activity);
            if (res > 0){
                jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
            }else {
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("系统忙,请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("系统忙,请稍后重试");
        }
        return jsonReturn;

    }

    /**
     * 展示市场活动列表
     * @param paraMap
     * @return
     */
    @RequestMapping("/workbench/activity/show.do")
    public @ResponseBody Object showActivities(@RequestBody Map<String,Object> paraMap){

        //查询活动总数
        long counts = activityService.countOf(paraMap);
        //分页查询活动
        List<Activity> activityList = activityService.queryForPage(paraMap);
        //将结果封装到map中
        Map<String, Object> resMap = new HashMap<String,Object>();
        resMap.put("activityList",activityList);
        resMap.put("totalRows",counts);

        return resMap;
    }

    /**
     * 批量删除市场活动
     * @param ids
     * @return
     */
    @RequestMapping("/workbench/activity/delete.do")
    public @ResponseBody Object deleteActivities(String[] ids){
        Arrays.stream(ids).iterator().forEachRemaining(System.out::println);
        JsonReturn jsonReturn = new JsonReturn();
        try {
            int res = activityService.deleteByIds(ids);
            if (res > 0){
                jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
            }else {
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("系统忙,请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("系统忙,请稍后重试");
        }
        return jsonReturn;
    }

    /**
     * 查询指定活动的全部信息
     * @param id
     * @return
     */
    @RequestMapping("/workbench/activity/select.do")
    public @ResponseBody Object selectByID(String id){
        //查询符合条件的活动
        Activity activity = activityService.selectByID(id);

        return activity;
    }

    /**
     * 修改指定活动
     * @param activity
     * @param session
     * @return
     */
    @RequestMapping("/workbench/activity/modify.do")
    public @ResponseBody Object modifyActivity(Activity activity,HttpSession session){
        //获取当前登录的用户
        User user = (User)session.getAttribute(Constant.USER_SESSION);
        //属性填充 设置本次修改人,修改时间
        activity.setEditTime(DateUtil.formDateTime(new Date()));
        activity.setEditBy(user.getId());

        JsonReturn jsonReturn = new JsonReturn();

        try {
            int res = activityService.modify(activity);
            if (res > 0){
                jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
            }else {
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("系统忙,请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("系统忙,请稍后重试");
        }

        return jsonReturn;
    }

    /**
     * 批量导出市场活动
     * @param response
     * @throws Exception
     */
    @RequestMapping("/workbench/activity/export.do")
    public void exportActivities(HttpServletResponse response) throws Exception {

        //调用mapper查询所有活动
        List<Activity> activityList = activityService.queryAll();

        //再内存中 创建虚拟的 xls文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建者");
        cell=row.createCell(9);
        cell.setCellValue("修改时间");
        cell=row.createCell(10);
        cell.setCellValue("修改者");
        //将查到的市场活动映射到虚拟xls文件
        Activity activity = null;
        for (int i = 0; i < activityList.size(); i++) {
            activity = activityList.get(i);
            row = sheet.createRow(i + 1);
            cell=row.createCell(0);
            cell.setCellValue(activity.getId());
            cell=row.createCell(1);
            cell.setCellValue(activity.getOwner());
            cell=row.createCell(2);
            cell.setCellValue(activity.getName());
            cell=row.createCell(3);
            cell.setCellValue(activity.getStartDate());
            cell=row.createCell(4);
            cell.setCellValue(activity.getEndDate());
            cell=row.createCell(5);
            cell.setCellValue(activity.getCost());
            cell=row.createCell(6);
            cell.setCellValue(activity.getDescription());
            cell=row.createCell(7);
            cell.setCellValue(activity.getCreateTime());
            cell=row.createCell(8);
            cell.setCellValue(activity.getCreateBy());
            cell=row.createCell(9);
            cell.setCellValue(activity.getEditTime());
            cell=row.createCell(10);
            cell.setCellValue(activity.getEditBy());
        }
        //设置响应内容类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置响应头
        response.addHeader("Content-Disposition","attachment;filename=activities.xls");
        //将内存中的数据放入输出流
        OutputStream out = response.getOutputStream();

//        ToExcelUtil.toExcel(out,activityList,Activity.class,"市场活动列表");
        workbook.write(out);
        //释放资源
        workbook.close();
        out.close();

    }

    /**
     * 导出选中的市场活动
     * @param ids
     * @param response
     * @throws IOException
     */
    @RequestMapping("/workbench/activity/exportP.do")
    public void exportPartActivities(String[] ids,HttpServletResponse response) throws IOException {
        //获取符合条件的市场活动
        List<Activity> activityList = activityService.queryByIds(ids);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建者");
        cell=row.createCell(9);
        cell.setCellValue("修改时间");
        cell=row.createCell(10);
        cell.setCellValue("修改者");

        Activity activity = null;
        for (int i = 0; i < activityList.size(); i++) {
            activity = activityList.get(i);
            row = sheet.createRow(i + 1);
            cell=row.createCell(0);
            cell.setCellValue(activity.getId());
            cell=row.createCell(1);
            cell.setCellValue(activity.getOwner());
            cell=row.createCell(2);
            cell.setCellValue(activity.getName());
            cell=row.createCell(3);
            cell.setCellValue(activity.getStartDate());
            cell=row.createCell(4);
            cell.setCellValue(activity.getEndDate());
            cell=row.createCell(5);
            cell.setCellValue(activity.getCost());
            cell=row.createCell(6);
            cell.setCellValue(activity.getDescription());
            cell=row.createCell(7);
            cell.setCellValue(activity.getCreateTime());
            cell=row.createCell(8);
            cell.setCellValue(activity.getCreateBy());
            cell=row.createCell(9);
            cell.setCellValue(activity.getEditTime());
            cell=row.createCell(10);
            cell.setCellValue(activity.getEditBy());
        }

        //设置响应内容类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置响应头
        response.addHeader("Content-Disposition","attachment;filename=activitiesP.xls");
        //将内存中的数据放入输出流
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        //释放资源
        workbook.close();
        out.close();

    }

    /**
     * 导入市场活动
     * @param multipartFile
     * @param session
     * @return
     */
    @RequestMapping("/workbench/activity/import.do")
    public @ResponseBody Object importActivities(MultipartFile multipartFile,HttpSession session){
        User user = (User) session.getAttribute(Constant.USER_SESSION);
        JsonReturn jsonReturn = new JsonReturn();
        try {
            InputStream in = multipartFile.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activities = new ArrayList<Activity>();
            //解析excel文件
            for (int i = 0; i < sheet.getLastRowNum(); i++) {//遍历每一行
                row = sheet.getRow(i);
                activity=new Activity();
                activity.setId(UUIDUtil.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtil.formDateTime(new Date()));
                activity.setCreateBy(user.getId());
                for (int j = 0; j < row.getLastCellNum(); j++) {//遍历每一行的每一个单元格
                    cell = row.getCell(j);
                    //获取每单元格 字符串形式的值
                    String cellValue = HSSFUtil.getCellValueForStr(cell);
                    if(j == 0){
                        activity.setName(cellValue);
                    }else if(j == 1){
                        activity.setStartDate(cellValue);
                    }else if(j == 2){
                        activity.setEndDate(cellValue);
                    }else if(j == 3){
                        activity.setCost(cellValue);
                    }else if(j == 4){
                        activity.setDescription(cellValue);
                    }
                }
                //遍历一行结束后得到一个完整的Activity对象
                //将其放入列表中
                activities.add(activity);
            }
            //执行批量插入工作
            int res = activityService.importActivities(activities);
            //设置响应信息
            jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
            jsonReturn.setImportRet(Integer.toString(res));
        } catch (IOException e) {
            e.printStackTrace();
            //插入失败
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("系统忙,请稍后重试");
        }

        return jsonReturn;
    }

    /**
     * 跳转到市场活动页面，
     * 展示市场活动明细
     * @param id
     * @return
     */
    @RequestMapping("/workbench/activity/detailActivity.do")
    public ModelAndView toDetail(String id){
        //查询市场活动信息
        Activity activity = activityService.queryActivityForDetailById(id);
        //查询市场活动明细
        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/pages/workbench/activity/detail.jsp");
        //将数据保存到 model中
        modelAndView.addObject("activity",activity);
        modelAndView.addObject("remarkList",remarkList);

        return modelAndView;
    }

    /**
     * 保存市场活动备注
     * @param remark
     * @param session
     * @return
     */
    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public @ResponseBody Object saveCreateActivityRemark(ActivityRemark remark,HttpSession session){
        User user = (User) session.getAttribute(Constant.USER_SESSION);

        //封装参数
        remark.setId(UUIDUtil.getUUID());
        remark.setCreateTime(DateUtil.formDateTime(new Date()));
        remark.setCreateBy(user.getId());
        remark.setEditFlag(Constant.REMARK_EDIT_FLAG_NO_EDITED);

        JsonReturn jsonReturn = new JsonReturn();

        //插入数据
        try {
            int res = activityRemarkService.saveCreateActivityRemark(remark);
            if (res > 0){
                jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
                jsonReturn.setData(remark);
            }else {
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("系统忙,请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("系统忙,请稍后重试");
        }
        return jsonReturn;
    }

    /**
     * 删除市场活动备注
     * @param id
     * @return
     */
    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    public @ResponseBody Object deleteActivityRemarkById(String id){

        JsonReturn jsonReturn= new JsonReturn();
        try {
            //调用service层方法，删除备注
            int ret = activityRemarkService.deleteActivityRemarkById(id);

            if(ret>0){
                jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
            }else{
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("系统忙，请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("系统忙，请稍后重试");
        }

        return jsonReturn;
    }

    /**
     * 编辑市场活动备注
     * @param remark
     * @param session
     * @return
     */
    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    public @ResponseBody Object saveEditActivityRemark(ActivityRemark remark,HttpSession session){
        User user=(User) session.getAttribute(Constant.USER_SESSION);
        //封装参数
        remark.setEditTime(DateUtil.formDateTime(new Date()));
        remark.setEditBy(user.getId());
        remark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);

        JsonReturn jsonReturn=new JsonReturn();
        try {
            //调用service层方法，保存修改的市场活动备注
            int ret = activityRemarkService.saveEditActivityRemark(remark);

            if(ret>0){
                jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
                jsonReturn.setData(remark);
            }else{
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("系统忙，请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("系统忙，请稍后重试");
        }

        return jsonReturn;
    }



}
