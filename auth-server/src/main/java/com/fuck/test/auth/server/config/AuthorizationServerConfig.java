package com.fuck.test.auth.server.config;

import com.fuck.test.auth.server.security.TokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * DESCRIPTION:
 * 配置授权服务
 * @author zouyan
 * @create 2017-12-21 16:22
 * Created by fuck~ on 2017/12/21.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public JdbcClientDetailsService clientDetailsService(DataSource dataSource) {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public JdbcTokenStore tokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    @Primary
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore(dataSource));
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService(dataSource));
        tokenServices.setTokenEnhancer(accessTokenConverter());
        return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        return new TokenEnhancer();
    }

    /**
     * 客户端详情服务配置
     * oauth_client_details(client_id、client_secret等)信息的认证
     * 基于内存/JDBC的客户端信息服务
     * (计划：若集成于各应用，申请新应用时需要向管理服务申请开通应用，此时系统生成该应用的client_id、client_secret)
     * 客户端对象重要属性：
     * client_id：（必须）客户端id
     * client_secret：（对于可信任的客户端是必须的）客户端的私密信息
     * scope：客户端的作用域。如果scope未定义或者为空（默认值），则客户端作用域不受限制
     * authorizedGrantTypes：授权给客户端使用的权限类型。默认值为空
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService(dataSource));
    }

    /**
     * 配置authorization、token、token services
     * authenticationManager 身份管理器
     * tokenStore（memory、jdbc、jwt、redis...）
     * tokenServices 对token管理操作（DefaultTokenServices使用uuid创建、刷新token等 详见DefaultTokenServices注释）
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore(dataSource))
                .tokenServices(authorizationServerTokenServices())
                .accessTokenConverter(accessTokenConverter());
    }

    /**
     * 配置token endpoint安全约束
     * /oauth/check_token 验证token端点 设置非匿名用户才可访问
     * /oauth/token_key提供公有密匙的端点(jwt) 设置放行
     * 也可直接设置在HttpSecurity设置
     * 最后是设置在HttpSecurity
     * 详见org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration
     * Spring EL https://docs.spring.io/spring-security/site/docs/4.2.2.RELEASE/reference/htmlsingle/
     * 原因：
     * 在网关侧做了远程调用验证token，故需要设置放行策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients();//允许客户表单认证
    }

}
