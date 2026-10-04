package thermodynamix.core;

import static thermodynamix.core.DeferredRegistry.TEMPERATURE;

import net.minecraft.world.item.ItemStack;

public class Temperature {

    public static final float AMBIENT = 300f;

    public static float getTemperature(ItemStack stack) {
        return stack.getOrDefault(TEMPERATURE.get(), AMBIENT);
    }

    public static String getTemperatureTooltip(ItemStack itemSTack) {
        float temperature = getTemperature(itemSTack);
        return temperature + "°C";
    }
}
