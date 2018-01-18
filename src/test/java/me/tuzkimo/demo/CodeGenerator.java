package me.tuzkimo.demo;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.tuzkimo.demo.core.ProjectConstant.PACKAGE_MAPPER;
import static me.tuzkimo.demo.core.ProjectConstant.PACKAGE_MODEL;

/**
 * model, mapper, service 和 controller 的自动生成器
 * 参考大神 <a href="https://github.com/lihengming/">lihengming</a>
 * 的 <a href="https://github.com/lihengming/spring-boot-api-project-seed/blob/master/src/test/java/CodeGenerator.java">CodeGenerator</a> 写的
 * 基本上一样，建议看原创的
 *
 * @author tuzkimo
 * @version %I%, %G%
 * @since 1.0
 */
public class CodeGenerator {
  // JDBC 连接参数
  private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
  private static final String JDBC_USERNAME = "root";
  private static final String JDBC_PASSWORD = "p123456";
  private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

  // 代码生成路径
  private static final String PATH_PROJECT = System.getProperty("user.dir");
  private static final String PATH_JAVA = "/src/main/java";
  private static final String PATH_RESOURCES = "/src/main/resources";

  public static void main(String[] args) {
    generateModelAndMapper("user", null);
  }

  /**
   * 调用 mybatis generator 生成 model, mapper 接口和 mapper xml
   *
   * @param tableName 数据表名
   * @param modelName 生成 model 名
   */
  public static void generateModelAndMapper(String tableName, String modelName) {
    // 配置 mybatis generator
    MyBatisGenerator generator = null;
    DefaultShellCallback callback = new DefaultShellCallback(true);
    List<String> warnings = new ArrayList<>();
    try {
      Configuration configuration = configMybatisGenerator(tableName, modelName);
      generator = new MyBatisGenerator(configuration, callback, warnings);

      // 开始生成 model, mapper 接口和 mapper xml
      generator.generate(null);
    } catch (InvalidConfigurationException e) {
      System.err.println("配置信息验证不通过");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("数据库连接错误或 SQL 语句执行出错");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("文件读写错误");
      e.printStackTrace();
    } catch (InterruptedException e) {
      System.err.println("线程中断");
      e.printStackTrace();
    }

    // 验证生成结果
    boolean isGenerateFailed = Objects.isNull(generator) || generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty();
    if (isGenerateFailed) {
      throw new RuntimeException("生成 model, mapper 接口和 mapper xml 失败: " + warnings);
    }
    if (StringUtils.isEmpty(modelName)) {
      modelName = tableNameConvertUpperCamel(tableName);
    }
    System.out.println(modelName + ".java 生成成功");
    System.out.println(modelName + "Mapper.java 生成成功");
    System.out.println(modelName + "Mapper.xml 生成成功");
  }

  /**
   * 配置 mybatis generator
   *
   * @param tableName 数据表名称
   * @param modelName 生成 model 名称
   * @return mybatis generator configuration
   * @throws InvalidConfigurationException 配置信息验证不通过
   */
  private static Configuration configMybatisGenerator(String tableName, String modelName) throws InvalidConfigurationException {
    Context context = new Context(ModelType.FLAT);
    context.setId("mysql");
    context.setTargetRuntime("Mybatis3Simple");
    context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
    context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

    // 配置注解生成器
    CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
    commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
    commentGeneratorConfiguration.addProperty("suppressDate", "true");
    context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

    // 配置 JDBC 连接
    JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
    jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
    jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
    jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
    jdbcConnectionConfiguration.setDriverClass(JDBC_DRIVER_CLASS_NAME);
    context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

    // 配置 model 生成路径
    JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
    javaModelGeneratorConfiguration.setTargetProject(PATH_PROJECT + PATH_JAVA);
    javaModelGeneratorConfiguration.setTargetPackage(PACKAGE_MODEL);
    context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

    // 配置 mapper xml 生成路径
    SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
    sqlMapGeneratorConfiguration.setTargetProject(PATH_PROJECT + PATH_RESOURCES);
    sqlMapGeneratorConfiguration.setTargetPackage("mapper");
    context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

    // 配置 mapper 接口生成路径
    JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
    javaClientGeneratorConfiguration.setTargetProject(PATH_PROJECT + PATH_JAVA);
    javaClientGeneratorConfiguration.setTargetPackage(PACKAGE_MAPPER);
    javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
    context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

    // 自定义 JDBC 类型与 Java 类型的转换策略
    JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
    javaTypeResolverConfiguration.setConfigurationType(JavaTypeResolverMyImpl.class.getName());
    context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

    // 配置 table 转 model 参数
    TableConfiguration tableConfiguration = new TableConfiguration(context);
    tableConfiguration.setTableName(tableName);
    if (StringUtils.isNotEmpty(modelName)) {
      tableConfiguration.setDomainObjectName(modelName);
    }
    tableConfiguration.setGeneratedKey(new GeneratedKey("id", "MySql", true, null));

    // 配置 column 改名策略
    ColumnRenamingRule columnRenamingRule = new ColumnRenamingRule();
    columnRenamingRule.setSearchString("is_");
    columnRenamingRule.setReplaceString("");
    tableConfiguration.setColumnRenamingRule(columnRenamingRule);
    context.addTableConfiguration(tableConfiguration);

    // 验证配置并返回
    Configuration configuration = new Configuration();
    configuration.addContext(context);
    configuration.validate();
    return configuration;
  }

  private static String tableNameConvertUpperCamel(String tableName) {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
  }
}
