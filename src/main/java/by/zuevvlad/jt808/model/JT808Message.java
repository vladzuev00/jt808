package by.zuevvlad.jt808.model;

import lombok.*;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public abstract class JT808Message<B> {
    private final short id;
    private final BodyProperties bodyProperties;
    private final String phoneNumber;
    private final short serialNumber;
    private final B body;
    private final byte checkCode;

    @Value
    public static class BodyProperties {
        boolean reserved;
        boolean containSubpackage;
        int encryptionType;
        int length;
    }
}
