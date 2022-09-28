package api.pot.db.xtable;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Field {
    public String name;
    public FType type;
    public Integer length;
    public String value;//to set default value of integer field
    public ForeignKey foreignKey;
    public String check;
    public List<FPriority> priorities = new ArrayList<>();

    public String toScript() {
        StringBuilder script = new StringBuilder();
        script.append(" "+name!=null ? name+" " : "");
        script.append(type!=null ? type+" " : "");
        script.append(length!=null ? "("+length+") " : "");
        script.append(value!=null ? "DEFAULT "+value+" " : "");
        script.append(check!=null ? "CHECK ("+check+") " : "");
        if(priorities!=null && priorities.size()!=0){
            script.append(priorities.get(0)!=null ? priorities.get(0).toString().replaceAll("_", " ")+" " : "");
            script.append(priorities.get(1)!=null ? priorities.get(1).toString().replaceAll("_", " ")+" " : "");
            script.append(priorities.get(2)!=null ? priorities.get(2).toString().replaceAll("_", " ")+" " : "");
            script.append(priorities.get(3)!=null ? priorities.get(3).toString().replaceAll("_", " ")+" " : "");
        }
        return script.toString();
    }

    public String foreign() {
        StringBuilder script = new StringBuilder();
        script.append(foreignKey!=null ? "FOREIGN KEY("+name+") REFERENCES "+
                foreignKey.table.getName()+"("+foreignKey.table.getField(foreignKey.field).name+")" : "");
        return script.toString();
    }

    public Field(String name, FType type) {
        this(name, type, null, null, null, null, null);
    }

    public Field(String name, FType type, FPriority... priorities) {
        this(name, type, null, null, null, null, priorities);
    }

    public Field(String name, FType type, Integer length, FPriority... priorities) {
        this(name, type, length, null, null, null, priorities);
    }

    public Field(String name, FType type, Integer length, String value, FPriority... priorities) {
        this(name, type, length, value, null, null, priorities);
    }

    public Field(String name, FType type, ForeignKey foreignKey, FPriority... priorities) {
        this(name, type, null, null, foreignKey, null, priorities);
    }

    public Field(String name, FType type, String check, FPriority... priorities) {
        this(name, type, null, null, null, check, priorities);
    }

    public Field(@NonNull String name, @NonNull FType type, Integer length, String value, ForeignKey foreignKey, String check, FPriority... priorities) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.value = value;
        this.foreignKey = foreignKey;
        this.check = check;
        if(priorities!=null) {
            this.priorities.add(priorities.length > 0 ? priorities[0] : null);
            this.priorities.add(priorities.length > 1 ? priorities[1] : null);
            this.priorities.add(priorities.length > 2 ? priorities[2] : null);
            this.priorities.add(priorities.length > 3 ? priorities[3] : null);
        }
    }
}
