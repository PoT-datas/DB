package api.pot.db.xtable;

public enum FType {
    INTEGER, LONG, FLOAT, DOUBLE, TEXT, VARCHAR, DATE, TIME, DATETIME, NONE;

    public static FType fromString(String type) {
        return type.contains(INTEGER.toString())?INTEGER:
                type.contains(LONG.toString())?LONG:
                        type.contains(FLOAT.toString())?FLOAT:
                                type.contains(DOUBLE.toString())?DOUBLE:
                                        type.contains(TEXT.toString())?TEXT:
                                                type.contains(VARCHAR.toString())?VARCHAR:
                                                        type.contains(DATE.toString())?DATE:
                                                                type.contains(TIME.toString())?TIME:
                                                                        type.contains(DATETIME.toString())?DATETIME:NONE;
    }
}
