package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808RegistrationMessage;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import static by.zuevvlad.jt808.util.JT808Util.decodeString;

@Component
public final class JT808RegistrationMessageDecoder extends JT808MessageDecoder<JT808RegistrationMessage> {
    private static final short MESSAGE_ID = 256;
    private static final int MANUFACTURER_ID_BYTE_COUNT = 5;
    private static final int PROVINCE_ID_BYTE_COUNT = 2;
    private static final int CITY_ID_BYTE_COUNT = 2;

    public JT808RegistrationMessageDecoder() {
        super(MESSAGE_ID);
    }

    @Override
    protected JT808RegistrationMessage decodeInternal(ByteBuf buffer, String phoneNumber) {
        skipProvinceId(buffer);
        skipCityId(buffer);
        String manufacturerId = decodeString(buffer, MANUFACTURER_ID_BYTE_COUNT);
        return new JT808RegistrationMessage(phoneNumber, manufacturerId);
    }

    private void skipProvinceId(ByteBuf buffer) {
        buffer.skipBytes(PROVINCE_ID_BYTE_COUNT);
    }

    private void skipCityId(ByteBuf buffer) {
        buffer.skipBytes(CITY_ID_BYTE_COUNT);
    }
}