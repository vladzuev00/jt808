package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.util.JT808Util;
import io.netty.buffer.ByteBuf;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static by.zuevvlad.jt808.util.JT808Util.decodePhoneNumber;
import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mockStatic;

public final class Jt808MessageDecoderTest {
    private final TestJT808MessageDecoder decoder = new TestJT808MessageDecoder();

    @Test
    public void decoderShouldBeAbleToDecodeBuffer() {
        assertTrue(decoder.isAbleDecode(TestJT808MessageDecoder.MESSAGE_ID));
    }

    @Test
    public void decoderShouldNotBeAbleToDecodeBuffer() {
        short givenMessageId = TestJT808MessageDecoder.MESSAGE_ID - 1;

        assertFalse(decoder.isAbleDecode(givenMessageId));
    }

    @Test
    public void messageShouldBeDecoded() {
        try (MockedStatic<JT808Util> mockedJT808Util = mockStatic(JT808Util.class)) {
            ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("003604fa003760"));

            String givenPhoneNumber = "070061952865";
            mockedJT808Util.when(() -> decodePhoneNumber(same(givenBuffer))).thenReturn(givenPhoneNumber);

            TestJT808Message actual = decoder.decode(givenBuffer);
            TestJT808Message expected = new TestJT808Message(givenPhoneNumber, (short) 55);
            assertEquals(expected, actual);

            assertEquals(0, givenBuffer.readableBytes());
        }
    }

    @Value
    private static class TestJT808Message {
        String phoneNumber;
        short value;
    }

    private static final class TestJT808MessageDecoder extends JT808MessageDecoder<TestJT808Message> {
        private static final short MESSAGE_ID = 255;

        public TestJT808MessageDecoder() {
            super(MESSAGE_ID);
        }

        @Override
        protected TestJT808Message decodeInternal(ByteBuf buffer, String phoneNumber) {
            short value = buffer.readShort();
            return new TestJT808Message(phoneNumber, value);
        }
    }
}
