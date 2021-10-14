package com.cztian;

import com.cztian.utils.CaseUtils;
import com.cztian.utils.DataSourceUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.Map.entry;

public class Generator {

    //配置
    private GeneratorConfig config;

    //类型映射
    private static final Map<String, String> typeMap = Map.ofEntries(
            //数值
            entry("TINYINT", "Integer"),
            entry("SMALLINT", "Integer"),
            entry("MEDIUMINT", "Integer"),
            entry("INT", "Integer"),
            entry("INTEGER", "Integer"),
            entry("BIGINT", "Long"),
            entry("DOUBLE", "Double"),
            entry("DECIMAL", "BigDecimal"),
            //字符串
            entry("CHAR", "String"),
            entry("VARCHAR", "String"),
            entry("TINYBLOB", "String"),
            entry("TINYTEXT", "String"),
            entry("TEXT", "String"),
            entry("BLOB", "String"),
            entry("MEDIUMBLOB", "String"),
            entry("MEDIUMTEXT", "String"),
            entry("LONGBLOB", "String"),
            entry("LONGTEXT", "String"),
            //日期和时间
            entry("DATE", "Date"),
            entry("DATETIME", "Date"),
            entry("TIMESTAMP", "Date")
    );

    public Generator() {
        this(null);
    }


    public Generator(GeneratorConfig config) {
        this.config = null == config ? new GeneratorConfig() : config;
    }

    public void generate() {
        Connection conn = DataSourceUtils.getConnection();

        try {
            //获取表名
            Set<String> tables = getTables(conn);
            for (String table : tables) {
                //获取列信息
                List<Map<String, String>> fields = getFields(conn, table);
                //创建数据模型对象
                DataModel dm = new DataModel();
                //传入包名
                dm.setPackageName(config.getPackageName());
                //传入类名
                String className = CaseUtils.toCamelCase(table, true);
                dm.setClassName(className);
                //传入字段
                dm.setFields(fields);
                //是否使用Lombok
                dm.setUseLombok(config.useLombok());
                //是否链式调用
                dm.setChain(config.getChainAccess());
                //是否生成全参构造器
                dm.setAllArgs(config.getAllArgsConstructor());
                //是否有Date类型
                dm.setHasDateType(fields.stream().anyMatch(f -> "Date".equals(f.get("type"))));
                //是否有BigDecimal类型
                dm.setHasDecimalType(fields.stream().anyMatch(f -> "BigDecimal".equals(f.get("type"))));
                //获取输出路径
                String path = config.getOutput() + File.separator +
                        config.getPackageName().replaceAll("\\.", "/") + File.separator +
                        className + ".java";
                File file = new File(path);
                //渲染
                if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
                    if (file.exists() || file.createNewFile()) {
                        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
                        render(dm, writer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取表
     */
    public Set<String> getTables(Connection conn) throws SQLException {
        //创建SQL语句
        Statement statement = conn.createStatement();
        String sql = "SHOW TABLES;";
        //查询
        ResultSet resultSet = statement.executeQuery(sql);
        //表集合
        Set<String> tables = new HashSet<>();
        //遍历结果集
        while (resultSet.next()) {
            tables.add(resultSet.getString(1));
        }
        return tables;
    }

    /**
     * 获取列
     */
    public List<Map<String, String>> getFields(Connection conn, String table) throws SQLException {
        //创建SQL语句
        String sql = "SHOW COLUMNS FROM " + table;
        Statement statement = conn.createStatement();
        //查询
        ResultSet resultSet = statement.executeQuery(sql);
        //列信息
        List<Map<String, String>> columns = new LinkedList<>();
        //遍历结果集
        while (resultSet.next()) {
            Map<String, String> m = new HashMap<>(4);
            //字段名
            m.put("name", convertName(resultSet.getString("field")));
            //类型
            m.put("type", convertType(resultSet.getString("type")));
            columns.add(m);
        }
        return columns;
    }

    /**
     * 名称转换
     */
    public String convertName(String name) {
        if (null != config) {
            if (!config.getCamelCase()) {
                return name;
            }
        }
        return CaseUtils.toCamelCase(name, false);
    }


    /**
     * 类型转换
     */
    public String convertType(String type) {
        if (null != config) {
            //获取自定义的类型
            String ct = config.getType(type);
            if (0 != ct.length()) {
                return ct;
            }
        }
        return typeMap.get(type.replaceAll("\\(.*\\)", "").toUpperCase());
    }


    /**
     * 渲染模版
     */
    public void render(DataModel dm, Writer writer) throws Exception {
        //FreeMarker配置
        Configuration fmc = new Configuration(Configuration.VERSION_2_3_28);
        fmc.setDirectoryForTemplateLoading(new File(Generator.class.getClassLoader().getResource("template").toURI()));
        fmc.setDefaultEncoding("UTF-8");
        fmc.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        //获取模版
        Template template = fmc.getTemplate("bean.ftl");
        //输出
        template.process(dm, writer);
    }

}
