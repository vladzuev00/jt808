package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import static by.zuevvlad.jt808.decoder.JT808SingleLocationMessageDecoder.LOCATION_COUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public final class JT808SingleLocationMessageDecoderTest {
    private final JT808SingleLocationMessageDecoder decoder = new JT808SingleLocationMessageDecoder();

    @Test
    public void locationCountShouldBeDecoded() {
        ByteBuf givenBuffer = mock(ByteBuf.class);

        int actual = decoder.decodeLocationCount(givenBuffer);
        assertEquals(LOCATION_COUNT, actual);

        verifyNoInteractions(givenBuffer);
    }

    @Test
    public void bufferShouldBeSkippedUntilFirstLocation() {
        ByteBuf givenBuffer = mock(ByteBuf.class);

        decoder.skipUntilFirstLocation(givenBuffer);

        verifyNoInteractions(givenBuffer);
    }

    @Test
    public void locationPrefixShouldBeSkipped() {
        ByteBuf givenBuffer = mock(ByteBuf.class);

        decoder.skipLocationPrefix(givenBuffer);

        verifyNoInteractions(givenBuffer);
    }
}
