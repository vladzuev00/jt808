package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class Jt808MessageBodyPropertiesDecoderTest {
    private final JT808MessageBodyPropertiesDecoder decoder = new JT808MessageBodyPropertiesDecoder();

    @Test
    public void propertiesShouldBeDecoded() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("0036"));

        BodyProperties actual = decoder.decode(givenBuffer);
        BodyProperties expected = new BodyProperties(false, false, 0, 54);
        assertEquals(expected, actual);
    }
}
