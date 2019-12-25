import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 认证
 *
 * @author raintrees
 * @date 2019/12/25 11:28
 */
public class AuthenticationTest {

    SimpleAccountRealm realm = new SimpleAccountRealm();


    @Before  //在方法执行之前添加一个用户
    public void addUser(){
        realm.addAccount("胡大仙","1234234");
    }


    @Test
    public void testAuthentication(){
        //1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);

        //2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);   //设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject();
        //这里的username，password是要认证的
        UsernamePasswordToken token = new UsernamePasswordToken("胡大仙","1234234");
        //登录
        subject.login(token);
        System.out.println("isAuthentication:"+subject.isAuthenticated());
        //退出登录
        subject.logout();
        System.out.println("isAuthentication:"+subject.isAuthenticated());

    }
}
