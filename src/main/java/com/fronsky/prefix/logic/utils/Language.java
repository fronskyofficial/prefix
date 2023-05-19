package com.fronsky.prefix.logic.utils;

public enum Language {
    DEFAULT("Please choose a language message. The current message is a default message."), // id = 0
    NO_PERMISSION("&cYou do not have permissions to perform this action. Please contact your system administrator for assistance."); // id = 1

    private final String message;

    Language(String message) {
        this.message = message;
    }

    public int getId() {
        return ordinal();
    }

    public String getMessage() {
        return message;
    }

    public String getMessageWithColor() {
        return ColorUtils.colorize(message);
    }

    public Language getLanguage(String name) {
        Language language = null;
        for (Language obj : Language.values()) {
            if (name.equalsIgnoreCase(obj.name())) {
                language = obj;
                break;
            }
        }
        return language;
    }
}
