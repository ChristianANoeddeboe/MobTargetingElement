package angercraft.mobtargetingelement.proxy;

import angercraft.mobtargetingelement.config.Keybinds;

public class ClientProxy implements IProxy {

	@Override
	public void preInit() {}

	@Override
	public void init() {
		Keybinds.register();
	}
}
