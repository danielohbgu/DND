package UI;
import Business.Player;
import Service.GameManager;
import Service.TileFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class CLI {
    public static void main(String[] args){
        // print available player selections
        TileFactory tf = new TileFactory();
        int i = 1;
        List<Player> playerList = tf.listPlayers();
        for (Player player : playerList) {
            System.out.println(i + ". " + player.describe());
            i++;
        }

        // read player selection
        Scanner s = new Scanner(System.in);
        boolean selectedPlayer = false;
        int playerSelection = 0;
        while(!selectedPlayer) {
            try {
                playerSelection = Integer.parseInt(s.next()) - 1;
                System.out.println("Selected " + playerList.get(playerSelection).getName());
                selectedPlayer = true;
            }
            catch (NumberFormatException ex) {
                System.out.println("Please input a NUMBER");
            }
            catch (IndexOutOfBoundsException ex){
                System.out.println("Please select a valid player (invalid player index selected)");
            }
        }

        // TODO: read real path from args here
        String pathToLevels = System.getProperty("user.dir") + "\\levels";

        GameManager gameManager = new GameManager(playerSelection, pathToLevels);
        gameManager.playGame();
    }
}
