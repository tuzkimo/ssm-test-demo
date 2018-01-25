package me.tuzkimo.demo;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import java.math.BigDecimal;
import java.util.*;

/**
 * Mybatis generator JDBC 与 Java 类型转换的自定义实现
 * 直接复制源码修改的
 *
 * @author tuzkimo
 * @version %I%, %%
 * @since 1.0
 */
public class JavaTypeResolverMyImpl implements JavaTypeResolver {
  protected List<String> warnings;
  protected Properties properties = new Properties();
  protected Context context;
  protected boolean forceBigDecimals;
  protected Map<Integer, JavaTypeResolverMyImpl.JdbcTypeInformation> typeMap = new HashMap();

  public JavaTypeResolverMyImpl() {
    this.typeMap.put(2003, new JavaTypeResolverMyImpl.JdbcTypeInformation("ARRAY", new FullyQualifiedJavaType(Object.class.getName())));
    this.typeMap.put(-5, new JavaTypeResolverMyImpl.JdbcTypeInformation("BIGINT", new FullyQualifiedJavaType(Long.class.getName())));
    this.typeMap.put(-2, new JavaTypeResolverMyImpl.JdbcTypeInformation("BINARY", new FullyQualifiedJavaType("byte[]")));
    this.typeMap.put(-7, new JavaTypeResolverMyImpl.JdbcTypeInformation("BIT", new FullyQualifiedJavaType(Boolean.class.getName())));
    this.typeMap.put(2004, new JavaTypeResolverMyImpl.JdbcTypeInformation("BLOB", new FullyQualifiedJavaType("byte[]")));
    this.typeMap.put(16, new JavaTypeResolverMyImpl.JdbcTypeInformation("BOOLEAN", new FullyQualifiedJavaType(Boolean.class.getName())));
    this.typeMap.put(1, new JavaTypeResolverMyImpl.JdbcTypeInformation("CHAR", new FullyQualifiedJavaType(String.class.getName())));
    this.typeMap.put(2005, new JavaTypeResolverMyImpl.JdbcTypeInformation("CLOB", new FullyQualifiedJavaType(String.class.getName())));
    this.typeMap.put(70, new JavaTypeResolverMyImpl.JdbcTypeInformation("DATALINK", new FullyQualifiedJavaType(Object.class.getName())));
    this.typeMap.put(91, new JavaTypeResolverMyImpl.JdbcTypeInformation("DATE", new FullyQualifiedJavaType(Date.class.getName())));
    this.typeMap.put(3, new JavaTypeResolverMyImpl.JdbcTypeInformation("DECIMAL", new FullyQualifiedJavaType(BigDecimal.class.getName())));
    this.typeMap.put(2001, new JavaTypeResolverMyImpl.JdbcTypeInformation("DISTINCT", new FullyQualifiedJavaType(Object.class.getName())));
    this.typeMap.put(8, new JavaTypeResolverMyImpl.JdbcTypeInformation("DOUBLE", new FullyQualifiedJavaType(Double.class.getName())));
    this.typeMap.put(6, new JavaTypeResolverMyImpl.JdbcTypeInformation("FLOAT", new FullyQualifiedJavaType(Double.class.getName())));
    this.typeMap.put(4, new JavaTypeResolverMyImpl.JdbcTypeInformation("INTEGER", new FullyQualifiedJavaType(Integer.class.getName())));
    this.typeMap.put(2000, new JavaTypeResolverMyImpl.JdbcTypeInformation("JAVA_OBJECT", new FullyQualifiedJavaType(Object.class.getName())));
    this.typeMap.put(-16, new JavaTypeResolverMyImpl.JdbcTypeInformation("LONGNVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
    this.typeMap.put(-4, new JavaTypeResolverMyImpl.JdbcTypeInformation("LONGVARBINARY", new FullyQualifiedJavaType("byte[]")));
    this.typeMap.put(-1, new JavaTypeResolverMyImpl.JdbcTypeInformation("LONGVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
    this.typeMap.put(-15, new JavaTypeResolverMyImpl.JdbcTypeInformation("NCHAR", new FullyQualifiedJavaType(String.class.getName())));
    this.typeMap.put(2011, new JavaTypeResolverMyImpl.JdbcTypeInformation("NCLOB", new FullyQualifiedJavaType(String.class.getName())));
    this.typeMap.put(-9, new JavaTypeResolverMyImpl.JdbcTypeInformation("NVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
    this.typeMap.put(0, new JavaTypeResolverMyImpl.JdbcTypeInformation("NULL", new FullyQualifiedJavaType(Object.class.getName())));
    this.typeMap.put(2, new JavaTypeResolverMyImpl.JdbcTypeInformation("NUMERIC", new FullyQualifiedJavaType(BigDecimal.class.getName())));
    this.typeMap.put(1111, new JavaTypeResolverMyImpl.JdbcTypeInformation("OTHER", new FullyQualifiedJavaType(Object.class.getName())));
    this.typeMap.put(7, new JavaTypeResolverMyImpl.JdbcTypeInformation("REAL", new FullyQualifiedJavaType(Float.class.getName())));
    this.typeMap.put(2006, new JavaTypeResolverMyImpl.JdbcTypeInformation("REF", new FullyQualifiedJavaType(Object.class.getName())));
    this.typeMap.put(5, new JavaTypeResolverMyImpl.JdbcTypeInformation("SMALLINT", new FullyQualifiedJavaType(Short.class.getName())));
    this.typeMap.put(2002, new JavaTypeResolverMyImpl.JdbcTypeInformation("STRUCT", new FullyQualifiedJavaType(Object.class.getName())));
    this.typeMap.put(92, new JavaTypeResolverMyImpl.JdbcTypeInformation("TIME", new FullyQualifiedJavaType(Date.class.getName())));
    this.typeMap.put(93, new JavaTypeResolverMyImpl.JdbcTypeInformation("TIMESTAMP", new FullyQualifiedJavaType(Date.class.getName())));
    // TINYINT 由默认的转 Byte 改为 Boolean
    this.typeMap.put(-6, new JavaTypeResolverMyImpl.JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Boolean.class.getTypeName())));
    this.typeMap.put(-3, new JavaTypeResolverMyImpl.JdbcTypeInformation("VARBINARY", new FullyQualifiedJavaType("byte[]")));
    this.typeMap.put(12, new JavaTypeResolverMyImpl.JdbcTypeInformation("VARCHAR", new FullyQualifiedJavaType(String.class.getName())));
  }

  public void addConfigurationProperties(Properties properties) {
    this.properties.putAll(properties);
    this.forceBigDecimals = StringUtility.isTrue(properties.getProperty("forceBigDecimals"));
  }

  public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
    FullyQualifiedJavaType answer = null;
    JavaTypeResolverMyImpl.JdbcTypeInformation jdbcTypeInformation = (JavaTypeResolverMyImpl.JdbcTypeInformation)this.typeMap.get(introspectedColumn.getJdbcType());
    if (jdbcTypeInformation != null) {
      answer = jdbcTypeInformation.getFullyQualifiedJavaType();
      answer = this.overrideDefaultType(introspectedColumn, answer);
    }

    return answer;
  }

  protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
    FullyQualifiedJavaType answer = defaultType;
    switch(column.getJdbcType()) {
      case -7:
        answer = this.calculateBitReplacement(column, defaultType);
        break;
      case 2:
      case 3:
        answer = this.calculateBigDecimalReplacement(column, defaultType);
    }

    return answer;
  }

  protected FullyQualifiedJavaType calculateBitReplacement(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
    FullyQualifiedJavaType answer;
    if (column.getLength() > 1) {
      answer = new FullyQualifiedJavaType("byte[]");
    } else {
      answer = defaultType;
    }

    return answer;
  }

  protected FullyQualifiedJavaType calculateBigDecimalReplacement(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
    FullyQualifiedJavaType answer;
    if (column.getScale() <= 0 && column.getLength() <= 18 && !this.forceBigDecimals) {
      if (column.getLength() > 9) {
        answer = new FullyQualifiedJavaType(Long.class.getName());
      } else if (column.getLength() > 4) {
        answer = new FullyQualifiedJavaType(Integer.class.getName());
      } else {
        answer = new FullyQualifiedJavaType(Short.class.getName());
      }
    } else {
      answer = defaultType;
    }

    return answer;
  }

  public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
    String answer = null;
    JavaTypeResolverMyImpl.JdbcTypeInformation jdbcTypeInformation = (JavaTypeResolverMyImpl.JdbcTypeInformation)this.typeMap.get(introspectedColumn.getJdbcType());
    if (jdbcTypeInformation != null) {
      answer = jdbcTypeInformation.getJdbcTypeName();
    }

    return answer;
  }

  public void setWarnings(List<String> warnings) {
    this.warnings = warnings;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public static class JdbcTypeInformation {
    private String jdbcTypeName;
    private FullyQualifiedJavaType fullyQualifiedJavaType;

    public JdbcTypeInformation(String jdbcTypeName, FullyQualifiedJavaType fullyQualifiedJavaType) {
      this.jdbcTypeName = jdbcTypeName;
      this.fullyQualifiedJavaType = fullyQualifiedJavaType;
    }

    public String getJdbcTypeName() {
      return this.jdbcTypeName;
    }

    public FullyQualifiedJavaType getFullyQualifiedJavaType() {
      return this.fullyQualifiedJavaType;
    }
  }
}
