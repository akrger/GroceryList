package akrger.grocerylist;

import java.util.ArrayList;

class GroceryArrayList<E> extends ArrayList<E> {

    @Override
    public boolean add(E e) {
        if (super.contains(e)) {
            int index = super.indexOf(e);
            GroceryListEntry entry = ((GroceryListEntry) super.get(index));
            GroceryListEntry oldEntry = ((GroceryListEntry) e);
            int newQuantity = entry.get_quantity() + oldEntry.get_quantity();
            entry.set_quantity(newQuantity);
            return false;
        }
        return super.add(e);
    }

}
