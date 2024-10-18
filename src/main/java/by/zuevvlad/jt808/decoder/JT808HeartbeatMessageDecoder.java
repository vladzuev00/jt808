package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808HeartbeatMessage;
import by.zuevvlad.jt808.model.JT808Message.BodyProperties;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public final class JT808HeartbeatMessageDecoder extends JT808MessageDecoder<Void, JT808HeartbeatMessage> {

    public JT808HeartbeatMessageDecoder(JT808MessageBodyPropertiesDecoder bodyPropertiesDecoder,
                                        JT808PhoneNumberDecoder phoneNumberDecoder) {
        super(MESSAGE_ID, bodyPropertiesDecoder, phoneNumberDecoder);
    }

    @Override
    protected Void decodeBody(ByteBuf buffer) {
        return null;
    }

    @Override
    protected JT808HeartbeatMessage createMessage(short messageId,
                                                  BodyProperties bodyProperties,
                                                  String phoneNumber,
                                                  short serialNumber,
                                                  Void body,
                                                  byte checkCode) {
        return new JT808HeartbeatMessage(messageId, bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }
}
