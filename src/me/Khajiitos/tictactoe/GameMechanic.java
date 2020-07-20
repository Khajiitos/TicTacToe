package me.Khajiitos.tictactoe;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameMechanic implements Listener {
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent event) {
		Player sender = event.getPlayer();
		if (TicTacToe.isPlaying(sender)) {
			Game game = TicTacToe.getGame(sender);
			switch (event.getMessage().toLowerCase()) {
			case "a1":
			case "a2":
			case "a3":
			case "b1":
			case "b2":
			case "b3":
			case "c1":
			case "c2":
			case "c3":
				if (TicTacToe.getSide(sender) == game.getTurn()) {
					if (game.getScore(event.getMessage()) == 0) {
						game.setScore(event.getMessage(), TicTacToe.getSide(sender));
						game.onScorePlaced();
					} else {
						sender.sendMessage(ChatColor.RED + "This slot is already occupied.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "It's not your turn.");
				}
				event.setCancelled(true);
			}
		}
	}
	
	public static int checkForWinner(Game game) {
		
		// 0 - winner undefined yet
		// 1 - inviter won
		// 2 - invited won
		// 3 - it's a draw
		
		int a1 = game.a1;
		int a2 = game.a2;
		int a3 = game.a3;
		
		int b1 = game.b1;
		int b2 = game.b2;
		int b3 = game.b3;
		
		int c1 = game.c1;
		int c2 = game.c2;
		int c3 = game.c3;
		
		if (a1 != 0 && a1 == a2 && a2 == a3) {
			return a1;
		}
		
		if (b1 != 0 && b1 == b2 && b2 == b3) {
			return b1;
		}
		
		if (c1 != 0 && c1 == c2 && c2 == c3) {
			return c1;
		}
		
		if (a1 != 0 && a1 == b1 && b1 == c1) {
			return a1;
		}
		
		if (a2 != 0 && a2 == b2 && b2 == c2) {
			return a2;
		}
		
		if (a3 != 0 && a3 == b3 && b3 == c3) {
			return a3;
		}
		
		if (a1 != 0 && a1 == b2 && b2 == c3) {
			return a1;
		}
		
		if (a3 != 0 && a3 == b2 && b2 == c1) {
			return a3;
		}
		
		if (a1 != 0 && a2 != 0 && a3 != 0
			&& b1 != 0 && b2 != 0 && b3 != 0
			&& c1 != 0 && c2 != 0 && c3 != 0) {
			return 3;
		}
		
		
		return 0;
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (TicTacToe.isPlaying(player)) {
			Game game = TicTacToe.getGame(player);
			game.getInvited().sendMessage(ChatColor.RED + "Your opponent has left the game.");
			game.getInviter().sendMessage(ChatColor.RED + "Your opponent has left the game.");
			TicTacToe.games.remove(game);
		}
	}
	
}
