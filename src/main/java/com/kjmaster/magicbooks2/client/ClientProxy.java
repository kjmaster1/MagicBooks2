package com.kjmaster.magicbooks2.client;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.CommonProxy;
import com.kjmaster.magicbooks2.common.blocks.tile.PedestalTESR;
import com.kjmaster.magicbooks2.common.blocks.tile.TilePedestal;
import com.kjmaster.magicbooks2.common.init.ModBlocks;
import com.kjmaster.magicbooks2.common.init.ModEntities;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by pbill_000 on 11/09/2017.
 */
@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {

    Minecraft MINECRAFT = Minecraft.getMinecraft();

    @Override
    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed) {
        MINECRAFT.world.spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModelBakeryVariants() {
        ModelBakery.registerItemVariants(ModItems.Shard,
                new ResourceLocation(MagicBooks2.MODID, "shard_air"),
                new ResourceLocation(MagicBooks2.MODID, "shard_earth"),
                new ResourceLocation(MagicBooks2.MODID, "shard_fire"),
                new ResourceLocation(MagicBooks2.MODID, "shard_water"),
                new ResourceLocation(MagicBooks2.MODID, "shard_arcane")
        );
        ModelBakery.registerItemVariants(ModItems.Book,
                new ResourceLocation(MagicBooks2.MODID, "book_air"),
                new ResourceLocation(MagicBooks2.MODID, "book_earth"),
                new ResourceLocation(MagicBooks2.MODID, "book_fire"),
                new ResourceLocation(MagicBooks2.MODID, "book_water"),
                new ResourceLocation(MagicBooks2.MODID, "book_arcane")
        );
        ModelBakery.registerItemVariants(ModItems.Wand,
                new ResourceLocation(MagicBooks2.MODID, "wand_air"),
                new ResourceLocation(MagicBooks2.MODID, "wand_earth"),
                new ResourceLocation(MagicBooks2.MODID, "wand_fire"),
                new ResourceLocation(MagicBooks2.MODID, "wand_water"),
                new ResourceLocation(MagicBooks2.MODID, "wand_arcane"),
                new ResourceLocation(MagicBooks2.MODID, "wand_ultimate")
        );
        ModelBakery.registerItemVariants(ModItems.earthSpell,
                new ResourceLocation(MagicBooks2.MODID, "spell_earth_grow"),
                new ResourceLocation(MagicBooks2.MODID, "spell_earth_walling")
        );
        ModelBakery.registerItemVariants(ModItems.airSpell,
                new ResourceLocation(MagicBooks2.MODID, "spell_air_lightning"),
                new ResourceLocation(MagicBooks2.MODID, "spell_air_invisibility")
        );
        ModelBakery.registerItemVariants(ModItems.fireSpell,
                new ResourceLocation(MagicBooks2.MODID, "spell_fire_fireblast")
        );
        ModelBakery.registerItemVariants(ModItems.waterSpell,
                new ResourceLocation(MagicBooks2.MODID, "spell_water_bubble")
        );
        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.crystal),
                new ResourceLocation(MagicBooks2.MODID, "crystal_air"),
                new ResourceLocation(MagicBooks2.MODID, "crystal_earth"),
                new ResourceLocation(MagicBooks2.MODID, "crystal_fire"),
                new ResourceLocation(MagicBooks2.MODID, "crystal_water"),
                new ResourceLocation(MagicBooks2.MODID, "crystal_arcane")

        );
        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.greaterCrystal),
                new ResourceLocation(MagicBooks2.MODID, "crystal_greater_air"),
                new ResourceLocation(MagicBooks2.MODID, "crystal_greater_earth"),
                new ResourceLocation(MagicBooks2.MODID, "crystal_greater_fire"),
                new ResourceLocation(MagicBooks2.MODID, "crystal_greater_water"),
                new ResourceLocation(MagicBooks2.MODID, "crystal_greater_arcane")

        );
        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.shardOre),
                new ResourceLocation(MagicBooks2.MODID, "shard_ore_air"),
                new ResourceLocation(MagicBooks2.MODID, "shard_ore_earth"),
                new ResourceLocation(MagicBooks2.MODID, "shard_ore_fire"),
                new ResourceLocation(MagicBooks2.MODID, "shard_ore_water"),
                new ResourceLocation(MagicBooks2.MODID, "shard_ore_arcane")
        );
        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.shardBlock),
                new ResourceLocation(MagicBooks2.MODID, "shard_block_air"),
                new ResourceLocation(MagicBooks2.MODID, "shard_block_earth"),
                new ResourceLocation(MagicBooks2.MODID, "shard_block_fire"),
                new ResourceLocation(MagicBooks2.MODID, "shard_block_water"),
                new ResourceLocation(MagicBooks2.MODID, "shard_block_arcane")
        );
        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.pedestal),
                new ResourceLocation(MagicBooks2.MODID, "pedestal_block_air"),
                new ResourceLocation(MagicBooks2.MODID, "pedestal_block_earth"),
                new ResourceLocation(MagicBooks2.MODID, "pedestal_block_fire"),
                new ResourceLocation(MagicBooks2.MODID, "pedestal_block_water"),
                new ResourceLocation(MagicBooks2.MODID, "pedestal_block_arcane")
        );
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModItems.registerModels();
        ModBlocks.registerModels();
        ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new PedestalTESR());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx));
    }
    @SideOnly(Side.CLIENT)
    @Override
    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx));
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        OBJLoader.INSTANCE.addDomain(MagicBooks2.MODID);
        MinecraftForge.EVENT_BUS.register(new HUDHandler());
        ModEntities.initModels();
    }

    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event) {
        Item item = event.getItemStack().getItem();
        if(item == ModItems.shardPickaxeAir)
            event.getToolTip().add("Doubles as a magnet!");
        if (item == ModItems.shardPickaxeArcane)
            event.getToolTip().add("Gotta get those shards!");
        if (item == ModItems.shardPickaxeWater)
            event.getToolTip().add("Handy for obsidian!");
        if (item == ModItems.shardPickaxeFire)
            event.getToolTip().add("All your auto-smelting needs!");
        if (item == ModItems.shardPickaxeEarth)
            event.getToolTip().add("Like a hammer but a bit better!");
        if (item == new ItemStack(ModItems.Wand, 1, 5).getItem())
            event.getToolTip().add("WIP");
        if (item == Item.getItemFromBlock(ModBlocks.earthWall))
            event.getToolTip().add("WIP");
        if (item == Item.getItemFromBlock(ModBlocks.arcaneCrafter))
            event.getToolTip().add("WIP");
    }
}
