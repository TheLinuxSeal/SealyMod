package com.thelinuxseal.sealymod.client.config.categories;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import com.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import com.thelinuxseal.sealymod.client.config.SealyModConfigScreen;
import com.thelinuxseal.sealymod.client.config.categories.sealyhud.ConfigSealyHUDWidgetsScreen;
import com.thelinuxseal.sealymod.client.sealyhud.SealyHUDElement;
import com.thelinuxseal.sealymod.client.sealyhud.SealyHUDElementManager;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigCategorySealyHUDWidgetsWrapper {
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(Component.literal("HUD Editor"))
                .group(OptionGroup.createBuilder()
                        .name(Component.literal("Overlay Element Controls"))
                        .option(ButtonOption.createBuilder()
                                .name(Component.literal("Open SealyHUD Editor"))
                                .description(OptionDescription.of(Component.literal("Opens a dedicated, high-density screen to customize and toggle all layout items side-by-side.")))
                                .action((yaclScreen, btnOpt) -> {
                                    // Drop seamlessly out of YACL and jump into your master panel layout!
                                    Minecraft.getInstance().setScreen(new ConfigSealyHUDWidgetsScreen(yaclScreen, config));
                                })
                                .build())
                        .build())
                .build();
        /*return ConfigCategory.createBuilder()
                .name(Component.literal("HUD Editor"))

                // ACTION BUTTON: Create a fresh widget
                .option(ButtonOption.createBuilder()
                        .name(Component.literal("➕ Create New Widget"))
                        .description(OptionDescription.of(Component.literal("Appends a fully customizable widget to your overlay.")))
                        .action((screen, opt) -> {
                            // 1. Add default parameters directly into your confuration's widget list
                            config.hudWidgets.add(new SealyHUDElement("", "", "", false));

                            // 2. Refresh the active runtime elements layout
                            SealyHUDElementManager.loadFromConfig(config.hudWidgets);
                            SealyModConfigHandler.save();

                            // 3. Hot-swap and redraw the screen layout seamlessly
                            if (screen != null) {
                                Minecraft.getInstance().setScreen(SealyModConfigScreen.create(parent, config));
                            }
                        })
                        .build()
                )

                // DYNAMIC GENERATION LOOP: Creates an individual option grouping per widget container
                .groups(config.hudWidgets.stream().map(widget -> OptionGroup.createBuilder()
                        .name(Component.literal("Widget: " + (widget.getTextTemplate().length() > 15 ? widget.getTextTemplate().substring(0, 12) + "..." : widget.getTextTemplate())))

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Enabled"))
                                .binding(true, widget::isEnabled, widget::setEnabled)
                                .controller(TickBoxControllerBuilder::create)
                                .build())

                        .option(Option.<String>createBuilder()
                                .name(Component.literal("X Formula Position"))
                                .description(OptionDescription.of(Component.literal("Supports numbers and formulas like: screenwidth - 100")))
                                .binding("10", widget::getXFormula, widget::setXFormula)
                                .controller(StringControllerBuilder::create)
                                .build())

                        .option(Option.<String>createBuilder()
                                .name(Component.literal("Y Formula Position"))
                                .description(OptionDescription.of(Component.literal("Supports numbers and formulas like: screenheight / 2")))
                                .binding("10", widget::getYFormula, widget::setYFormula)
                                .controller(StringControllerBuilder::create)
                                .build())

                        .option(Option.<String>createBuilder()
                                .name(Component.literal("Text Content Template"))
                                .description(OptionDescription.of(Component.literal("Placeholders: %fps, %x, %y, %z")))
                                .binding("FPS: %fps", widget::getTextTemplate, widget::setTextTemplate)
                                .controller(StringControllerBuilder::create)
                                .build())

                        // ACTION BUTTON: Remove this specific widget
                        .option(ButtonOption.createBuilder()
                                .name(Component.literal("❌ Delete This Widget"))
                                .action((screen, opt) -> {
                                    config.hudWidgets.remove(widget);
                                    SealyHUDElementManager.loadFromConfig(config.hudWidgets);
                                    SealyModConfigHandler.save();

                                    if (screen != null) {
                                        Minecraft.getInstance().setScreen(SealyModConfigScreen.create(parent, config));
                                    }
                                })
                                .build())

                        .build()
                ).toList())
                .build();*/
    }
}
