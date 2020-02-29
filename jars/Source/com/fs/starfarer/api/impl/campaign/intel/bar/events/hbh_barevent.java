package com.fs.starfarer.api.impl.campaign.intel.bar.events;

import java.util.Random;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent.AvailableOfficer;

public class hbh_barevent extends BaseBarEvent {
	protected PersonAPI person;
	protected long seed;
	protected MarketAPI market = null;
	protected transient Random random;
	
	public hbh_barevent() {
		seed = Misc.random.nextLong();
	}
	
	protected void regen(MarketAPI market) {
		if (this.market == market) return;
		this.market = market;
		
		random = new Random(seed + market.getId().hashCode());
		person = createPerson();
	}
	
	protected PersonAPI createPerson() {
                PersonAPI person = Global.getFactory().createPerson();
                person.setFaction(Factions.INDEPENDENT);
                person.setGender(Gender.FEMALE);
                person.setPersonality("steady");
                person.getName().setFirst("Fritillaria");
                person.getName().setLast("The Cursed Knight");
                person.getRelToPlayer().setRel(100);
                person.setRankId(Ranks.SPACE_CAPTAIN);
                person.hasTag("hbh");
                person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "hbh_0"));
                person.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 3);
                person.getStats().setSkillLevel(Skills.EVASIVE_ACTION, 3);
                person.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 3);
                person.getStats().setSkillLevel(Skills.ORDNANCE_EXPERT, 3);
                person.getStats().setSkillLevel(Skills.FLUX_MODULATION, 3);
                person.getStats().setSkillLevel(Skills.HELMSMANSHIP, 2);
		return person;
	}
	
	protected String getPersonPortrait() {
		return Global.getSettings().getSpriteName("characters", "hbh_0");
	}
	
	protected String getPersonFaction() {
		return Factions.INDEPENDENT;
	}
	protected String getPersonRank() {
		return Ranks.SPACE_CAPTAIN;
	}
	protected Gender getPersonGender() {
		return Gender.FEMALE;
	}
	protected String getPersonPost() {
		return Ranks.SPACE_CAPTAIN;
	}
	
	protected String getManOrWoman() {
		String manOrWoman = "man";
		if (person.getGender() == Gender.FEMALE) manOrWoman = "woman";
		return manOrWoman;
	}
	
	protected String getHeOrShe() {
		String heOrShe = "he";
		if (person.getGender() == Gender.FEMALE) {
			heOrShe = "she";
		}
		return heOrShe;
	}
	
	protected String getHimOrHer() {
		String himOrHer = "him";
		if (person.getGender() == Gender.FEMALE) {
			himOrHer = "her";
		}
		return himOrHer;
	}
	
	protected String getHimOrHerself() {
		String himOrHer = "himself";
		if (person.getGender() == Gender.FEMALE) {
			himOrHer = "herself";
		}
		return himOrHer;
	}
	
	protected String getHisOrHer() {
		String hisOrHer = "his";
		if (person.getGender() == Gender.FEMALE) {
			hisOrHer = "her";
		}
		return hisOrHer;
	}

	@Override
	public boolean isDialogFinished() {
		return done;
	}

	public PersonAPI getPerson() {
		return person;
	}

	public MarketAPI getMarket() {
		return market;
	}

	public Random getRandom() {
		return random;
	}
    /**
     * True if this event may be selected to be offered to the player,
     * or false otherwise.
     */
    public boolean shouldShowAtMarket(MarketAPI market) {
        return true; // add any conditions you want
    }

    /**
     * Set up the text that appears when the player goes to the bar
     * and the option for them to start the conversation.
     */
    @Override
    public void addPromptAndOption(InteractionDialogAPI dialog) {
        // Calling super does nothing in this case, but is good practice because a subclass should
        // implement all functionality of the superclass (and usually more)
        super.addPromptAndOption(dialog);
        regen(dialog.getInteractionTarget().getMarket()); // Sets field variables and creates a random person

        // Display the text that will appear when the player first enters the bar and looks around
        dialog.getTextPanel().addPara("You also find your dream Waifu, Fritilllaria waiting for you.");

        // Display the option that lets the player choose to investigate our bar event
        dialog.getOptionPanel().addOption("Go to your Waifu.", this);
    }

    /**
     * Called when the player chooses this event from the list of options shown when they enter the bar.
     */
    @Override
    public void init(InteractionDialogAPI dialog) {
        super.init(dialog);
        // Choose where the player has to travel to

        // If player starts our event, then backs out of it, `done` will be set to true.
        // If they then start the event again without leaving the bar, we should reset `done` to false.
        done = false;

        // The boolean is for whether to show only minimal person information. True == minimal
        dialog.getVisualPanel().showPersonInfo(person, true);

        // Launch into our event by triggering the "INIT" option, which will call `optionSelected()`
        this.optionSelected(null, OptionId.INIT);
    }

    /**
     * This method is called when the player has selected some option for our bar event.
     *
     * @param optionText the actual text that was displayed on the selected option
     * @param optionData the value used to uniquely identify the option
     */
    @Override
    public void optionSelected(String optionText, Object optionData) {
        if (optionData instanceof OptionId) {
            // Clear shown options before we show new ones
            dialog.getOptionPanel().clearOptions();

            // Handle all possible options the player can choose
            switch ((OptionId) optionData) {
                case INIT:
                    // The player has chosen to walk over to the crowd, so let's tell them what happens.
                    dialog.getTextPanel().addPara("You walk over and is quickly embraced by your Waifu. She asks to join you..");

                    // And give them some options on what to do next
                    dialog.getOptionPanel().addOption("Please do!", OptionId.TAKE_NOTES);
                    dialog.getOptionPanel().addOption("Not yet.", OptionId.LEAVE);
                    break;
                case TAKE_NOTES:
CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
playerFleet.getFleetData().addOfficer(person);
playerFleet.getFleetData().addFleetMember("hbh_pod_main_seeker");

                    dialog.getTextPanel().addPara("Your Waifu joins you, along with her Bladeship, the Seeker.");
                    dialog.getOptionPanel().addOption("Leave", OptionId.LEAVE);
                    break;
                case LEAVE:
                    // They've chosen to leave, so end our interaction. This will send them back to the bar.
                    // If noContinue is false, then there will be an additional "Continue" option shown
                    // before they are returned to the bar. We don't need that.
                    noContinue = true;
                    done = true;

                    // Removes this event from the bar so it isn't offered again
                    BarEventManager.getInstance().notifyWasInteractedWith(this);
                    break;
            }
        }
    }

    enum OptionId {
        INIT,
        TAKE_NOTES,
        LEAVE
    }
    /*
    public void advance(float amount) {
        if (hbh_generated == false) {
            AvailableOfficer officer = createOfficer();
            OfficerDataAPI officerData = Global.getFactory().createOfficerData(person);
            MarketAPI hbh_market = null;
            hbh_market = Global.getSector().getEconomy().getMarket("jangala");
            if (hbh_market != null) {
                PersonAPI person = Global.getFactory().createPerson();
                person.setFaction(Factions.INDEPENDENT);
                person.setGender(Gender.FEMALE);
                person.setPersonality("steady");
                person.getName().setFirst("Fritillaria");
                person.getName().setLast("The Cursed Knight");
                person.getRelToPlayer().setRel(100);
                person.setRankId(Ranks.SPACE_CAPTAIN);
                person.hasTag("hbh");
                person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "hbh_0"));
                person.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 3);
                person.getStats().setSkillLevel(Skills.EVASIVE_ACTION, 3);
                person.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 3);
                person.getStats().setSkillLevel(Skills.ORDNANCE_EXPERT, 3);
                person.getStats().setSkillLevel(Skills.FLUX_MODULATION, 3);

                person.getMemoryWithoutUpdate().set("$ome_hireable", true);
                hbh_market.getCommDirectory().addPerson(person, 0);
                hbh_market.addPerson(person);
                hbh_generated = true;
            }
        }
    }*/
}
