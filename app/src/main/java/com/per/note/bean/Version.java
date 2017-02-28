package com.per.note.bean;

import java.io.Serializable;

/**
 * Created by home on 2015/10/13.
 */
public class Version implements Serializable{
    public int versioncode=0;

    public Version(int versioncode) {
        this.versioncode = versioncode;
    }
}
