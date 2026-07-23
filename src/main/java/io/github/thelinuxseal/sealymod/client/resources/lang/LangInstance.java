package io.github.thelinuxseal.sealymod.client.resources.lang;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class LangInstance {
    private final Minecraft client = Minecraft.getInstance();
    private JsonObject langData;
    private Function<String, String> pathFunc;

    public LangInstance(Function<String, String> pathFunc){
        this.pathFunc = pathFunc;

    }


    public String get(String key) {
        if (langData==null){return key;}
        if (langData.has(key)) {
            return langData.get(key).getAsString();
        } else {
            return key;
        }
    }

    public Component getAsComponent(String key){
        return Component.literal(get(key));
    }

    public JsonElement getAsJsonElement(String key) {
        // If data isn't loaded or missing the key, wrap the fallback key into a JsonPrimitive
        if (langData == null || !langData.has(key)) {
            return new JsonPrimitive(key);
        }

        // Otherwise, return the actual element (which could be your JsonObject)
        return langData.get(key);
    }

    public JsonObject getAll(){
        return langData;
    }

    public void reload() {
        String lang = Minecraft.getInstance().getLanguageManager().getSelected();
        Identifier path = Identifier.fromNamespaceAndPath("sealymod", "sealylang/" + pathFunc.apply(lang));

        List<Resource> langFiles = client.getResourceManager().getResourceStack(path);

        ArrayList<JsonObject> unmergedLangData = new ArrayList<>();

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

    private static JsonObject mergeJson(ArrayList<JsonObject> json) {
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



