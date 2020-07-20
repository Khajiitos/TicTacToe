package me.Khajiitos.tictactoe;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TicTacToe {
	
	public static ArrayList<Game> games = new ArrayList<>();
	
	public static ArrayList<Invitation> pendinginvitations = new ArrayList<>();
	
	public static boolean isPlaying(Player player) {
		return getGame(player) != null;
	}
	
	public static void startGame(Player inviter, Player invited) {
		Game game = new Game(inviter, invited);
		games.add(game);
		printGame(inviter, game);
		printGame(invited, game);
		
		inviter.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "It's your turn!");
		invited.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "It's your opponent's turn!");
	}
	
	public static int getSide(Player player) {
		if (isPlaying(player)) {
			if (getGame(player).getInviter().equals(player)) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return 0;
		}
	}
	
	public static void sendInvitation(Player player, Player target) {
		for (Invitation inv : pendinginvitations) {
			if (inv.getSender().equals(player) && inv.getTarget().equals(target)) {
				player.sendMessage(ChatColor.RED + "You have already invited this person.");
				return;
			}
		}
		Invitation invitation = new Invitation(player, target, System.currentTimeMillis() + 60000);
		player.sendMessage(ChatColor.YELLOW + "Invited player " + ChatColor.AQUA + target.getName() + ChatColor.YELLOW + " to a TicTacToe game! They have" + ChatColor.AQUA + " 60 seconds" + ChatColor.YELLOW + " to accept.");
		target.sendMessage("");
		target.sendMessage(ChatColor.YELLOW + "You have been invited to a TicTacToe game by " + ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "! Type '/ttt accept " + ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "' to accept.");
		target.sendMessage("");
		pendinginvitations.add(invitation);
	}
	
	public static String getCharacter(int score) {
		switch (score) {
		case 0: return "-";
		case 1: return "X";
		case 2: return "O";
		default: return "?";
		}
	}
	
	public static void printGame(Player player, Game game) {
		player.sendMessage("");
		player.sendMessage("     |     |     ");
		player.sendMessage("  " + getCharacter(game.getScore("a1")) + "  |  " + getCharacter(game.getScore("a2")) + " |  " + getCharacter(game.getScore("a3")) + "  ");
		player.sendMessage(" ___|___|___");
		player.sendMessage("     |     |     ");
		player.sendMessage("  " + getCharacter(game.getScore("b1")) + "  |  " + getCharacter(game.getScore("b2")) + " |  " + getCharacter(game.getScore("b3")) + "  ");
		player.sendMessage(" ___|___|___");
		player.sendMessage("     |     |     ");
		player.sendMessage("  " + getCharacter(game.getScore("c1")) + "  |  " + getCharacter(game.getScore("c2")) + " |  " + getCharacter(game.getScore("c3")) + "  ");
		player.sendMessage("     |     |     ");
		
	}
	
	public static Game getGame(Player player) {
		for (Game g : games) {
			if (g.getInviter().equals(player) || g.getInvited().equals(player)) {
				return g;
			}
		}
		return null;
	}
}
