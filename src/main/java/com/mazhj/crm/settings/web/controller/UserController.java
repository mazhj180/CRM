package com.mazhj.crm.settings.web.controller;

import com.mazhj.crm.commons.constant.Constant;
import com.mazhj.crm.commons.json.JsonReturn;
import com.mazhj.crm.commons.utils.DateUtil;
import com.mazhj.crm.settings.pojo.User;
import com.mazhj.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
public class UserController {

    public UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        System.out.println("toLogin.do");
        return "/WEB-INF/pages/settings/qx/user/login.jsp";
    }

    /**
     * 处理登录请求
     * @param params
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println("login.do");
        User user = userService.login(params);
        System.out.println(params.get("isRemPwd").getClass().getName());
        JsonReturn jsonReturn = new JsonReturn();
        if(user == null){
            jsonReturn.setState(Constant.RETURN_STATE_FAIL);
            jsonReturn.setMessage("用户名或密码错误");
            Cookie cookie = new Cookie("test", "test");
            cookie.setMaxAge(60);
            response.addCookie(cookie);
        }else {
            //进一步判断用户是否可以登录
            if (DateUtil.formDateTime(new Date()).compareTo(user.getExpireTime()) > 0){
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("账号已过期");
            }else if (user.getLockState().equals("0")){
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("账号被锁定");
            }else if (!user.getAllowIps().contains(request.getRemoteAddr())){
                jsonReturn.setState(Constant.RETURN_STATE_FAIL);
                jsonReturn.setMessage("IP不安全,禁止登录");
            }else{
                //将用户放入session中
                session.setAttribute(Constant.USER_SESSION,user);
                //响应登录成功信息
                jsonReturn.setState(Constant.RETURN_STATE_SUCCESS);
                //判断是否记住密码
                if ((Boolean) params.get("isRemPwd")){

                    //创建 cookie 记录账号和密码
                    Cookie act = new Cookie("loginAct",user.getLoginAct());
                    act.setMaxAge(10*24*60*60); //设置 cookie 十天失效
                    Cookie pwd = new Cookie("loginPwd",user.getLoginPwd());
                    pwd.setMaxAge(10*24*60*60);
                    //向浏览器发送cookie
                    response.addCookie(act);
                    response.addCookie(pwd);
                }else {
//                    删除cookie
                    Cookie act = new Cookie("loginAct","false");
                    act.setMaxAge(0);
                    Cookie pwd = new Cookie("loginPwd","false");
                    pwd.setMaxAge(0);

                    response.addCookie(act);
                    response.addCookie(pwd);
                }
            }
        }
        return jsonReturn;
    }

    @RequestMapping("/settings/qx/user/loginOut.do")
    public String loginOut(HttpServletResponse response,HttpSession session){
        //清除session
        session.invalidate();
        //清除cookie
        Cookie act = new Cookie("loginAct","out");
        act.setMaxAge(0);
        Cookie pwd = new Cookie("loginPwd","out");
        pwd.setMaxAge(0);

        response.addCookie(act);
        response.addCookie(pwd);

        return "redirect:/";
    }
}
