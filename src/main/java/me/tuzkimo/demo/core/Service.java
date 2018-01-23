package me.tuzkimo.demo.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 基础接口
 *
 * Created By tuzkimo on 2018-01-23
 */
public interface Service<T> {
  void save(T model);
  void save(List<T> models);
  void deleteById(Long id);
  // 根据 id 批量删除数据, eg: ids -> "1, 2, 3"
  void deleteByIds(String ids);
  void update(T model);
  T findById(Long id);
  // 根据 model 的成员变量查找, value 要符合 unique 约束
  T findBy(String fieldName, Object value) throws TooManyResultsException;
  // eg: ids -> "1, 2, 3"
  List<T> findByIds(String ids);
  List<T> findByCondition(Condition condition);
  List<T> findAll();
}
