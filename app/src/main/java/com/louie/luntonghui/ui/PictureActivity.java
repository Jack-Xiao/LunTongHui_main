package com.louie.luntonghui.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.louie.luntonghui.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Jack on 15/8/12.
 */
public class PictureActivity extends BaseToolbarActivity{
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String TRANSIT_PIC = "picture";

    private ImageView imageView;
    private PhotoViewAttacher mPhotoViewAttacher;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = (ImageView)findViewById(R.id.picture);
        mPhotoViewAttacher = new PhotoViewAttacher(imageView);

        imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        Picasso.with(this).load(imageUrl).into(imageView);

        mToolbar.setBackgroundColor(getResources().getColor(R.color.black));

        setAppBarAlpha(0.8f);

        ViewCompat.setTransitionName(imageView, TRANSIT_PIC);

        mPhotoViewAttacher.setOnViewTapListener(
                new PhotoViewAttacher.OnViewTapListener() {
                    @Override
                    public void onViewTap(View view, float v, float v1) {
                        //hideOrShowToolbar();
                        finish();
                    }
                }
        );
    }

    @Override
    protected int toolbarTitle() {
        return R.string.empty_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_picture;
    }
}
