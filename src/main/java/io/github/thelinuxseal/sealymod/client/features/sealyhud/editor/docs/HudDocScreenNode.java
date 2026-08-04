package io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs;

import io.github.thelinuxseal.sealymod.client.SealyModClient;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.HudDocScanner.FuncRecord;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.HudDocScanner.ClassRecord;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.HudDocScreen.Position;

import java.util.ArrayList;
import java.util.List;

public class HudDocScreenNode {
    private final List<FuncRecord> func = new ArrayList<>();
    private final List<HudDocScreenNode> children = new ArrayList<>();
    private final ClassRecord me;
    private final HudDocScreen parent;
    private boolean isExpanded = false;
    private Button expandBtn;
    public HudDocScreenNode(Object o, HudDocScreen parent, ClassRecord me){
        this.parent = parent;
        this.me = me;
        Component expandedStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.docs.node.expanded");
        Component closedStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.docs.node.closed");

        expandBtn = Button.builder(closedStr,(btn)->{
            isExpanded = !isExpanded;
            expandBtn.setMessage(isExpanded ? expandedStr : closedStr);
        }).build();

        parent.addNewWidget(expandBtn);

        expandBtn.setWidth(20);
        expandBtn.setHeight(20);


    }
    public void addFunc(FuncRecord record){
        func.add(record);
    }
    public void addChild(HudDocScreenNode node){
        children.add(node);
    }

       public Position render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta, Position oldPos){
        //System.out.println(oldPos.x());
        //System.out.println(oldPos.y());
        float x = oldPos.x();
        float y = oldPos.y();
        expandBtn.setX((int) x);
        expandBtn.setY((int) y);
        expandBtn.extractRenderState(graphics,mouseX,mouseY,delta);
        graphics.text(parent.getFont(),me.name(),(int)x+22, (int) y+6,0xFF00FFFF);
        Position pos = new Position(x+22,y+22);
        Position pos2;
        if (isExpanded) {

            for (int i = 0; i < children.size(); i++) {
                HudDocScreenNode child = children.get(i);
                pos2 = pos;
                pos = child.render(graphics, mouseX, mouseY, delta, pos);
                graphics.horizontalLine((int) (pos2.x()-12), (int) pos2.x(), (int) pos2.y()+10,0xFFBBBBBB);
                if (!func.isEmpty() || i + 1 != children.size()) {
                    graphics.verticalLine((int) pos.x() - 12, (int) pos2.y() - 4, (int) pos.y(), 0xFFBBBBBB);
                } else {
                    graphics.verticalLine((int) pos.x() - 12, (int) pos2.y() - 4, (int) pos2.y()+10, 0xFFBBBBBB);
                }
            }
            for (int j = 0; j < func.size(); j++) {
                FuncRecord f = func.get(j);
                pos2 = pos;
                pos = renderEntry(graphics,pos,f);

                graphics.horizontalLine((int) (pos2.x()-12), (int) pos2.x()+2, (int) pos2.y()+3,0xFFBBBBBB);
                if (j + 1 != func.size()) {
                    graphics.verticalLine((int) pos.x()-12, (int) pos2.y()-4, (int) pos.y(),0xFFBBBBBB);
                } else {
                    graphics.verticalLine((int) pos.x()-12, (int) pos2.y()-4, (int) pos2.y()+3,0xFFBBBBBB);
                }
            }
        }
        return new Position(x, pos.y()+2);
    }


    private Position renderEntry(GuiGraphicsExtractor graphics, Position pos, FuncRecord f) {
        int x = (int) pos.x()+5;
        int y = (int) pos.y();
        Font font = parent.getFont();
        graphics.text(
                font,
                f.name(),
                x,
                y,
                0xFFFF5500
        );

        y += 14;

        graphics.text(
                font,
                f.desc(),
                x+10,
                y,
                0xFFCCCCCC
        );


        y += 14;

        graphics.text(
                font,
                f.path(),
                x+10,
                y,
                0xFF55FF55
        );
        graphics.text(
                font,
                " -> ",
                (int) (x+10+font.getSplitter().stringWidth(f.path())),
                y,
                0xFFAAAAAA
        );
        graphics.text(
                font,
                f.returns(),
                (int) (x+10+font.getSplitter().stringWidth(f.path())+parent.arrowWidth),
                y,
                0xFFFFFF55
        );



        y += 20;
        return new Position(x-5,y);
    }



}
