package seal.thelinuxseal.sealymod.client.sealyhud.editorold;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import seal.thelinuxseal.sealymod.client.SealyModClient;

import java.util.ArrayList;
import java.util.List;

@Deprecated
class LegacyHudHelpScreen {

    private final LegacyHudEditor parent;

    private JsonObject data = new JsonObject();
    private final List<SubHelp> entries = new ArrayList<>();

    public LegacyHudHelpScreen(LegacyHudEditor parent) {
        this.parent = parent;
        reload();
    }

    public void reload() {
        entries.clear();

        JsonObject obj = SealyModClient.lang.getAsJsonElement("sealymod.sealyhud.editor.help.docs").getAsJsonObject();

        if (!obj.isJsonObject()) {
            return;
        }


        data = obj;

        obj.keySet()
                .stream()
                .sorted()
                .map(SubHelp::new)
                .forEach(entries::add);
    }

    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {

        graphics.fill(
                20,
                20,
                parent.width - 20,
                parent.height - 20,
                0xAA000000
        );

        double y = 30 - parent.scrollAmount;

        for (SubHelp help : entries) {
            y = help.render(graphics, y);
        }
    }

    /*public void mouseClicked(double mouseX, double mouseY, int button) {
        for (SubHelp help : entries) {
            help.expand.mouseClicked(mouseX, mouseY, button);
        }
    }*/

    private class SubHelp {

        private final String name;
        private final String desc;
        private final String example;

        SubHelp(String key) {
            JsonObject dat = data.getAsJsonObject(key);

            name = dat.get("name").getAsString();
            desc = dat.get("desc").getAsString();
            example = dat.get("example").getAsString();
        }

        double render(GuiGraphicsExtractor graphics, double y) {
            net.minecraft.network.chat.MutableComponent component = net.minecraft.network.chat.Component.literal(name);
            graphics.text(
                    parent.getFont(),
                    component.setStyle(component.getStyle().withBold(true)),
                    30,
                    (int) y,
                    0xFFFFFFFF
            );

            y += 14;

            graphics.text(
                    parent.getFont(),
                    desc,
                    40,
                    (int) y,
                    0xFFCCCCCC
            );

            y += 14;

            graphics.text(
                    parent.getFont(),
                    "Example:",
                    40,
                    (int) y,
                    0xFFFFFF55
            );

            y += 12;

            graphics.text(
                    parent.getFont(),
                    example,
                    50,
                    (int) y,
                    0xFF55FF55
            );

            y += 20;

            return y;
        }
    }
}