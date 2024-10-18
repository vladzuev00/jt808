package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808RegistrationMessage;
import by.zuevvlad.jt808.model.JT808RegistrationMessage.Body;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JT808RegistrationMessageDecoderTest {
    private final JT808RegistrationMessageDecoder decoder = new JT808RegistrationMessageDecoder(null, null);

    @Test
    public void bodyShouldBeDecoded() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("0000000038363937374E5438303800000000000000000000000000000031393532383635004C42313233343536373839303132333435"));

        Body actual = decoder.decodeBody(givenBuffer);
        Body expected = new Body((short) 0, (short) 0, "86977", "NT808", "1952865", (byte) 0, "LB123456789012345");
        assertEquals(expected, actual);
    }

    @Test
    public void messageShouldBeCreated() {
        BodyProperties givenBodyProperties = new BodyProperties(true, false, 10, 20);
        String givenPhoneNumber = "375446932345";
        short givenSerialNumber = 234;
        Body givenBody = new Body((short) 0, (short) 0, "86977", "NT808", "1952865", (byte) 0, "LB123456789012345");
        byte givenCheckCode = 55;

        JT808RegistrationMessage actual = decoder.createMessage(
                givenBodyProperties,
                givenPhoneNumber,
                givenSerialNumber,
                givenBody,
                givenCheckCode
        );
        JT808RegistrationMessage expected = new JT808RegistrationMessage(
                givenBodyProperties,
                givenPhoneNumber,
                givenSerialNumber,
                givenBody,
                givenCheckCode
        );
        assertEquals(expected, actual);
    }
}
