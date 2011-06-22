package me.ic3d.eco.Command;

import java.util.logging.Logger;

import me.ic3d.eco.ECOP;
import me.ic3d.eco.ECO;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ECOCMD implements CommandExecutor {
  private final ECO plugin;

  public ECOCMD(ECO instance) {
    plugin = instance;
  }
  
  private static final Logger log = Logger.getLogger("Minecraft");

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	  if(!(sender instanceof Player)) {
		  log.info("[3co] Console is not supported in v1.0");
		  return true;
	  }
	  Player player = (Player) sender;
	  if(args.length == 0) {
		  player.sendMessage(ChatColor.GREEN + "== 3co ==");
		  player.sendMessage("Commands available to you:");
		  if(plugin.hasPerm(player, "give")) {
			  player.sendMessage("/eco give <player> <amount>");
		  }
		  if(plugin.hasPerm(player, "take")) {
			  player.sendMessage("/eco take <player> <amount>");
		  }
		  if(plugin.hasPerm(player, "set")) {
			  player.sendMessage("/eco set <player> <amount>");
		  }
		  if(plugin.hasPerm(player, "show")) {
			  player.sendMessage("/eco show <player>");
		  }
		  if(plugin.hasPerm(player, "pay")) {
			  player.sendMessage("/eco pay <player> <amount>");
		  }
		  return true;
	  }
	  String arg = args[0];
	  if(arg.equals("give")) {
		  if(!plugin.hasPerm(player, "give")) {
			  player.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			  return true;
		  }
		  if(args.length == 1) {
			  player.sendMessage(ChatColor.GREEN + "/eco give <player> <amount>");
		  }
		  if(args.length == 3) {
			  String tn = args[1];
			  Player user = plugin.getServer().getPlayer(tn);
			  Integer amount = Integer.parseInt(args[2]);
			  plugin.giveMoney(user, amount);
			  if(amount > 1) {
				  user.sendMessage(ChatColor.GREEN + amount.toString() + " " + plugin.pluralCurrency + " has been credited to your account");
				  player.sendMessage(ChatColor.GREEN + "You gave " + user.getDisplayName() + " " + amount.toString() + " " + plugin.pluralCurrency);
			  }
			  if(amount == 1) {
				  user.sendMessage(ChatColor.GREEN + amount.toString() + " " + plugin.singularCurrency + " has been credited to your account");
				  player.sendMessage(ChatColor.GREEN + "You gave " + user.getDisplayName() + " " + amount.toString() + " " + plugin.singularCurrency);
			  }
		  }
		  return true;
	  }
	  if(arg.equals("take")) {
		  if(!plugin.hasPerm(player, "take")) {
			  player.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			  return true;
		  }
		  if(args.length == 1) {
			  player.sendMessage(ChatColor.GREEN + "/eco take <player> <amount>");
		  }
		  if(args.length == 3) {
			  String tn = args[1];
			  Player user = plugin.getServer().getPlayer(tn);
			  Integer amount = Integer.parseInt(args[2]);
			  plugin.takeMoney(user, amount);
			  if(amount > 1) {
				  user.sendMessage(ChatColor.GREEN + amount.toString() + " " + plugin.pluralCurrency + " has been withdrawn from your account");
				  player.sendMessage(ChatColor.GREEN + "You took " + amount.toString() + " " + plugin.pluralCurrency + " from " + user.getDisplayName());
			  }
			  if(amount == 1) {
				  user.sendMessage(ChatColor.GREEN + amount.toString() + plugin.singularCurrency + " has been withdrawn from your account");
				  player.sendMessage(ChatColor.GREEN + "You took " + amount.toString() + " " + plugin.singularCurrency + " from " + user.getDisplayName());
			  }
		  }
		  return true;
	  }
	  if(arg.equals("set")) {
		  if(!plugin.hasPerm(player, "set")) {
			  player.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			  return true;
		  }
		  if(args.length == 1) {
			  player.sendMessage(ChatColor.GREEN + "/eco set <player> <amount>");
		  }
		  if(args.length == 3) {
			  String tn = args[1];
			  Player user = plugin.getServer().getPlayer(tn);
			  Integer amount = Integer.parseInt(args[2]);
			  plugin.setMoney(user, amount);
			  if(amount > 1) {
				  user.sendMessage(ChatColor.GREEN + "Your account balance is now " + amount.toString() + " " + plugin.pluralCurrency);
			  }
			  if(amount == 1) {
				  user.sendMessage(ChatColor.GREEN + "Your account balance is now " + amount.toString() + "" + plugin.singularCurrency);
				  player.sendMessage(ChatColor.GREEN + user.getDisplayName() + "'s account balance is now " + amount.toString() + " " + plugin.singularCurrency);
			  }
		  }
		  return true;
	  }
	  if(arg.equals("show")) {
		  if(args.length == 1) {
			  Integer money = plugin.getMoney(player);
			  if(money > 1) {
				  player.sendMessage(ChatColor.GREEN + "Your account balance is " + money.toString() + " " + plugin.pluralCurrency);
				  }	
				  if(money == 1) {
					  player.sendMessage(ChatColor.GREEN + "Your account balance is " + money.toString() + " " + plugin.singularCurrency);
				  }	
		  }
		  if(args.length == 2) {
			  String tname = args[1];
			  Player user = plugin.getServer().getPlayer(tname);
			  Integer money = plugin.getMoney(user);
			  if(money > 1) {
			  player.sendMessage(ChatColor.GREEN + user.getDisplayName() + "'s account balance is " + money.toString() + " " + plugin.pluralCurrency);
			  }	
			  if(money == 1) {
				  player.sendMessage(ChatColor.GREEN + user.getDisplayName() + "'s account balance is " + money.toString() + " " + plugin.singularCurrency);
			  }	
		  }
		  return true;
	  }
	  if(arg.equals("pay")) {
		  if(!plugin.hasPerm(player, "pay") == true) {
			  player.sendMessage(ChatColor.RED + "You don't have permission to do that");
			  return true;
		  }
		  if(args.length == 1) {
			  player.sendMessage(ChatColor.GREEN + "/eco pay <player> <amount>");
		  }
		  if(args.length == 2) {
			  player.sendMessage(ChatColor.GREEN + "/eco pay <player> <amount>");
		  }
		  if(args.length == 3) {
			  String rname = args[1];
			  Player to = plugin.getServer().getPlayer(rname);
			  Integer amount = Integer.parseInt(args[2]);
			  if(!plugin.hasEnough(player, amount) == true) {
				  player.sendMessage(ChatColor.RED + "You don't have enough money to do that!");
				  return true;
			  }
			  plugin.transferMoney(player, to, amount);
			  Integer newMoney = plugin.getMoney(player);
			  player.sendMessage(ChatColor.GREEN + "Your account balance is now " + newMoney.toString() + " " + plugin.pluralCurrency);
			  to.sendMessage(ChatColor.GREEN + player.getDisplayName() + " has paid you " + amount.toString() + " " + plugin.pluralCurrency);
		  }
		  
	  }
	  return true;
  }
}