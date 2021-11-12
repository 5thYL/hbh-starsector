package data.scripts.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShieldAPI;

public class hbh_parryshield extends BaseHullMod {

    public static final float SHIELD_UPKEEP_STEP = 100f;
    public static final float SHIELD_BONUS_TURN = 500f;
    public static final float SHIELD_BONUS_UNFOLD = 2000f;
    public static final float SHIELD_BONUS = 40f;
    public static final float INCREASE_INTERVAL = 3f;
    public static final float DECREASE_INTERVAL = 3f;
    public float counter = 0f;
    public float multiplier = 0f;

    protected Object STATUSKEY1 = new Object();

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getShieldDamageTakenMult().modifyMult(id, 1f - SHIELD_BONUS * 0.01f);
        stats.getShieldTurnRateMult().modifyPercent(id, SHIELD_BONUS_TURN);
        stats.getShieldUnfoldRateMult().modifyPercent(id, SHIELD_BONUS_UNFOLD);
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return "" + (int) SHIELD_BONUS + "%";
        }
        if (index == 1) {
            return "" + (int) SHIELD_BONUS_TURN + "%";
        }
        if (index == 2) {
            return "" + (int) SHIELD_BONUS_UNFOLD + "%";
        }
        if (index == 3) {
            return "" + (int) SHIELD_UPKEEP_STEP + "%";
        }
        if (index == 4) {
            return "" + (int) INCREASE_INTERVAL;
        }
        if (index == 5) {
            return "" + (int) DECREASE_INTERVAL;
        }

        return null;
    }

    @Override
    public void advanceInCombat(ShipAPI Ship, float amount) {
        MutableShipStatsAPI stats = Ship.getMutableStats();
        ShieldAPI shield = Ship.getShield();
        String id = Ship.getId();
        if (shield.isOn()) {
            counter += 1 / INCREASE_INTERVAL;
            multiplier = counter * counter / 36;
        }
        if (shield.isOff()) {
            counter -= 1 / DECREASE_INTERVAL;
            if(counter < 0) counter = 0;
            multiplier = counter * counter / 36;
        }
            stats.getShieldUpkeepMult().modifyPercent(id, 1f + SHIELD_UPKEEP_STEP * multiplier * 0.01f);

        Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY1,
                "graphics\\icons\\hullsys\\damper_field.png", "Parry Shield",
                "Current shield upkeep multiplier:" + stats.getShieldUpkeepMult().getModifiedValue(), false);
    }

    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        return ship != null && ship.getShield() != null;
    }
@Override
    
    public String getUnapplicableReason(ShipAPI ship) {
        return "Ship has no shields";
    }
}
