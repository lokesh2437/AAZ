
package com.retailsols.dbutility;

public class QueryConstants {
    public static String getEmpSalt="SELECT EM.PW_SLT_EM,EM.PW_ACS_EM FROM PA_EM EM WHERE EM.ID_LOGIN=?";
    public static String getTicketDetails="SELECT TR.FL_SND,TR.TKT_START_DT,TR.TKT_END_DT,TR.SPL_INSTRC FROM TR_LTM_SLS_RTN TR WHERE TR.ID_NMB_SRZ=?";
//    public static String getTicketDetails1="select RT.FL_SND from TR_TRN TR INNER JOIN TR_LTM_SLS_RTN RT ON (TR.ID_STR_RT=RT.ID_STR_RT AND TR.ID_WS=RT.ID_WS AND TR.DC_DY_BSN=RT.DC_DY_BSN AND TR.AI_TRN=RT.AI_TRN) where TR.SC_TRN=2 AND TR.FL_TRG_TRN <> 1 AND TR.TY_TRN in (1)";
    public static String updateFlag="UPDATE TR_LTM_SLS_RTN TR SET TR.FL_SND='1' WHERE TR.ID_NMB_SRZ=?";
    public static String updateFlag1="UPDATE TR_LTM_SLS_RTN TR SET TR.FL_SND=? WHERE TR.ID_NMB_SRZ=?";
}
