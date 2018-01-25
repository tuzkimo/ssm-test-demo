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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static me.tuzkimo.demo.core.ProjectConstant.*;

/**
 * model, mapper, service 和 controller 的自动生成器
 * 参考大神 lihengming 的 CodeGenerator 写的
 *
 * Created by tuzkimo on 2018-01-16
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
  private static final String PATH_PACKAGE_CONTROLLER = packageConvertPath(PACKAGE_CONTROLLER);

  // Javadoc 信息
  private static final String AUTHOR = "CodeGenerator";
  private static final String DATE = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  public static void main(String[] args) {
    // 输入数据表名生成代码
    generateCode("user", "book");
//    generateCodeByCustomModelName("tb_user", "User");
  }

  /**
   * 批量生成代码，默认模型名根据数据表名转换而成
   * @param tableNames 数据表名...
   */
  public static void generateCode(String... tableNames) {
    for (String tableName : tableNames) {
      generateCodeByCustomModelName(tableName, null);
    }
  }

  /**
   * 根据数据表名及自定义模型名生成代码
   * @param tableName 数据表名
   * @param modelName 生成 model 名
   */
  public static void generateCodeByCustomModelName(String tableName, String modelName) {
    generateModelAndMapper(tableName, modelName);
    modelName = StringUtils.isEmpty(modelName) ? tableNameConvertModelName(tableName) : modelName;
    generateCodeByFreeMarker(modelName, PATH_PACKAGE_SERVICE, "Service.java", "service.ftl");
    generateCodeByFreeMarker(modelName, PATH_PACKAGE_SERVICE_IMPL, "ServiceImpl.java", "service-impl.ftl");
    generateCodeByFreeMarker(modelName, PATH_PACKAGE_CONTROLLER, "Controller.java", "controller.ftl");
  }

  /**
   * 调用 MyBatis Generator 生成 model, mapper 接口和 mapper xml
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
    boolean isGenerateFailed = generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty();
    if (isGenerateFailed) {
      throw new RuntimeException("生成 model, mapper 接口和 mapper xml 失败: " + warnings);
    }
    if (StringUtils.isEmpty(modelName)) {
      modelName = tableNameConvertModelName(tableName);
    }
    System.out.println(modelName + ".java 生成成功");
    System.out.println(modelName + "Mapper.java 生成成功");
    System.out.println(modelName + "Mapper.xml 生成成功");
  }

  /**
   * 配置 MyBatis Generator
   * @param tableName 数据表名称
   * @param modelName 生成 model 名称
   * @return MyBatis Generator Configuration
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
   * 使用 FreeMarker 生成代码文件
   * @param modelName 对应模型
   * @param path 生成文件包路径
   * @param fileNameSuffix 文件名后缀
   * @param templateName 模板名
   */
  private static void generateCodeByFreeMarker(String modelName, String path, String fileNameSuffix, String templateName) {
    Map<String, Object> data = getDataModel(modelName);

    try {
      freemarker.template.Configuration configuration = getConfiguration();

      // 根据包路径、模型名及文件名后缀新建空白源文件，父文件夹不存在则新建
      File file = new File(PATH_PROJECT + PATH_JAVA + path + modelName + fileNameSuffix);
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdir();
      }

      // 根据模板及数据模型向空白源文件写入内容
      configuration.getTemplate(templateName).process(data, new FileWriter(file));
      System.out.println(modelName + fileNameSuffix + " 生成成功");
    } catch (Exception e) {
      throw new RuntimeException("生成 " + modelName + fileNameSuffix + " 失败", e);
    }
  }

  private static freemarker.template.Configuration getConfiguration() throws IOException {
    freemarker.template.Configuration configuration = new freemarker.template.Configuration();
    configuration.setDirectoryForTemplateLoading(new File(PATH_TEMPLATE));
    configuration.setDefaultEncoding(Charsets.UTF_8.name());
    configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
    return configuration;
  }

  private static Map<String, Object> getDataModel(String modelName) {
    Map<String, Object> data = new HashMap<>();
    data.put("author", AUTHOR);
    data.put("date", DATE);
    data.put("basePackage", PACKAGE_BASE);
    data.put("modelNameUpperCamel", modelName);
    data.put("modelNameLowerCamel", modelNameConvertLowerCamel(modelName));
    data.put("baseRequestMapping", modelNameConvertMappingPath(modelName));
    return data;
  }

  private static String tableNameConvertModelName(String tableName) {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
  }

  private static String modelNameConvertLowerCamel(String modelName) {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelName);
  }

  private static String packageConvertPath(String packageName) {
    return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
  }

  private static String modelNameConvertMappingPath(String modelName) {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
  }
}
