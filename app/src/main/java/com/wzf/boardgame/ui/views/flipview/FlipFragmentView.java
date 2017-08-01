package com.wzf.boardgame.ui.views.flipview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.wzf.boardgame.R;
import com.wzf.boardgame.utils.DebugLog;

import java.util.ArrayList;

/**
 * Created by zhenfei.wang on 2016/6/24.
 */
public class FlipFragmentView extends BaseView implements ViewPager.OnPageChangeListener {
    private FlipFragmentViewSetting setting;
    private RadioGroup rg;
    private ViewPager vp;
    private TextView tv_line;
    private int mScreenWidth;
    private int item_width;
    private int endPosition;
    private int beginPosition;
    private int currentFragmentIndex; // 设置选中的标签页
    private boolean isEnd;
    private int pageSelectedPosition;

    public FlipFragmentView(Context context) {
        super(context, null);
        initView();
    }

    public FlipFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FlipFragmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        rg = (RadioGroup) findViewById(R.id.rg);
        vp = (ViewPager) findViewById(R.id.vp);
        tv_line = (TextView) findViewById(R.id.line);
        vp.addOnPageChangeListener(this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                vp.setCurrentItem(checkedId);
            }
        });
    }

    @Override
    public void setViewSettingAndShow(BaseViewSetting setting) {
        if (setting != null) {
            this.setting = (FlipFragmentViewSetting) setting;
            updateView();
        }
    }

    @Override
    public BaseViewSetting getViewSetting() {
        return setting;
    }

    @Override
    protected int getLayuoutId() {
        return R.layout.flip_fragment_view;
    }

    @Override
    protected void updateView() {
        if (checkSetting()) {
            vp.setOffscreenPageLimit(5);
            rg.removeAllViews();
            item_width = mScreenWidth / setting.getTitles().size();
            ViewGroup.LayoutParams params = tv_line.getLayoutParams();
            params.width = item_width;
            tv_line.setLayoutParams(params);
            tv_line.setBackgroundColor(setting.getBgLineColor());
            for (int i = 0; i < setting.getTitles().size(); i++) { // 动态添加radiobutton
                RadioButton rb = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.radio_button, null);
                ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(item_width, FrameLayout.LayoutParams.MATCH_PARENT);
//                ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                rb.setLayoutParams(param);
                rb.setTextColor(setColorList());
                rb.setTextSize(18);
                rb.setText(setting.getTitles().get(i));
                rb.setGravity(Gravity.CENTER);
                rb.setId(i);
                rb.setTag(i);
                rb.setChecked(i == 0);
                rg.addView(rb);
            }
            FragmentManager fragmentManager = setting.getFragmentManager();
            vp.setAdapter(new MyFragmentPagerAdapter(fragmentManager == null ?
                    ((FragmentActivity) mContext).getSupportFragmentManager() : fragmentManager, setting.getFragments()));
        }
        if(setting.getCurrentPage() > 0 && setting.getCurrentPage() < setting.getTitles().size()){
            vp.setCurrentItem(setting.getCurrentPage());
        }
    }

    private boolean checkSetting() {
        return setting != null && setting.getTitles().size() > 0;
    }

    /**
     * 动态获取selector
     *
     * @return
     */
    public ColorStateList setColorList() {
        int[] colors = new int[]{setting.getTitleCheckTextColor(), setting.getTitleUnCheckTextColor()};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!isEnd) {
            if (currentFragmentIndex == position) {
                endPosition = item_width * currentFragmentIndex +
                        (int) (item_width * positionOffset);
            }
            if (currentFragmentIndex == position + 1) {
                endPosition = item_width * currentFragmentIndex -
                        (int) (item_width * (1 - positionOffset));
            }

            Animation mAnimation = new TranslateAnimation(beginPosition, endPosition, 0, 0);
            mAnimation.setFillAfter(true);
            mAnimation.setDuration(0);
            tv_line.startAnimation(mAnimation);
            beginPosition = endPosition;
        }
    }

    @Override
    public void onPageSelected(int position) {
        rg.check(position);
        Animation animation = new TranslateAnimation(endPosition, position * item_width, 0, 0);
        beginPosition = position * item_width;
        currentFragmentIndex = position;
        if (animation != null) {
            animation.setFillAfter(true);
            animation.setDuration(0);
            tv_line.startAnimation(animation);
        }
        setPageSelectedPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) { // 还在拖动
            isEnd = false;
        } else if (state == ViewPager.SCROLL_STATE_SETTLING) { // 手指已经离开
            isEnd = true;
            beginPosition = currentFragmentIndex * item_width;
            if (vp.getCurrentItem() == currentFragmentIndex) {
                // 未跳入下一个页面
                tv_line.clearAnimation();
                Animation animation = null;
                // 恢复位置
                animation = new TranslateAnimation(endPosition, currentFragmentIndex * item_width, 0, 0);
                animation.setFillAfter(true);
                animation.setDuration(1);
                tv_line.startAnimation(animation);
                endPosition = currentFragmentIndex * item_width;
            }
        }
    }

    /**
     * FragmentPagerAdapter 都将保存在内存之中，因此适用于那些相对静态的页，数量也比较少的那种；
     * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapter。
     * PagerAdapter 的实现将只保留当前页面，当页面离开视线后，就会被消除，释放其资源；而在页面需要显示时，
     * 生成新的页面(就像 ListView 的实现一样)。这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存。
     */
    private static class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Object obj = super.instantiateItem(container, position);
            return obj;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            try{
                super.finishUpdate(container);
            } catch (NullPointerException nullPointerException){
                DebugLog.i("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
            }
        }

    }


    public String getPageSelectedPosition() {
        return pageSelectedPosition + "";
    }

    public void setPageSelectedPosition(int pageSelectedPosition) {
        this.pageSelectedPosition = pageSelectedPosition;
        if(checkChangeListener != null){
            checkChangeListener.check(pageSelectedPosition);
        }
    }

    private OnCheckChangeListener checkChangeListener;

    public void setCheckChangeListener(OnCheckChangeListener checkChangeListener) {
        this.checkChangeListener = checkChangeListener;
    }

    public interface OnCheckChangeListener{
        void check(int position);
    }

}
