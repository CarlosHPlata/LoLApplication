package com.kingskull.lolapplication.api.restfull.connections;

/**
 * Created by Usuario on 07/09/2015.
 */
public final class RIOT {

    public static final String END_POINT = "https://lan.api.pvp.net";
    public static final String PROFILE_ICONS_URL = "http://ddragon.leagueoflegends.com/cdn/5.17.1/img/profileicon/";



    /* ==================================================================================================================
     *
     *                                  MATCHMAKING GAME TYPES CONSTANTS
     *
     ====================================================================================================================*/


    public static final String CUSTOM_GAMES         = "CUSTOM",
                               NORMAL_3X3           = "NORMAL_3x3",
                               NORMAL_5X5           = "NORMAL_5x5_BLIND",
                               NORMAL_DRAFT         = "NORLAM_5x5_DRAFT",
                               SOLO_Q               = "RANKED_SOLO_5x5",
                               RANKED_PREMADE_      = "RANKED_PREMADE_5x5",
                               RANKED_PREMADE_3X3   = "RANKED_PREMADE_3x3",
                               TEAM_3X3             = "RANKED_TEAM_3x3",
                               TEAM_5X5             = "RANKED_TEAM_5x5",
                               DOMINION             = "ODIN_5x5_BLIND",
                               DOMINION_DRAFT       = "ODIN_5x5_DRAFT",
                               BOT_5X5_DEP          = "BOT_5x5",
                               DOMINION_BOT         = "BOT_ODIN_5x5",
                               INTRO                = "BOT_5x5_INTRO",
                               BOTS_BEGINNER        = "BOT_5x5_BEGINNER",
                               BOTS_INTERMEDIATE    = "BOT_5x5_INTERMEDIATE",
                               BOTS_TT_3X3          = "BOT_TT_3x3",
                               TEAM_BUILDER         = "GROUP_FINDER_5x5",
                               ARAM                 = "ARAM_5x5",
                               ONE_FOR_ALL          = "ONEFORALL_5x5",
                               SHOWDONW_1X1         = "FIRSTBLOOD_1x1",
                               SHOWDOWN_2X2         = "FIRSTBLOOD_2x2",
                               HEXAKILL_RIFT        = "SR_6x6",
                               URF_5X5              = "URF_5x5",
                               URF_BOT              = "BOT_URF_5x5",
                               DOOM_BOTS_1          = "NIGHTMARE_BOT_5x5_RANK1",
                               DOOM_BOTS_2          = "NIGHTMARE_BOT_5x5_RANK2",
                               DOOM_BOTS_5          = "NIGHTMARE_BOT_5x5_RANK5",
                               ASSENSION            = "ASCENSION_5x5",
                               HEXAKILL_TT          = "HEXAKILL",
                               BUTCHERS_BRIDGE_ARAM = "BILGEWATER_ARAM_5x5",
                               KING_PORO            = "KING_PORO_5x5",
                               NEMESIS              = "COUNTER_PICK",
                               BLACK_MARKET         = "BILGEWATER_5x5";





    /* ==================================================================================================================
     *
     *                                                      MAP NAMES
     *
     ====================================================================================================================*/

    private static final int ORIGINAL_SUMMONER_RIFT   = 1,
                             AUTUMN_SUMMONER_RIFT     = 2,
                             TUTORIAL_MAP             = 3,
                             ORIGINAL_TWISTED_TREELINE= 4,
                             CRYSTAL_SCAR             = 8,
                             TWISTED_TREELINE         = 10,
                             SUMMONER_RIFT            = 11,
                             HOWLING_ABYSS            = 12,
                             BUTCHERS_BRIDGE          = 14;




    /* ==================================================================================================================
     *
     *                                                  SERVER'S REGIONS
     *
     ====================================================================================================================*/

    public static final String REGION = "lan";

    public static final String API_KEY = "8988f1d2-dc95-4d0d-b4a9-3f2708fd3c8a";

    //seasons






    /* ==================================================================================================================
     *
     *                                                  LEAGUE TIERS
     *
     ====================================================================================================================*/
    public static final String  TIER_CHALLENGER = "CHALLENGER",
                                TIER_MASTER     = "MASTER",
                                TIER_DIAMOND    = "DIAMOND",
                                TIER_PLATINUM   = "PLATINUM",
                                TIER_GOLD       = "GOLD",
                                TIER_SILVER     = "SILVER",
                                TIER_BRONZE     = "BRONZE";

    //seassons
    public static final String SEASON_5 = "SEASON2015";
    public static final String SEASON_4 = "SEASON2014";
    public static final String SEASON_3 = "SEASON3";

}
