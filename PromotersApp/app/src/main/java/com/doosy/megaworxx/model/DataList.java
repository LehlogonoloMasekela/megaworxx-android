package com.doosy.megaworxx.model;

import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.entity.Message;
import com.doosy.megaworxx.entity.Survey;

import java.util.ArrayList;
import java.util.List;

public class DataList {

    public static List<Survey> getSurveys(){
        List<Survey> surveys = new ArrayList<>();

        surveys.add(new Survey("1", "Calvin", "Masindi"));
        surveys.add(new Survey("2", "Calvin", "Masindi"));
        surveys.add(new Survey("3", "Calvin", "Masindi"));
        surveys.add(new Survey("4", "Calvin", "Masindi"));
        return surveys;
    }

    public static List<CampaignModel> getCampaigns(){
        List<CampaignModel> campaignModels = new ArrayList<>();

        campaignModels.add(new CampaignModel("1", "Samsung J21 Promo at Menlyn", "01/02/2021"));
        campaignModels.add(new CampaignModel("2", "Samsung J21 Promo at Menlyn", "01/02/2021"));
        campaignModels.add(new CampaignModel("3", "Samsung J21 Promo at Menlyn", "01/02/2021"));
        campaignModels.add(new CampaignModel("4", "Samsung J21 Promo at Menlyn", "01/02/2021"));
        campaignModels.add(new CampaignModel("5", "Samsung J21 Promo at Menlyn", "01/02/2021"));
        campaignModels.add(new CampaignModel("6", "Samsung J21 Promo at Menlyn", "01/02/2021"));
        return campaignModels;
    }

    public static List<Feedback> getFeedbacks(){
        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback("1", "Calvin", "Masindi"));
        feedbacks.add(new Feedback("2", "Calvin", "Masindi"));
        feedbacks.add(new Feedback("3", "Calvin", "Masindi"));
        return feedbacks;
    }

    public static List<Message> getMessages(){
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("20/02/2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Diam phasellus vestibulum lorem sed risus ultricies tristique. Ultricies mi quis hendrerit dolor magna."));
        messages.add(new Message("21/01/2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Diam phasellus vestibulum lorem sed risus ultricies tristique. Ultricies mi quis hendrerit dolor magna."));
        messages.add(new Message("25/02/2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Diam phasellus vestibulum lorem sed risus ultricies tristique. Ultricies mi quis hendrerit dolor magna."));
        messages.add(new Message("20/01/2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Diam phasellus vestibulum lorem sed risus ultricies tristique. Ultricies mi quis hendrerit dolor magna."));
        messages.add(new Message("20/11/2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Diam phasellus vestibulum lorem sed risus ultricies tristique. Ultricies mi quis hendrerit dolor magna."));
        return messages;
    }


}
