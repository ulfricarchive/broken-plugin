package com.ulfric.plugin.broken;

import com.ulfric.broken.ErrorHandler;
import com.ulfric.plugin.Plugin;

public class BrokenPlugin extends Plugin {

	public BrokenPlugin() {
		addBootHook(this::bindBroken);
		addShutdownHook(this::unbindBroken);
	}

	private void bindBroken() {
		FACTORY.bind(ErrorHandler.class).toSingleton();
	}

	private void unbindBroken() {
		FACTORY.bind(ErrorHandler.class).toNothing();
	}

}
