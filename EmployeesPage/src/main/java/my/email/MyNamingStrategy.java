/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.email;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 *
 * @author tigran
 */
public class MyNamingStrategy extends DefaultNamingStrategy {

    private String userName;

    public MyNamingStrategy(String userName) {
        this.userName = userName;
    }

    @Override
    public String tableName(String tableName) {
        return tableName + userName;
    }

}
