package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808LocationMessage;
import by.zuevvlad.jt808.model.Location;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static java.time.Instant.parse;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JT808LocationMessageDecoderTest {
    private final JT808LocationMessageDecoder decoder = new TestJT808LocationMessageDecoder();

    @Test
    public void bufferShouldBeDecodedInternally() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("00010100420001000000000001015881C906CA8E0500000000000023072707091430011F31010051080000000000000000560231005708000200000000000063020000FD020026"));
        String givenPhoneNumber = "";

        JT808LocationMessage actual = decoder.decodeInternal(givenBuffer, givenPhoneNumber);
        JT808LocationMessage expected = new JT808LocationMessage(
                singletonList(
                        new Location(
                                parse("2023-07-27T07:09:14Z"),
                                22.577608F,
                                11.393793F,
                                (short) 0,
                                (short) 0,
                                (short) 0
                        )
                )
        );
        assertEquals(expected, actual);
    }

    private static final class TestJT808LocationMessageDecoder extends JT808LocationMessageDecoder {
        private static final short MESSAGE_ID = 255;

        public TestJT808LocationMessageDecoder() {
            super(MESSAGE_ID);
        }

        @Override
        protected int decodeLocationCount(ByteBuf buffer) {
            return buffer.readShort();
        }

        @Override
        protected void skipToFirstLocation(ByteBuf buffer) {
            buffer.skipBytes(Byte.BYTES);
        }

        @Override
        protected void skipLocationPrefix(ByteBuf buffer) {
            buffer.skipBytes(Short.BYTES);
        }
    }
}
