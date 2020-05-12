package democat.entitytest.handlers;

import java.util.Map;

import org.apache.logging.log4j.Level;

import democat.entitytest.EntityTesting;
import democat.entitytest.config.Configuration;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityEventHandler {
    int entityMsgTimer;

    public EntityEventHandler() {
        entityMsgTimer = Configuration.mobMessageTimer;
    }

    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        String message = "Item " + event.getItem().getDisplayName().getFormattedText() + " has been picked up!";
        event.getEntity().sendMessage(new TextComponentString(message));
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event) {
        EntityEntry ee = EntityRegistry.getEntry(event.getTarget().getClass());
        EntityTesting.logger.log(Level.DEBUG, "EntityEntry is " + ee == null ? "NULL" : ee);
        if (ee != null)
            EntityTesting.logger.log(Level.DEBUG, "EntityEntry registry name is " + ee.getRegistryName());

        if (entityMsgTimer == 0 && ee != null && Configuration.mobMessages.containsKey(ee.getRegistryName())) {
            String message = randomStringWithWeight(Configuration.mobMessages.get(ee.getRegistryName()));
            EntityTesting.logger.log(Level.DEBUG, "Obtained message " + message);
            if (message != "")
                event.getEntity().sendMessage(new TextComponentString(message));

            entityMsgTimer = Configuration.mobMessageTimer;
        }
    }

    @SubscribeEvent
    public void tickEvent(TickEvent event) {
        if (entityMsgTimer > 0) entityMsgTimer--;
    }

    public String randomStringWithWeight(Map<String, Integer> strings) {
        if (strings.size() == 1) {
            return Math.random() <= strings.values().toArray(new Integer[1])[0]/100.0 ? strings.keySet().toArray(new String[1])[0] : "";
        }

        int sumChance = 0;
        for (int weight : strings.values())
            sumChance += weight;
        if (sumChance < 100 && !strings.containsKey(""))
            strings.put("", 100 - sumChance);
        int r = (int) (Math.random() * (sumChance + 1));
        int countChance = 0;
        for (String key : strings.keySet()) {
            countChance += strings.get(key);
            if (countChance >= r)
                return key;
        }
        return "Default message";
    }
}