package data.scripts.campaign.intel.bar.events;;

import com.fs.starfarer.api.impl.campaign.intel.bar.PortsideBarEvent;
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventCreator;

public class hbh_bareventcreator extends BaseBarEventCreator {
    @Override
    public PortsideBarEvent createBarEvent() {
        return new hbh_barevent();
    }
}