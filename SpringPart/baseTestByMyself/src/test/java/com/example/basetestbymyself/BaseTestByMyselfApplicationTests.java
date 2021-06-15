package com.example.basetestbymyself;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootTest
class BaseTestByMyselfApplicationTests {

    @Test
    void contextLoads() {
        //替代LinkedList
        List<String> linkedList = Collections.synchronizedList(new LinkedList<String>());
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        //替代ArrayList
        List<String> arrayList = Collections.synchronizedList(new ArrayList<String>());
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        HashMap map = new HashMap();
        map.put(null, null);

    }

}
