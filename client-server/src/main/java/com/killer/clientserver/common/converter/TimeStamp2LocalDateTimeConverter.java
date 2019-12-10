package com.killer.clientserver.common.converter;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *     时间戳转LocalDateTime
 * </p>
 * @author killer
 * @date 2019/08/25 - 21:53
 */
public class TimeStamp2LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        // 有两种可能，一个是时间戳，还有一个就是字符串格式
        try {
            return LocalDateTime.ofEpochSecond(Integer.valueOf(source), 0, ZoneOffset.ofHours(8));
        } catch (Exception e) {
            try {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(source, df);
            } catch (Exception ex) {
                throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(LocalDateTime.class), source, ex);
            }
        }
    }

    public static void main(String[] args) {

        // Integer.valueOf("qweqwe");
        // DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // LocalDateTime.parse("1231231", df);
    }
}
