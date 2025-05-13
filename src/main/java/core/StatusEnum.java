package core;

public enum StatusEnum {
    TODO("todo"),
    PROGRESS("in-progress"),
    DONE("done");

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StatusEnum fromValue(String value) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
