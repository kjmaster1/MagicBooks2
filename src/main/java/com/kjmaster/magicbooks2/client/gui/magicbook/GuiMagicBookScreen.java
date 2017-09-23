package com.kjmaster.magicbooks2.client.gui.magicbook;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.EntriesProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.IEntries;
import com.kjmaster.magicbooks2.common.network.ModGuiHandler;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import com.kjmaster.magicbooks2.common.network.ServerEntriesPacket;
import com.kjmaster.magicbooks2.utils.GuiToolTipScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiMagicBookScreen extends GuiToolTipScreen {

    private ResourceLocation BACKGROUNDTEXTURE = new ResourceLocation(MagicBooks2.MODID,
            "textures/gui/magicbook/background.png");
    private ResourceLocation BUTTTONTEXTURE = new ResourceLocation(MagicBooks2.MODID,
            "textures/gui/magicbook/buttons.png");
    private int guiLeft, guiTop, midX, midY;
    private EntryButton intro;
    private EntityPlayer player = Minecraft.getMinecraft().player;

    @Override
    public void setGuiSize(int w, int h) {
        super.setGuiSize(192, 192);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(BACKGROUNDTEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 192, 192);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void initGui() {
        guiLeft = (this.width - 192) / 2;
        guiTop = (this.height - 192) / 2;
        midX = guiLeft + (146 / 2) + (21 / 2);
        midY = guiTop + (180 / 2) - 18;

        intro = new EntryButton(1, midX, midY, "", BUTTTONTEXTURE, "Intro", 0, 0, 20);
        this.buttonList.add(intro);

        super.initGui();
    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        switch (buttonId) {
            case 1:
                return "Introduction to MagicBooks. Requires 1 of each Skill Point to unlock.";
            default: return null;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        IEntries entriesCap = player.getCapability(EntriesProvider.ENTRIES_CAP, null);
        ISkillPoints skillPointsCap = player.getCapability(SkillPointsProvider.SKILL_POINTS_CAP, null);
        int airPoints = skillPointsCap.getPoints("Air");
        int earthPoints = skillPointsCap.getPoints("Earth");
        int firePoints = skillPointsCap.getPoints("Fire");
        int waterPoints = skillPointsCap.getPoints("Water");
        int arcanePoints = skillPointsCap.getPoints("Arcane");
        switch (button.id) {
            case 1:
                Boolean isIntroUnlocked = entriesCap.isEntryUnlocked("Intro");
                if (isIntroUnlocked)
                    player.openGui(MagicBooks2.instance, ModGuiHandler.introEntry, player.world,
                            (int) player.posX, (int) player.posY, (int) player.posZ);
                else if (canBuy("Intro", airPoints, earthPoints, firePoints, waterPoints, arcanePoints)) {
                    entriesCap.unlockEntry("Intro");
                    PacketInstance.INSTANCE.sendToServer(new ServerEntriesPacket("Intro"));
                }
        }
        super.actionPerformed(button);
    }

    private Boolean canBuy(String entry, int airPoints, int earthPoints, int firePoints, int waterPoints, int arcanePoints) {
        switch (entry) {
            case "Intro":
                return airPoints >= 1 && earthPoints >= 1 && firePoints >= 1 && waterPoints >= 1 && arcanePoints >= 1;
            default:
                return false;
        }
    }
}

