package dev.jkopecky.titanstrials.data;

import net.minecraft.nbt.CompoundTag;

public class DifficultyCapability {

    private int difficulty = 0;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void copyFrom(DifficultyCapability source) {
        this.difficulty = source.getDifficulty();
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("titanstrials_difficulty", this.difficulty);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.difficulty = nbt.getInt("titanstrials_difficulty");
    }
}
