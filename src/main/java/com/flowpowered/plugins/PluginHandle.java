package com.flowpowered.plugins;

import org.slf4j.Logger;

public abstract class PluginHandle {
    private PluginState state;

    public abstract String getName();

    public abstract PluginManager getManager();

    protected abstract void onEnable() throws PluginException;

    protected abstract void onDisable() throws PluginException;

    public void enable() throws PluginException {
        getManager().enable(this);
    }

    public void disable() throws PluginException {
        getManager().disable(this);
    }

    public Logger getLogger() {
        return getManager().getLogger(this);
    }

    public PluginState getState() {
        return state;
    }

    protected void setState(PluginState state) {
        this.state = state;
    }
}
