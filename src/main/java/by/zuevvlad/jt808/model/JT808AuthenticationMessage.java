package by.zuevvlad.jt808.model;

import lombok.Value;

public final class JT808AuthenticationMessage extends JT808Message<JT808AuthenticationMessage.Body> {
    public static final short MESSAGE_ID = 258;

    public JT808AuthenticationMessage(BodyProperties bodyProperties,
                                      String phoneNumber,
                                      short serialNumber,
                                      Body body,
                                      byte checkCode) {
        super(MESSAGE_ID, bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }

    @Value
    public static class Body {
        String authenticationCode;
    }
}
