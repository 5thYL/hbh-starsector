package data.missions.hbh_fate;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {

    @Override
    public void defineMission(MissionDefinitionAPI api) {
        // Set up the fleets
        api.initFleet(FleetSide.PLAYER, "BRS", FleetGoal.ATTACK, false);
        api.initFleet(FleetSide.ENEMY, "ISS", FleetGoal.ATTACK, true);

        // Set a small blurb for each fleet that shows up on the mission detail and
        // mission results screens to identify each side.
        api.setFleetTagline(FleetSide.PLAYER, "Fritillaria");
        api.setFleetTagline(FleetSide.ENEMY, "Death");

        // These show up as items in the bulleted list under
        // "Tactical Objectives" on the mission detail screen
        api.addBriefingItem("Face Death. Meet Fate.");

        // Set up the player's fleet
        api.addToFleet(FleetSide.PLAYER, "hbh_pod_main_seeker", FleetMemberType.SHIP, true);


        // Set up the enemy fleet
        api.addToFleet(FleetSide.ENEMY, "paragon_Elite", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "paragon_Escort", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "paragon_Raider", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "onslaught_Elite", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "onslaught_Outdated", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "onslaught_Standard", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "onslaught_xiv_Elite", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "conquest_Elite", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "conquest_Standard", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "astral_Attack", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "astral_Elite", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "astral_Strike", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "legion_Assault", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "legion_Escort", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "legion_FS", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "legion_Strike", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "legion_xiv_Elite", FleetMemberType.SHIP, false);


        // Set up the map.
        float width = 20000f;
        float height = 20000f;
        api.initMap(-width / 2f, width / 2f, -height / 2f, height / 2f);

        float minX = -width / 2;
        float minY = -height / 2;

        for (int i = 0; i < 50; i++) {
            float x = (float) Math.random() * width - width / 2;
            float y = (float) Math.random() * height - height / 2;
            float radius = 100f + (float) Math.random() * 400f;
            api.addNebula(x, y, radius);
        }

        // Add objectives
        api.addObjective(minX + width * 0.25f, minY + height * 0.25f, "nav_buoy");
        api.addObjective(minX + width * 0.75f, minY + height * 0.25f, "comm_relay");
        api.addObjective(minX + width * 0.75f, minY + height * 0.75f, "nav_buoy");
        api.addObjective(minX + width * 0.25f, minY + height * 0.75f, "comm_relay");
        api.addObjective(minX + width * 0.5f, minY + height * 0.5f, "sensor_array");
    }
}
