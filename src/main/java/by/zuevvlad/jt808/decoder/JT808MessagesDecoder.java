package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808Message;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public final class JT808MessagesDecoder {
    private final List<JT808MessageDecoder<?, ?>> decoders;

    public JT808Message<?> decode(ByteBuf buffer) {
        buffer.retain();
        try {
            ByteBuf trimmedBuffer = trimIdentification(buffer);
            short messageId = trimmedBuffer.readShort();
            return findDecoder(messageId).decode(trimmedBuffer);
        } finally {
            buffer.release();
        }
    }

    private ByteBuf trimIdentification(ByteBuf buffer) {
        return buffer.slice(1, buffer.writerIndex() - 2);
    }

    private JT808MessageDecoder<?, ?> findDecoder(short messageId) {
        return decoders.stream()
                .filter(decoder -> decoder.isAbleDecode(messageId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No decoder for message's id '%d'".formatted(messageId)));
    }
}
