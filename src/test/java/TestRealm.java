import com.example.shiro.realm.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author raintrees
 * @date 2019/12/25 14:18
 */
public class TestRealm {

    @Test
    public void testMyRealm(){
        MyRealm myRealm=new MyRealm();

        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(myRealm);

        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("hudaxian", "1234234");

        subject.login(token);
        System.out.println("isAuthentication:"+subject.isAuthenticated());

        subject.checkRoles("admin","user");
        subject.checkPermission("user:add");

    }

}
