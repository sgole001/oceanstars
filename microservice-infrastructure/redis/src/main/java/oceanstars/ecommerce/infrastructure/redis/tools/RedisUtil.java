package oceanstars.ecommerce.infrastructure.redis.tools;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

/**
 * Redis工具类 基于spring boot和redis的redisTemplate工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:01 上午
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class RedisUtil {

  private final Logger logger = LogManager.getLogger(RedisUtil.class.getName());

  /**
   * Redis数据库操作模板对象
   */
  private RedisTemplate<String, Object> redisTemplate;

  private RedisUtil() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * 构建默认Redis工具类
   *
   * @return Redis工具类对象
   */
  public static RedisUtil build() {

    // 创建工具类实例
    RedisUtil redisUtil = new RedisUtil();

    // 使用默认配置RedisTemplate
    redisUtil.redisTemplate = (RedisTemplate<String, Object>) ApplicationContextProvider.getBean("redisTemplate");

    return redisUtil;
  }

  /**
   * 构建指定RedisTemplate的Redis工具类
   *
   * @param configGroup 指定RedisTemplate信息配置组
   * @return Redis工具类对象
   */
  public static RedisUtil build(final String configGroup) {

    // 创建工具类实例
    RedisUtil redisUtil = new RedisUtil();
    // 指定RedisTemplate
    Map<String, RedisTemplate<String, Object>> redisTemplates = (Map<String, RedisTemplate<String, Object>>) ApplicationContextProvider.getBean(
        "redisTemplates");
    redisUtil.redisTemplate = redisTemplates.get(configGroup);

    return redisUtil;
  }

  /**
   * String类型缓存获取
   *
   * @param key 键
   * @return 值
   */
  public Object get(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }

  /**
   * String类型缓存保存
   *
   * @param key   键
   * @param value 值
   * @return true：成功；false：失败
   */
  public boolean set(String key, Object value) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 指定缓存失效时间
   *
   * @param key  键
   * @param time 时间(秒)
   * @return true：成功；false：失败
   */
  public boolean expire(String key, long time) {
    try {
      if (StringUtils.isNotEmpty(key) && time > 0) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 指定缓存失效时间
   *
   * @param key      键
   * @param time     时间(秒)
   * @param timeUnit 时间单位
   * @return true：成功；false：失败
   */
  public boolean expire(String key, long time, TimeUnit timeUnit) {
    try {
      if (StringUtils.isNotEmpty(key) && time > 0) {
        redisTemplate.expire(key, time, timeUnit);
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 根据key获取过期时间
   *
   * @param key 键 不能为null
   * @return 时间(秒)返回0代表为永久有效；-1代表key不存在
   */
  public long getExpire(String key) {
    if (StringUtils.isNotEmpty(key)) {
      return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    return -1L;
  }

  /**
   * 判断key是否存在
   *
   * @param key 键
   * @return true：存在；false：不存在
   */
  public boolean exists(String key) {
    try {
      if (StringUtils.isNotEmpty(key)) {
        return redisTemplate.hasKey(key);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 删除缓存（批量）
   */
  public void delete(String... keys) {
    if (keys != null && keys.length > 0) {
      if (keys.length == 1) {
        redisTemplate.delete(keys[0]);
      } else {
        // 批量删除
        redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(keys));
      }
    }
  }

  /**
   * String类型缓存保存并设置时间
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒)：time要大于0 如果time小于等于0 将设置无限期
   * @return true：成功；false：失败
   */
  public boolean set(String key, Object value, long time) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        if (time > 0) {
          redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
          set(key, value);
        }
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 递增
   *
   * @param key   键
   * @param delta 要增加几(大于0)
   */
  public long increment(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递增因子必须大于0");
    }
    if (StringUtils.isNotEmpty(key)) {
      return redisTemplate.opsForValue().increment(key, delta);
    } else {
      return -1L;
    }
  }

  /**
   * 递减
   *
   * @param key   键
   * @param delta 要减少几(小于0)
   */
  public long decrement(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递减因子必须大于0");
    }
    if (StringUtils.isNotEmpty(key)) {
      return redisTemplate.opsForValue().increment(key, -delta);
    } else {
      return -1L;
    }
  }

  /**
   * 获取hash结构的数据 (Hash类型)
   *
   * @param key  键: 不能为null
   * @param item 项: 不能为null
   * @return 值
   */
  public Object getHash(String key, String item) {
    if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item)) {
      return redisTemplate.opsForHash().get(key, item);
    } else {
      return null;
    }
  }

  /**
   * 获取Key对应的所有键值HashMap
   *
   * @param key 键
   * @return 对应的多个键值
   */
  public Map getHashMap(String key) {
    if (StringUtils.isNotEmpty(key)) {
      return redisTemplate.opsForHash().entries(key);
    } else {
      return null;
    }
  }

  /**
   * 保存hashMap结构的数据(HashMap类型)
   *
   * @param key 键
   * @param map 对应多个键值
   * @return true:成功;false:失败
   */
  public boolean setHashMap(String key, Map map) {
    try {
      if (StringUtils.isNotEmpty(key) && null != map && map.size() > 0) {
        redisTemplate.opsForHash().putAll(key, map);
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 保存hashMap结构的数据,并设置时间 (HashMap类型)
   *
   * @param key  键
   * @param map  对应多个键值
   * @param time 时间(秒)
   * @return true:成功; false:失败
   */
  public boolean setHashMap(String key, Map map, long time) {
    try {
      if (StringUtils.isNotEmpty(key) && null != map && map.size() > 0) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
          expire(key, time);
        }
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 向一个hash类型的数据中保存数据,如果不存在将创建
   *
   * @param key   键
   * @param item  项
   * @param value 值
   * @return true：成功；false：失败
   */
  public boolean setHash(String key, String item, Object value) {
    try {
      if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item) && null != value) {
        redisTemplate.opsForHash().put(key, item, value);
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 向一个hash类型的数据中保存数据,如果不存在将创建,指定保存时间
   *
   * @param key   键
   * @param item  项
   * @param value 值
   * @param time  时间(秒)  注意:如果已存在的hash数据时间,这里将会替换原有的时间
   * @return true：成功；false：失败
   */
  public boolean setHash(String key, String item, Object value, long time) {
    try {
      if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item) && null != value) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
          expire(key, time);
        }
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 删除hash数据中的值
   *
   * @param key  键 不能为null
   * @param item 项 可以使多个 不能为null
   */
  public void deleteHash(String key, Object... item) {
    if (StringUtils.isNotEmpty(key) && null != item) {
      redisTemplate.opsForHash().delete(key, item);
    }
  }

  /**
   * 判断hash数据中是否有该项的值
   *
   * @param key  键 不能为null
   * @param item 项 不能为null
   * @return true:存在; false:不存在
   */
  public boolean existHashKey(String key, String item) {
    if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item)) {
      return redisTemplate.opsForHash().hasKey(key, item);
    } else {
      return false;
    }
  }

  /**
   * hash递增,如果不存在,就会创建一个,并把新增后的值返回
   *
   * @param key  键
   * @param item 项
   * @param by   要增加几(大于0)
   * @return -1,失败
   */
  public double incrementHash(String key, String item, double by) {
    if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item)) {
      return redisTemplate.opsForHash().increment(key, item, by);
    } else {
      return -1;
    }
  }

  /**
   * hash递减
   *
   * @param key  键
   * @param item 项
   * @param by   要减少记(小于0)
   * @return -1,失败
   */
  public double decrementHash(String key, String item, double by) {
    if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item)) {
      return redisTemplate.opsForHash().increment(key, item, -by);
    } else {
      return -1;
    }
  }

  // ============================set=============================

  /**
   * 根据key获取Set中的所有值
   *
   * @param key 键
   */
  public Set<Object> getSet(String key) {
    try {
      if (StringUtils.isNotEmpty(key)) {
        return redisTemplate.opsForSet().members(key);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return null;
  }

  /**
   * 根据value从一个set中查询,是否存在
   *
   * @param key   键
   * @param value 值
   * @return true:存在; false:不存在
   */
  public boolean existSetKey(String key, Object value) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        return redisTemplate.opsForSet().isMember(key, value);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 将数据放入set缓存
   *
   * @param key    键
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long setSet(String key, Object... values) {
    try {
      if (StringUtils.isNotEmpty(key) && null != values) {
        return redisTemplate.opsForSet().add(key, values);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return 0;
  }

  /**
   * 将set数据放入缓存,指定保存时间
   *
   * @param key    键
   * @param time   时间(秒)
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long setSet(String key, long time, Object... values) {
    try {
      if (StringUtils.isNotEmpty(key) && null != values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
          expire(key, time);
        }
        return count;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return 0;
  }

  /**
   * 获取set缓存的长度
   *
   * @param key 键
   */
  public long getSetSize(String key) {
    try {
      if (StringUtils.isNotEmpty(key)) {
        return redisTemplate.opsForSet().size(key);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return 0;
  }

  /**
   * 移除值为value的
   *
   * @param key    键
   * @param values 值 可以是多个
   * @return 移除的个数
   */
  public long removeSet(String key, Object... values) {
    try {
      if (StringUtils.isNotEmpty(key) && null != values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return 0;
  }

  // ===============================list=================================

  /**
   * 获取list缓存的内容
   *
   * @param key   键
   * @param start 开始
   * @param end   结束  0 到 -1代表所有值
   */
  public List<Object> getList(String key, long start, long end) {
    try {
      if (StringUtils.isNotEmpty(key)) {
        return redisTemplate.opsForList().range(key, start, end);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return null;
  }

  /**
   * 获取list缓存的长度
   *
   * @param key 键
   */
  public long getListSize(String key) {
    try {
      if (StringUtils.isNotEmpty(key)) {
        return redisTemplate.opsForList().size(key);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return 0;
  }

  /**
   * 通过索引 获取list中的值
   *
   * @param key   键
   * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
   */
  public Object getListIndex(String key, long index) {
    try {
      if (StringUtils.isNotEmpty(key)) {
        return redisTemplate.opsForList().index(key, index);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return null;
  }

  /**
   * 将list放入缓存,指定时间
   *
   * @param key   键
   * @param value 值
   */
  public boolean setList(String key, Object value) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        redisTemplate.opsForList().rightPush(key, value);
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 将list放入缓存，指定保存时间
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒)
   */
  public boolean setList(String key, Object value, long time) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) {
          expire(key, time);
        }
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 将list放入缓存
   *
   * @param key   键
   * @param value 值
   */
  public boolean setList(String key, List<Object> value) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        redisTemplate.opsForList().rightPushAll(key, value);
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 将list放入缓存，指定时间
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒)
   */
  public boolean setList(String key, List<Object> value, long time) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
          expire(key, time);
        }
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 根据索引修改list中的某条数据
   *
   * @param key   键
   * @param index 索引
   * @param value 值
   */
  public boolean updateListIndex(String key, long index, Object value) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        redisTemplate.opsForList().set(key, index, value);
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  /**
   * 移除N个值为value
   *
   * @param key   键
   * @param count 移除多少个
   * @param value 值
   * @return 移除的个数
   */
  public long removeList(String key, long count, Object value) {
    try {
      if (StringUtils.isNotEmpty(key) && null != value) {
        Long remove = redisTemplate.opsForList().remove(key, count, value);
        return remove;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return 0;
  }
}
