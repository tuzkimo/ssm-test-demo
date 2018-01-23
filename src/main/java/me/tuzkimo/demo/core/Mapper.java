package me.tuzkimo.demo.core;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 按需选用 Mybatis 通用 mapper 接口，
 * 所有接口介绍看官方文档：https://mapperhelper.github.io/all/
 *
 * @author tuzkimo
 * @date 2018-01-18 17:12:39
 */
public interface Mapper<T> extends BaseMapper<T>, InsertListMapper<T>, IdsMapper<T>, ConditionMapper<T> {
}
