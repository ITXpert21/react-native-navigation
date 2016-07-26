package com.reactnativenavigation.views;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;

import com.reactnativenavigation.params.ScreenStyleParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.utils.ViewUtils;

import java.util.List;

public class TopBar extends AppBarLayout {

    private TitleBar titleBar;
    private TabLayout tabLayout;

    public TopBar(Context context) {
        super(context);
        setFitsSystemWindows(true);
        setId(ViewUtils.generateViewId());
    }

    public void addTitleBarAndSetButtons(List<TitleBarButtonParams> buttons, String navigatorEventId) {
        titleBar = new TitleBar(getContext());
        addView(titleBar);
        titleBar.setButtons(buttons, navigatorEventId);
    }

    public void setTitle(String title) {
        titleBar.setTitle(title);
    }

    public void setStyle(ScreenStyleParams styleParams) {
        if (styleParams.topBarColor.hasColor()) {
            setBackgroundColor(styleParams.topBarColor.getColor());
        }
        setVisibility(styleParams.topBarHidden ? GONE : VISIBLE);
        titleBar.setVisibility(styleParams.titleBarHidden ? GONE : VISIBLE);
    }

    public void setTitleBarButtons(String navigatorEventId, List<TitleBarButtonParams> titleBarButtons) {
        titleBar.setButtons(titleBarButtons, navigatorEventId);
    }

    public TabLayout initTabs() {
        tabLayout = new TabLayout(getContext());
        addView(tabLayout);
        return tabLayout;
    }
}
