package com.example.demo.shiro.config;

import com.example.demo.shiro.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(MyRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 配置登录的url和登录成功的url
        filterFactoryBean.setLoginUrl("/shiro/login");
        filterFactoryBean.setSuccessUrl("/shiro/index");
        // 配置未授权跳转页面
        filterFactoryBean.setUnauthorizedUrl("/shiro/unauthorized");
        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/shiro/login", "anon");
        hashMap.put("/shiro/**", "authc");
        filterFactoryBean.setFilterChainDefinitionMap(hashMap);
        return filterFactoryBean;
    }
}
