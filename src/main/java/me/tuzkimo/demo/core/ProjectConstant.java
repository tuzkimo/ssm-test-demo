package me.tuzkimo.demo.core;

/**
 * 项目的一些固定常量
 * 参考 lihengming 大神的版本写的
 *
 * 常量命名改用特定前缀方式，便于 IDE 快速定位
 * 参考尤雨溪大神的风格指南
 *
 * @author tuzkimo
 * @date 2018-01-16
 */
public class ProjectConstant {
  public static final String PACKAGE_BASE = "me.tuzkimo.demo";
  public static final String PACKAGE_MODEL = PACKAGE_BASE + ".model";
  public static final String PACKAGE_MAPPER = PACKAGE_BASE + ".dao";
  public static final String PACKAGE_SERVICE = PACKAGE_BASE + ".service";
  public static final String PACKAGE_SERVICE_IMPL = PACKAGE_SERVICE + ".impl";

  public static final String MAPPER_INTERFACE_REFERENCE = PACKAGE_BASE + ".core.Mapper";
}
