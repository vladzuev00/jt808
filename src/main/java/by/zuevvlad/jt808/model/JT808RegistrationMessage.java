package by.zuevvlad.jt808.model;

import lombok.Value;

public final class JT808RegistrationMessage extends JT808Message<JT808RegistrationMessage.Body> {

    public JT808RegistrationMessage(short id,
                                    BodyProperties bodyProperties,
                                    String phoneNumber,
                                    short serialNumber,
                                    Body body,
                                    byte checkCode) {
        super(id, bodyProperties, phoneNumber, serialNumber, body, checkCode);
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
