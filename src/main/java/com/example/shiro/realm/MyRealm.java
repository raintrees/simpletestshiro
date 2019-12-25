package com.example.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author raintrees
 * @date 2019/12/25 13:41
 */
public class MyRealm extends AuthorizingRealm {

    /**
     * 模拟数据库的数据
     */
    HashMap<String,String> userMap = new HashMap<>(16);
    {
        userMap.put("hudaxian","1234234");
        //设置自定义Realm的名字，随便取
        super.setName("myRealm");
    }

    public String getPasswordByUserName(String username){
        return userMap.get(username);
    }

    public Set<String> getRolesByUserName(String username){
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    public Set<String> getPromissionsByUserName(String usernaem){
        Set<String> promissions = new HashSet<>();
        promissions.add("user:delete");
        promissions.add("user:add");
        return promissions;
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取从主体重传过来的username
        String username = (String) authenticationToken.getPrincipal();
        //通过用户名获取数据库中用户的密码
        String password = getPasswordByUserName(username);
        if(password == null){
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("hudaxian", password, "myRealm");
        return info;
    }

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByUserName(username);
        Set<String> promissions = getPromissionsByUserName(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(promissions);
        return info;

    }
}
