package com.pot.db;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import api.pot.db.queries.FX;
import api.pot.db.queries.QueryCallback;
import api.pot.db.tools.DBContext;
import api.pot.db.xdb.XDB;
import api.pot.db.xitem.XItem;
import api.pot.db.xtable.FPriority;
import api.pot.db.xtable.FType;
import api.pot.db.xtable.Field;
import api.pot.db.xtable.ForeignKey;
import api.pot.db.xtable.XTable;

import static api.pot.db.xitem.XItem.NO_VALUE;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Context context;
    String DB_DIR;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        File sdcard = Environment.getExternalStorageDirectory();
        DB_DIR = sdcard.getAbsolutePath() + File.separator+ "databases";

        textView = (TextView) findViewById(R.id.textView);



        DBContext dbContext = new DBContext(this, DB_DIR);//DB_DIR

        List<XDB> dbs = dbContext.getDatabases();
        textView.setText("Les bases de "+dbContext.getDBdir()+" :\n");
        for(XDB db : dbs){
            textView.setText(textView.getText()+db.getDatabaseName()+":::"+db.getVersion()+"\n");
            db.close();
        }



        final XDB xdb = XDB.connect(dbContext, "mydb");

        XTable table = new XTable("users");
        table.withField(new Field("id", FType.INTEGER, FPriority.PRIMARY_KEY, FPriority.AUTOINCREMENT, FPriority.NOT_NULL))
                .withField(new Field("nom", FType.VARCHAR, 25, FPriority.NOT_NULL))
                .withField(new Field("telephone", FType.INTEGER, FPriority.NOT_NULL, FPriority.UNIQUE));
        xdb.setTable(table);

        XTable tab = new XTable("properties");
        tab.withField(new Field("id", FType.INTEGER, FPriority.PRIMARY_KEY, FPriority.AUTOINCREMENT, FPriority.NOT_NULL, FPriority.UNIQUE))
                .withField(new Field("stock", FType.INTEGER, null, "0"))
                .withField(new Field("owner", FType.INTEGER, new ForeignKey(table, 0), FPriority.NOT_NULL))
                .withField(new Field("value", FType.FLOAT, "value>=100", FPriority.NOT_NULL))
                .withField(new Field("status", FType.TEXT, "status IN ('Location', 'Propriétère')", FPriority.NOT_NULL));
        xdb.setTable(tab);

        textView.setText(textView.getText()+"\n\n"+xdb);


        XTable users = new XTable("users", xdb);
        XTable t2 = new XTable("properties", xdb);
        XTable t3 = new XTable("properties");
        XTable t4 = new XTable("messages");


        textView.setText(textView.getText()+"\n\n"+users);
        textView.setText(textView.getText()+"\n\n"+t2);

        textView.setText(textView.getText()+"\n\n"+xdb.isTableExists(t3));
        textView.setText(textView.getText()+"\n\n"+xdb.isTableExists(t4));

        XItem item = new XItem(users, NO_VALUE, "Olivier Tambo", 653379379);
        XItem item2 = new XItem(users, NO_VALUE, "Olivier Fotsing Tambo", 53379379);
        XItem item3 = new XItem(users, NO_VALUE, "Olivier Fotsing", 79379);
        XItem item4 = new XItem(users, NO_VALUE, "Olivier F Tambo", 379379);
        XItem item5 = new XItem(users, NO_VALUE, "O Fotsing Tambo", NO_VALUE);

        textView.setText(textView.getText()+"\n\n"+"insert::: \n"+""

                /*FX.insert(item)
                        .from(users)
                        .build(xdb)+""+
                FX.insert(item2)
                        .from(users)
                        .build(xdb)+""+
                FX.insert(item3)
                        .from(users)
                        .build(xdb)+""+
                FX.insert(item4)
                        .from(users)
                        .build(xdb)+""+
                FX.insert(item5)
                        .from(users)
                        .build(xdb)*/

                );

        textView.setText(textView.getText()+"\n\n"+"update::: \n"+""

                /*FX.update(item5)
                        .from(users)
                        .where(FX.equal(0, "5"))
                        .build(xdb)*/

        );

        textView.setText(textView.getText()+"\n\n"+"delete::: \n"+""

                /*FX.delete()
                        .from(users)
                        .where(FX.equal(0, "3"))
                        .build(xdb)*/

                );

        textView.setText(textView.getText()+"\n\n"+"select::: \n"+""

                        /*FX.select(FX.as("nbr_user", FX.count(0), FX.distinct(1)), FX.element(2))
                                .from(users)
                                .where(FX.or(
                                        FX.and(FX.diff(FX.element(0), FX.element("-1")), FX.sup(FX.element(2), FX.element("0"))),
                                        FX.between(FX.element(0), FX.and(FX.element("3"), FX.element("5")))))
                                .groupBy(FX.element(2))
                                .having(FX.xsup(FX.count(0), FX.element("0")))
                                .orderBy(FX.desc(FX.element(1)))
                                .limit(FX.element("10"))
                                .build(xdb)*/

                        /*FX.select(FX.count(FX.element(0)), FX.element(1), FX.sum(FX.element(2)))
                                .from(users)
                                .where(FX.or(
                                        FX.and(FX.diff(FX.element(0), FX.element("-1")), FX.sup(FX.element(2), FX.element("0"))),
                                        FX.between(FX.element(0), FX.and(FX.element("3"), FX.element("5")))))
                                .build(xdb, null)*/

                );

        FX.select(FX.count(FX.element(0)), FX.element(1), FX.sum(FX.element(2)))
                .from(users)
                .where(FX.or(
                        FX.and(FX.diff(FX.element(0), FX.element("-1")), FX.sup(FX.element(2), FX.element("0"))),
                        FX.between(FX.element(0), FX.and(FX.element("3"), FX.element("5")))))
                .build(xdb, new QueryCallback(){
                    @Override
                    public void onQueryStart(boolean started, String msg) {
                        textView.setText(textView.getText()+"\n\n"+started+"/"+msg);
                    }

                    @Override
                    public void onQueryProgress(float evolution) {
                        textView.setText(textView.getText()+"\n"+evolution+"/");
                    }

                    @Override
                    public void onQueryEnd(Cursor cursor, String msg) {
                        textView.setText(textView.getText()+"\n\n"+cursor+"/"+msg);
                    }

                    @Override
                    public void onResultParsed(XItem item, String msg) {
                        textView.setText(textView.getText()+"\n\n"+item+"/"+msg);
                    }

                    @Override
                    public void onParsingsEnd(List<XItem> items, String msg) {
                        textView.setText(textView.getText()+"\n\n"+items+"/"+msg);
                        xdb.close();
                    }
                });
    }
}
