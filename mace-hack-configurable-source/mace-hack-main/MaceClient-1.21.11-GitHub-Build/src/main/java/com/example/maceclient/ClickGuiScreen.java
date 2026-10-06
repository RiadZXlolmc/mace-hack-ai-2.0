package com.example.maceclient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    private int offset=0;
    public ClickGuiScreen(){super(Text.literal("Mace Client"));}

    @Override protected void init(){
        super.init();
        int visible=8;
        int max=Math.max(0,MaceClient.MODULES.modules.size()-visible);
        if(offset>max)offset=max;
        int x=width/2-180,y=55;
        for(int i=0;i<visible && i+offset<MaceClient.MODULES.modules.size();i++){
            Module m=MaceClient.MODULES.modules.get(i+offset);
            int yy=y+i*38;
            addDrawableChild(ButtonWidget.builder(Text.literal(m.name+" ["+(m.enabled?"ON":"OFF")+"]"),
                    b->{m.toggle();b.setMessage(Text.literal(m.name+" ["+(m.enabled?"ON":"OFF")+"]"));})
                    .dimensions(x,yy,220,24).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Settings"),
                    b->{if(client!=null)client.setScreen(new ModuleSettingsScreen(m));})
                    .dimensions(x+225,yy,90,24).build());
        }
    }
    @Override public void render(DrawContext d,int mx,int my,float delta){
        super.render(d,mx,my,delta);
        d.drawCenteredTextWithShadow(textRenderer,Text.literal("MACE CLIENT"),width/2,18,0xFFFFFF);
        d.drawCenteredTextWithShadow(textRenderer,Text.literal("Fabric 1.21.11  •  Right Shift"),width/2,32,0xAAAAAA);
        d.drawCenteredTextWithShadow(textRenderer,Text.literal("Mouse wheel: scroll modules"),width/2,height-18,0xAAAAAA);
    }
    @Override public boolean mouseScrolled(double mx,double my,double horizontal,double vertical){
        int max=Math.max(0,MaceClient.MODULES.modules.size()-8);
        if(vertical<0)offset=Math.min(max,offset+1); else if(vertical>0)offset=Math.max(0,offset-1);
        clearAndInit(); return true;
    }
    @Override public boolean shouldPause(){return false;}
}
