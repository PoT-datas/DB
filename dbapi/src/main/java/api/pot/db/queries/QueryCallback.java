package api.pot.db.queries;

import android.database.Cursor;

import java.util.List;

import api.pot.db.xitem.XItem;

public class QueryCallback implements QueryListener{
    @Override
    public void onQueryStart(boolean started, String msg) {

    }

    @Override
    public void onQueryProgress(float evolution) {

    }

    @Override
    public void onQueryEnd(Cursor cursor, String msg) {

    }

    @Override
    public void onResultParsed(XItem item, String msg) {

    }

    @Override
    public void onParsingsEnd(List<XItem> items, String msg) {

    }
}
