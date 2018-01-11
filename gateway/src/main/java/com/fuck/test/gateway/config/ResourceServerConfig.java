package com.fuck.test.gateway.config;

import com.fuck.test.gateway.oauth2.RemoteTokenServices;
import com.fuck.test.gateway.properties.PermitAllConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * DESCRIPTION:
 * 配置资源服务
 * @author zouyan
 * @create 2017-12-21 10:55
 * Created by fuck~ on 2017/12/21.
 */
@Configuration
@EnableResourceServer
@DependsOn({"permitAllConfigProperties"})
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private PermitAllConfigProperties permitAllConfigProperties;

    @Autowired
    private ResourceServerProperties resource;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 配置security放行、保护资源
     * 除配置放行url其他一律鉴权
     * 关闭csrf(跨站请求伪造)
     * 不关闭可能出现Could not verify the provided CSRF token because your session was not found
     * 原因:
     * spring security为防止CSRF攻击,
     * 成功登陆后,会创建csrf token
     * 并且会在每个页面验证登陆成功后创建的csrf token,
     * 若存在静态页面则无法传递csrftoken故报错
     * 不关闭csrf解决方案：
     * 存在的静态页面修改为动态页面且传递csrf token
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(permitAllConfigProperties.getAntPatterns()).permitAll()
                .anyRequest().authenticated();

        ;
    }

    /**
     * 配置ResourceServerTokenServices
     * 目的：
     * 验证token，验证通过的路由转发至相应的后端服务，不通过的返回401 Unauthorized
     * 验证端点为auth-server /oauth/check_token（org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint）
     * 自定义resourceServerTokenServices
     * 原因：
     * auth-server 横向扩展，全部在注册中心注册，统一调用接口
     * 自定义remoteTokenServices集成ribbon负载均衡客户端，统一接口调用auth-server以作token check
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setLoadBalancerClient(loadBalancerClient);
        remoteTokenServices.setClientId(resource.getClientId());
        remoteTokenServices.setClientSecret(resource.getClientSecret());
        remoteTokenServices.setCheckTokenEndpointUrl(resource.getTokenInfoUri());
        resources.tokenServices(remoteTokenServices);
    }
}
