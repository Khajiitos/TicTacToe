package me.Khajiitos.tictactoe;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new GameMechanic(), this);
		everysecloop();
	}
	
	public void everysecloop() {
		
		BukkitScheduler scheduler = Bukkit.getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				int i = 0;
				ArrayList<Integer> toRemove = new ArrayList<>();
				for (Invitation inv : TicTacToe.pendinginvitations) {
					if (inv.getExpiretime() < System.currentTimeMillis()) {					
						inv.getSender().sendMessage(ChatColor.YELLOW + "Your TicTacToe invite to " + inv.getTarget().getName() + " has expired.");
						inv.getTarget().sendMessage(ChatColor.YELLOW + "Your TicTacToe invite from " + inv.getSender().getName() + " has expired.");
						toRemove.add(i);
					}
					i++;
				}
				
				
				for (int in : toRemove) {
					TicTacToe.pendinginvitations.remove(in);
				}
				
				for (Game game : TicTacToe.games) {
					if (game.secondspassed >= 15) {
						game.secondspassed = 0;
						game.failedToPlace();
						continue;
					}
					game.secondspassed += 1;
				}
				
			}
			
		}, 20, 20);
	}
	
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	
    	if (command.getName().equalsIgnoreCase("ttt") || command.getName().equalsIgnoreCase("tictactoe")) {
    		
    		if (!(sender instanceof Player)) {
    			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
    			return false;
    		}
    		
    		Player pSender = (Player) sender;
    		
    		String cmdname = command.getName().toLowerCase();
    		
    		if (args.length == 0) {
				pSender.sendMessage(ChatColor.RED + "Usage: /" + cmdname + " <invite, accept, leave> <args...>");
    			return false;
    		}
    		
    		else if (args[0].equals("invite")) {
    			
    			if (args.length > 1) {
        			Player target = Bukkit.getPlayerExact(args[1]);
        			
        			if (target != null) {
        				
        				if (target.equals(pSender)) {
        					pSender.sendMessage(ChatColor.RED + "You can't invite yourself.");
        					return false;
        				}
        				
        				TicTacToe.sendInvitation(pSender, target);
        			} else {
        				pSender.sendMessage(ChatColor.RED + "Usage: /" + cmdname + " invite <target>");
        			}
    			} else {
    				pSender.sendMessage(ChatColor.RED + "Usage: /" + cmdname + " invite <target>");
    			}
    				
    		}
    		
    		else if (args[0].equals("accept")) {
    			if (args.length > 1) {
        			Player target = Bukkit.getPlayerExact(args[1]);
        			if (target != null) {
        				
        				if (TicTacToe.getGame(pSender) != null) {
        					pSender.sendMessage(ChatColor.RED + "You are already in a game!");
        					return false;
        				}
        				
        				Player invtarget = pSender;
        				Player invsender = target;
        				
        				int i = 0;
        				boolean found = false;
        				for (Invitation inv : TicTacToe.pendinginvitations) {
        					if (inv.getSender().equals(invsender) && inv.getTarget().equals(invtarget)) {
        						TicTacToe.startGame(invsender, invtarget);
        						found = true;
        						break;
        					}
        					i++;
        				}
        				
        				if (found) {
        					TicTacToe.pendinginvitations.remove(i);
        				} else {
        					pSender.sendMessage(ChatColor.RED + "You haven't been invited by this person!");
        				}
        				
        			} else {
        				pSender.sendMessage(ChatColor.RED + "This player is offline.");
        			}
    			} else {
    				pSender.sendMessage(ChatColor.RED + "Usage: /" + cmdname + " accept <inviter>");
    			}
    		}
    		
    		else if (args[0].equals("leave")) {
    			if (TicTacToe.isPlaying(pSender)) {
    				pSender.sendMessage(ChatColor.RED + "You left your" + ChatColor.DARK_RED + " TicTacToe" + ChatColor.RED + " game.");
    				TicTacToe.games.remove(TicTacToe.getGame(pSender));
    			} else {
    				pSender.sendMessage(ChatColor.RED + "You're not in a game.");
    			}
    		}
    		
    		else {
				pSender.sendMessage(ChatColor.RED + "Usage: /" + cmdname + " <invite, accept, leave> <args...>");
    		}
    		
    	}
    	
    	return false;
    }
}
