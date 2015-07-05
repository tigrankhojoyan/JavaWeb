/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.my.batis;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @author tigran
 */
public interface UserMapper {

    @Insert("INSERT INTO USER(email_id, password, first_name, last_name)"
            + " VALUES(#{emailId}, #{password}, #{firstName}, #{lastName})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public void insertUser(User user);

    @Select("SELECT user_id as userId, email_id as emailId, password, "
            + "first_name as firstName, last_name as lastName FROM USER "
            + "WHERE USER_ID = #{userId}")
    public User getUserById(Integer userId);

    @Select("SELECT * FROM USER ")
    @Results({
        @Result(id = true, property = "userId", column = "user_id"),
        @Result(property = "emailId", column = "email_id"),
        @Result(property = "firstName", column = "first_name"),
        @Result(property = "password", column = "password"),
        @Result(property = "lastName", column = "last_name")
    })
    public List<User> getAllUsers();

    @Update("UPDATE USER SET PASSWORD= #{password}, FIRST_NAME = #{firstName}, LAST_NAME = #{lastName} WHERE USER_ID = #{userId}")
    public void updateUser(User user);

    @Delete("DELETE FROM USER WHERE USER_ID = #{userId}")
    public void deleteUser(Integer userId);
}
