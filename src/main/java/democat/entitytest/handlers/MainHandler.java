package democat.entitytest.handlers;

import democat.entitytest.EntityTesting;
import democat.entitytest.config.ConfigHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MainHandler {
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event) {
        if (event.getModID().equals(EntityTesting.MODID)) {
            ConfigHandler.config.save();
            ConfigHandler.initConfigs();
        }
    }
}