package com.test.work.test4work.test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;
import java.util.ArrayList;
import java.util.List;

public class JSqlParserDemo {
    
    // 提取所有表名（支持JOIN/子查询）
    public static List<String> extractTables(String sql) throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse(sql);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(stmt);
    }

    // 提取查询字段
    public static List<String> extractColumns(String sql) throws JSQLParserException {
        List<String> columns = new ArrayList<>();
        Statement stmt = CCJSqlParserUtil.parse(sql);
        
        if (stmt instanceof Select) {
            Select select = (Select) stmt;
            PlainSelect plainSelect = select.getPlainSelect();
            
            // 直接使用toString()方法获取列名
            String selectBodyStr = plainSelect.toString();
            // 提取SELECT子句部分
            int selectIndex = selectBodyStr.toUpperCase().indexOf("SELECT");
            int fromIndex = selectBodyStr.toUpperCase().indexOf("FROM");
            if (selectIndex >= 0 && fromIndex > selectIndex) {
                String selectClause = selectBodyStr.substring(selectIndex + 6, fromIndex).trim();
                String[] parts = selectClause.split(",");
                for (String part : parts) {
                    columns.add(part.trim());
                }
            }
        }
        return columns;
    }

    public static void main(String[] args) throws Exception {
        String sql = "SELECT id, name FROM users WHERE id IN (SELECT user_id FROM orders)";
        
        System.out.println("表名: " + extractTables(sql)); 
        // 输出: [users, orders]
        
        System.out.println("字段: " + extractColumns(sql)); 
        // 输出: [id, name]
    }
}

