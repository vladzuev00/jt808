package by.zuevvlad.jt808.decoder;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.zuevvlad.jt808.decoder.JT808MessagesDecoder.IDENTIFICATION_BYTE_COUNT;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public final class JT808MessagesDecoderTest {

    @Mock
    private JT808MessageDecoder<Object> firstMockedDecoder;

    @Mock
    private JT808MessageDecoder<Object> secondMockedDecoder;

    @Mock
    private JT808MessageDecoder<Object> thirdMockedDecoder;

    private JT808MessagesDecoder messagesDecoder;

    @BeforeEach
    public void initializeMessagesDecoder() {
        messagesDecoder = new JT808MessagesDecoder(List.of(firstMockedDecoder, secondMockedDecoder, thirdMockedDecoder));
    }

    @Test
    public void messageShouldBeDecoded() {
        ByteBuf givenBuffer = mock(ByteBuf.class);

        int givenWriterIndex = 10;
        when(givenBuffer.writerIndex()).thenReturn(givenWriterIndex);

        int expectedLength = givenWriterIndex - 1 - IDENTIFICATION_BYTE_COUNT;
        ByteBuf givenTrimmedBuffer = mock(ByteBuf.class);
        when(givenBuffer.slice(eq(IDENTIFICATION_BYTE_COUNT), eq(expectedLength))).thenReturn(givenTrimmedBuffer);

        short givenMessageId = 255;
        when(givenTrimmedBuffer.readShort()).thenReturn(givenMessageId);

        when(firstMockedDecoder.isAbleDecode(eq(givenMessageId))).thenReturn(false);
        when(secondMockedDecoder.isAbleDecode(eq(givenMessageId))).thenReturn(true);

        Object givenMessage = new Object();
        when(secondMockedDecoder.decode(same(givenTrimmedBuffer))).thenReturn(givenMessage);

        Object actual = messagesDecoder.decode(givenBuffer);
        assertSame(givenMessage, actual);

        verify(givenBuffer, times(1)).retain();
        verify(givenBuffer, times(1)).release();
        verify(firstMockedDecoder, times(0)).decode(any(ByteBuf.class));
        verifyNoInteractions(thirdMockedDecoder);
    }

    @Test
    public void messageShouldNotBeDecoded() {
        ByteBuf givenBuffer = mock(ByteBuf.class);

        int givenWriterIndex = 10;
        when(givenBuffer.writerIndex()).thenReturn(givenWriterIndex);

        int expectedLength = givenWriterIndex - 1 - IDENTIFICATION_BYTE_COUNT;
        ByteBuf givenTrimmedBuffer = mock(ByteBuf.class);
        when(givenBuffer.slice(eq(IDENTIFICATION_BYTE_COUNT), eq(expectedLength))).thenReturn(givenTrimmedBuffer);

        short givenMessageId = 255;
        when(givenTrimmedBuffer.readShort()).thenReturn(givenMessageId);

        when(firstMockedDecoder.isAbleDecode(eq(givenMessageId))).thenReturn(false);
        when(secondMockedDecoder.isAbleDecode(eq(givenMessageId))).thenReturn(false);
        when(thirdMockedDecoder.isAbleDecode(eq(givenMessageId))).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> messagesDecoder.decode(givenBuffer));

        verify(givenBuffer, times(1)).retain();
        verify(givenBuffer, times(1)).release();
        verify(firstMockedDecoder, times(0)).decode(any(ByteBuf.class));
        verify(secondMockedDecoder, times(0)).decode(any(ByteBuf.class));
        verify(thirdMockedDecoder, times(0)).decode(any(ByteBuf.class));
    }
}
