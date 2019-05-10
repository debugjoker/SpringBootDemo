package me.debugjoker.sell.service.impl;

import me.debugjoker.sell.domain.User;
import me.debugjoker.sell.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-10 11:35
 **/

@Service
public class UserService {

    @Resource
    private UserRepository userDao;

    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    public Set<String> getRoles(String username) {
        return userDao.getRoles(username);
    }

    public Set<String> getPermissions(String username) {
        return userDao.getPermissions(username);
    }
}