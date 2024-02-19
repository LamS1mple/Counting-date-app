package com.example.countingdate.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MemoryWithImage {
    @Embedded
    private Memory memory;
    @Relation(
            parentColumn = "memoryId",
            entityColumn = "memoryToId",
            entity = ImageMemory.class
    )
    private List<ImageMemory> imageMemories;

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public List<ImageMemory> getImageMemories() {
        return imageMemories;
    }

    public void setImageMemories(List<ImageMemory> imageMemories) {
        this.imageMemories = imageMemories;
    }
}
