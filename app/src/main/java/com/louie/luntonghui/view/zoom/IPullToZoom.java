package com.louie.luntonghui.view.zoom;

import android.content.res.TypedArray;
import android.view.View;

/**
 * Created by Jack on 15/10/13.
 */
public interface IPullToZoom<T extends View> {

    /**
     * Get the Wrapped Zoom View. Anything returned here has already been
     * added to the content view.
     * @return The View which is currently wrapped.
     */
    public View getZoomView();

    public View getHeaderView();

    //
    public View getFooterView();
    /**
     * Get the Wrapped root view.
     * @return
     */
    public T getPullRootView();


    public boolean isPullToZoomEnabled();

    public boolean isZooming();
    public boolean isParallax();
    public boolean isHideHeader();
    public boolean isHideFooter();
    public void handleStyledAttributes(TypedArray a);


}
