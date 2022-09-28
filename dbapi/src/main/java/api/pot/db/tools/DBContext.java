package api.pot.db.tools;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.pot.db.xdb.XDB;

public class DBContext  extends ContextWrapper {
    private String DB_DIR;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDBdir() {
        return DB_DIR==null?getDatabaseDir().getAbsolutePath():DB_DIR;
    }

    public void setDBdir(String DB_DIR) {
        this.DB_DIR = DB_DIR;
    }

    public DBContext(Context context) {
        super(context);
        DB_DIR = null;
    }

    public DBContext(Context context, String dir) {
        super(context);
        DB_DIR = dir;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public File getDatabaseDir() {
        if(DB_DIR!=null) return new File(DB_DIR);
        return new File(getDataDir(), "databases");
    }

    @Override
    public File getDatabasePath(String name) {
        if(DB_DIR==null) return super.getDatabasePath(name);

        String dbfile = DB_DIR + File.separator + name;
        if (!dbfile.endsWith(".db"))
        {
            dbfile += ".db" ;
        }

        File result = new File(dbfile);

        if (!result.getParentFile().exists())
        {
            result.getParentFile().mkdirs();
        }

        return result;
    }

    @Override
    public String[] databaseList() {
        if(DB_DIR==null) return super.databaseList();

        int i=0;
        File file = new File(DB_DIR);
        if(!file.exists()) return new String[]{};

        String[] result = new String[file.listFiles().length];
        for(File db : file.listFiles()){
            result[i] = db.getName();
            i++;
        }

        return result;
    }

    public List<XDB> getDatabases() {
        XDB db;
        List<XDB> dbs = new ArrayList<>();
        String[] names = databaseList();
        for(String name : names){
            if(name.endsWith(".db")) {
                db = XDB.connect(this, name);
                dbs.add(db);
            }
        }
        return dbs;
    }

    /* this version is called for android devices >= api-11. thank to @damccull for fixing this. */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(name,mode, factory);
    }

    /* this version is called for android devices < api-11 */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }
}
