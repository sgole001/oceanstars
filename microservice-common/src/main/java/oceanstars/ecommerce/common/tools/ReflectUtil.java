package oceanstars.ecommerce.common.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

/**
 * 反射工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 7:08 下午
 */
public class ReflectUtil {

  /**
   * 日志管理器
   */
  private static final Logger logger = LogManager.getLogger(ReflectUtil.class.getName());

  /**
   * 构造函数：私有化，显式表示静态工具类
   */
  private ReflectUtil() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * 获取反射成员数据
   *
   * @param source    数据对象
   * @param fieldName 反射成员名
   * @return 反射成员数据
   */
  public static Object getFieldValue(Object source, String fieldName) {

    // 参数非空判定
    if (source == null) {
      return null;
    }

    // 反射成员对象获取
    Field field = getField(source.getClass(), fieldName);

    try {

      // 获取反射成员数据
      return FieldUtils.readField(field, source, Boolean.TRUE);

    } catch (Exception e) {
      // 日志收集
      logger.error(e);
    }

    return null;
  }

  /**
   * 设置反射成员数据
   *
   * @param source    数据对象
   * @param fieldName 反射成员名
   * @param value     设置数据
   */
  public static void setFieldValue(Object source, String fieldName, Object value) {

    // 参数非空判定
    if (source == null) {
      return;
    }

    // 反射成员对象获取
    Field field = getField(source.getClass(), fieldName);

    try {

      // 设置反射成员数据
      FieldUtils.writeField(field, source, value);

    } catch (Exception e) {
      // 日志收集
      logger.error(e);
    }
  }

  /**
   * 获取数据对象的反射成员对象
   *
   * @param sourceClass 数据对象类
   * @param fieldName   成员名
   * @return 反射成员对象
   */
  public static Field getField(Class<?> sourceClass, String fieldName) {

    // 参数非空判定
    if (sourceClass == null) {
      return null;
    }

    // 递归跳出条件（父类为Object）
    if (sourceClass.equals(Object.class)) {
      return null;
    }

    // 反射对象获取
    Field field = FieldUtils.getDeclaredField(sourceClass, fieldName, Boolean.TRUE);

    return Optional.ofNullable(field).orElse(getField(sourceClass.getSuperclass(), fieldName));
  }


  /**
   * 根据指定注解信息萃取对象字段列表
   *
   * @param target        萃取对象类
   * @param annotationCls 指定注解对象类
   * @return 萃取对象字段列表
   */
  @SafeVarargs
  public static List<Field> getFieldsWithAnnotation(Class<?> target, Class<Annotation>... annotationCls) {

    // 获取指定注解的字段信息列表
    List<Field> fields = Arrays.stream(target.getDeclaredFields())
        // 过滤指定范围内注解的字段信息
        .filter(
            // 遍历指定注解类型，判定字段是否拥有相关注解
            field -> Arrays.stream(annotationCls).anyMatch(cls -> field.getAnnotation(cls) != null))
        // 数组转列表
        .collect(Collectors.toList());

    // 递归跳出（条件：没有父类）
    if (target.getSuperclass() == null) {
      return fields;
    }

    // 递归查询父类字段呢信息
    if (CollectionUtils.isEmpty(fields)) {
      fields = getFieldsWithAnnotation(target.getSuperclass(), annotationCls);
    } else {
      fields.addAll(getFieldsWithAnnotation(target.getSuperclass(), annotationCls));
    }

    return fields;
  }

  /**
   * 获取数据对象的反射方法对象
   *
   * @param objectCls      数据对象类型
   * @param methodName     方法名
   * @param parameterTypes 方法参数类型
   * @return 数据对象的反射方法对象
   */
  public static Method getDeclaredMethod(Class<?> objectCls, String methodName, Class<?>... parameterTypes) {

    // 参数非空判定
    if (objectCls == null) {
      return null;
    }

    // 递归跳出条件（父类为Object）
    if (objectCls.equals(Object.class)) {
      return null;
    }

    // 获取对象指定方法
    try {
      return objectCls.getDeclaredMethod(methodName, parameterTypes);
    } catch (NoSuchMethodException e) {
      return getDeclaredMethod(objectCls.getSuperclass(), methodName, parameterTypes);
    }
  }

  /**
   * 调用对象成员方法，无视修饰符（private,protected）
   *
   * @param object         数据对象
   * @param methodName     方法名
   * @param parameterTypes 方法参数类型
   * @param parameters     参数数据
   * @return 对象成员方法执行后返回对象
   */
  public static Object invoke(Object object, String methodName, Class<?>[] parameterTypes, Object... parameters) {

    // 参数非空判定
    if (object == null) {
      return null;
    }

    // 反射方法对象获取
    Method method = getDeclaredMethod(object.getClass(), methodName, parameterTypes);

    if (null == method) {
      return null;
    }

    // 执行方法
    try {
      return method.invoke(object, parameters);
    } catch (Exception e) {
      // 日志收集
      logger.error(e);
    }

    return null;
  }
}
