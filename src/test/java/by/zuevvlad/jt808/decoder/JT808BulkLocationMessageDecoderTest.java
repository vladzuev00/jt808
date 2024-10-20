package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JT808BulkLocationMessageDecoderTest {
    private final JT808BulkLocationMessageDecoder decoder = new JT808BulkLocationMessageDecoder();

    @Test
    public void locationCountShouldBeDecoded() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("0100"));

        int actual = decoder.decodeLocationCount(givenBuffer);
        int expected = 256;
        assertEquals(expected, actual);
    }

    @Test
    public void firstLocationShouldBeSkipped() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("010001000100"));

        decoder.skipUntilFirstLocation(givenBuffer);

        int actual = givenBuffer.readerIndex();
        int expected = Byte.BYTES;
        assertEquals(expected, actual);
    }

    @Test
    public void locationPrefixShouldBeSkipped() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("010001000100"));

        decoder.skipLocationPrefix(givenBuffer);

        int actual = givenBuffer.readerIndex();
        int expected = Short.BYTES;
        assertEquals(expected, actual);
    }
}
