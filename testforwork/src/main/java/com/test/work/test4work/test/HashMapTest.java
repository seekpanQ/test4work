package com.test.work.test4work.test;

import com.test.work.test4work.vo.EmployeeKey;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试HashMap的key是对象的情况
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<EmployeeKey, String> employeeMap = new HashMap<>();
        EmployeeKey key = new EmployeeKey("Engineering", 123);
        employeeMap.put(key, "John Doe");

        // 检索
        String employee = employeeMap.get(new EmployeeKey("Engineering", 123));
        System.out.println(employee);
        // 返回 "John Doe"，因为 equals() 和 hashCode() 正确实现
    }
}
