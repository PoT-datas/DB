package api.pot.db.queries;

import java.util.ArrayList;
import java.util.List;

import api.pot.db.xitem.Value;
import api.pot.db.xitem.XItem;
import api.pot.db.xtable.Field;
import api.pot.db.xtable.XTable;

public class Function {
    public String name;
    public XTable table;
    public List<String> values = new ArrayList<>();
    public List<XItem> items = new ArrayList<>();
    public List<Integer> fields = new ArrayList<>();
    public List<XTable> tables = new ArrayList<>();
    public List<Function> functions = new ArrayList<>();

    public Function(String name) {
        this.name = name;
    }

    public Function(String name, XItem... items) {
        this.name = name;
        if(items!=null){
            for(XItem i : items)
                this.items.add(i);
        }
    }

    public Function(String name, int... fields) {
        this.name = name;
        if(fields!=null){
            for(int i : fields)
                this.fields.add(i);
        }
    }

    public Function(String name, String... values) {
        this.name = name;
        if(values!=null){
            for(String i : values)
                this.values.add(i);
        }
    }

    public Function(String name, int field, String value) {
        this.name = name;
        this.fields.add(field);
        this.values.add(value);
    }

    public Function(String name, XTable... tables) {
        this.name = name;
        if(tables!=null){
            for(XTable i : tables)
                this.tables.add(i);
        }
    }

    public Function(String name, Function... functions) {
        this.name = name;
        if(functions!=null){
            for(Function i : functions)
                this.functions.add(i);
        }
    }

    public void into(XTable table) {
        this.table = table;
        for(Function function : functions)
            function.into(this.table);
    }

    public String toScript() {
        StringBuilder script = new StringBuilder();
        switch (name){
            case FX.ELEMENT : {
                if(fields.size()!=0){
                    if(fields.get(0)==FX.ALL){
                        for(Field field : table.getFields())
                            script.append(field.name+", ");
                        script.replace(script.length()-2, script.length(), " ");
                        return script.toString();
                    }else {
                        for(int i : fields)
                            script.append(table.getField(i).name+", ");
                        script.replace(script.length()-2, script.length(), " ");
                        return script.toString();
                    }
                }else if(values.size()!=0){
                    for(String value : values){
                        try {
                            script.append(Integer.valueOf(value)+", ");
                        }catch (Exception e){
                            try {
                                script.append(Float.valueOf(value)+", ");
                            }catch (Exception e2){
                                script.append( (value.startsWith("'")?"":"'") + value + (value.endsWith("'")?"":"'") + ", ");
                            }
                        }
                    }
                    script.replace(script.length()-2, script.length(), " ");
                    return script.toString();
                }
            }

            case FX.SELECT : {
                if(functions.size()==0) return null;
                script.append(FX.SELECT + " ");
                for(Function function : functions){
                    script.append(function.toScript()+", ");
                }
                script.replace(script.length()-2, script.length(), " ");
                return script.toString();
            }
            case FX.FROM : {
                if(tables.size()==0) return null;
                script.append(FX.FROM + " ");
                for(XTable table : tables)
                    script.append(table.getName()+", ");
                script.replace(script.length()-2, script.length(), " ");
                return script.toString();
            }
            case FX.WHERE : {
                if(functions.size()==0) return null;
                return FX.WHERE + " " +functions.get(0).toScript()+ " ";
            }

            case FX.GROUP_BY : {
                if(functions.size()==0) return null;
                script.append(FX.GROUP_BY + " ");
                for(Function function : functions){
                    script.append(function.toScript()+", ");
                }
                script.replace(script.length()-2, script.length(), " ");
                return script.toString();
            }
            case FX.HAVING : {
                if(functions.size()==0) return null;
                script.append(FX.HAVING + " ");
                for(Function function : functions){
                    script.append(function.toScript()+", ");
                }
                script.replace(script.length()-2, script.length(), " ");
                return script.toString();
            }
            case FX.ORDER_BY : {
                if(functions.size()==0) return null;
                script.append(FX.ORDER_BY + " ");
                for(Function function : functions){
                    script.append(function.toScript()+", ");
                }
                script.replace(script.length()-2, script.length(), " ");
                return script.toString();
            }
            case FX.LIMIT : {
                if(functions.size()==0) return null;
                script.append(FX.LIMIT+" ");
                script.append(functions.get(0).toScript()+" ");
                return script.toString();
            }

            case FX.INSERT : {
                break;
            }
            case FX.UPDATE : {
                break;
            }
            case FX.DELETE : {
                break;
            }

            case FX.COUNT : {
                if(functions.size()==0) return null;
                script.append(FX.COUNT+"(");
                script.append(functions.get(0).toScript()+"");
                script.append(") ");
                return script.toString();
            }
            case FX.DISTINCT : {
                if(functions.size()==0) return null;
                script.append(FX.DISTINCT+"(");
                script.append(functions.get(0).toScript()+"");
                script.append(") ");
                return script.toString();
            }
            case FX.NOT : {
                if(functions.size()==0) return null;
                script.append(FX.NOT+" ");
                script.append(functions.get(0).toScript()+" ");
                return script.toString();
            }
            case FX.ASC : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.append(FX.ASC+" ");
                return script.toString();
            }
            case FX.IS_NULL : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.append(FX.IS_NULL+" ");
                return script.toString();
            }
            case FX.IS_NOT_NULL : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.append(FX.IS_NOT_NULL+" ");
                return script.toString();
            }
            case FX.DESC : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.append(FX.DESC+" ");
                return script.toString();
            }
            case FX.MIN : {
                if(functions.size()==0) return null;
                script.append(FX.MIN+"(");
                script.append(functions.get(0).toScript()+"");
                script.append(") ");
                return script.toString();
            }
            case FX.MAX : {
                if(functions.size()==0) return null;
                script.append(FX.MAX+"(");
                script.append(functions.get(0).toScript()+"");
                script.append(") ");
                return script.toString();
            }
            case FX.AVG : {
                if(functions.size()==0) return null;
                script.append(FX.AVG+"(");
                script.append(functions.get(0).toScript()+"");
                script.append(") ");
                return script.toString();
            }
            case FX.SUM : {
                if(functions.size()==0) return null;
                script.append(FX.SUM+"(");
                script.append(functions.get(0).toScript()+"");
                script.append(") ");
                return script.toString();
            }

            case FX.EXISTS : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.EXISTS+" (");
                script.append(functions.get(1).toScript()+")");
                return script.toString();
            }
            case FX.NOT_EXISTS : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.NOT_EXISTS+" (");
                script.append(functions.get(1).toScript()+")");
                return script.toString();
            }
            case FX.IN : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.IN+" (");
                script.append(functions.get(1).toScript()+")");
                return script.toString();
            }
            case FX.NOT_IN : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.NOT_IN+" (");
                script.append(functions.get(1).toScript()+")");
                return script.toString();
            }
            case FX.AS : {
                if(functions.size()==0) return null;
                script.append(functions.get(1).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.AS+" ");
                script.append(functions.get(0).toScript()+"");
                return script.toString();
            }
            case FX.BETWEEN : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.BETWEEN+" ");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.LIKE : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.LIKE+" ");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.AND : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.AND+" ");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.OR : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(" "+FX.OR+" ");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.EQUAL : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append("=");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.DIFF : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append("!=");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.XSUP : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(">");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.XINF : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append("<");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.SUP : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append(">=");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
            case FX.INF : {
                if(functions.size()==0) return null;
                script.append(functions.get(0).toScript()+"");
                script.replace(script.length()-1, script.length(), "");
                script.append("<=");
                script.append(functions.get(1).toScript()+"");
                return script.toString();
            }
        }
        return script.toString();
    }

    /*
    public FX where(Function... functions){
        this.functions.add(new Function(FX.WHERE, functions));
        return this;
    }

    public FX groupBy(Function... functions){
        this.functions.add(new Function(FX.GROUP_BY, functions));
        return this;
    }

    public FX having(Function... functions){
        this.functions.add(new Function(FX.HAVING, functions));
        return this;
    }

    public FX orderBy(Function... functions){
        this.functions.add(new Function(FX.ORDER_BY, functions));
        return this;
    }

    public FX limit(Function... functions){
        this.functions.add(new Function(FX.LIMIT, functions));
        return this;
    }*/
}
