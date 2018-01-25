package me.tuzkimo.demo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Spring MVC 配置
 *
 * Created by tuzkimo on 2018-01-24 15:14
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  // 使用 FastJSON 进行序列化/反序列化
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    converter.setFeatures(SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.UseISO8601DateFormat);
    converter.setCharset(FastJsonHttpMessageConverter.UTF8);
    converters.add(converter);
  }
}
