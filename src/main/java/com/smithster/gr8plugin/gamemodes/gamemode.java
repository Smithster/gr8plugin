package com.smithster.gr8plugin.gamemodes;

import com.smithster.gr8plugin.classes.Team;

public interface gamemode {
    public boolean winCondition();

    public void gainPoint(Team team);
}
