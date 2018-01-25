package com.jovial.floatingview;

/**
 * Created by:[ Jovial ]
 * Created date:[ 2018/1/25 0025]
 * About Class:[ ]
 */

public class MathUtil {
    private final static String TAG = "MyMath";

    /**
     * 以原点为圆点，以radius维半径画圆，通过弧度o,获得坐标
     * @param radius 半径
     * @param o 弧度
     * @return
     */
    public static float[] getXYPoint(float[] centrePoint, int radius, float o){
        float[] xyPoint = {(float) (radius*Math.sin(o) + centrePoint[0]), (float) ((-1)*radius*Math.cos(o) + centrePoint[1])};
//        Log.d(TAG,"test: ["+xyPoint[0]+","+xyPoint[1]+"]");
        return xyPoint;
    }
}
