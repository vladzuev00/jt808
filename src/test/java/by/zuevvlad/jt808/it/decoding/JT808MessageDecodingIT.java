package by.zuevvlad.jt808.it.decoding;

import by.zuevvlad.jt808.base.AbstractSpringBootTest;
import by.zuevvlad.jt808.decoder.JT808MessagesDecoder;
import by.zuevvlad.jt808.model.JT808Message;
import by.zuevvlad.jt808.model.JT808Message.BodyProperties;
import by.zuevvlad.jt808.model.JT808TerminalRegistrationMessage;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JT808MessageDecodingIT extends AbstractSpringBootTest {

    @Autowired
    private JT808MessagesDecoder decoder;

    @Test
    public void terminalRegistrationMessageShouldBeDecoded() {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump("7e0100003607006195286504fa0000000038363937374e5438303800000000000000000000000000000031393532383635004c42313233343536373839303132333435607e"));

        JT808Message<?> actual = decoder.decode(givenBuffer);
        JT808Message<?> expected = new JT808TerminalRegistrationMessage(
                (short) 256,
                new BodyProperties(false, false, 0, 54),
                "070061952865",
                (short) 1274,
                new JT808TerminalRegistrationMessage.Body(
                        (short) 0,
                        (short) 0,
                        "86977",
                        "NT808",
                        "1952865",
                        (byte) 0,
                        "LB123456789012345"
                ),
                (byte) 96
        );
        assertEquals(expected, actual);
    }
}
