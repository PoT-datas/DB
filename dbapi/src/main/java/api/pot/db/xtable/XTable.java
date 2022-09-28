package api.pot.db.xtable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.pot.db.queries.FX;
import api.pot.db.queries.Function;
import api.pot.db.xdb.XDB;
import api.pot.db.xitem.XItem;
import api.pot.db.xtable.Field;

public class XTable {
    protected String tName;
    protected List<Field> tFields = new ArrayList<>();
    protected XDB tDB;

    public XTable(@NonNull String tName) {
        this.tName = tName;
    }

    public XTable(@NonNull String tName, @NonNull XDB tDB) {
        this.tName = tName;
        tDB.setTable(this);
    }

    public String getName() {
        return tName;
    }

    public void setName(String tName) {
        if(this.tName.equals(tName) || tDB==null) return;
        try {
            tDB.getDb().execSQL("ALTER TABLE " + this.tName + " REtName TO " + tName);
            this.tName = tName;
        }catch (Exception e){}
    }

    public List<Field> getFields() {
        return tFields;
    }

    public Field getField(int i) {
        if(i>tFields.size()) return null;
        return tFields.get(i);
    }

    public XTable withField(@NonNull Field field) {
        tFields.add(field);
        return this;
    }

    public String getCreationScript(){
        StringBuilder script = new StringBuilder();
        script.append("CREATE TABLE ");
        script.append(tName);
        script.append(" (");
        for(Field field : tFields)
            script.append(field.toScript()+", ");
        if(tFields.size()!=0) script.replace(script.length()-2, script.length(), "");
        for(Field field : tFields){
            String fScript = field.foreign();
            script.append(fScript.length()!=0?", "+fScript:"");
        }
        script.append(");");
        return script.toString();
    }

    public String getDestructionScript(){
        String script = "DROP TABLE IF EXISTS " + tName + ";";
        return script;
    }

    public void setDB(XDB tDB) {
        this.tDB = tDB;
        //
        if(tFields.size()==0) schema();
    }

    public void schema() {
        if(tDB==null) return;
        String column_tName, column_type;
        String Query = "PRAGMA table_info("+tName+")";
        Cursor my_cursor  = tDB.getDb().rawQuery(Query, null);
        for (my_cursor.moveToFirst();!my_cursor.isAfterLast();my_cursor.moveToNext()){
            column_tName = my_cursor.getString(my_cursor.getColumnIndex("tName"));
            column_type = my_cursor.getString(my_cursor.getColumnIndex("type"));
            tFields.add(new Field(column_tName, FType.getType(column_type)));
        }
    }

    @Override
    public String toString() {
        return getCreationScript();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof XTable){
            XTable table = (XTable) obj;
            return getName().equals(table.getName());
        }
        return super.equals(obj);
    }

    /*public void update(int idNbr, Integer id, XItem item) {
        FX.update(item)
                .from(this)
                .where(FX.equal(idNbr, id+""))
                .build(tDB);
    }*/
}
