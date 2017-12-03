package com.ulfric.plugin.broken;

import java.util.HashMap;
import java.util.Map;

import com.ulfric.broken.ErrorHandler;
import com.ulfric.dragoon.qualifier.Qualifier;
import com.ulfric.dragoon.stereotype.Stereotypes;
import com.ulfric.plugin.Plugin;

public class BrokenPlugin extends Plugin {

	public BrokenPlugin() {
		addBootHook(this::bindBroken);
		addShutdownHook(this::unbindBroken);
	}

	private void bindBroken() {
		ErrorHandler main = new ErrorHandler();
		Map<String, ErrorHandler> handlers = new HashMap<>();
		handlers.put("main", main);
		FACTORY.bind(ErrorHandler.class).toFunction(parameters -> {
			Qualifier qualifier = parameters.getQualifier();
			if (qualifier == null) {
				return main;
			}

			Channel channelMeta = Stereotypes.getFirst(qualifier, Channel.class);
			if (channelMeta == null) {
				return main;
			}

			String channel = channelMeta.value();
			if (channel.isEmpty()) {
				return main;
			}

			return handlers.computeIfAbsent(channel, ignore -> new ErrorHandler());
		});
	}

	private void unbindBroken() {
		FACTORY.bind(ErrorHandler.class).toNothing();
	}

}
