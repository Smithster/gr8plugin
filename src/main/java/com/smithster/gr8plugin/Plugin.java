package com.smithster.gr8plugin;

import com.smithster.gr8plugin.classes.*;
import com.smithster.gr8plugin.commands.*;
import com.smithster.gr8plugin.handlers.*;
import com.smithster.gr8plugin.utils.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*
 * gr8plugin java plugin
 */
public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("gr8plugin");

  // public static MongoCollection<Document> playersCollection =
  // database.getCollection("playerStates");

  public static HashMap<String, Arena> arenas;
  // public static HashMap<String, LobbyJoin> lobbyJoins;
  public static HashMap<UUID, Profile> profiles;
  // public static HashMap<String, Player> players;
  // public static HashMap<String, Spawn> spawns;

  public static Server server;

  public void onEnable() {
    server = getServer();

    this.getCommand("arena").setExecutor(new arena());
    // this.getCommand("plot").setExecutor(new plotcmd());
    // this.getCommand("createSpawn").setExecutor(new createSpawn());
    this.getServer().getPluginManager().registerEvents(new plotBreakProtection(this), this);
    this.getServer().getPluginManager().registerEvents(null, null);
  }

  public void onDisable() {
    LOGGER.info("gr8plugin disabled");
  }

}
