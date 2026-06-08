package com.thelinuxseal.sealymod.client.sealyhud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SealyHUDTextParser {
    public static String getSimpleText(String textTemplate) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;

        String fps = String.valueOf(SealyHUDVars.getFPS());
        String x = SealyHUDVars.getX();
        String y = SealyHUDVars.getY();
        String z = SealyHUDVars.getZ();
        String dim = SealyHUDVars.getDim();
        String biome = SealyHUDVars.getBiome();
        String gameMode = SealyHUDVars.getGameMode();
        String ping = SealyHUDVars.getPing();
        String dir = SealyHUDVars.getDir();


        // Regex pattern: Matches "%%", "%fps", "%x", "%y", or "%z"
        Pattern pattern = Pattern.compile("%%|%fps|%x|%y|%z|%dim|%dimension|%biome|%gamemode|%ping|%dir|%direction|%facing");
        Matcher matcher = pattern.matcher(textTemplate);
        StringBuilder sb = new StringBuilder();

        // Scan through the string from left to right
        while (matcher.find()) {
            String token = matcher.group();

            switch (token) {
                case "%%" -> matcher.appendReplacement(sb, Matcher.quoteReplacement("%"));
                case "%fps" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(fps));
                case "%x" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(x));
                case "%y" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(y));
                case "%z" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(z));
                case "%dim" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dim));
                case "%dimension" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dim));
                case "%biome" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(biome));
                case "%gamemode" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(gameMode));
                case "%ping" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(ping));
                case "%dir" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dir));
                case "%direction" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dir));
                case "%facing" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dir));
                default -> matcher.appendReplacement(sb, Matcher.quoteReplacement(token));
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
