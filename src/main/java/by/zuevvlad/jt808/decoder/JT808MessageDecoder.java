package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JT808MessageDecoder {
    private final short messageId;

    public final boolean isAbleDecode(short messageId) {
        return this.messageId == messageId;
    }

    public final M decode(ByteBuf buffer) {

    }
}
