package by.zuevvlad.jt808.it.decoding;

import by.zuevvlad.jt808.base.AbstractSpringBootTest;
import by.zuevvlad.jt808.decoder.JT808MessagesDecoder;
import by.zuevvlad.jt808.model.*;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static io.netty.buffer.ByteBufUtil.decodeHexDump;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static java.time.Instant.parse;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JT808MessageDecodingIT extends AbstractSpringBootTest {

    @Autowired
    private JT808MessagesDecoder decoder;

    @ParameterizedTest
    @MethodSource("provideHexDumpAndExpectedMessage")
    public void messageShouldBeDecoded(String givenHexDump, Object expected) {
        ByteBuf givenBuffer = wrappedBuffer(decodeHexDump(givenHexDump));

        Object actual = decoder.decode(givenBuffer);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideHexDumpAndExpectedMessage() {
        return Stream.of(
                Arguments.of(
                        "7e0100003607006195286504fa0000000038363937374e5438303800000000000000000000000000000031393532383635004c42313233343536373839303132333435607e",
                        new JT808RegistrationMessage("070061952865", "86977")
                ),
                Arguments.of(
                        "7e0100002c0182700570781022001f00000000000000434152564953204d442d34343453440000000000303035373037380142313233343536bc7e",
                        new JT808RegistrationMessage("018270057078", "")
                ),
                Arguments.of(
                        "7e0200004207006195286500520001000000000001015881c906ca8e0500000000000023072707091430011f31010051080000000000000000560231005708000200000000000063020000fd020026157e",
                        new JT808LocationMessage(
                                singletonList(
                                        new Location(
                                                parse("2023-07-27T07:09:14Z"),
                                                22.577608F,
                                                11.393793F,
                                                (short) 0,
                                                (short) 0,
                                                (short) 0
                                        )
                                )
                        )
                ),
                Arguments.of(
                        "7e000200000072610190040378ff7e",
                        new JT808HeartBeatMessage()
                ),
                Arguments.of(
                        "7e07040047070061952865004a00010100420001000000000001015881c906ca8e0500000000000023072707091430011f31010051080000000000000000560231005708000200000000000063020000fd020026107e",
                        new JT808LocationMessage(
                                singletonList(
                                        new Location(
                                                parse("2023-07-27T07:09:14Z"),
                                                22.577608F,
                                                11.393793F,
                                                (short) 0,
                                                (short) 0,
                                                (short) 0
                                        )
                                )
                        )
                ),
                Arguments.of(
                        "7e0102000c07006195286504fc3037303036313935323836354c7e",
                        new JT808AuthenticationMessage()
                )
        );
    }
}
