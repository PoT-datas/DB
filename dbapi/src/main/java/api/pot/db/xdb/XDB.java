package api.pot.db.xdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.pot.db.tools.DBContext;
import api.pot.db.xtable.XTable;

public class XDB  extends SQLiteOpenHelper {

    protected DBContext context;
    protected String NAME;
    protected String DIR;
    protected int VERSION;

    protected SQLiteDatabase mDB;
    protected boolean onReadOnly = false;

    //se connecter à la plus recente version d'une db
    public static XDB connect(@Nullable Context context, String dir, @Nullable String name){
        return connect(new DBContext(context, dir), name);
    }

    //se connecter à la plus recente version d'une db
    public static XDB connect(@Nullable Context context, @Nullable String name){
        return connect(new DBContext(context), name);
    }

    //se connecter à la plus recente version d'une db
    public static XDB connect(@Nullable DBContext dbContext, @Nullable String name){
        SQLiteDatabase db = dbContext.openOrCreateDatabase(name, dbContext.MODE_PRIVATE, null);
        return new XDB(dbContext, name, null, db.getVersion()!=0?db.getVersion():1);
    }

    //se connecter à la plus recente version d'une db
    public static XDB connect(@Nullable Context context, @Nullable String name, int version){
        return connect(new DBContext(context), name, version);
    }

    //se connecter à la plus recente version d'une db
    public static XDB connect(@Nullable DBContext dbContext, @Nullable String name, int version){
        return new XDB(dbContext, name, null, version);
    }

    //se connecter à une db précise
    public XDB(@Nullable Context context, @Nullable String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(new DBContext(context), name, factory, version);
    }

    public XDB(@Nullable DBContext context, @Nullable String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name.endsWith(".db")?name:name+".db", factory, version);
        this.context = context;
        this.NAME = name;
        this.VERSION = version;
        //
        open();
    }

    public DBContext getContext() {
        return context;
    }

    public boolean isOnReadOnly() {
        return onReadOnly;
    }

    public void setOnReadOnly(boolean onReadOnly) {
        if(this.onReadOnly == onReadOnly) return;
        this.onReadOnly = onReadOnly;
        close();
        open();
    }

    public SQLiteDatabase getDb() {
        return mDB;
    }

    public void open() {
        if(onReadOnly) mDB = getReadableDatabase();
        else mDB = getWritableDatabase();
    }

    @Override
    public synchronized void close() {
        super.close();
        try {
            mDB.close();
        }catch (Exception e){}
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        //db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {}

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            ///close();
            ///---context.deleteDatabase(getDatabaseName());
            // Simplest implementation is to drop all old tables and recreate them
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public String toString() {
        String s = getPath() + "\nVersion: " + getVersion() + "\nNbr Table: " + getTables().size()+"\nTables: ";
        for(XTable tb : getTables())
            s+=tb.getName()+"/";
        return s;
    }

    public void setName(String name) {
        if(name==null || this.getDatabaseName().equals(name) || true) return;

        name = name.endsWith(".db")?name:name+".db";

        File dbFile = new File(getPath());
        File newDbFile = new File(dbFile.getParentFile(), name);

        dbFile.renameTo(newDbFile);

        this.NAME = name;
    }

    public String getName() {
        return getDatabaseName();
    }

    public String getPath() {
        return mDB.getPath();
    }

    public List<XTable> getTables(){
        List<XTable> tables = new ArrayList<>();
        Cursor c = mDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                tables.add(new XTable(c.getString(0)));
                c.moveToNext();
            }
        }
        return tables;
    }

    public boolean isTableExists(XTable table){
        List<XTable> tables = getTables();
        for(XTable t : tables){
            if(t.equals(table)) return true;
        }
        return false;
    }

    public int getVersion(){
        return mDB.getVersion();
    }

    public void setTable(@Nullable XTable table) {
        try {
            mDB.execSQL(table.getCreationScript());
        }catch (Exception e){
        }finally {
            table.setDB(this);
        }
    }

    public void dropTable(@Nullable XTable table) {
        try {
            mDB.execSQL(table.getDestructionScript());
            table.setDB(null);
        }catch (Exception e){}
    }

}
