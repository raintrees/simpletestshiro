import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 授权
 *
 * @author raintrees
 * @date 2019/12/25 13:20
 */
public class AuthorizationTest {

    SimpleAccountRealm realm = new SimpleAccountRealm();

    //添加一个用户，次用户具有两个角色：管理员和用户
    @Before //
    public void addUser(){
        realm.addAccount("hudaxian","1234234","admin","user");
    }

    @Test
    public void testAuthorization(){

        DefaultSecurityManager manager= new DefaultSecurityManager();
        manager.setRealm(realm);

        //主体提交
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("hudaxian","1234234");
        subject.login(token);

        System.out.println("isAuthentication:"+subject.isAuthenticated());

        subject.checkRoles("admin","user");

    }

}

