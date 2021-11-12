 package com.fs.starfarer.api.impl.campaign.missions;

 import com.fs.starfarer.api.Global;
 import com.fs.starfarer.api.campaign.CampaignFleetAPI;
 import com.fs.starfarer.api.campaign.InteractionDialogAPI;
 import com.fs.starfarer.api.campaign.econ.MarketAPI;
 import com.fs.starfarer.api.campaign.rules.MemoryAPI;
 import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.combat.ShipVariantAPI;
 import com.fs.starfarer.api.fleet.FleetMemberAPI;
 import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.ids.Voices;
 import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithBarEvent;
 import com.fs.starfarer.api.util.Misc;
 import com.fs.starfarer.api.util.Misc.Token;
 import java.util.List;
 import java.util.Map;
 
 public class FritillariaJoins extends HubMissionWithBarEvent {
    protected FleetMemberAPI member;
    protected int price = 100;
    protected PersonAPI frit;

    protected boolean create(MarketAPI createdAt, boolean barEvent) {

      this.setGiverPortrait(Global.getSettings().getSpriteName("characters", "hbh_0"));
      this.setGiverFaction("independent");
      this.setGiverPost(Ranks.POST_WARLORD);
      this.setGiverVoice(Voices.SPACER);
      this.setGiverImportance(this.pickLowImportance());
      this.findOrCreateGiver(createdAt, false, false);

      PersonAPI person = this.getPerson();
      person.getName().setFirst("Fritillaria");
      person.getName().setLast("The Cursed Knight");
      person.getRelToPlayer().setRel(100);
      person.setGender(Gender.FEMALE);
      person.setRankId(Ranks.KNIGHT_CAPTAIN);

      String variantId = "hbh_pod_main_seeker";
      ShipVariantAPI variant = Global.getSettings().getVariant(variantId).clone();
      this.member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, variant);
      this.assignShipName(this.member, "independent");
      this.member.getCrewComposition().setCrew(100000.0F);
      this.member.getRepairTracker().setCR(1.0F);
      this.setRepPersonChangesLow();
      return true;
    }
 
    @Override
    protected void updateInteractionDataImpl() {
       this.set("$hbh_ref2", this);
       this.set("$hbh_barEvent", this.isBarEvent());
       this.set("$hbh_hullClass", this.member.getHullSpec().getHullNameWithDashClass());
       this.set("$hbh_price", Misc.getWithDGS((float)this.price));
       this.set("$hbh_manOrWoman", this.getPerson().getManOrWoman());
       this.set("$hbh_hisOrHer", this.getPerson().getHisOrHer());
       this.set("$hbh_member", this.member);
       this.set("$hbh_frit", this.frit);
    }
 @Override
    
    protected boolean callAction(String action, String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
       if ("showShip".equals(action)) {
          dialog.getVisualPanel().showFleetMemberInfo(this.member, true);
          return true;
       } else if ("showPerson".equals(action)) {
          dialog.getVisualPanel().showPersonInfo(this.getPerson(), true);
          return true;
       } else {
          return false;
       }
    }

    protected PersonAPI createFrit() {
      PersonAPI person = Global.getFactory().createPerson();
      person.setFaction(Factions.INDEPENDENT);
      person.setGender(Gender.FEMALE);
      person.setPersonality("steady");
      person.getName().setFirst("Fritillaria");
      person.getName().setLast("The Cursed Knight");
      person.getRelToPlayer().setRel(100);
      person.setRankId(Ranks.KNIGHT_CAPTAIN);
      person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "hbh_0"));
      person.getStats().setLevel(10);
      person.getStats().setSkillLevel(Skills.HELMSMANSHIP, 2);
      person.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 2);
      person.getStats().setSkillLevel(Skills.POINT_DEFENSE, 2);
      person.getStats().setSkillLevel(Skills.IMPACT_MITIGATION, 2);
      person.getStats().setSkillLevel(Skills.RANGED_SPECIALIZATION, 2);
      person.getStats().setSkillLevel(Skills.SHIELD_MODULATION, 2);
      person.getStats().setSkillLevel(Skills.SYSTEMS_EXPERTISE, 2);
      person.getStats().setSkillLevel(Skills.GUNNERY_IMPLANTS, 2);
      person.getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 2);
      person.getStats().setSkillLevel(Skills.RELIABILITY_ENGINEERING, 2);
      return person;
      }
 
    @Override
    public String getBaseName() {
       return "Fritillaria Joins";
    }
 @Override
    
    public void accept(InteractionDialogAPI dialog, Map<String, MemoryAPI> memoryMap) {
      CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
        
      frit = createFrit();
      playerFleet.getFleetData().addOfficer(frit);
      this.currentStage = new Object();
      this.abort();
    }
 }
 