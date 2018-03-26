package com.reactnativenavigation.parse;

import com.reactnativenavigation.parse.params.Color;
import com.reactnativenavigation.parse.params.NullColor;
import com.reactnativenavigation.parse.params.NullText;
import com.reactnativenavigation.parse.params.Text;
import com.reactnativenavigation.parse.parsers.ColorParser;
import com.reactnativenavigation.parse.parsers.TextParser;

import org.json.JSONObject;

public class TopBarBackgroundOptions {
    public static TopBarBackgroundOptions parse(JSONObject json) {
        TopBarBackgroundOptions options = new TopBarBackgroundOptions();
        if (json == null) {
            return options;
        }

        options.color = ColorParser.parse(json, "color");
        options.component = TextParser.parse(json, "component");

        if (options.component.hasValue()) {
            options.color = new Color(android.graphics.Color.TRANSPARENT);
        }

        return options;
    }

    public Color color = new NullColor();
    public Text component = new NullText();

    void mergeWith(final TopBarBackgroundOptions other) {
        if (other.color.hasValue()) color = other.color;
        if (other.component.hasValue()) component = other.component;
    }

    void mergeWithDefault(TopBarBackgroundOptions defaultOptions) {
        if (!color.hasValue()) color = defaultOptions.color;
        if (!component.hasValue()) component = defaultOptions.component;
    }
}
