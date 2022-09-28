package api.pot.db.xtable;

public enum FType {
    INTEGER, FLOAT, TEXT, VARCHAR, DATE, TIME, DATETIME, NONE;

    public static FType getType(String type) {
        return type.contains("INTEGER")?INTEGER:type.contains("FLOAT")?FLOAT:type.contains("TEXT")?TEXT:
        type.contains("VARCHAR")?VARCHAR:type.contains("DATE")?DATE:type.contains("TIME")?TIME:type.contains("DATETIME")?DATETIME:NONE;
    }
}
