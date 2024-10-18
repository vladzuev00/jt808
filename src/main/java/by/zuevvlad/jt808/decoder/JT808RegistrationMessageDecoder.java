package by.zuevvlad.jt808.decoder;

import by.zuevvlad.jt808.model.JT808RegistrationMessage;
import by.zuevvlad.jt808.model.JT808RegistrationMessage.Body;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Component
public final class JT808RegistrationMessageDecoder extends JT808MessageDecoder<JT808RegistrationMessage> {

    @Override
    protected Body decodeBody(ByteBuf buffer) {
        short provinceId = readProvinceId(buffer);
        short cityId = readCityId(buffer);
        String manufacturerId = readManufacturerId(buffer);
        String terminalModel = readTerminalModel(buffer);
        String terminalId = readTerminalId(buffer);
        byte licencePlateColor = readLicencePlateColor(buffer);
        String vehicleIdentification = readVehicleIdentification(buffer);
        return new Body(
                provinceId,
                cityId,
                manufacturerId,
                terminalModel,
                terminalId,
                licencePlateColor,
                vehicleIdentification
        );
    }

    @Override
    protected JT808RegistrationMessage createMessage(BodyProperties bodyProperties,
                                                     String phoneNumber,
                                                     short serialNumber,
                                                     Body body,
                                                     byte checkCode) {
        return new JT808RegistrationMessage(bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }

    private short readProvinceId(ByteBuf buffer) {
        return buffer.readShort();
    }

    private short readCityId(ByteBuf buffer) {
        return buffer.readShort();
    }

    private String readManufacturerId(ByteBuf buffer) {
        return readString(buffer, MANUFACTURER_ID_BYTE_COUNT);
    }

    private String readTerminalModel(ByteBuf buffer) {
        return readString(buffer, TERMINAL_MODEL_BYTE_COUNT).trim();
    }

    private String readTerminalId(ByteBuf buffer) {
        return readString(buffer, TERMINAL_ID_BYTE_COUNT);
    }

    private byte readLicencePlateColor(ByteBuf buffer) {
        return buffer.readByte();
    }

    private String readVehicleIdentification(ByteBuf buffer) {
        return readString(buffer, VEHICLE_IDENTIFICATION_BYTE_COUNT);
    }

    private String readString(ByteBuf buffer, int byteCount) {
        return buffer.readCharSequence(byteCount, US_ASCII).toString();
    }

    @Override
    public JT808RegistrationMessage decode(ByteBuf buffer) {
        buffer.skipBytes(2);
        return null;
    }
}