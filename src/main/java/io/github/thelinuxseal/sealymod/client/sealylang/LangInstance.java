package io.github.thelinuxseal.sealymod.client.sealylang;

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
import java.util.Arrays;
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
        if (langData == null || !langData.has(key)) {
            return new JsonPrimitive(key);
        }

        return langData.get(key);
    }

    public JsonObject getAll(){
        return langData;
    }

    public void reload() {
        //SealyModClient.LOGGER.info("Reloading Lang");
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
                    System.out.println("[SealyLib] Failed reading lang from resource pack: "+ resource.knownPackInfo().toString());
                    System.out.println(Arrays.toString(e.getStackTrace()));
                } catch (Exception f) {
                    System.out.println("[SealyLib] Failed reading lang from some resource pack, and can't print info about it :(");
                    System.out.println(Arrays.toString(e.getStackTrace()));
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



