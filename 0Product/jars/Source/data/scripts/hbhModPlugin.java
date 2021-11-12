package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;

public class hbhModPlugin extends BaseModPlugin {

    /*
     * Called when the player loads a saved game.
     *
     * @param newGame true if the save game was just created for the first time.
     *                Note that there are a few `onGameLoad` methods that may be a better choice than using this parameter
     */
    @Override
    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);
    //If the prerequisites for the quest have been met (optional) and the game isn't already aware of the bar event,
    //add it to the BarEventManager so that it shows up in bars

    }
}