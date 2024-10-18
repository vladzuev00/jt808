package by.zuevvlad.jt808.util;

import io.netty.buffer.ByteBuf;

public class JT808Util {

    public static String decodePhoneNumber(ByteBuf buffer) {
        byte[] bytes = new byte[6];
        buffer.readBytes(bytes);
        return bcd2String(bytes);
    }

    public static String bcd2String(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            temp.append((aByte & 0xf0) >>> 4);
            temp.append(aByte & 0x0f);
        }
        return temp.toString();
    }
}
