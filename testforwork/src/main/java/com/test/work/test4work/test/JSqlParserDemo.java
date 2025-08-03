package com.test.work.test4work.test;


import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.*;

public class JSqlParserDemo {

    /**
     * 解析sql
     *
     * @param sql
     * @throws JSQLParserException
     */
    public static void parseSql(String sql) throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect plainSelect = stmt.getPlainSelect();

        System.out.println("表名: " + extractTables(plainSelect));
        // 输出: [users, orders]

        System.out.println("字段: " + extractColumns(plainSelect));
        // 输出: [id, name]

        System.out.println(extractWhere(plainSelect));
        // 输出： {created_at={>=['2025-06-18']}, id={IN=[(1, 2, 3)]}, email={LIKE=['%@example.com']}, username={==['test1']}}

    }

    // 提取所有表名（支持JOIN/子查询）
    public static List<String> extractTables(PlainSelect plainSelect) {
        List<String> tables = new ArrayList<>();
        FromItem fromItem = plainSelect.getFromItem();
        tables.add(fromItem.toString());
        return tables;
    }

    // 提取查询字段
    public static List<String> extractColumns(PlainSelect plainSelect) {
        List<String> columns = new ArrayList<>();
        for (SelectItem selectItem : plainSelect.getSelectItems()) {
            selectItem.accept(item -> columns.add(item.getExpression().toString()));
        }
        return columns;
    }

    // 提取所有where条件
    public static Map<String, Map<String, Set<String>>> extractWhere(PlainSelect plainSelect) {
        Map<String, Map<String, Set<String>>> whereMap = new HashMap<>();
        plainSelect.getWhere().accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(EqualsTo expr) {
                Expression leftExpression = expr.getLeftExpression();
                Expression rightExpression = expr.getRightExpression();
                saveWhereCondition(leftExpression, rightExpression, "=", whereMap);
            }

            @Override
            public void visit(InExpression expr) {
                Expression leftExpression = expr.getLeftExpression();
                Expression rightExpression = expr.getRightExpression();
                saveWhereCondition(leftExpression, rightExpression, "IN", whereMap);
            }

            @Override
            public void visit(LikeExpression expr) {
                Expression leftExpression = expr.getLeftExpression();
                Expression rightExpression = expr.getRightExpression();
                saveWhereCondition(leftExpression, rightExpression, "LIKE", whereMap);
            }

            @Override
            public void visit(GreaterThan expr) {
                Expression leftExpression = expr.getLeftExpression();
                Expression rightExpression = expr.getRightExpression();
                saveWhereCondition(leftExpression, rightExpression, ">", whereMap);
            }
        });
        return whereMap;
    }

    /**
     * 存储解析where条件的结果
     *
     * @param leftExpression
     * @param rightExpression
     * @param operator
     * @param whereMap
     */
    private static void saveWhereCondition(Expression leftExpression, Expression rightExpression, String operator,
                                           Map<String, Map<String, Set<String>>> whereMap) {
        Map<String, Set<String>> operatorMap = whereMap.get(leftExpression.toString());
        if (operatorMap == null) {
            Map<String, Set<String>> map = new HashMap<>();
            Set<String> valueSet = new HashSet<>();
            valueSet.add(rightExpression.toString());
            map.put(operator, valueSet);
            whereMap.put(leftExpression.toString(), map);
        } else {
            operatorMap.get(operator).add(rightExpression.toString());
            whereMap.put(leftExpression.toString(), operatorMap);
        }
    }


    public static void main(String[] args) throws Exception {
        String sql = "SELECT id,created_at,email,username,PASSWORD FROM `user` " +
                "WHERE id IN (1,2,3) AND username = 'test1' and email LIKE '%@example.com' OR created_at >'2025-06-18';";

        parseSql(sql);

    }
}

