package me.tuzkimo.demo.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Service 接口虚拟实现类，基于通用 MyBatis Mapper 插件
 */
public abstract class AbstractService<T> implements Service<T> {

  @Autowired
  protected Mapper<T> mapper;

  // 当前泛型的真实类型
  private Class<T> modelClass;

  public AbstractService() {
    ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
    modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
  }

  @Override
  public void save(T model) {
    mapper.insertSelective(model);
  }

  @Override
  public void save(List<T> models) {
    mapper.insertList(models);
  }

  @Override
  public void deleteById(Long id) {
    mapper.deleteByPrimaryKey(id);
  }

  @Override
  public void deleteByIds(String ids) {
    mapper.deleteByIds(ids);
  }

  @Override
  public void update(T model) {
    mapper.updateByPrimaryKeySelective(model);
  }

  @Override
  public T findById(Long id) {
    return mapper.selectByPrimaryKey(id);
  }

  @Override
  public T findBy(String fieldName, Object value) throws TooManyResultsException {
    try {
      T model = modelClass.newInstance();
      Field field = modelClass.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(model, value);
      return mapper.selectOne(model);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage(), e);
    }
  }

  @Override
  public List<T> findByIds(String ids) {
    return mapper.selectByIds(ids);
  }

  @Override
  public List<T> findByCondition(Condition condition) {
    return mapper.selectByCondition(condition);
  }

  @Override
  public List<T> findAll() {
    return mapper.selectAll();
  }
}
