package com.mazhj.crm.workbench.web.controller;

import com.mazhj.crm.commons.constant.Constant;
import com.mazhj.crm.commons.json.JsonReturn;
import com.mazhj.crm.commons.utils.DateUtil;
import com.mazhj.crm.commons.utils.UUIDUtil;
import com.mazhj.crm.settings.pojo.DicValue;
import com.mazhj.crm.settings.pojo.User;
import com.mazhj.crm.settings.service.DicValueService;
import com.mazhj.crm.settings.service.UserService;
import com.mazhj.crm.workbench.pojo.*;
import com.mazhj.crm.workbench.service.ActivityService;
import com.mazhj.crm.workbench.service.ClueActivityRelationService;
import com.mazhj.crm.workbench.service.ClueRemarkService;
import com.mazhj.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {

    private UserService userService;

    private DicValueService dicValueService;
    
    private ClueService clueService;

    private ClueRemarkService clueRemarkService;

    private ActivityService activityService;

    private ClueActivityRelationService clueActivityRelationService;

    public ClueController(UserService userService, DicValueService dicValueService, ClueService clueService, ClueRemarkService clueRemarkService, ActivityService activityService, ClueActivityRelationService clueActivityRelationService) {
        this.userService = userService;
        this.dicValueService = dicValueService;
        this.clueService = clueService;
        this.clueRemarkService = clueRemarkService;
        this.activityService = activityService;
        this.clueActivityRelationService = clueActivityRelationService;
    }

    @RequestMapping("/workbench/clue/index.do")
    public ModelAndView index(){
        //调用service层方法，查询动态数据
        List<User> userList=userService.queryUsers();
        List<DicValue> appellationList=dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList=dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");

        ModelAndView modelAndView = new ModelAndView("/WEB-INF/pages/workbench/clue/index.jsp");
        //在 model 中添加数据
        modelAndView.addObject("userList",userList);
        modelAndView.addObject("appellationList",appellationList);
        modelAndView.addObject("clueStateList",clueStateList);
        modelAndView.addObject("sourceList",sourceList);

        return modelAndView;
    }

    /**
     * 分页展示 线索
     * @param paraMap
     * @return
     */
    @RequestMapping("/workbench/clue/show.do")
    public @ResponseBody Object showClue(@RequestBody Map<String,Object> paraMap){

        System.out.println("6666666666666"+paraMap.get("company"));
        //查询线索总数
        Long count = clueService.queryClueCountOfByCondition(paraMap);

        List<Clue> clueList = clueService.queryClueByConditionForPage(paraMap);

        HashMap<String, Object> resMap = new HashMap<>();

        //将查询结果封装到map中
        resMap.put("clueList",clueList);
        resMap.put("totalRows",count);

        return resMap;

    }

    /**
     * 保存新建线索
     * @param clue
     * @param session
     * @return
     */
    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody Object saveCreateClue(Clue clue, HttpSession session){
        User user=(User)session.getAttribute(Constant.USER_SESSION);

        //封装参数
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateUtil.formDateTime(new Date()));
        clue.setCreateBy(user.getId());

        JsonReturn jsonReturn=new JsonReturn();
        try {
            //调用service层方法，保存创建的线索
            int ret = clueService.saveCreateClue(clue);

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
     * 显示线索备注,线索明细,线索关联关系
     * @param id
     * @return
     */
    @RequestMapping("/workbench/clue/detailClue.do")
    public ModelAndView detailClue(String id){
        //调用service层方法，查询数据
        Clue clue=clueService.queryClueForDetailById(id);
        List<ClueRemark> remarkList=clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList=activityService.queryActivityForDetailByClueId(id);
        //把数据保存到model中
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/pages/workbench/clue/detail.jsp");
        modelAndView.addObject("clue",clue);
        modelAndView.addObject("remarkList",remarkList);
        modelAndView.addObject("activityList",activityList);
        //请求转发
        return modelAndView;
    }

    @RequestMapping("/workbench/clue/saveCreateClueRemark.do")
    public @ResponseBody Object saveCreateActivityRemark(ClueRemark remark,HttpSession session){

        User user = (User) session.getAttribute(Constant.USER_SESSION);

        //封装参数
        remark.setId(UUIDUtil.getUUID());
        remark.setCreateTime(DateUtil.formDateTime(new Date()));
        remark.setCreateBy(user.getId());
        remark.setEditFlag(Constant.REMARK_EDIT_FLAG_NO_EDITED);

        JsonReturn jsonReturn = new JsonReturn();

        //插入数据
        try {
            int res = clueRemarkService.addClueRemark(remark);
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
     * 删除线索备注
     * @param id
     * @return
     */
    @RequestMapping("/workbench/clue/deleteClueRemarkById.do")
    public @ResponseBody Object deleteClueRemarkById(String id){

        JsonReturn jsonReturn= new JsonReturn();

        try {
            //调用service层方法，删除备注
            int ret = clueRemarkService.removeClueRemarkById(id);

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
     * 修改线索备注
     * @param remark
     * @param session
     * @return
     */
    @RequestMapping("/workbench/clue/saveEditClueRemark.do")
    public @ResponseBody Object saveEditClueRemark(ClueRemark remark, HttpSession session){
        User user=(User) session.getAttribute(Constant.USER_SESSION);
        //封装参数
        remark.setEditTime(DateUtil.formDateTime(new Date()));
        remark.setEditBy(user.getId());
        remark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);

        JsonReturn jsonReturn=new JsonReturn();
        try {
            //调用service层方法，保存修改线索备注
            int ret = clueRemarkService.editClueRemark(remark);

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

    /**
     * 查询该线索需要关联的市场活动
     * @param activityName
     * @param clueId
     * @return
     */
    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId.do")
    public @ResponseBody Object queryActivityForDetailByNameClueId(String activityName,String clueId){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        //调用service层方法，查询市场活动
        List<Activity> activityList=activityService.queryActivityForDetailByNameClueId(map);
        //根据查询结果，返回响应信息
        return activityList;
    }

    /**
     * 保存创建的关联
     * @param activityId
     * @param clueId
     * @return
     */
    @RequestMapping("/workbench/clue/saveBund.do")
    public @ResponseBody Object saveBund(String[] activityId,String clueId){
        //封装参数
        ClueActivityRelation car=null;
        List<ClueActivityRelation> relationList=new ArrayList<>();
        for(String ai:activityId){
            car=new ClueActivityRelation();
            car.setActivityId(ai);
            car.setClueId(clueId);
            car.setId(UUIDUtil.getUUID());
            relationList.add(car);
        }

        JsonReturn jsonReturn=new JsonReturn();
        try {
            //调用service方法，批量保存线索和市场活动的关联关系
            int ret = clueActivityRelationService.saveCreateClueActivityRelationByList(relationList);

            if(ret>0){
                jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);

                List<Activity> activityList=activityService.queryActivityForDetailByIds(activityId);
                jsonReturn.setData(activityList);
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
     * 删除关联
     * @param relation
     * @return
     */
    @RequestMapping("/workbench/clue/unBund.do")
    public @ResponseBody Object saveUnbund(ClueActivityRelation relation){
        JsonReturn jsonReturn=new JsonReturn();
        try {
            //调用service层方法，删除线索和市场活动的关联关系
            int ret = clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(relation);

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
     * 跳转到转换页面
     * @param id
     * @return
     */
    @RequestMapping("/workbench/clue/toConvert.do")
    public ModelAndView toConvert(String id){
        //调用service层方法，查询线索的明细信息
        Clue clue=clueService.queryClueForDetailById(id);
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        //把数据保存到request中
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/pages/workbench/clue/convert.jsp");
        modelAndView.addObject("clue",clue);
        modelAndView.addObject("stageList",stageList);
        //请求转发
        return modelAndView;
    }

    /**
     * 查询线索转换需要的市场活动
     * @param activityName
     * @param clueId
     * @return
     */
    @RequestMapping("/workbench/clue/queryActivityForConvertByNameClueId.do")
    public @ResponseBody Object queryActivityForConvertByNameClueId(String activityName,String clueId){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        //调用service层方法，查询市场活动
        List<Activity> activityList=activityService.queryActivityForConvertByNameClueId(map);
        //根据查询结果，返回响应信息
        return activityList;
    }

    @RequestMapping("/workbench/clue/convertClue.do")
    public @ResponseBody Object convertClue(String clueId,String money,String name,String expectedDate,String stage,String activityId,String isCreateTran,HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran",isCreateTran);
        map.put(Constant.USER_SESSION,session.getAttribute(Constant.USER_SESSION));

        JsonReturn jsonReturn=new JsonReturn();
        try {
            //调用service层方法，保存线索转换
            clueService.saveConvertClue(map);

            jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("系统忙，请稍后重试");
        }

        return jsonReturn;
    }




}
