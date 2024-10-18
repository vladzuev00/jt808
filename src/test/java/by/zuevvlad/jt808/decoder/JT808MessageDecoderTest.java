package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808Message;
import by.zuevvlad.jt808.model.JT808Message.BodyProperties;
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
public final class JT808MessageDecoderTest {

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
        assertTrue(messageDecoder.isAbleDecode(TestJT808MessageDecoder.MESSAGE_ID));
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

        TestJT808Message actual = messageDecoder.decode(givenBuffer);
        TestJT808Message expected = new TestJT808Message(
                TestJT808MessageDecoder.MESSAGE_ID,
                givenBodyProperties,
                givenPhoneNumber,
                (short) 1274,
                (short) 54,
                (byte) 96
        );
        assertEquals(expected, actual);
    }

    private static final class TestJT808Message extends JT808Message<Short> {

        public TestJT808Message(short messageId,
                                BodyProperties bodyProperties,
                                String phoneNumber,
                                short serialNumber,
                                Short body,
                                byte checkCode) {
            super(messageId, bodyProperties, phoneNumber, serialNumber, body, checkCode);
        }
    }

    private static final class TestJT808MessageDecoder extends JT808MessageDecoder<Short, TestJT808Message> {
        private static final short MESSAGE_ID = 255;

        public TestJT808MessageDecoder(JT808MessageBodyPropertiesDecoder bodyPropertiesDecoder,
                                       JT808PhoneNumberDecoder phoneNumberDecoder) {
            super(MESSAGE_ID, bodyPropertiesDecoder, phoneNumberDecoder);
        }

        @Override
        protected Short decodeBody(ByteBuf buffer) {
            return buffer.readShort();
        }

        @Override
        protected TestJT808Message createMessage(short messageId,
                                                 BodyProperties bodyProperties,
                                                 String phoneNumber,
                                                 short serialNumber,
                                                 Short body,
                                                 byte checkCode) {
            return new TestJT808Message(messageId, bodyProperties, phoneNumber, serialNumber, body, checkCode);
        }
    }
}
