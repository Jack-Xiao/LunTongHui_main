package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Jack on 16/1/26.
 */
@Table(name = "home_advertisement")
public class DBHomeAdvertisement extends Model {
    @Column(name = "adver_round")
    public String adver;

    @Column(name = "adv_array")
    public String adverArray;
}