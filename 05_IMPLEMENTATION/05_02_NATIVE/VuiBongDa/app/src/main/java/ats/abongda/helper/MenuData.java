package ats.abongda.helper;

import java.util.ArrayList;

import ats.abongda.R;
import ats.abongda.model.MenuModel;

/**
 * Created by NienLe on 04-Aug-16.
 */
public class MenuData {
    public static final ArrayList<MenuModel> menuData = new ArrayList<MenuModel>(){{
        add(new MenuModel(
                "Tài khoản",
                R.drawable.menuitem_tk,
                Constant.MENUGROUP_CODE,
                new ArrayList<MenuModel>(){{
                    add(new MenuModel("Hồ sơ",R.drawable.menuitem_hs, Constant.TK_HOSO_CODE));
                    add(new MenuModel("Lịch sử tài khoản", R.drawable.account_history, Constant.TK_LICHSU_CODE));
                    add(new MenuModel("Lịch sử đặt cược", R.drawable.menuitem_dc, Constant.TK_DATCUOC_CODE));
                    add(new MenuModel("Hộp thư", R.drawable.email, Constant.HOPTHUFRAGMENT));
                }}));
        add(new MenuModel(
                "Live score",
                R.drawable.menuitem_ls,
                Constant.LIVESCORE_CODE,
                new ArrayList<MenuModel>(){{
					/*add(new MenuModel("Live score",R.drawable.menuitem_ls, Constant.LIVESCORE_CODE));
					add(new MenuModel("Tin tức", R.drawable.menuitem_tt, Constant.TINTUC_CODE));
					add(new MenuModel("24h tới", R.drawable.menuitem_24h, Constant.NEXT24H_CODE));
					add(new MenuModel("Giải thưởng", R.drawable.menuitem_gt, Constant.GIAITHUONG_CODE));*/
                }}));
        add(new MenuModel(
                "Tin tức",
                R.drawable.menuitem_tt,
                Constant.TINTUC_CODE,
                new ArrayList<MenuModel>(){{
					/*add(new MenuModel("Live score",R.drawable.menuitem_ls, Constant.LIVESCORE_CODE));
					add(new MenuModel("Tin tức", R.drawable.menuitem_tt, Constant.TINTUC_CODE));
					add(new MenuModel("24h tới", R.drawable.menuitem_24h, Constant.NEXT24H_CODE));
					add(new MenuModel("Giải thưởng", R.drawable.menuitem_gt, Constant.GIAITHUONG_CODE));*/
                }}));

        add(new MenuModel(
                "Thông tin giải đấu",
                R.drawable.menuitem_ttgd,
                Constant.MENUGROUP_CODE,
                new ArrayList<MenuModel>(){{
                    add(new MenuModel("Premier League",R.drawable.league_pl, Constant.LEAGUE_PL_CODE));
                    add(new MenuModel("Primera Liga",R.drawable.league_lfp, Constant.LEAGUE_LFP_CODE));
                    add(new MenuModel("Serie A",R.drawable.league_seriea, Constant.LEAGUE_SA_CODE));
                    add(new MenuModel("Bundesliga",R.drawable.league_bundesliga, Constant.LEAGUE_BDL_CODE));
                    add(new MenuModel("League One",R.drawable.league_l1, Constant.LEAGUE_L1_CODE));
                    add(new MenuModel("Champion League",R.drawable.league_cl, Constant.LEAGUE_CL_CODE));
                    add(new MenuModel("Europa League",R.drawable.league_el, Constant.LEAGUE_EL_CODE));
                }}));
        add(new MenuModel(
                "Đặt cược 24h tới",
                R.drawable.menuitem_24h,
                Constant.NEXT24H_CODE,
                new ArrayList<MenuModel>(){{
					/*add(new MenuModel("Live score",R.drawable.menuitem_ls, Constant.LIVESCORE_CODE));
					add(new MenuModel("Tin tức", R.drawable.menuitem_tt, Constant.TINTUC_CODE));
					add(new MenuModel("24h tới", R.drawable.menuitem_24h, Constant.NEXT24H_CODE));
					add(new MenuModel("Giải thưởng", R.drawable.menuitem_gt, Constant.GIAITHUONG_CODE));*/
                }}));
        add(new MenuModel(
                "Bảng xếp hạng",
                R.drawable.xephang,
                Constant.MENUGROUP_CODE,
                new ArrayList<MenuModel>(){{
                    add(new MenuModel("Top đại gia",R.drawable.menuitem_tdg, Constant.TOPDAIGIA_CODE));
                    add(new MenuModel("Top cao thủ", R.drawable.menuitem_tct, Constant.TOPCAOTHU_CODE));
                }}));
    }};
}
