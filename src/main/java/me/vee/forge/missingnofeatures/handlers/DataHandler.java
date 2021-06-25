package me.vee.forge.missingnofeatures.handlers;

import me.vee.forge.missingnofeatures.MissingNoFeatures;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class DataHandler {

    private static final byte[] ZERO = new byte[] { 0x30 };
    public static final Map<String, Integer> PLAYER_TRAINER_WINS = new HashMap<>();
    public static Path PLAYER_DIRECTORY;

    @SubscribeEvent
    public void onSave(PlayerEvent.SaveToFile e) throws IOException {
        int value = PLAYER_TRAINER_WINS.get(e.getPlayerUUID());
        write(e.getPlayerUUID(), value);
    }

    @SubscribeEvent
    public void onLoad(PlayerEvent.LoadFromFile e) throws IOException {
        byte[] bytes = Files.readAllBytes(data(e.getPlayerDirectory(), e.getPlayerUUID()));
        int value = Integer.parseInt(new String(bytes));
        PLAYER_TRAINER_WINS.put(e.getPlayerUUID(), value);
    }

    private static Path data(File file, String uuid) throws IOException {

        PLAYER_DIRECTORY = file.toPath();

        Path modDirectory = PLAYER_DIRECTORY.resolve(MissingNoFeatures.MOD_ID);
        if (!Files.exists(modDirectory)) Files.createDirectory(modDirectory);

        Path data = modDirectory.resolve(uuid);
        if (!Files.exists(data)) {
            Files.createFile(data);
            Files.write(data, ZERO, StandardOpenOption.TRUNCATE_EXISTING);
        }

        return data;
    }

    public static void write(String uuid, int value) throws IOException {
        Files.write(data(PLAYER_DIRECTORY.toFile(), uuid), String.valueOf(value).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }
}
