package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.reactnativenavigation.animation.VisibilityAnimator;
import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.utils.ViewUtils;

import java.util.List;

public class TitleBar extends Toolbar {

    private boolean hideOnScroll = false;
    private VisibilityAnimator visibilityAnimator;
    private LeftButton leftButton;
    private ActionMenuView actionMenuView;

    public TitleBar(Context context) {
        super(context);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (child instanceof ActionMenuView) {
            actionMenuView = (ActionMenuView) child;
        }
    }

    public void setRightButtons(List<TitleBarButtonParams> rightButtons, String navigatorEventId) {
        Menu menu = getMenu();
        menu.clear();

        if (rightButtons == null) {
            return;
        }

        addButtonsToTitleBar(rightButtons, navigatorEventId, menu);
    }

    public void setLeftButton(TitleBarLeftButtonParams leftButtonParams,
                               TitleBarBackButtonListener titleBarBackButtonListener, String navigatorEventId) {
        if (shouldSetLeftButton(leftButtonParams)) {
            createAndSetLeftButton(leftButtonParams, titleBarBackButtonListener, navigatorEventId);
        } else if (hasLeftButton()) {
            updateLeftButton(leftButtonParams);
        }
    }

    public void setStyle(StyleParams params) {
        setVisibility(params.titleBarHidden ? GONE : VISIBLE);
        setTitleTextColor(params);
        colorOverflowButton(params);
    }

    private void colorOverflowButton(StyleParams params) {
        Drawable overflowIcon = actionMenuView.getOverflowIcon();
        if (shouldColorOverflowButton(params, overflowIcon)) {
            ViewUtils.tintDrawable(overflowIcon, params.titleBarButtonColor.getColor(), true);
        }
    }

    private boolean shouldColorOverflowButton(StyleParams params, Drawable overflowIcon) {
        return overflowIcon != null && params.titleBarButtonColor.hasColor();
    }

    private void setTitleTextColor(StyleParams params) {
        if (params.titleBarTitleColor.hasColor()) {
            setTitleTextColor(params.titleBarTitleColor.getColor());
        }
    }

    private void addButtonsToTitleBar(List<TitleBarButtonParams> rightButtons, String navigatorEventId, Menu menu) {
        for (int i = 0; i < rightButtons.size(); i++) {
            final TitleBarButton button = new TitleBarButton(menu, this, rightButtons.get(i), navigatorEventId);
            addButtonInReverseOrder(rightButtons, i, button);
        }
    }

    private void addButtonInReverseOrder(List<TitleBarButtonParams> rightButtons, int i, TitleBarButton button) {
        final int index = rightButtons.size() - i - 1;
        button.addToMenu(index);
    }

    private boolean hasLeftButton() {
        return leftButton != null;
    }

    private void updateLeftButton(TitleBarLeftButtonParams leftButtonParams) {
        leftButton.setIconState(leftButtonParams);
    }

    private boolean shouldSetLeftButton(TitleBarLeftButtonParams leftButtonParams) {
        return leftButton == null && leftButtonParams != null;
    }

    private void createAndSetLeftButton(TitleBarLeftButtonParams leftButtonParams, TitleBarBackButtonListener titleBarBackButtonListener, String navigatorEventId) {
        leftButton = new LeftButton(getContext(), leftButtonParams, titleBarBackButtonListener, navigatorEventId);
        setNavigationOnClickListener(leftButton);
        setNavigationIcon(leftButton);
    }

    public void setHideOnScroll(boolean hideOnScroll) {
        this.hideOnScroll = hideOnScroll;
    }

    public void onScrollChanged(ScrollDirectionListener.Direction direction) {
        if (hideOnScroll) {
            if (visibilityAnimator == null) {
                createScrollAnimator();
            }
            visibilityAnimator.onScrollChanged(direction);
        }
    }

    private void createScrollAnimator() {
        visibilityAnimator = new VisibilityAnimator(this, VisibilityAnimator.HideDirection.Up, getHeight());
    }
}
