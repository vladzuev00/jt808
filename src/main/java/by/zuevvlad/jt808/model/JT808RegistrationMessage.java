package by.zuevvlad.jt808.model;

import lombok.Value;

public final class JT808RegistrationMessage extends JT808Message<JT808RegistrationMessage.Body> {
    public static final short MESSAGE_ID = 256;

    public JT808RegistrationMessage(BodyProperties bodyProperties,
                                    String phoneNumber,
                                    short serialNumber,
                                    Body body,
                                    byte checkCode) {
        super(MESSAGE_ID, bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }

    @Value
    public static class Body {
        short provinceId;
        short cityId;
        String manufacturerId;
        String terminalModel;
        String terminalId;
        byte licensePlateColor;
        String vehicleIdentification;
    }
}
