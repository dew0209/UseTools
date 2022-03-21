package com.example.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* shiro的HelloWorld
* 如何理解shiro
*   这个用户，角色，行为
*  -->
*   这个人   用那些身份           可以干什么
*   me      学生，计算机生       学习，打游戏
* */
public class HelloWorld {
    private static final transient Logger log = LoggerFactory.getLogger(HelloWorld.class);
    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //获取当前的subject
        Subject currentUser = SecurityUtils.getSubject();
        //测试使用session
        Session session = currentUser.getSession();
        session.setAttribute("someKey","aValue");
        String someKey = (String)session.getAttribute("someKey");
        if(someKey.equals("aValue")){
            log.info("测试session存储数据");
        }
        //测试当前用户是否已经被认证，即是否已经登录
        //调用Subject的isAuthenticated()
        if(!currentUser.isAuthenticated()){
            //把用户名和密码封装为UsernamePasswordToken对象
            //把这个对象的用户名和密码去和classpath:shiro.ini里面的配置进行比对
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            try{
                currentUser.login(token);
            }catch (UnknownAccountException uae){
                log.info("未知账户");
                return;
            }catch (IncorrectCredentialsException ice){
                log.info("密码错误");
                return;
            }catch (LockedAccountException e){
                log.info("用户被锁定异常");
                return;
            }catch (AuthenticationException e){
                log.info("异常了");
                return;
            }
        }
        log.info("登录成功");
        //测试角色
        if(currentUser.hasRole("schwartz")){
            log.info("存在schwartz角色");
        }else {
            log.info("不存在schwartz角色");
        }
        //可以干什么
        if (currentUser.isPermitted("lightsaber:xxxx")){
            log.info("lightsaber:xxxx");
        }else {
            log.info("not  lightsaber:xxxx");
        }
        //这个user类型的zhangsan实例干delete
        if(currentUser.isPermitted("user:delete:zhangsan")){
            log.info("你被允许了");
        }
        log.info(""+currentUser.isAuthenticated());
        //执行登出
        currentUser.logout();
        log.info(""+currentUser.isAuthenticated());

    }
}
