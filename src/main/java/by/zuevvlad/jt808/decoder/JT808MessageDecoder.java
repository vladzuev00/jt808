package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

import static by.zuevvlad.jt808.util.JT808Util.decodePhoneNumber;

@RequiredArgsConstructor
public abstract class JT808MessageDecoder<M> {
    private static final int BODY_PROPERTIES_BYTES = 2;
    private static final int SERIAL_NUMBER_BYTES = 2;
    private static final int CHECK_CODE_BYTES = 1;

    private final short messageId;

    public final boolean isAbleDecode(short messageId) {
        return this.messageId == messageId;
    }

    public final M decode(ByteBuf buffer) {
        skipBodyProperties(buffer);
        String phoneNumber = decodePhoneNumber(buffer);
        skipSerialNumber(buffer);
        M message = decodeInternal(buffer, phoneNumber);
        skipCheckCode(buffer);
        return message;
    }

    protected abstract M decodeInternal(ByteBuf buffer, String phoneNumber);

    private void skipBodyProperties(ByteBuf buffer) {
        buffer.skipBytes(BODY_PROPERTIES_BYTES);
    }

    private void skipSerialNumber(ByteBuf buffer) {
        buffer.skipBytes(SERIAL_NUMBER_BYTES);
    }

    private void skipCheckCode(ByteBuf buffer) {
        buffer.skipBytes(CHECK_CODE_BYTES);
    }
}
