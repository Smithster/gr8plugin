package com.smithster.gr8plugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.classes.LobbyJoin;
import com.smithster.gr8plugin.classes.Team;
import com.smithster.gr8plugin.gamemodes.gamemode;

public class Profile {

    private Player player;
    private OfflinePlayer offlinePlayer;
    private Team team;
    private Arena arena;
    private gamemode gamemode;
    private String role;
    private Integer experience;
    private boolean settingJoin = false;
    private LobbyJoin join;
    private Set<String> permList;
    private PermissionAttachment perms;

    public static Plugin plugin;
    public static HashMap<UUID, Profile> profiles = new HashMap<UUID, Profile>();
    public static HashMap<UUID, Profile> offlineProfiles = new HashMap<UUID, Profile>();

    public Profile(Player player) {
        this.player = player;
        this.perms = this.player.addAttachment(plugin);
        this.role = "default";
    }

    public Profile(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.role = "default";
    }

    public static void setPlugin(Plugin p) {
        plugin = p;
    }

    public void save() {
        Document document = new Document();

        // Object permissions = (Object) this.perms.getPermissions();
        document.put("_id", this.player.getUniqueId().toString());
        if (this.perms == null) {
            document.put("perms", null);
        } else {
            // ArrayList<String> permList = new
            // ArrayList<String>(this.perms.getPermissions().keySet());
            document.put("perms", new ArrayList<String>(this.perms.getPermissions().keySet()));
        }
        if (this.role == null) {
            document.put("role", null);
        } else {
            document.put("role", this.role);
        }

        Data.save("profiles", document);
        return;
    }

    public static void load(Document document) {
        UUID playerID = UUID.fromString((String) document.get("_id"));
        OfflinePlayer offlinePlayer = Plugin.server.getOfflinePlayer(playerID);
        Profile profile = new Profile(offlinePlayer);
        profile.setOfflinePermList(new HashSet<String>());
        if (document.get("perms") != null) {
            Set<String> permsList = new HashSet<String>((ArrayList<String>) document.get("perms"));
            profile.setOfflinePermList(permsList);
        }
        if (document.get("role") != null) {
            profile.setRole((String) document.get("role"));
        }
        profiles.put(playerID, profile);
        return;
    }

    public void addPermission(String permission) {
        this.permList.add(permission);
        if (this.offlinePlayer.isOnline()) {
            this.perms.setPermission(permission, true);
        }
    }

    public void removePermission(String permission) {
        this.permList.remove(permission);
        if (this.offlinePlayer.isOnline()) {
            this.perms.unsetPermission(permission);
        }
    }

    public void setOfflinePermList(Set<String> permList) {
        this.permList = permList;
    }

    public Set<String> getOfflinePermList() {
        return this.permList;
    }

    public void setJoin(LobbyJoin join) {
        this.join = join;
    }

    public LobbyJoin getJoin() {
        return this.join;
    }

    public void settingJoin(boolean b) {
        this.settingJoin = b;
    }

    public boolean isSettingJoin() {
        return this.settingJoin;
    }

    // public void setTriggerName(String name) {
    // this.triggerName = name;
    // }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }

    public void handleKill(UUID killed) {
        if (this.gamemode.getType().equals("TDM")) {
            gamemode.gainPoint(team);
        }
    };

    public void setPlayer(Player player) {
        this.player = player;
        PermissionAttachment perms = player.addAttachment(plugin);

        for (String permission : this.permList) {
            perms.setPermission(permission, true);
        }

        this.perms = perms;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // AS OF YET NOT USED
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Arena getArena() {
        return this.arena;
    }

    public Player getPlayer() {
        return this.player;
    }

}
