package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808Message.BodyProperties;
import by.zuevvlad.jt808.model.JT808TerminalRegistrationMessage;
import by.zuevvlad.jt808.model.JT808TerminalRegistrationMessage.Body;
import io.netty.buffer.ByteBuf;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class JT808TerminalRegistrationMessageDecoder extends JT808MessageDecoder<Body, JT808TerminalRegistrationMessage> {

    public JT808TerminalRegistrationMessageDecoder(JT808MessageBodyPropertiesDecoder bodyPropertiesDecoder, JT808PhoneNumberDecoder phoneNumberDecoder) {
        super(bodyPropertiesDecoder, phoneNumberDecoder);
    }

    @Override
    protected Body decodeBody(ByteBuf buffer) {
        short provinceId = buffer.readShort();
        short cityId = buffer.readShort();
        String manufacturerId = buffer.readCharSequence(5, US_ASCII).toString();
        String terminalModel = buffer.readCharSequence(20, US_ASCII).toString().trim();
        String terminalId = buffer.readCharSequence(7, US_ASCII).toString();
        byte licencePlateColor = buffer.readByte();
        String vehicleIdentification = buffer.readCharSequence(17, US_ASCII).toString();
        return new Body(provinceId, cityId, manufacturerId, terminalModel, terminalId, licencePlateColor, vehicleIdentification);
    }

    @Override
    protected JT808TerminalRegistrationMessage createMessage(BodyProperties bodyProperties, String phoneNumber, short serialNumber, Body body, byte checkCode) {
        return new JT808TerminalRegistrationMessage(bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }
}