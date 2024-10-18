package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808Message;
import by.zuevvlad.jt808.model.JT808Message.BodyProperties;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JT808MessageDecoder<B, M extends JT808Message<B>> {
    private final JT808MessageBodyPropertiesDecoder bodyPropertiesDecoder;
    private final JT808PhoneNumberDecoder phoneNumberDecoder;

    public final M decode(ByteBuf buffer) {
        BodyProperties bodyProperties = bodyPropertiesDecoder.decode(buffer);
        String phoneNumber = phoneNumberDecoder.decode(buffer);
        short serialNumber = buffer.readShort();
        B body = decodeBody(buffer);
        byte checkCode = buffer.readByte();
        return createMessage(bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }

    protected abstract B decodeBody(ByteBuf buffer);

    protected abstract M createMessage(BodyProperties bodyProperties,
                                       String phoneNumber,
                                       short serialNumber,
                                       B body,
                                       byte checkCode);
}
