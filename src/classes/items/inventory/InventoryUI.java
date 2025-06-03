package classes.items.inventory;

public class InventoryUI implements InventoryObserver{
    @Override
    public void onInventoryChanged() {
        System.out.println("Inventory has changed.");
    }
}
