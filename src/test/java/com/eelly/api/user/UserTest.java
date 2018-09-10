package com.eelly.api.user;

import com.eelly.java.core.EellyClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User Tester.
 */
public class UserTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getInfo()
     */
    @Test
    public void testGetInfo() throws Exception {
        EellyClient.createTokenWithPasswod("molimoq", "123456");
    }

    /**
     * Method: getUserByPassword(String username, String password)
     */
    @Test
    public void testGetUserByPassword() throws Exception {

    }


} 
