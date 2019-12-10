package com.killer.clientserver.modules.stream.list;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author killer
 * @date 2019/08/19 - 19:51
 */
public class ListStream {

    @Test
    public void test1() {
        ArrayList<Integer> integers = new ArrayList<>();

        integers.add(3);
        integers.add(13);
        integers.add(1);
        integers.add(4);
        integers.add(18);
        integers.add(18);
        integers.add(16);

        System.out.println(integers.stream().distinct().sorted().filter(item -> item % 2 == 0).peek(System.out::println).count());

        System.out.println(integers.stream().reduce(0, (x, y) -> x + y).intValue());
    }

}
