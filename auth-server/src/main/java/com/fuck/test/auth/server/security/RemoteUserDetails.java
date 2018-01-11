package com.fuck.test.auth.server.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * DESCRIPTION:
 * 自定义userDetails 保存用户正确的密码
 * provider authenticate 会赋值userDetails
 * 添加自定义userId属性
 * @author zouyan
 * @create 2017-12-22 11:04
 * Created by fuck~ on 2017/12/22.
 */
public class RemoteUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final String username;

    private final String password;

    private final String userId;

    private final Set<GrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;

    private RemoteUserDetails(UserBuilder builder) {
        username = builder.username;
        password = builder.password;
        userId = builder.userId;
        authorities = Collections.unmodifiableSet(builder.authorities);
        accountNonExpired = builder.accountNonExpired;
        accountNonLocked = builder.accountNonLocked;
        credentialsNonExpired = builder.credentialsNonExpired;
        enabled = builder.enabled;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static class UserBuilder {
        //required
        private final String username;

        private final String password;

        private final String userId;

        //option
        private Set<GrantedAuthority> authorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());;

        private boolean accountNonExpired = true;

        private boolean accountNonLocked = true;

        private boolean credentialsNonExpired = true;

        private boolean enabled = true;

        public UserBuilder(String username, String password, String userId) {
            this.username = username;
            this.password = password;
            this.userId = userId;
        }

        /**
         * 示例：
         * UserBuilder.authorities("ROLE_USER","ROLE_ADMIN");
         * @param roles
         * @return
         */
        public UserBuilder roles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles.length);
            for (String role : roles) {
                Assert.isTrue(!role.startsWith("ROLE_"), role
                        + " cannot start with ROLE_ (it is automatically added)");
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            return authorities(authorities);
        }

        public UserBuilder authorities(List<? extends GrantedAuthority> authorities) {
            Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
            // Ensure array iteration order is predictable (as per
            // UserDetails.getAuthorities() contract and SEC-717)
            SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(
                    new AuthorityComparator());

            for (GrantedAuthority grantedAuthority : authorities) {
                Assert.notNull(grantedAuthority,
                        "GrantedAuthority list cannot contain any null elements");
                sortedAuthorities.add(grantedAuthority);
            }
            this.authorities = sortedAuthorities;
            return this;
        }

        public UserBuilder accountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public UserBuilder accountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public UserBuilder credentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public UserBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public RemoteUserDetails build() {
            return new RemoteUserDetails(this);
        }
    }


    //权限排序规则
    private static class AuthorityComparator implements Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = 1L;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}
