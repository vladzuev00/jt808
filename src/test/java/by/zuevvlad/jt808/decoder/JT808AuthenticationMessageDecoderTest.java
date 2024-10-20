package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808AuthenticationMessage;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public final class JT808AuthenticationMessageDecoderTest {
    private final JT808AuthenticationMessageDecoder decoder = new JT808AuthenticationMessageDecoder();

    @Test
    public void messageShouldBeDecodedInternally() {
        ByteBuf givenBuffer = mock(ByteBuf.class);
        String givenPhoneNumber = "";

        JT808AuthenticationMessage actual = decoder.decodeInternal(givenBuffer, givenPhoneNumber);
        assertNotNull(actual);
    }
}
