/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.functions;

import java.util.HashMap;

/**
 *
 * @author tigran
 */
public class AccessTokens {

    private static AccessTokens accessTokens = null;
    private static HashMap<String, String> accessTokensList = new HashMap();

    private AccessTokens() {

    }

    public static AccessTokens getInstance() {
        if (accessTokens == null) {
            accessTokens = new AccessTokens();
        }
        return accessTokens;
    }

    public String getEmployeeAccessToken(String userName) {
        if (accessTokensList.containsKey(userName)) {
            return accessTokensList.get(userName);
        }
        return null;
    }

    public void setAccessToken(String userName, String accessToken) {
        accessTokensList.put(userName, accessToken);
    }

    public void deletAccessToken(String userName) {
        accessTokensList.remove(userName);
    }

    public String getEmployeeUsernameByAccessToken(String accessToken) {
        System.out.println("in function");
        if (accessTokensList.containsValue(accessToken)) {
            System.out.println("000000000in if000000000000000");
            for (String key : accessTokensList.keySet()) {
                System.out.println("*********\n" + accessTokensList.get(key));
                if (accessTokensList.get(key).equals(accessToken)) {
                    return key;
                }
            }
        }
        System.out.println("000000000in else000000000000000");
        return null;
    }

    public void printAccessTokens() {
        System.out.println("accessTokens=========\n"
                + accessTokensList.toString() + "\n============================");
    }

}
