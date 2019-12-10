package com.killer.clientserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// @RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceServerApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(Integer.valueOf('9'));
        String a = "qweqwe";
        System.out.println(a.substring(0, 1));

        System.out.println(999999999);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int a = getRealNumber(l1);
        int b = getRealNumber(l2);

        int c = a + b;
        String d = new StringBuilder(String.valueOf(c)).reverse().toString();
        ListNode e = new ListNode(0);
        ListNode f = e;
        for(int i=0;i<d.length();i++){

            String l = d.substring(i, i+1);
            if(i == 0) {
                f.val = Integer.valueOf(l);
                continue;
            }
            ListNode g = new ListNode(Integer.valueOf(l));
            f.next = g;
            f = g;
        }

        return e;
    }

    public int getRealNumber(ListNode node) {
        StringBuilder str = new StringBuilder();
        str.append(node.val);

        ListNode m = node.next;
        while(m != null) {
            str.append(node);
            m = m.next;
        }

        return Integer.valueOf(str.reverse().toString());
    }

    /**
     * Definition for singly-linked list.
     */
     public class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
     }
}
