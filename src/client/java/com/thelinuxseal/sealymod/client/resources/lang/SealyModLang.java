package com.thelinuxseal.sealymod.client.resources.lang;

import com.google.gson.JsonElement;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public final class SealyModLang {
    public static Minecraft client = Minecraft.getInstance();
    public static Identifier path;
    public static List<Resource> langFiles;
    public static ArrayList<JsonObject> unmergedLangData;
    public static JsonObject langData;


    public static String get(String key) {
        if (langData==null){return key;}
        if (langData.has(key)) {
            return langData.get(key).getAsString();
        } else {
            return key;
        }
    }

    public static Component getAsComponent(String key){
        return Component.literal(get(key));
    }

    public static void reload() {
        String lang = Minecraft.getInstance().getLanguageManager().getSelected();
        path = Identifier.fromNamespaceAndPath("sealymod", "sealylang/"+lang+".json");

        langFiles = client.getResourceManager().getResourceStack(path);

        unmergedLangData = new ArrayList<>();

        for (Resource resource : langFiles) {
            try (var stream = resource.open()) {
                String fileContents = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                JsonObject obj = JsonParser.parseString(
                        fileContents
                ).getAsJsonObject();

                unmergedLangData.add(obj);

            } catch (Exception e) {
                try {
                    System.out.println("[SealyMod] Failed reading lang pack: "+ resource.knownPackInfo().toString());
                } catch (Exception f) {
                    System.out.println("[SealyMod] Failed reading lang pack");
                }
            }
        }

        langData = mergeJson(unmergedLangData);
    }

    public static JsonObject mergeJson(ArrayList<JsonObject> json) {
        JsonObject result = new JsonObject();

        for (JsonObject pack : json) {
            for (String key : pack.keySet()) {
                JsonElement value = pack.get(key);
                result.add(key, value);
            }
        }
        return result;
    }
}
