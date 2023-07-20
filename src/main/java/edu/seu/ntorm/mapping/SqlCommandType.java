package edu.seu.ntorm.mapping;

import javax.swing.text.StyledEditorKit;

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
}
