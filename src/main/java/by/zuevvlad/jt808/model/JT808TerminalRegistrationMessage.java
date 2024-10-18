package by.zuevvlad.jt808.model;

import lombok.Value;

public final class JT808TerminalRegistrationMessage extends JT808Message<JT808TerminalRegistrationMessage.Body> {
    private static final short ID = 256;

    public JT808TerminalRegistrationMessage(BodyProperties bodyProperties,
                                            String phoneNumber,
                                            short serialNumber,
                                            Body body,
                                            byte checkCode) {
        super(ID, bodyProperties, phoneNumber, serialNumber, body, checkCode);
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
