package classes.items;

import classes.joker.HintJoker;
import classes.joker.KeyJoker;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
    private static final Map<String, Class<? extends Item>> itemRegistry = new HashMap<>();

    static {
        itemRegistry.put("White Key", WhiteKey.class);
        itemRegistry.put("Purple Key", PurpleKey.class);
        itemRegistry.put("Green Key", GreenKey.class);
        itemRegistry.put("Gold Key", GoldKey.class);
        itemRegistry.put("Staplergun", Staplergun.class);
        itemRegistry.put("Box Cutter", BoxCutter.class);
        itemRegistry.put("Pencil", Pencil.class);
        itemRegistry.put("Donut", Donut.class);
        itemRegistry.put("Cup of Coffee", CupOfCoffee.class);
        itemRegistry.put("Hint Joker", HintJoker.class);
        itemRegistry.put("Key Joker", KeyJoker.class);
    }

    public static Item createItem(String itemName) {
        Class<? extends Item> itemClass = itemRegistry.get(itemName);
        if (itemClass != null) {
            try {
                if (Key.class.isAssignableFrom(itemClass)) {
                    return itemClass.getDeclaredConstructor(int.class).newInstance(0);
                }
                return itemClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}