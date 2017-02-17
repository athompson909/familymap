package model;

import android.content.Context;
import android.graphics.Color;

import com.amazon.geo.mapsv2.AmazonMap;

/**
 * Created by athom909 on 4/7/16.
 */
public class SettingsInfo {
    private static SettingsInfo instance = new SettingsInfo();

    public static SettingsInfo getInstance() {
        return instance;
    }

    private SettingsInfo() {

        lifeStoryLinesOn = true;
        familyTreeLinesOn = true;
        spouseLinesOn = true;

        lifeStoryLinesColor = Color.BLUE;
        familyTreeLinesColor = Color.GREEN;
        spouseLinesColor = Color.YELLOW;

        lslColorStr = "blue";
        ftlColorStr = "green";
        slColorStr = "yellow";

        mapType = MapType.NORMAL;
        mtStr = "normal";

        reSync = false;
        loggedOut = true;
        context = null;

        returnToMainActivity = false;
    }

    private static final int BLUE = 0;
    private static final int GREEN = 1;
    private static final int MAGENTA = 2;
    private static final int YELLOW = 3;

    private static final int _NORMAL = 0;
    private static final int _SATELLITE = 1;
    private static final int _HYBRID = 2;

    private enum MapType { NORMAL, SATELLITE, HYBRID }

    private boolean lifeStoryLinesOn;
    private boolean familyTreeLinesOn;
    private boolean spouseLinesOn;

    private int lifeStoryLinesColor;
    private int familyTreeLinesColor;
    private int spouseLinesColor;
    private String lslColorStr;
    private String ftlColorStr;
    private String slColorStr;

    private MapType mapType;
    private String mtStr;

    private boolean reSync;
    private boolean loggedOut;
    private Context context;

    boolean returnToMainActivity;

    public boolean isLifeStoryLinesOn() {
        return lifeStoryLinesOn;
    }

    public void setLifeStoryLinesOn(boolean lifeStoryLinesOn) {
        this.lifeStoryLinesOn = lifeStoryLinesOn;
    }

    public boolean isFamilyTreeLinesOn() {
        return familyTreeLinesOn;
    }

    public void setFamilyTreeLinesOn(boolean familyTreeLinesOn) {
        this.familyTreeLinesOn = familyTreeLinesOn;
    }

    public boolean isSpouseLinesOn() {
        return spouseLinesOn;
    }

    public void setSpouseLinesOn(boolean spouseLinesOn) {
        this.spouseLinesOn = spouseLinesOn;
    }

    public int getLifeStoryLinesColor() {
        return lifeStoryLinesColor;
    }

    public void setLifeStoryLinesColor(int lifeStoryLinesColor) {
        this.lifeStoryLinesColor = lifeStoryLinesColor;
    }

    public int getFamilyTreeLinesColor() {
        return familyTreeLinesColor;
    }

    public void setFamilyTreeLinesColor(int familyTreeLinesColor) {
        this.familyTreeLinesColor = familyTreeLinesColor;
    }

    public int getSpouseLinesColor() {
        return spouseLinesColor;
    }

    public void setSpouseLinesColor(int spouseLinesColor) {
        this.spouseLinesColor = spouseLinesColor;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public String getLslColorStr() {
        return lslColorStr;
    }

    public void setLslColorStr(String lslColorStr) {
        this.lslColorStr = lslColorStr;
    }

    public String getFtlColorStr() {
        return ftlColorStr;
    }

    public void setFtlColorStr(String ftlColorStr) {
        this.ftlColorStr = ftlColorStr;
    }

    public String getSlColorStr() {
        return slColorStr;
    }

    public void setSlColorStr(String slColorStr) {
        this.slColorStr = slColorStr;
    }

    public String getMtStr() {
        return mtStr;
    }

    public void setMtStr(String mtStr) {
        this.mtStr = mtStr;
    }

    public boolean reSyncData() {
        return reSync;
    }

    public void setReSync(boolean reSync) {
        this.reSync = reSync;
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(boolean loggedOut) {
        this.loggedOut = loggedOut;
    }

    public void setMapType(String str) {
        mtStr = str;
        switch (str) {
            case "normal": mapType = MapType.NORMAL; break;
            case "satellite": mapType = MapType.SATELLITE; break;
            case "hybrid" : mapType = MapType.HYBRID; break;
            default: assert false;
        }
    }

    public void setLifeStoryLinesColor(String str) {
        lslColorStr = str;
        switch (str) {
            case "blue": lifeStoryLinesColor = Color.BLUE; break;
            case "green": lifeStoryLinesColor = Color.GREEN; break;
            case "magenta": lifeStoryLinesColor = Color.MAGENTA; break;
            case "yellow": lifeStoryLinesColor = Color.YELLOW; break;
            default: assert false;
        }
    }

    public void setFamilyTreeLinesColor(String str) {
        ftlColorStr = str;
        switch (str) {
            case "blue": familyTreeLinesColor = Color.BLUE; break;
            case "green": familyTreeLinesColor = Color.GREEN; break;
            case "magenta": familyTreeLinesColor = Color.MAGENTA; break;
            case "yellow": familyTreeLinesColor = Color.YELLOW; break;
            default: assert false;
        }
    }

    public void setSpouseLinesColor(String str) {
        slColorStr = str;
        switch (str) {
            case "blue": spouseLinesColor = Color.BLUE; break;
            case "green": spouseLinesColor = Color.GREEN; break;
            case "magenta": spouseLinesColor = Color.MAGENTA; break;
            case "yellow": spouseLinesColor = Color.YELLOW; break;
            default: assert false;
        }
    }

    public int getLslPosition() {
        return getPosition(lifeStoryLinesColor);
    }

    public int getFtlPosition() {
        return getPosition(familyTreeLinesColor);
    }

    public int getSlPosition() {
        return getPosition(spouseLinesColor);
    }

    private int getPosition(int color) {
        switch (color) {
            case Color.BLUE: return BLUE;
            case Color.GREEN: return GREEN;
            case Color.MAGENTA: return MAGENTA;
            case Color.YELLOW: return YELLOW;
            default: assert false;
        }
        return 0;
    }

    public int getMtPosition() {
        switch (mapType) {
            case NORMAL: return _NORMAL;
            case SATELLITE: return _SATELLITE;
            case HYBRID: return  _HYBRID;
            default: assert false;
        }
        return 0;
    }

    public int getMapTypeAsInt() {
        switch (mapType) {
            case NORMAL: return AmazonMap.MAP_TYPE_NORMAL;
            case SATELLITE: return AmazonMap.MAP_TYPE_SATELLITE;
            case HYBRID: return  AmazonMap.MAP_TYPE_HYBRID;
            default: assert false;
        }
        return AmazonMap.MAP_TYPE_NORMAL;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean returnToMainActivity() {
        return returnToMainActivity;
    }

    public void setReturnToMainActivity(boolean returnToMainActivity) {
        this.returnToMainActivity = returnToMainActivity;
    }
}