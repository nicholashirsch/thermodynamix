package thermodynamix.core;

import net.minecraft.world.item.ItemStack;
import thermodynamix.Thermodynamix;

public class Temperature {

    public static final float AMBIENT = 300f;

    public static float getTemperature(ItemStack stack) {
        return stack.getOrDefault(Thermodynamix.TEMPERATURE.get(), AMBIENT);
    }

    public static String getTemperatureTooltip(ItemStack itemSTack) {
        float temperature = getTemperature(itemSTack);
        return temperature + "°C";
    }
}
