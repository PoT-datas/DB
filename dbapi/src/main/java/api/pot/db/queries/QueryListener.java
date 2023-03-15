package api.pot.db.queries;

import android.database.Cursor;

import java.util.List;

import api.pot.db.xitem.XItem;

public interface QueryListener {

    void onQueryStart();
    void onQueryError();
    void onQueryEnd();
    void onQueryStart(boolean started, String msg);
    void onQueryProgress(float evolution);
    void onQueryEnd(Cursor cursor, String msg);
    void onQueryEnd(String msg);
    void onQueryError(String msg);
    void onResultParsed(XItem item, String msg);
    void onParsingsEnd(List<XItem> items, String msg);
}
