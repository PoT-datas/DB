package api.pot.db.xitem;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.pot.db.xtable.Field;
import api.pot.db.xtable.XTable;

import static api.pot.db.xtable.FType.FLOAT;
import static api.pot.db.xtable.FType.INTEGER;

public class XItem {
    // Ex: Les listener doivent pouvoir fonctionner avec tout acces à un item. Instance different
    // Va falloir pousser la reflexion beaucoup plus loin que cet element devient vraiment utile
    private List<ItemListener> itemListeners = new ArrayList<>();

    public static Value NO_VALUE = new Value();

    protected XTable iTable;

    protected List<Field> tFields = new ArrayList<>();
    protected ContentValues iValues = new ContentValues();

    protected int iFieldId = 0;

    public int getiFieldId() {
        return iFieldId;
    }

    public void setiFieldId(int iFieldId) {
        this.iFieldId = iFieldId;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListeners.add(itemListener);
    }

    private void onItemChange(int field, Object value) {
        if(itemListeners==null) return;
        for(ItemListener itemListener : itemListeners)
            itemListener.onItemChange(field, value);
    }

    public XItem(XItem item) {
        setTable(item.getTable());
        setValues(item.getValues());
    }

    public XItem(XTable table, Object... values) {
        this.iTable = table;
        if(table!=null) this.tFields = table.getFields();
        if(values!=null){
            int i=0;
            for(Object value : values){
                if(value!=null){
                    if(!(value instanceof Value)) insert(i, value);
                    i++;
                }
            }
        }
    }

    public XItem(XTable table, Cursor cursor) {
        this.iTable = table;
        if(table!=null) this.tFields = table.getFields();
        int i=0;
        for (Field field : tFields){
            if(field.type==INTEGER) iValues.put(field.name, cursor.getInt(i));
            else if(field.type==FLOAT) iValues.put(field.name, cursor.getFloat(i));
            else iValues.put(field.name, cursor.getString(i));
            i++;
        }
    }

    private void insert(int field, Object value){
        if(value==null) return;
        if(value instanceof Integer) this.iValues.put(tFields.get(field).name, (Integer)value);
        else if(value instanceof Float) this.iValues.put(tFields.get(field).name, (Float) value);
        else this.iValues.put(tFields.get(field).name, value.toString());
    }

    public XTable getTable() {
        return iTable;
    }

    public void setTable(XTable table){
        this.iTable = table;
        this.tFields = table.getFields();
    }

    public ContentValues getValues() {
        return iValues;
    }

    public void setValues(ContentValues iValues) {
        this.iValues = iValues;
    }

    public Object getValue(int field) {
        Object val;
        try {
            val = iValues.get(tFields.get(field).name);
            return val!=null?val:new String();
        }catch (Exception e){}
        return new String();
    }

    public void setValue(int field, Object value) {
        insert(field, value);
        //iTable.update(0, (Integer) getValue(0), this);
        onItemChange(field, value);
    }

    public void update(int iField, Object data) {
        iTable.update(this, iField, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Object> value : iValues.valueSet()){
            sb.append(value.getKey()+":"+value.getValue()+"/");
        }
        return sb.toString();
    }
}
