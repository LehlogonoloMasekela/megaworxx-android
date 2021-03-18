package com.doosy.megaworxx.service.listener;

import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;

public interface AdapterListener {
    public CampaignModel getCampaignModel();
    public Campaign getCampaign();
}
