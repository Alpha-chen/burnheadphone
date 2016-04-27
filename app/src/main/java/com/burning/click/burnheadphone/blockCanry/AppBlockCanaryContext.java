package com.burning.click.burnheadphone.blockCanry;

import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * 设置卡顿监听上下文
 * Created by click on 16-4-17.
 */
public class AppBlockCanaryContext extends BlockCanaryContext {
    private int blockthreshold = 200; //  界面卡顿超过500ms 触发监听

    @Override
    public int getConfigBlockThreshold() {
        return blockthreshold;
    }


    @Override
    public boolean isNeedDisplay() {
        return super.isNeedDisplay();
    }

    @Override
    public String getLogPath() {
        return super.getLogPath();
    }
}
