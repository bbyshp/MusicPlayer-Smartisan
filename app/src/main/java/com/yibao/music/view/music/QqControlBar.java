package com.yibao.music.view.music;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yibao.music.R;
import com.yibao.music.adapter.QqBarPagerAdapter;
import com.yibao.music.base.listener.MusicPagerListener;
import com.yibao.music.model.MusicBean;
import com.yibao.music.util.Constant;
import com.yibao.music.view.MusicProgressView;

import java.util.List;

/**
 * @ Name:   QqControlBar
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/8/11/ 21:50
 * @ Des:    TODO
 * @author Luoshipeng
 */
public class QqControlBar extends LinearLayout implements View.OnClickListener {
    LinearLayout mQqMusicBar;
    MusicProgressView mButtonPlay;
    ImageView mButtonFavorite;
    ViewPager mSlideViewPager;
    private QqBarPagerAdapter mPagerAdapter;

    public QqControlBar(Context context) {
        super(context);
        initView();
    }

    public QqControlBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.muisc_tabbar_qq, this, true);
        mQqMusicBar = findViewById(R.id.qq_music_bar);
        mSlideViewPager = findViewById(R.id.qq_music_vp);
        mButtonPlay = findViewById(R.id.music_floating_pager_play);
        mButtonFavorite = findViewById(R.id.music_floating_pager_favorite);
        initData();
        initListener();
    }

    private void initData() {
        mPagerAdapter = new QqBarPagerAdapter(getContext(), null);
        mSlideViewPager.setAdapter(mPagerAdapter);

    }

    private void initListener() {
        mButtonFavorite.setOnClickListener(this);
        mButtonPlay.setOnClickListener(this);
        mSlideViewPager.addOnPageChangeListener(new MusicPagerListener() {
            @Override
            public void onPageSelected(int position) {
                if (mSelectListener != null) {
                    mSelectListener.selectPosition(position);
                    setPagerCurrentItem(position);
                }
            }
        });
    }

    //**************按钮状态********************
    public void setPlayButtonState(int resourceId) {
        mButtonPlay.setIcon(resourceId);
    }

    public void updatePlayButtonState(boolean isPlaying) {
        mButtonPlay.setIcon(isPlaying ? R.drawable.btn_playing_pause_selector : R.drawable.btn_playing_play_selector);
    }

    public void setFavoriteButtonState(boolean isFavorite) {
        mButtonFavorite.setImageResource(isFavorite ? R.drawable.btn_favorite_red_selector : R.drawable.btn_favorite_gray_selector);
    }

    // **************ViewPager数据********************
    public void updaPagerData(List<MusicBean> musicItems, int currentPosition) {
        mPagerAdapter = null;
        mPagerAdapter = new QqBarPagerAdapter(getContext(), musicItems);
        mSlideViewPager.setAdapter(mPagerAdapter);
        mSlideViewPager.setCurrentItem(currentPosition, false);
        mPagerAdapter.notifyDataSetChanged();
    }

    public void setPagerData(List<MusicBean> musicItems) {
        mPagerAdapter.setData(musicItems);
    }

    public void setPagerCurrentItem(int cureetPosition) {
        mSlideViewPager.setCurrentItem(cureetPosition, false);
    }

    //**************歌曲进度********************
    public void setMaxProgress(int maxProgress) {
        mButtonPlay.setMax(maxProgress);
    }

    public void setProgress(int currentProgress) {
        mButtonPlay.setProgress(currentProgress);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.music_floating_pager_play:
                controlBarClick(Constant.NUMBER_ONE);
                break;
            case R.id.music_floating_pager_favorite:
                controlBarClick(Constant.NUMBER_TWO);
                break;
            default:
                break;
        }
    }


    private void controlBarClick(int clickFlag) {
        if (mButtonClickListener != null) {
            mButtonClickListener.click(clickFlag);
        }
    }

    //**************按钮点击监听********************

    private OnButtonClickListener mButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener buttonClickListener) {
        mButtonClickListener = buttonClickListener;
    }

    public interface OnButtonClickListener {
        void click(int clickFlag);
    }

    private OnPagerSelecteListener mSelectListener;

    public void setOnPagerSelectListener(OnPagerSelecteListener selectListener) {
        mSelectListener = selectListener;
    }

    public interface OnPagerSelecteListener {
        /**
         * p
         * @param currentPosition d
         */
        void selectPosition(int currentPosition);
    }
}
