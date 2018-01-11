package com.fuck.test.auth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.fuck.test.auth.server.security.RemoteAuthenticationProvider;
/**
 * DESCRIPTION:
 * 配置AuthenticationManager
 * 添加自定义AuthenticationProvider
 * 便于自身业务login的验证
 * AuthenticationProcessingFilter --> AuthenticationManager(ProviderManager...) --> AuthenticationProvider
 * 例：
 * org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 * @author zouyan
 * @create 2017-12-21 19:34
 * Created by fuck~ on 2017/12/21.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RemoteAuthenticationProvider RemoteAuthenticationProvider;
    /**
     * AuthenticationManager --> ProviderManager
     * --> List<AuthenticationProvider> --> AbstractAuthenticationToken
     * 不同provider对应不同验证方式
     * 添加自定义provider（使用包含自身业务的验证）
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(RemoteAuthenticationProvider);
    }
}
