/**
 * Copyright (c) 2013 Shopgate GmbH
 */

package game.engine.game;

import jdk.nashorn.api.scripting.JSObject;

/**
 * Implements logging functions.
 */
public class JavascriptConsole {

	/**
	 * @param o
	 */
	public void log(Object o) {
		StringBuilder builder = new StringBuilder();
		dump(o, builder);
		System.out.println(builder);
	}

	/**
	 * 
	 */
	private void dump(Object o, StringBuilder builder) {
		
		// handle known types
		if (o == null) {
			builder.append("null");
			return;
		} else if (o instanceof String) {
			builder.append('"').append(o).append('"');
			return;
		}
		
		// try to wrap JS values and dump them, or fall back to toString()
		o = jdk.nashorn.api.scripting.ScriptUtils.wrap(o);
		if (o instanceof JSObject) {
			JSObject object = (JSObject)o;
			if (object.isArray()) {
				builder.append('[');
				boolean first = true;
				for (Object element : object.values()) {
					if (first) {
						first = false;
					} else {
						builder.append(", ");
					}
					dump(element, builder);
				}
				builder.append("]");
			} else if (object.isFunction()) {
				builder.append("function");
			} else {
				builder.append('{');
				boolean first = true;
				for (Object key : object.keySet()) {
					if (first) {
						first = false;
					} else {
						builder.append(", ");
					}
					dump(key, builder);
					builder.append(": ");
					dump(object.getMember(key.toString()), builder);
				}
				builder.append("}");
			}
		} else {
			builder.append(o);
		}
		
	}
	
}
