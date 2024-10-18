package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public final class Jt808MessageDecoderTest {

    @Mock
    private JT808MessageBodyPropertiesDecoder mockedBodyPropertiesDecoder;

    @Mock
    private JT808PhoneNumberDecoder mockedPhoneNumberDecoder;

    private TestJT808MessageDecoder messageDecoder;

    @BeforeEach
    public void initializeMessageDecoder() {
        messageDecoder = new TestJT808MessageDecoder(mockedBodyPropertiesDecoder, mockedPhoneNumberDecoder);
    }

    @Test
    public void decoderShouldBeAbleToDecodeBuffer() {
        assertTrue(messageDecoder.isAbleDecode(TestJt808Message.MESSAGE_ID));
    }

    @Test
    public void decoderShouldNotBeAbleToDecodeBuffer() {
        short givenMessageId = 254;

        assertFalse(messageDecoder.isAbleDecode(givenMessageId));
    }

    @Test
    public void messageShouldBeDecoded() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("04fa003660"));

        BodyProperties givenBodyProperties = new BodyProperties(true, false, 10, 20);
        when(mockedBodyPropertiesDecoder.decode(same(givenBuffer))).thenReturn(givenBodyProperties);

        String givenPhoneNumber = "375443434422";
        when(mockedPhoneNumberDecoder.decode(same(givenBuffer))).thenReturn(givenPhoneNumber);

        TestJt808Message actual = messageDecoder.decode(givenBuffer);
        TestJt808Message expected = new TestJt808Message(
                givenBodyProperties,
                givenPhoneNumber,
                (short) 1274,
                (short) 54,
                (byte) 96
        );
        assertEquals(expected, actual);
    }

    private static final class TestJt808Message {
        public static final short MESSAGE_ID = 255;

        public TestJt808Message(BodyProperties bodyProperties,
                                String phoneNumber,
                                short serialNumber,
                                Short body,
                                byte checkCode) {
            super(MESSAGE_ID, bodyProperties, phoneNumber, serialNumber, body, checkCode);
        }
    }

    private static final class TestJT808MessageDecoder extends JT808MessageDecoder<Short, TestJt808Message> {

        public TestJT808MessageDecoder(JT808MessageBodyPropertiesDecoder bodyPropertiesDecoder,
                                       JT808PhoneNumberDecoder phoneNumberDecoder) {
            super(TestJt808Message.MESSAGE_ID, bodyPropertiesDecoder, phoneNumberDecoder);
        }

        @Override
        protected Short decodeBody(ByteBuf buffer) {
            return buffer.readShort();
        }

        @Override
        protected TestJt808Message createMessage(BodyProperties bodyProperties,
                                                 String phoneNumber,
                                                 short serialNumber,
                                                 Short body,
                                                 byte checkCode) {
            return new TestJt808Message(bodyProperties, phoneNumber, serialNumber, body, checkCode);
        }
    }
}
