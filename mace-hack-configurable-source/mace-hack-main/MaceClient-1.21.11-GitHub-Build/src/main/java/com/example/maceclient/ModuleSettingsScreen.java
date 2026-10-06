package com.example.maceclient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModuleSettingsScreen extends Screen {
    private final Module module;
    public ModuleSettingsScreen(Module module){super(Text.literal(module.name+" Settings"));this.module=module;}

    @Override protected void init(){
        super.init();
        int y=55;
        for(Setting s:module.getSettings()){
            Setting current=s;
            addDrawableChild(ButtonWidget.builder(Text.literal(current.display()), b -> b.setMessage(Text.literal(current.display())))
                    .dimensions(width/2-150,y,300,24).build());
            y+=32;
        }
    }
    @Override public void render(DrawContext d,int mx,int my,float delta){
        super.render(d,mx,my,delta);
        d.drawCenteredTextWithShadow(textRenderer,Text.literal(module.name+" SETTINGS"),width/2,20,0xFFFFFF);
        d.drawCenteredTextWithShadow(textRenderer,Text.literal("Click a setting to cycle its value"),width/2,36,0xAAAAAA);
    }@Override
public boolean mouseClicked(net.minecraft.client.gui.Click click, boolean doubled) {
    double mx = click.x();
    double my = click.y();

    int y = 55;
    for (Setting s : module.getSettings()) {
        if (mx >= width / 2 - 150 && mx <= width / 2 + 150 && my >= y && my <= y + 24) {
            s.cycle();
            clearAndInit();
            return true;
        }
        y += 32;
    }

    return super.mouseClicked(click, doubled);
}
    @Override public void close(){if(client!=null)client.setScreen(new ClickGuiScreen());}
    @Override public boolean shouldPause(){return false;}
}
