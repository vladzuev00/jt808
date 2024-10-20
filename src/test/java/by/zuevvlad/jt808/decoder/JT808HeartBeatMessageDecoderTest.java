package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808HeartBeatMessage;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public final class JT808HeartBeatMessageDecoderTest {
    private final JT808HeartBeatMessageDecoder decoder = new JT808HeartBeatMessageDecoder();

    @Test
    public void messageShouldBeDecodedInternally() {
        ByteBuf givenBuffer = mock(ByteBuf.class);
        String givenPhoneNumber = "";

        JT808HeartBeatMessage actual = decoder.decodeInternal(givenBuffer, givenPhoneNumber);
        assertNotNull(actual);
    }
}
