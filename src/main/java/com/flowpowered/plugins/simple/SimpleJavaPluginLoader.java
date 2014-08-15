package com.flowpowered.plugins.simple;

import java.util.Map;

import com.flowpowered.plugins.PluginException;
import com.flowpowered.plugins.PluginHandle;
import com.flowpowered.plugins.PluginManager;
import com.flowpowered.plugins.WrappedPluginException;

public class SimpleJavaPluginLoader extends AbstractSingleClassLoaderJavaPluginLoader {
    public SimpleJavaPluginLoader(ClassLoader cl) {
        super(cl);
    }

    @Override
    protected PluginHandle load(PluginManager manager, String pluginName, Map<String, String> mains) throws PluginException {
        String main = mains.get(pluginName);
        if (main == null) {
            throw new PluginException("No main class specified");
        }
        try {
            Class<?> clazz = Class.forName(main, false, getClassLoader());
            Class<? extends JavaPlugin> pluginClass = clazz.asSubclass(JavaPlugin.class);
            JavaPlugin plugin = pluginClass.newInstance();
            PluginHandle handle = new SimpleJavaPluginHandle(manager, plugin, pluginName);
            plugin.init(manager, handle);
            return handle;
        } catch (ClassNotFoundException | ClassCastException | InstantiationException | IllegalAccessException e) {
            throw new PluginException("Invalid plugin main class", e);
        } catch (ExceptionInInitializerError e) {
            throw new WrappedPluginException("Could not instantiate plugin main class", e.getCause());
        }
    }
}
