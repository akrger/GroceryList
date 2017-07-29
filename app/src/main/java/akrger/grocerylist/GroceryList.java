package akrger.grocerylist;

import java.io.Serializable;

class GroceryList implements Serializable {
    private long _id;

    long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    private String _title;

    GroceryList(long id, String title) {
        _id = id;
        _title = title;
    }

    @Override
    public String toString() {
        return _title;
    }
}
