package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808RegistrationMessage;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JT808RegistrationMessageDecoderTest {
    private final JT808RegistrationMessageDecoder decoder = new JT808RegistrationMessageDecoder();

    @Test
    public void bufferShouldBeDecodedInternally() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("000000003836393737"));
        String givenPhoneNumber = "375446874433";

        JT808RegistrationMessage actual = decoder.decodeInternal(givenBuffer, givenPhoneNumber);
        JT808RegistrationMessage expected = new JT808RegistrationMessage(givenPhoneNumber, "86977");
        assertEquals(expected, actual);
    }
}
