package by.zuevvlad.jt808.it.decoding;

import by.zuevvlad.jt808.base.AbstractSpringBootTest;
import by.zuevvlad.jt808.decoder.JT808MessagesDecoder;
import by.zuevvlad.jt808.model.JT808RegistrationMessage;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class Jt808MessageDecodingIT extends AbstractSpringBootTest {

    @Autowired
    private JT808MessagesDecoder decoder;

    @ParameterizedTest
    @MethodSource("provideHexDumpAndExpectedMessage")
    public void messageShouldBeDecoded(String givenHexDump, Jt808Message<?> expected) {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump(givenHexDump));

        Jt808Message<?> actual = decoder.decode(givenBuffer);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideHexDumpAndExpectedMessage() {
        return Stream.of(
                Arguments.of(
                        "7e0100003607006195286504fa0000000038363937374e5438303800000000000000000000000000000031393532383635004c42313233343536373839303132333435607e",
                        new JT808RegistrationMessage(
                                new BodyProperties(false, false, 0, 54),
                                "070061952865",
                                (short) 1274,
                                new JT808RegistrationMessage.Body(
                                        (short) 0,
                                        (short) 0,
                                        "86977",
                                        "NT808",
                                        "1952865",
                                        (byte) 0,
                                        "LB123456789012345"
                                ),
                                (byte) 96
                        )
                ),
                Arguments.of(
                        "7e0102000c07006195286504fc3037303036313935323836354c7e",
                        new JT808AuthenticationMessage(
                                new BodyProperties(false, false, 0, 12),
                                "070061952865",
                                (short) 1276,
                                new JT808AuthenticationMessage.Body("070061952865"),
                                (byte) 76
                        )
                ),
                Arguments.of(
                        "7e000200000072610190040378ff7e",
                        new JT808HeartbeatMessage(
                                new BodyProperties(false, false, 0, 0),
                                "007261019004",
                                (short) 888,
                                null,
                                (byte) 255
                        )
                )
        );
    }
}
