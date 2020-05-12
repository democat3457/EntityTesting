package democat.entitytest;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import democat.entitytest.config.ConfigHandler;
import democat.entitytest.handlers.*;

@Mod(modid = EntityTesting.MODID, name = EntityTesting.NAME, version = EntityTesting.VERSION)
public class EntityTesting
{
    public static final String MODID = "entitytest";
    public static final String NAME = "Entity Testing";
    public static final String VERSION = "0.1.0";

    public static Logger logger;

    public EntityTesting() {
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new MainHandler());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        ConfigHandler.config = new net.minecraftforge.common.config.Configuration(event.getSuggestedConfigurationFile());
        ConfigHandler.initConfigs();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        logger.info("Hi, welcome!");
    }
}
