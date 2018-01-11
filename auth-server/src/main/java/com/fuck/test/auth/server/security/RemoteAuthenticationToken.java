package com.fuck.test.auth.server.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * DESCRIPTION:
 * 自定义RemoteAuthenticationToken
 * provider验证通过生成带有鉴权信息的Authentication
 * @author zouyan
 * @create 2017-12-22 15:31
 * Created by fuck~ on 2017/12/22.
 */
public class RemoteAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private RemoteUserDetails remoteUserDetails;

    public RemoteAuthenticationToken(RemoteUserDetails remoteUserDetails) {
        super(remoteUserDetails.getAuthorities());
        this.remoteUserDetails = remoteUserDetails;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.remoteUserDetails.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return this.remoteUserDetails;
    }
}
