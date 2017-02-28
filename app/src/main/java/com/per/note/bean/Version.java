package com.per.note.bean;

import java.io.Serializable;

/**
 * Created by liu on 2017/2/27.
 */
public class Version implements Serializable{
    public int versioncode=0;

    public Version(int versioncode) {
        this.versioncode = versioncode;
    }
}
