package com.vquick.business.config;

import com.vquick.business.manager.UserManager;
import com.vquick.business.manager.impl.UserManagerImpl;

import java.util.*;

/**
 * Created by tigran on 4/26/16.
 */
public abstract class Test  implements Comparable<Test>{

    public abstract void test();

    public void test1() {
        System.out.println("test1 super");
    }

    public static void main(String[] args) {
        /*List<String> strList = new ArrayList<String>();
        strList.add("kk");
        Iterator<String> it = strList.iterator();
        while(it.hasNext()){
            String obj = it.next();
            System.out.println(obj);
        }
        strList.add("dd");
        System.out.println("------------------------------------------");
       // it = strList.iterator();
        while(it.hasNext()){
            String obj = it.next();
            System.out.println(obj);
        }*/
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("test", "test");
        List<Float> list = new ArrayList<>();

        System.out.println(hashMap.get("test"));
        Integer i = 1000;
        Integer j = 1000;
        if(i == j) {
            System.out.println("pppppppppppppppppppppppp");
        }

        String[] sa = {"one", "two", "three", "four"};
        List sList = Arrays.asList(sa); // make a List
        System.out.println("size " + sList.size());
        System.out.println("idx2 " + sList.get(2));
        sList.set(3,"six"); // change List
        sa[1] = "five"; // change array
        for(String s : sa)
            System.out.print(s + " ");
        System.out.println("\nsl[1] " + sList.get(1));

        System.out.println("________________________________");
        System.out.println(sList);
        System.out.println("________________________________");

    }
}
