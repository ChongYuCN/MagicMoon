package com.chongyu.magicmoon.config;

import com.chongyu.magicmoon.MagicMoon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    private final CommonConfig commonConfig = new CommonConfig();
    final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();//创建配置文件
    public Config(){
    }

    //加载
    public void load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().normalize().resolve("magicmoon.json");//配置文件名称
        File config = configPath.toFile();

        if (!config.exists()) {
            MagicMoon.LOGGER.warn("MagicMoon Config for allowedCheat not found, recreating default");
            this.save();
        } else {
            try {
                this.commonConfig.deserialize((JsonObject)this.gson.fromJson(Files.newBufferedReader(configPath), JsonObject.class));
            } catch (IOException var4) {
                MagicMoon.LOGGER.error("MagicMoon Could not read config from:" + configPath, var4);
            }
        }
    }

    public void save() {
        Path configPath = FabricLoader.getInstance().getConfigDir().normalize().resolve("magicmoon.json");

        try {
            BufferedWriter writer = Files.newBufferedWriter(configPath);
            this.gson.toJson(this.commonConfig.serialize(), JsonObject.class, writer);
            writer.close();
        } catch (IOException var3) {
            MagicMoon.LOGGER.error("MagicMoon Could not write config to:" + configPath, var3);
        }
    }

    public CommonConfig getCommonConfig() {
        return this.commonConfig;
    }
}
