package oceanstars.ecommerce.common.tools;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 日期工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/16 16:31
 */
public class DateUtil {

  private DateUtil() {
  }

  /**
   * 将LocalDateTime转换为Proto时间戳
   *
   * @param dateTime LocalDateTime
   * @return Proto时间戳
   */
  public static Timestamp convertLocalDateTimeToProtoTimestamp(final LocalDateTime dateTime) {

    return convertLocalDateTimeToProtoTimestamp(dateTime, ZoneId.systemDefault());
  }

  /**
   * 将LocalDateTime转换为Proto时间戳
   *
   * @param dateTime LocalDateTime
   * @param zoneId   时区
   * @return Proto时间戳
   */
  public static Timestamp convertLocalDateTimeToProtoTimestamp(final LocalDateTime dateTime, ZoneId zoneId) {

    // 获取时间戳
    final Instant instant = dateTime.atZone(zoneId).toInstant();

    return Timestamp.newBuilder()
        .setSeconds(instant.getEpochSecond())
        .setNanos(instant.getNano()).build();
  }


  /**
   * 将LocalDate转换为Proto时间戳
   *
   * @param date LocalDate
   * @return Proto时间戳
   */
  public static Timestamp convertLocalDateToProtoTimestamp(final LocalDate date) {

    return convertLocalDateToProtoTimestamp(date, ZoneId.systemDefault());
  }

  /**
   * 将LocalDate转换为Proto时间戳
   *
   * @param date   LocalDate
   * @param zoneId 时区
   * @return Proto时间戳
   */
  public static Timestamp convertLocalDateToProtoTimestamp(final LocalDate date, ZoneId zoneId) {

    // 获取时间戳
    final Instant instant = date.atStartOfDay().atZone(zoneId).toInstant();

    return Timestamp.newBuilder()
        .setSeconds(instant.getEpochSecond())
        .setNanos(instant.getNano()).build();
  }

  /**
   * 将Proto时间戳转换为LocalDateTime
   *
   * @param timestamp Proto时间戳
   * @return LocalDateTime
   */
  public static LocalDateTime convertProtoTimestampToLocalDateTime(final Timestamp timestamp) {

    return convertProtoTimestampToLocalDateTime(timestamp, ZoneId.systemDefault());
  }

  /**
   * 将Proto时间戳转换为LocalDateTime
   *
   * @param timestamp Proto时间戳
   * @param zoneId    时区
   * @return LocalDateTime
   */
  public static LocalDateTime convertProtoTimestampToLocalDateTime(final Timestamp timestamp, ZoneId zoneId) {

    // 获取时间戳
    final Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());

    return LocalDateTime.ofInstant(instant, zoneId);
  }

  /**
   * 将Proto时间戳转换为LocalDate
   *
   * @param timestamp Proto时间戳
   * @return LocalDate
   */
  public static LocalDate convertProtoTimestampToLocalDate(final Timestamp timestamp) {

    return convertProtoTimestampToLocalDate(timestamp, ZoneId.systemDefault());
  }

  /**
   * 将Proto时间戳转换为LocalDate
   *
   * @param timestamp Proto时间戳
   * @param zoneId    时区
   * @return LocalDate
   */
  public static LocalDate convertProtoTimestampToLocalDate(final Timestamp timestamp, ZoneId zoneId) {

    // 获取时间戳
    final Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());

    return LocalDate.ofInstant(instant, zoneId);
  }
}
