package api.pot.db.xtable;

public class ForeignKey {
    public XTable table;
    public int field;

    public ForeignKey(XTable table, int field) {
        this.table = table;
        this.field = field;
    }
}
