package com.vquick.dao.persist.impl;


import com.vquick.dao.config.DaoConfigTest;
import com.vquick.dao.data.User;
import com.vquick.dao.persist.UserDaoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by tigran on 4/27/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DaoConfigTest.class})
public class UserDaoServiceImplTest {

    @Autowired
    private UserDaoService userDaoService;

    private static User testUser;


    @BeforeClass
    public static void setUp() {

        testUser = new User("testUserName", "testPassword",  null);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAddUser() throws Exception {
        userDaoService.addUser(testUser);
        User resultUser = userDaoService.getUser(testUser.getUserName());
        Assert.assertEquals(testUser.getUserName(), resultUser.getUserName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteUser() throws Exception {
        userDaoService.addUser(testUser);
        User resultUser = userDaoService.getUser(testUser.getUserName());
        Assert.assertEquals(testUser.getUserName(), resultUser.getUserName());
        userDaoService.deleteUser(resultUser.getUserName());
    }
}