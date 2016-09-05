package com.reactnativenavigation.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;

import com.reactnativenavigation.events.Event;
import com.reactnativenavigation.events.EventBus;
import com.reactnativenavigation.events.ScreenChangedEvent;
import com.reactnativenavigation.events.Subscriber;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.SnackbarParams;

public class SnackbarAndFabContainer extends CoordinatorLayout implements Snakbar.OnDismissListener, Subscriber{
    private Snakbar snakbar;
    private FloatingActionButtonCoordinator fabCoordinator;

    public SnackbarAndFabContainer(Context context) {
        super(context);
        fabCoordinator = new FloatingActionButtonCoordinator(this);
        EventBus.instance.register(this);
    }

    public void showSnackbar(final String navigatorEventId, final SnackbarParams params) {
        snakbar = new Snakbar(this, navigatorEventId, params);
        snakbar.show();
    }

    @Override
    public void onDismiss(Snakbar snakbar) {
        if (this.snakbar == snakbar) {
            this.snakbar = null;
        }
    }

    public void destroy() {
        EventBus.instance.unregister(this);
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == ScreenChangedEvent.TYPE) {
            onScreenChange(((ScreenChangedEvent) event).getScreenParams());
        }
    }

    private void onScreenChange(ScreenParams screenParams) {
        dismissSnackbar();
        updateFab(screenParams);
    }

    private void dismissSnackbar() {
        if (snakbar != null) {
            snakbar.dismiss();
            snakbar = null;
        }
    }

    private void updateFab(final ScreenParams screenParams) {
        fabCoordinator.remove(new Runnable() {
            @Override
            public void run() {
                if (screenParams.fabParams != null) {
                    fabCoordinator.add(screenParams.fabParams);
                }
            }
        });
    }
}
