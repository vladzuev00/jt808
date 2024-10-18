package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808AuthenticationMessage;
import by.zuevvlad.jt808.model.JT808AuthenticationMessage.Body;
import by.zuevvlad.jt808.model.JT808Message.BodyProperties;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Component
public final class JT808AuthenticationMessageDecoder extends JT808MessageDecoder<Body, JT808AuthenticationMessage> {

    public JT808AuthenticationMessageDecoder(JT808MessageBodyPropertiesDecoder bodyPropertiesDecoder,
                                             JT808PhoneNumberDecoder phoneNumberDecoder) {
        super(MESSAGE_ID, bodyPropertiesDecoder, phoneNumberDecoder);
    }

    @Override
    protected Body decodeBody(ByteBuf buffer) {
        String authenticationCode = buffer.readCharSequence(12, US_ASCII).toString();
        return new Body(authenticationCode);
    }

    @Override
    protected JT808AuthenticationMessage createMessage(short messageId,
                                                       BodyProperties bodyProperties,
                                                       String phoneNumber,
                                                       short serialNumber,
                                                       Body body,
                                                       byte checkCode) {
        return new JT808AuthenticationMessage(messageId, bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }
}
