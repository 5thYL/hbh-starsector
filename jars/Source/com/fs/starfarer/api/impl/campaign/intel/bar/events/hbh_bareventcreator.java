package com.fs.starfarer.api.impl.campaign.intel.bar.events;

import com.fs.starfarer.api.impl.campaign.intel.bar.PortsideBarEvent;

public class hbh_bareventcreator extends BaseBarEventCreator {
    @Override
    public PortsideBarEvent createBarEvent() {
        return new hbh_barevent();
    }
}