package api.pot.db.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.pot.db.xdb.XDB;
import api.pot.db.xitem.XItem;
import api.pot.db.xtable.XTable;

public class FX {
    public static final int ALL = -1;

    public static final String AS = "AS";

    public static final String ELEMENT = "ELEMENT";

    public static final String SELECT = "SELECT";

    public static final String INSERT = "INSERT INTO";

    public static final String UPDATE = "UPDATE";

    public static final String DELETE = "DELETE";

    public static final String FROM = "FROM";

    public static final String DISTINCT = "DISTINCT";
    public static final String MIN = "MIN";
    public static final String MAX = "MAX";
    public static final String COUNT = "COUNT";
    public static final String AVG = "AVG";
    public static final String SUM = "SUM";

    public static final String WHERE = "WHERE";
    public static final String EQUAL = "EQUAL";
    public static final String DIFF = "DIFF";
    public static final String SUP = "SUP";
    public static final String INF = "INF";
    public static final String XSUP = "XSUP";
    public static final String XINF = "XINF";
    public static final String BETWEEN = "BETWEEN";
    public static final String LIKE = "LIKE";
    public static final String NOT_LIKE = "NOT LIKE";
    public static final String IN = "IN";
    public static final String NOT_IN = "NOT_IN";
    public static final String IS_NULL = "IS NULL";
    public static final String IS_NOT_NULL = "IS NOT NULL";
    public static final String EXISTS = "EXISTS";
    public static final String NOT_EXISTS = "NOT EXISTS";

    public static final String ORDER_BY = "ORDER BY";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String NOT = "NOT";

    public static final String LIMIT = "LIMIT";

    public static final String GROUP_BY = "GROUP BY";

    public static final String HAVING = "HAVING";


    private List<Function> functions = new ArrayList<>();
    private XTable table;

    public static FX delete(){
        FX fx = new FX();
        fx.functions.add(new Function(FX.DELETE));
        return fx;
    }

    public static FX update(XItem item){
        FX fx = new FX();
        fx.functions.add(new Function(FX.UPDATE, item));
        return fx;
    }

    public static FX insert(XItem item){
        FX fx = new FX();
        fx.functions.add(new Function(FX.INSERT, item));
        return fx;
    }

    public static FX select(Function... functions){
        FX fx = new FX();
        fx.functions.add(new Function(FX.SELECT, functions));
        return fx;
    }




    public FX from(XTable... tables){
        this.functions.add(new Function(FX.FROM, tables));
        return this;
    }

    public FX from(Function... functions){
        this.functions.add(new Function(FX.FROM, functions));
        return this;
    }

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
    }





    public static Function element(int... fields){
        return new Function(FX.ELEMENT, fields);
    }

    public static Function element(String... values){
        return new Function(FX.ELEMENT, values);
    }

    public static Function all(){
        return element(FX.ALL);
    }

    public static  Function as(String name, Function... functions){
        Function f[] = new Function[functions.length+1];
        f[0] = new Function(FX.ELEMENT, name);
        for(int i=1;i<f.length;i++)
            f[i] = functions[i-1];
        return as(f);
    }

    public static Function as(Function... functions){
        return new Function(FX.AS, functions);
    }

    public static  Function between(int field, Function... functions){
        Function f[] = new Function[functions.length+1];
        f[0] = new Function(FX.ELEMENT, field);
        for(int i=1;i<f.length;i++)
            f[i] = functions[i-1];
        return between(f);
    }

    public static Function between(Function... functions){
        return new Function(FX.BETWEEN, functions);
    }

    public static Function distinct(int... fields){
        return distinct(new Function(FX.ELEMENT, fields));
    }

    public static Function distinct(Function... functions){
        return new Function(FX.DISTINCT, functions);
    }

    public static Function min(int... fields){
        return min(new Function(FX.ELEMENT, fields));
    }

    public static Function min(Function... functions){
        return new Function(FX.MIN, functions);
    }

    public static Function max(int... fields){
        return max(new Function(FX.ELEMENT, fields));
    }

    public static Function max(Function... functions){
        return new Function(FX.MAX, functions);
    }

    public static Function count(int... fields){
        return count(new Function(FX.ELEMENT, fields));
    }

    public static Function count(Function... functions){
        return new Function(FX.COUNT, functions);
    }

    public static Function avg(int... fields){
        return avg(new Function(FX.ELEMENT, fields));
    }

    public static Function avg(Function... functions){
        return new Function(FX.AVG, functions);
    }

    public static Function sum(int... fields){
        return sum(new Function(FX.ELEMENT, fields));
    }

    public static Function sum(Function... functions){
        return new Function(FX.SUM, functions);
    }

    public static Function equal(int... fields){ return equal(new Function(FX.ELEMENT, fields)); }

    public static Function equal(int field, String value){ return equal(new Function(FX.ELEMENT, field), new Function(FX.ELEMENT, value)); }

    public static Function equal(Function... functions){ return new Function(FX.EQUAL, functions); }

    public static Function diff(int... fields){
        return diff(new Function(FX.ELEMENT, fields));
    }

    public static Function diff(int field, String value){ return diff(new Function(FX.ELEMENT, field), new Function(FX.ELEMENT, value)); }

    public static Function diff(Function... functions){
        return new Function(FX.DIFF, functions);
    }

    public static Function sup(int... fields){
        return sup(new Function(FX.ELEMENT, fields));
    }

    public static Function sup(Function... functions){
        return new Function(FX.SUP, functions);
    }

    public static Function inf(int... fields){
        return inf(new Function(FX.ELEMENT, fields));
    }

    public static Function inf(Function... functions){
        return new Function(FX.INF, functions);
    }

    public static Function xsup(int... fields){
        return xsup(new Function(FX.ELEMENT, fields));
    }

    public static Function xsup(Function... functions){
        return new Function(FX.XSUP, functions);
    }

    public static Function xinf(int... fields){
        return xinf(new Function(FX.ELEMENT, fields));
    }

    public static Function xinf(Function... functions){
        return new Function(FX.XINF, functions);
    }

    public static Function like(int... fields){
        return like(new Function(FX.ELEMENT, fields));
    }

    public static Function like(int field, String value){ return like(new Function(FX.ELEMENT, field), new Function(FX.ELEMENT, value)); }

    public static Function like(Function... functions){
        return new Function(FX.LIKE, functions);
    }

    public static Function notLike(int... fields){
        return notLike(new Function(FX.ELEMENT, fields));
    }

    public static Function notLike(int field, String value){
        return notLike(new Function(FX.ELEMENT, field), new Function(FX.ELEMENT, value));
    }

    public static Function notLike(Function... functions){
        return new Function(FX.NOT_LIKE, functions);
    }

    public static Function in(int... fields){
        return in(new Function(FX.ELEMENT, fields));
    }

    public static Function in(Function... functions){
        return new Function(FX.IN, functions);
    }

    public static Function notIn(int... fields){
        return in(new Function(FX.ELEMENT, fields));
    }

    public static Function notIn(Function... functions){
        return new Function(FX.NOT_IN, functions);
    }

    public static Function isNull(int... fields){
        return isNull(new Function(FX.ELEMENT, fields));
    }

    public static Function isNull(Function... functions){
        return new Function(FX.IS_NULL, functions);
    }

    public static Function isNotNull(int... fields){
        return isNotNull(new Function(FX.ELEMENT, fields));
    }

    public static Function isNotNull(Function... functions){
        return new Function(FX.IS_NOT_NULL, functions);
    }

    public static Function exists(int... fields){
        return exists(new Function(FX.ELEMENT, fields));
    }

    public static Function exists(Function... functions){
        return new Function(FX.EXISTS, functions);
    }

    public static Function notExists(int... fields){
        return notExists(new Function(FX.ELEMENT, fields));
    }

    public static Function notExists(Function... functions){
        return new Function(FX.NOT_EXISTS, functions);
    }

    public static Function asc(int... fields){
        return asc(new Function(FX.ELEMENT, fields));
    }

    public static Function asc(Function... functions){
        return new Function(FX.ASC, functions);
    }

    public static Function desc(int... fields){
        return desc(new Function(FX.ELEMENT, fields));
    }

    public static Function desc(Function... functions){
        return new Function(FX.DESC, functions);
    }

    public static Function and(int... fields){
        return and(new Function(FX.ELEMENT, fields));
    }

    public static Function and(Function... functions){
        return new Function(FX.AND, functions);
    }

    public static Function or(int... fields){
        return or(new Function(FX.ELEMENT, fields));
    }

    public static Function or(Function... functions){
        return new Function(FX.OR, functions);
    }

    public static Function not(int... fields){
        return not(new Function(FX.ELEMENT, fields));
    }

    public static Function not(Function... functions){
        return new Function(FX.NOT, functions);
    }


    public void build(XDB xdb){
        new QueryTask().execute(xdb);
    }

    public void build(XDB xdb, QueryCallback queryCallback){
        new QueryTask(queryCallback).execute(xdb);
    }

    public void build(XDB xdb, boolean parsing, QueryCallback queryCallback){
        new QueryTask(parsing, queryCallback).execute(xdb);
    }

    private StringBuilder script;
    /*public String building(XDB xdb){
        if(functions==null || functions.size()==0) return null;
        //
        for(Function function : functions)
            function.into(functions.get(1).tables.get(0));
        //
        script = new StringBuilder();
        //
        switch (functions.get(0).name){
            case FX.SELECT : {
                Cursor cursor = xdb.getDb().rawQuery(
                        getFunction(FX.SELECT).toScript() +"\n"+
                                getFunction(FX.FROM).toScript() +"\n"+
                                getFunction(FX.WHERE).toScript() +"\n"+
                                getFunction(FX.GROUP_BY).toScript() +"\n"+
                                getFunction(FX.HAVING).toScript() +"\n"+
                                getFunction(FX.ORDER_BY).toScript() +"\n"+
                                getFunction(FX.LIMIT).toScript(), null);
                script.append("done ::: \n"+
                        getFunction(FX.SELECT).toScript() +"\n"+
                        getFunction(FX.FROM).toScript() +"\n"+
                        getFunction(FX.WHERE).toScript() +"\n"+
                        getFunction(FX.GROUP_BY).toScript() +"\n"+
                        getFunction(FX.HAVING).toScript() +"\n"+
                        getFunction(FX.ORDER_BY).toScript() +"\n"+
                        getFunction(FX.LIMIT).toScript());
                //
                for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                    script.append(cursor.getInt(0)+"/"+
                    cursor.getString(1)+"/"+
                    cursor.getInt(2)+"\n");
                }
                break;
            }
            case FX.INSERT : {
                if(functions.size()<2 || functions.get(1).tables.size()==0 || functions.get(0).items.size()==0) return null;
                xdb.getDb().insert(functions.get(1).tables.get(0).getName(), null,
                        functions.get(0).items.get(0).getValues());
                script.append("done ::: ");
                break;
            }
            case FX.UPDATE : {
                if(functions.size()<3 || functions.get(2).functions.size()==0 ||
                        functions.get(1).tables.size()==0 || functions.get(0).items.size()==0) return null;
                //xdb.getDb().update(functions.get(1).tables.get(0).getName(), functions.get(0).items.get(0).getValues(), where, arg.args);
                xdb.getDb().execSQL("UPDATE "+functions.get(1).tables.get(0).getName() +" \n"+
                        "SET " + valuesToString(functions.get(0).items.get(0).getValues()) +" \n"+
                        functions.get(2).toScript() +" \n");
                script.append("done ::: "+

                                "UPDATE "+functions.get(1).tables.get(0).getName() +" \n"+
                                "SET " + valuesToString(functions.get(0).items.get(0).getValues()) +" \n"+
                                functions.get(2).toScript() +" \n"

                        );
                break;
            }
            case FX.DELETE : {
                if(functions.size()<3 || functions.get(2).functions.size()==0 ||
                        functions.get(1).tables.size()==0) return null;
                //xdb.getDb().update(functions.get(1).tables.get(0).getName(), functions.get(0).items.get(0).getValues(), where, arg.args);
                xdb.getDb().execSQL("DELETE FROM "+functions.get(1).tables.get(0).getName() +" \n"+
                        functions.get(2).toScript() +" \n");
                script.append("done ::: "+

                        "DELETE FROM "+functions.get(1).tables.get(0).getName() +" \n"+
                        functions.get(2).toScript() +" \n"

                );
                break;
            }
        }
        return script.toString();
    }*/

    private Function getFunction(String name) {
        for(Function function : functions)
            if(function.name.equals(name)) return function;
        return new Function("NO_FUNCTION");
    }

    private String valuesToString(ContentValues values) {
        StringBuilder str = new StringBuilder();
        for(Map.Entry<String, Object> elt : functions.get(0).items.get(0).getValues().valueSet()){
            if(elt.getValue() instanceof Integer || elt.getValue() instanceof Integer ||
                    elt.getValue() instanceof Float || elt.getValue() instanceof Double) str.append(elt.getKey()+"="+elt.getValue()+", ");
            else str.append(elt.getKey()+"='"+elt.getValue()+"', ");
        }
        str.replace(str.length()-2, str.length(), "");
        return str.toString();
    }


    private class QueryTask extends AsyncTask<XDB, Float, String> {
        private QueryListener queryCallback;
        private boolean parsing = true;
        private List<XItem> items = new ArrayList<>();

        public QueryTask() {}

        public QueryTask(QueryListener queryCallback) {
            this.queryCallback = queryCallback;
        }

        public QueryTask(boolean parsing, QueryListener queryCallback) {
            this.queryCallback = queryCallback;
            this.parsing = parsing;
        }

        // Always same signature
        @Override
        public void onPreExecute() {
            if(queryCallback!=null) queryCallback.onQueryStart(true, "Query Ready!!!");
        }

        @Override
        public String doInBackground(XDB... params) {
            if(functions==null || functions.size()==0) {
                if(queryCallback!=null) queryCallback.onQueryStart(false,
                        "can't any function error!!!");
                return null;
            }
            //
            if(params==null || params.length==0) {
                if(queryCallback!=null) queryCallback.onQueryStart(false,
                        "can't any db error!!!");
                return null;
            }
            XDB xdb = params[0];
            //
            /**for(Function function : functions)
                function.into(functions.get(1).tables.get(0));*/
            for(int k=0;k<functions.size();k++){
                functions.get(k).into(functions.get(1).tables.get(0));
            }
            //
            script = new StringBuilder();
            //
            if(queryCallback!=null) queryCallback.onQueryStart(true, "Query Running!!!");
            //
            switch (functions.get(0).name){
                case FX.SELECT : {
                    try{
                        Cursor cursor = xdb.getDb().rawQuery(
                                getFunction(FX.SELECT).toScript() +"\n"+
                                        getFunction(FX.FROM).toScript() +"\n"+
                                        getFunction(FX.WHERE).toScript() +"\n"+
                                        getFunction(FX.GROUP_BY).toScript() +"\n"+
                                        getFunction(FX.HAVING).toScript() +"\n"+
                                        getFunction(FX.ORDER_BY).toScript() +"\n"+
                                        getFunction(FX.LIMIT).toScript(), null);
                        //
                        if(queryCallback!=null) queryCallback.onQueryEnd(cursor, "SELECT sucess!!!");
                        //
                        script.append("done ::: \n"+
                                getFunction(FX.SELECT).toScript() +"\n"+
                                getFunction(FX.FROM).toScript() +"\n"+
                                getFunction(FX.WHERE).toScript() +"\n"+
                                getFunction(FX.GROUP_BY).toScript() +"\n"+
                                getFunction(FX.HAVING).toScript() +"\n"+
                                getFunction(FX.ORDER_BY).toScript() +"\n"+
                                getFunction(FX.LIMIT).toScript());
                        //
                        if(parsing){
                            for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                                try {
                                    items.add(new XItem(functions.get(1).tables.get(0), cursor));
                                    if(queryCallback!=null) queryCallback.onResultParsed(items.get(items.size()-1), "PARSING sucess!!!");
                                }catch (Exception e){
                                    if(queryCallback!=null)
                                        queryCallback.onResultParsed(null, "PARSING error!!!\n"+e.getMessage());
                                }
                            }
                        }
                    }catch (Exception e){
                        if(queryCallback!=null) queryCallback.onQueryError(e.getMessage());
                        if(queryCallback!=null) queryCallback.onQueryEnd(null, e.getMessage());
                        return null;
                    }
                    break;
                }
                case FX.INSERT : {
                    try {
                        if(functions.size()<2 || functions.get(1).tables.size()==0 || functions.get(0).items.size()==0) return null;
                        xdb.getDb().insert(functions.get(1).tables.get(0).getName(), null,
                                functions.get(0).items.get(0).getValues());
                        script.append("done ::: ");
                        if(queryCallback!=null) queryCallback.onQueryEnd(null, "INSERT sucess!!!");
                    }catch (Exception e){
                        if(queryCallback!=null) queryCallback.onQueryEnd(null, e.getMessage());
                        return null;
                    }
                    break;
                }
                case FX.UPDATE : {
                    try {
                        if(functions.size()<3 || functions.get(2).functions.size()==0 ||
                                functions.get(1).tables.size()==0 || functions.get(0).items.size()==0) return null;
                        xdb.getDb().execSQL("UPDATE "+functions.get(1).tables.get(0).getName() +" \n"+
                                "SET " + valuesToString(functions.get(0).items.get(0).getValues()) +" \n"+
                                functions.get(2).toScript() +" \n");
                        script.append("done ::: "+
                                "UPDATE "+functions.get(1).tables.get(0).getName() +" \n"+
                                "SET " + valuesToString(functions.get(0).items.get(0).getValues()) +" \n"+
                                functions.get(2).toScript() +" \n"
                        );
                        if(queryCallback!=null) queryCallback.onQueryEnd(null, null/*"UPDATE sucess!!!"*/);
                    }catch (Exception e){
                        if(queryCallback!=null) queryCallback.onQueryEnd(null, e.getMessage());
                        return null;
                    }
                    break;
                }
                case FX.DELETE : {
                    try {
                        if(functions.size()<3 || functions.get(2).functions.size()==0 ||
                                functions.get(1).tables.size()==0) return null;
                        xdb.getDb().execSQL("DELETE FROM "+functions.get(1).tables.get(0).getName() +" \n"+
                                functions.get(2).toScript() +" \n");
                        script.append("done ::: ");
                        if(queryCallback!=null) queryCallback.onQueryEnd(null, "DELETE sucess!!!");
                    }catch (Exception e){
                        if(queryCallback!=null) queryCallback.onQueryEnd(null, e.getMessage());
                        return null;
                    }
                    break;
                }
            }
            return script.toString();
        }

        @Override
        public void onProgressUpdate(Float... params) {
            if(queryCallback!=null && params!=null && params.length!=0) queryCallback.onQueryProgress(params[0]);
        }

        @Override
        public void onPostExecute(String result) {
            if(parsing && queryCallback!=null) queryCallback.onParsingsEnd(items, result);
            super.onPostExecute(result);
        }

    }


}
