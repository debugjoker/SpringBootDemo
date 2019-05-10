package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-10 11:01
 **/

public interface UserRepository{

    // 通过用户名获取用户信息
    @Select("select * from t_user where username = #{username}")
    User getByUsername(String username);

    // 通过用户名获取用户拥有的所有角色
    @Select("select r.rolename from t_user u,t_role r " +
            "where u.role_id = r.id and u.username = #{username}")
    Set<String> getRoles(String username);

    // 通过用户名获取用户拥有的所有权限
    @Select("select p.permissionname from t_user u,t_role r,t_permission p " +
            "where u.role_id = r.id and p.role_id = r.id and u.username = #{username}")
    Set<String> getPermissions(String username);
}
