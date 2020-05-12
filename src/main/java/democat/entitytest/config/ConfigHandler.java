package democat.entitytest.config;

import java.util.HashMap;

import org.apache.logging.log4j.Level;

import democat.entitytest.EntityTesting;
import net.minecraft.util.ResourceLocation;

public class ConfigHandler {
    public static net.minecraftforge.common.config.Configuration config;

    private static final String CATEGORY_MOBMSG = "general";

    public static void initConfigs() {
        if (config == null) {
            EntityTesting.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialized!");
            return;
        }

        config.load();

        Configuration.mobMessageTimer = config.getInt("Mob Message Timer", CATEGORY_MOBMSG, 100, 20, Integer.MAX_VALUE, "Ticks before allowing another message");

        for (String s : config.getStringList("Mob Messages", CATEGORY_MOBMSG, new String[] { 
                    "minecraft:cow;MOO;", 
                    "minecraft:chicken;cluck cluck;50" 
                }, "Mob messages in the form entityid;message;[optional]chance")) {
            boolean hasChance = !s.endsWith(";");
            String[] parts = s.split(";");
            ResourceLocation resloc = new ResourceLocation(parts[0]);
            if (!Configuration.mobMessages.containsKey(resloc))
                Configuration.mobMessages.put(resloc, new HashMap<>());
            if (!Configuration.mobMessages.get(resloc).containsKey(parts[1]))
                Configuration.mobMessages.get(resloc).put(parts[1], hasChance ? Integer.parseInt(parts[2]) : 100);
            else
                EntityTesting.logger.log(Level.WARN, "Duplicate string for " + parts[0] + ", ignoring");
        }

        config.save();
    }
}