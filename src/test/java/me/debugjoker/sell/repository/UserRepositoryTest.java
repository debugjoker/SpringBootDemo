package me.debugjoker.sell.repository;

import com.alibaba.fastjson.JSON;
import me.debugjoker.sell.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-10 11:14
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;

    public static final String USERNAME = "admin";

    @Test
    public void getByUsername() {
        User user = userRepository.getByUsername(USERNAME);
        Assert.assertEquals(USERNAME, user.getUsername());
    }

    @Test
    public void getRoles() {
        Set<String> roleSet = userRepository.getRoles(USERNAME);
        Set<String> permissionSet = userRepository.getPermissions(USERNAME);
        System.out.println(JSON.toJSONString(roleSet));
        System.out.println(JSON.toJSONString(permissionSet));
    }

}