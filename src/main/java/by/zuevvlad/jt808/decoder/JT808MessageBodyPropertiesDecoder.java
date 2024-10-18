package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808Message.BodyProperties;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public final class JT808MessageBodyPropertiesDecoder {

    public BodyProperties decode(ByteBuf buffer) {
        short value = buffer.readShort();
        boolean reversed = decodeReversed(value);
        boolean containSubpackage = decodeContainSubpackage(value);
        int encryptionType = decodeEncryptionType(value);
        int length = decodeLength(value);
        return new BodyProperties(reversed, containSubpackage, encryptionType, length);
    }

    private boolean decodeReversed(short value) {
        return ((value & 0xc000) >> 14) == 1;
    }

    private boolean decodeContainSubpackage(short value) {
        return ((value & 0x2000) >> 13) == 1;
    }

    private int decodeEncryptionType(short value) {
        return (value & 0x1c00) >> 10;
    }

    private int decodeLength(short value) {
        return value & 0x3ff;
    }
}