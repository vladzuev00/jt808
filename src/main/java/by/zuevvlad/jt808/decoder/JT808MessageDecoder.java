package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JT808MessageDecoder<M> {
    private final short messageId;

    public final boolean isAbleDecode(short messageId) {
        return this.messageId == messageId;
    }

    public abstract M decode(ByteBuf buffer);
}
