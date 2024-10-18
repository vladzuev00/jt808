package by.zuevvlad.jt808.model;

import lombok.Value;

public final class JT808MessageAuthenticationMessage extends JT808Message<JT808MessageAuthenticationMessage.Body> {

    public JT808MessageAuthenticationMessage(short id,
                                             BodyProperties bodyProperties,
                                             String phoneNumber,
                                             short serialNumber,
                                             Body body,
                                             byte checkCode) {
        super(id, bodyProperties, phoneNumber, serialNumber, body, checkCode);
    }

    @Value
    public static class Body {
        String authenticationCode;
    }
}
