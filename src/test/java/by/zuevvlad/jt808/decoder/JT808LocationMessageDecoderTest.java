package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808LocationMessage;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static java.time.Instant.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JT808LocationMessageDecoderTest {
    private final JT808LocationMessageDecoder decoder = new JT808LocationMessageDecoder();

    @Test
    public void bufferShouldBeDecodedInternally() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("0001000000000001015881c906ca8e0500000000000023072707091430011f31010051080000000000000000560231005708000200000000000063020000fd02002615"));
        String givenPhoneNumber = "";

        JT808LocationMessage actual = decoder.decodeInternal(givenBuffer, givenPhoneNumber);
        JT808LocationMessage expected = new JT808LocationMessage(
                parse("2023-07-27T07:09:14Z"),
                22.577608F,
                11.393793F,
                (short) 0,
                (short) 0,
                (short) 0
        );
        assertEquals(expected, actual);
    }
}
