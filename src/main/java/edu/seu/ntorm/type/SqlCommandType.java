package edu.seu.ntorm.type;

public enum SqlCommandType {

    UNKNOWN("UNKNOWN"),

    INSERT("INSERT"),

    UPDATE("UPDATE"),

    DELETE("DELETE"),

    SELECT("SELECT");

    SqlCommandType(String typeName) {
        this.typeName = typeName;
    }

    private final String typeName;

    public String getTypeName() {
        return typeName;
    }

    public static SqlCommandType typeOf(String sqlType) {
        sqlType = sqlType.toUpperCase();
        switch (sqlType) {
            case "INSERT":
            {
                return INSERT;
            }
            case "UPDATE":
            {
                return UPDATE;
            }
            case "DELETE":
            {
                return DELETE;
            }
            case "SELECT":
            {
                return SELECT;
            }
            default:
            {
                return UNKNOWN;
            }
        }
    }
}
