package by.zuevvlad.jt808.util;

import io.netty.buffer.ByteBuf;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class JT808Util {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm::ss");

    public static String decodePhoneNumber(ByteBuf buffer) {
        byte[] bytes = new byte[6];
        buffer.readBytes(bytes);
        return bcd2String(bytes);
    }

    public static String decodeString(ByteBuf buffer, int byteCount) {
        return buffer.readCharSequence(byteCount, US_ASCII).toString();
    }

    public static String bcd2String(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            temp.append((aByte & 0xf0) >>> 4);
            temp.append(aByte & 0x0f);
        }
        return temp.toString();
    }

    public static float decodeGpsCoordinate(ByteBuf buffer) {
        return buffer.readUnsignedInt() * 1.0F / 1000000;
    }

    public static Instant decodeDateTime(ByteBuf buffer) {
        return LocalDateTime.parse(buffer.readCharSequence(6, US_ASCII).toString(), DATE_FORMAT).toInstant(ZoneOffset.UTC);
    }
}
