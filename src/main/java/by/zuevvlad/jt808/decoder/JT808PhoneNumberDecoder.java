package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public final class JT808PhoneNumberDecoder {

    public String decode(ByteBuf buffer) {
        byte[] bytes = new byte[6];
        buffer.readBytes(bytes);
        return bcd2String(bytes);
    }

    public String bcd2String(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            temp.append((aByte & 0xf0) >>> 4);
            temp.append(aByte & 0x0f);
        }
        return temp.toString();
    }
}