package akrger.grocerylist;


import java.util.HashMap;
import java.util.Map;

class GroceryListEntry {
    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    Category get_category() {
        return _category;
    }

    public void set_category(Category _category) {
        this._category = _category;
    }

    int get_quantity() {
        return _quantity;
    }

    void set_quantity(int _quantity) {
        this._quantity = _quantity;
    }

    long get_fk() {
        return _fk;
    }

    public void set_fk(long _fk) {
        this._fk = _fk;
    }

    private long _id;
    private String _description;
    private Category _category;
    private int _quantity;
    private long _fk;
    // serialize this
    private boolean _check;

    GroceryListEntry(long id, String description, Category category, int quantity, long fk) {
        _id = id;
        _description = description;
        _category = category;
        _quantity = quantity;
        _fk = fk;
        _check = false;
    }

    @Override
    public boolean equals(Object o) {
        return this._description == ((GroceryListEntry) o)._description
                && this._category == ((GroceryListEntry) o)._category;
    }

    enum Category {
        DRINKS(1),;

        private int _value;

        private static final Map<Integer, Category> map;

        static {
            map = new HashMap<>();
            for (Category c : Category.values()) {
                map.put(c._value, c);
            }
        }

        public int value() {
            return _value;
        }

        Category(int value) {
            _value = value;
        }

        public static Category findByKey(int i) {
            return map.get(i);
        }
    }

    @Override
    public int hashCode() {
        return _quantity;
    }

    @Override
    public String toString() {
        return _description;
    }
}
