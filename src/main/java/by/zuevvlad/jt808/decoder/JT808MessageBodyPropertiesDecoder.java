package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808Message.BodyProperties;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public final class JT808MessageBodyPropertiesDecoder {

    public BodyProperties decode(ByteBuf buffer) {
        short value = buffer.readShort();
        return new BodyProperties(
                isReversed(value),
                isContainSubpackage(value),
                getEncryptionType(value),
                getLength(value)
        );
    }

    private boolean isReversed(short value) {
        return ((value & 0xc000) >> 14) == 1;
    }

    private boolean isContainSubpackage(short value) {
        return ((value & 0x2000) >> 13) == 1;
    }

    private int getEncryptionType(short value) {
        return (value & 0x1c00) >> 10;
    }

    private int getLength(short value) {
        return value & 0x3ff;
    }
}