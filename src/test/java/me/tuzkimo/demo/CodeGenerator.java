package me.tuzkimo.demo;

import com.google.common.base.CaseFormat;
import com.google.common.base.Charsets;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import tk.mybatis.mapper.generator.MapperPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static me.tuzkimo.demo.core.ProjectConstant.*;

/**
 * model, mapper, service 和 controller 的自动生成器
 * 参考大神 lihengming 的 CodeGenerator 写的
 *
 * @author tuzkimo
 * @date 2018-01-16
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
  private static final String PATH_TEMPLATE = PATH_PROJECT + "/src/test/resources/generator/template";
  private static final String PATH_PACKAGE_SERVICE = packageConvertPath(PACKAGE_SERVICE);
  private static final String PATH_PACKAGE_SERVICE_IMPL = packageConvertPath(PACKAGE_SERVICE_IMPL);

  private static final String AUTHOR = "CodeGenerator";
  private static final String DATE = LocalDate.now().toString();

  public static void main(String[] args) {
//    generateModelAndMapper("user", null);
    generateService("user", null);
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
    } catch (Exception e) {
      throw new RuntimeException("生成 model, mapper 接口和 mapper xml 失败: " + e);
    }

    // 验证生成结果
    boolean isGenerateFailed = Objects.isNull(generator) || generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty();
    if (isGenerateFailed) {
      throw new RuntimeException("生成 model, mapper 接口和 mapper xml 失败: " + warnings);
    }
    if (StringUtils.isEmpty(modelName)) {
      modelName = lowerUnderscoreConvertUpperCamel(tableName);
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

    // 添加通用 mapper 插件
    PluginConfiguration pluginConfiguration = new PluginConfiguration();
    pluginConfiguration.setConfigurationType(MapperPlugin.class.getName());
    pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
    context.addPluginConfiguration(pluginConfiguration);

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

  /**
   * 用 FreeMarker 生成 service 接口及实现类
   *
   * @param tableName 数据表名
   * @param modelName 生成 model 名
   */
  public static void generateService(String tableName, String modelName) {
    // 构建 FreeMarker 数据模型
    Map<String, Object> data = new HashMap<>();
    data.put("author", AUTHOR);
    data.put("date", DATE);
    String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? lowerUnderscoreConvertUpperCamel(tableName) : modelName;
    data.put("modelNameUpperCamel", modelNameUpperCamel);
    data.put("modelNameLowerCamel", upperCamelConvertLowerCamel(modelNameUpperCamel));
    data.put("basePackage", PACKAGE_BASE);

    try {
      // 获取 FreeMarker Configuration
      freemarker.template.Configuration configuration = getConfiguration();

      // 新建 Service 接口源文件
      File serviceFile = new File(PATH_PROJECT + PATH_JAVA + PATH_PACKAGE_SERVICE + modelNameUpperCamel + "Service.java");
      if (!serviceFile.getParentFile().exists()) {
        serviceFile.getParentFile().mkdir();
      }

      // 根据 service.ftl 模板写入数据，生成对应 model 的 service 接口
      configuration.getTemplate("service.ftl").process(data, new FileWriter(serviceFile));
      System.out.println(modelNameUpperCamel + "Service.java 生成成功");

      // 新建 Service 实现类源文件
      File serviceImplFile = new File(PATH_PROJECT + PATH_JAVA + PATH_PACKAGE_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
      if (!serviceImplFile.getParentFile().exists()) {
        serviceImplFile.getParentFile().mkdir();
      }

      // 根据 service-impl.ftl 模板写入数据，生成对应 model 的 service 实现类
      configuration.getTemplate("service-impl.ftl").process(data, new FileWriter(serviceImplFile));
      System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
    } catch (Exception e) {
      throw new RuntimeException("生成 Service 失败", e);
    }
  }

  private static freemarker.template.Configuration getConfiguration() throws IOException {
    freemarker.template.Configuration configuration = new freemarker.template.Configuration();
    configuration.setDirectoryForTemplateLoading(new File(PATH_TEMPLATE));
    configuration.setDefaultEncoding(Charsets.UTF_8.name());
    configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
    return configuration;
  }

  private static String lowerUnderscoreConvertUpperCamel(String lowerUnderscoreStr) {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, lowerUnderscoreStr);
  }

  private static String upperCamelConvertLowerCamel(String upperCamelStr) {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, upperCamelStr);
  }

  private static String packageConvertPath(String packageName) {
    return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
  }
}
