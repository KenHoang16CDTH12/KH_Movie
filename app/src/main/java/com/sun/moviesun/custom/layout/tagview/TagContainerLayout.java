package com.sun.moviesun.custom.layout.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sun.moviesun.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.customview.widget.ViewDragHelper;

import static com.sun.moviesun.custom.layout.tagview.Utils.dp2px;
import static com.sun.moviesun.custom.layout.tagview.Utils.sp2px;

public class TagContainerLayout extends ViewGroup {

  private int mVerticalInterval;

  private List<int[]> mColorArrayList;

  private int mHorizontalInterval;

  private float mBorderWidth = 0.5f;

  private float mBorderRadius = 10.0f;

  private float mSensitivity = 1.0f;

  private int mChildHeight;

  private int mBorderColor = Color.parseColor("#22FF0000");

  private int mBackgroundColor = Color.parseColor("#11FF0000");

  private int mGravity = Gravity.LEFT;

  private int mMaxLines = 0;

  private int mTagMaxLength = 23;

  private float mTagBorderWidth = 0.5f;

  private float mTagBorderRadius = 15.0f;

  private float mTagTextSize = 14;

  private int mTagTextDirection = View.TEXT_DIRECTION_LTR;

  private int mTagHorizontalPadding = 10;

  private int mTagVerticalPadding = 8;

  private int mTagBorderColor = Color.parseColor("#88F44336");

  private int mTagBackgroundColor = Color.parseColor("#33F44336");

  private int mSelectedTagBackgroundColor = Color.parseColor("#33FF7669");

  private int mTagTextColor = Color.parseColor("#FF666666");

  private Typeface mTagTypeface = Typeface.DEFAULT;

  private boolean isTagViewClickable;

  private boolean isTagViewSelectable;

  private List<String> mTags;

  private int mDefaultImageDrawableID = -1;

  private boolean mDragEnable;

  private int mTagViewState = ViewDragHelper.STATE_IDLE;

  private float mTagBdDistance = 2.75f;

  private TagView.OnTagClickListener mOnTagClickListener;

  private boolean mTagSupportLettersRTL = false;

  private Paint mPaint;

  private RectF mRectF;

  private ViewDragHelper mViewDragHelper;

  private List<View> mChildViews;

  private int[] mViewPos;

  private int mTheme = ColorFactory.PURE_CYAN;

  private static final float DEFAULT_INTERVAL = 5;

  private static final int TAG_MIN_LENGTH = 3;

  private int mRippleDuration = 1000;

  private int mRippleColor;

  private int mRippleAlpha = 128;

  private boolean mEnableCross = false;

  private float mCrossAreaWidth = 0.0f;

  private float mCrossAreaPadding = 10.0f;

  private int mCrossColor = Color.BLACK;

  private float mCrossLineWidth = 1.0f;

  private int mTagBackgroundResource;

  public TagContainerLayout(Context context) {
    this(context, null);
  }

  public TagContainerLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TagContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.AndroidTagView,
        defStyleAttr, 0);
    mVerticalInterval = (int) attributes.getDimension(R.styleable.AndroidTagView_vertical_interval,
        dp2px(context, DEFAULT_INTERVAL));
    mHorizontalInterval = (int) attributes.getDimension(R.styleable.AndroidTagView_horizontal_interval,
        dp2px(context, DEFAULT_INTERVAL));
    mBorderWidth = attributes.getDimension(R.styleable.AndroidTagView_container_border_width,
        dp2px(context, mBorderWidth));
    mBorderRadius = attributes.getDimension(R.styleable.AndroidTagView_container_border_radius,
        dp2px(context, mBorderRadius));
    mTagBdDistance = attributes.getDimension(R.styleable.AndroidTagView_tag_bd_distance,
        dp2px(context, mTagBdDistance));
    mBorderColor = attributes.getColor(R.styleable.AndroidTagView_container_border_color,
        mBorderColor);
    mBackgroundColor = attributes.getColor(R.styleable.AndroidTagView_container_background_color,
        mBackgroundColor);
    mDragEnable = attributes.getBoolean(R.styleable.AndroidTagView_container_enable_drag, false);
    mSensitivity = attributes.getFloat(R.styleable.AndroidTagView_container_drag_sensitivity,
        mSensitivity);
    mGravity = attributes.getInt(R.styleable.AndroidTagView_container_gravity, mGravity);
    mMaxLines = attributes.getInt(R.styleable.AndroidTagView_container_max_lines, mMaxLines);
    mTagMaxLength = attributes.getInt(R.styleable.AndroidTagView_tag_max_length, mTagMaxLength);
    mTheme = attributes.getInt(R.styleable.AndroidTagView_tag_theme, mTheme);
    mTagBorderWidth = attributes.getDimension(R.styleable.AndroidTagView_tag_border_width,
        dp2px(context, mTagBorderWidth));
    mTagBorderRadius = attributes.getDimension(
        R.styleable.AndroidTagView_tag_corner_radius, dp2px(context, mTagBorderRadius));
    mTagHorizontalPadding = (int) attributes.getDimension(
        R.styleable.AndroidTagView_tag_horizontal_padding,
        dp2px(context, mTagHorizontalPadding));
    mTagVerticalPadding = (int) attributes.getDimension(
        R.styleable.AndroidTagView_tag_vertical_padding, dp2px(context, mTagVerticalPadding));
    mTagTextSize = attributes.getDimension(R.styleable.AndroidTagView_tag_text_size,
        sp2px(context, mTagTextSize));
    mTagBorderColor = attributes.getColor(R.styleable.AndroidTagView_tag_border_color,
        mTagBorderColor);
    mTagBackgroundColor = attributes.getColor(R.styleable.AndroidTagView_tag_background_color,
        mTagBackgroundColor);
    mTagTextColor = attributes.getColor(R.styleable.AndroidTagView_tag_text_color, mTagTextColor);
    mTagTextDirection = attributes.getInt(R.styleable.AndroidTagView_tag_text_direction, mTagTextDirection);
    isTagViewClickable = attributes.getBoolean(R.styleable.AndroidTagView_tag_clickable, false);
    isTagViewSelectable = attributes.getBoolean(R.styleable.AndroidTagView_tag_selectable, false);
    mRippleColor = attributes.getColor(R.styleable.AndroidTagView_tag_ripple_color, Color.parseColor("#EEEEEE"));
    mRippleAlpha = attributes.getInteger(R.styleable.AndroidTagView_tag_ripple_alpha, mRippleAlpha);
    mRippleDuration = attributes.getInteger(R.styleable.AndroidTagView_tag_ripple_duration, mRippleDuration);
    mEnableCross = attributes.getBoolean(R.styleable.AndroidTagView_tag_enable_cross, mEnableCross);
    mCrossAreaWidth = attributes.getDimension(R.styleable.AndroidTagView_tag_cross_width,
        dp2px(context, mCrossAreaWidth));
    mCrossAreaPadding = attributes.getDimension(R.styleable.AndroidTagView_tag_cross_area_padding,
        dp2px(context, mCrossAreaPadding));
    mCrossColor = attributes.getColor(R.styleable.AndroidTagView_tag_cross_color, mCrossColor);
    mCrossLineWidth = attributes.getDimension(R.styleable.AndroidTagView_tag_cross_line_width,
        dp2px(context, mCrossLineWidth));
    mTagSupportLettersRTL = attributes.getBoolean(R.styleable.AndroidTagView_tag_support_letters_rlt,
        mTagSupportLettersRTL);
    mTagBackgroundResource = attributes.getResourceId(R.styleable.AndroidTagView_tag_background,
        mTagBackgroundResource);
    attributes.recycle();

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mRectF = new RectF();
    mChildViews = new ArrayList<View>();
    mViewDragHelper = ViewDragHelper.create(this, mSensitivity, new DragHelperCallBack());
    setWillNotDraw(false);
    setTagMaxLength(mTagMaxLength);
    setTagHorizontalPadding(mTagHorizontalPadding);
    setTagVerticalPadding(mTagVerticalPadding);

    if (isInEditMode()) {
      addTag("sample tag");
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    measureChildren(widthMeasureSpec, heightMeasureSpec);
    final int childCount = getChildCount();
    int lines = childCount == 0 ? 0 : getChildLines(childCount);
    int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
    int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

    if (childCount == 0) {
      setMeasuredDimension(0, 0);
    } else if (heightSpecMode == MeasureSpec.AT_MOST
        || heightSpecMode == MeasureSpec.UNSPECIFIED) {
      setMeasuredDimension(widthSpecSize, (mVerticalInterval + mChildHeight) * lines
          - mVerticalInterval + getPaddingTop() + getPaddingBottom());
    } else {
      setMeasuredDimension(widthSpecSize, heightSpecSize);
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mRectF.set(0, 0, w, h);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int childCount;
    if ((childCount = getChildCount()) <= 0) {
      return;
    }
    int availableW = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    int curRight = getMeasuredWidth() - getPaddingRight();
    int curTop = getPaddingTop();
    int curLeft = getPaddingLeft();
    int sPos = 0;
    mViewPos = new int[childCount * 2];

    for (int i = 0; i < childCount; i++) {
      final View childView = getChildAt(i);
      if (childView.getVisibility() != GONE) {
        int width = childView.getMeasuredWidth();
        if (mGravity == Gravity.RIGHT) {
          if (curRight - width < getPaddingLeft()) {
            curRight = getMeasuredWidth() - getPaddingRight();
            curTop += mChildHeight + mVerticalInterval;
          }
          mViewPos[i * 2] = curRight - width;
          mViewPos[i * 2 + 1] = curTop;
          curRight -= width + mHorizontalInterval;
        } else if (mGravity == Gravity.CENTER) {
          if (curLeft + width - getPaddingLeft() > availableW) {
            int leftW = getMeasuredWidth() - mViewPos[(i - 1) * 2]
                - getChildAt(i - 1).getMeasuredWidth() - getPaddingRight();
            for (int j = sPos; j < i; j++) {
              mViewPos[j * 2] = mViewPos[j * 2] + leftW / 2;
            }
            sPos = i;
            curLeft = getPaddingLeft();
            curTop += mChildHeight + mVerticalInterval;
          }
          mViewPos[i * 2] = curLeft;
          mViewPos[i * 2 + 1] = curTop;
          curLeft += width + mHorizontalInterval;

          if (i == childCount - 1) {
            int leftW = getMeasuredWidth() - mViewPos[i * 2]
                - childView.getMeasuredWidth() - getPaddingRight();
            for (int j = sPos; j < childCount; j++) {
              mViewPos[j * 2] = mViewPos[j * 2] + leftW / 2;
            }
          }
        } else {
          if (curLeft + width - getPaddingLeft() > availableW) {
            curLeft = getPaddingLeft();
            curTop += mChildHeight + mVerticalInterval;
          }
          mViewPos[i * 2] = curLeft;
          mViewPos[i * 2 + 1] = curTop;
          curLeft += width + mHorizontalInterval;
        }
      }
    }

    // layout all child views
    for (int i = 0; i < mViewPos.length / 2; i++) {
      View childView = getChildAt(i);
      childView.layout(mViewPos[i * 2], mViewPos[i * 2 + 1],
          mViewPos[i * 2] + childView.getMeasuredWidth(),
          mViewPos[i * 2 + 1] + mChildHeight);
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setColor(mBackgroundColor);
    canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);

    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(mBorderWidth);
    mPaint.setColor(mBorderColor);
    canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return mViewDragHelper.shouldInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    mViewDragHelper.processTouchEvent(event);
    return true;
  }

  @Override
  public void computeScroll() {
    super.computeScroll();
    if (mViewDragHelper.continueSettling(true)) {
      requestLayout();
    }
  }

  private int getChildLines(int childCount) {
    int availableW = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    int lines = 1;
    for (int i = 0, curLineW = 0; i < childCount; i++) {
      View childView = getChildAt(i);
      int dis = childView.getMeasuredWidth() + mHorizontalInterval;
      int height = childView.getMeasuredHeight();
      mChildHeight = i == 0 ? height : Math.min(mChildHeight, height);
      curLineW += dis;
      if (curLineW - mHorizontalInterval > availableW) {
        lines++;
        curLineW = dis;
      }
    }

    return mMaxLines <= 0 ? lines : mMaxLines;
  }

  private int[] onUpdateColorFactory() {
    int[] colors;
    if (mTheme == ColorFactory.RANDOM) {
      colors = ColorFactory.onRandomBuild();
    } else if (mTheme == ColorFactory.PURE_TEAL) {
      colors = ColorFactory.onPureBuild(ColorFactory.PURE_COLOR.TEAL);
    } else if (mTheme == ColorFactory.PURE_CYAN) {
      colors = ColorFactory.onPureBuild(ColorFactory.PURE_COLOR.CYAN);
    } else {
      colors = new int[]{mTagBackgroundColor, mTagBorderColor, mTagTextColor, mSelectedTagBackgroundColor};
    }
    return colors;
  }

  private void onSetTag() {
    if (mTags == null) {
      throw new RuntimeException("NullPointer exception!");
    }
    removeAllTags();
    if (mTags.size() == 0) {
      return;
    }
    for (int i = 0; i < mTags.size(); i++) {
      onAddTag(mTags.get(i), mChildViews.size());
    }
    postInvalidate();
  }

  private void onAddTag(String text, int position) {
    if (position < 0 || position > mChildViews.size()) {
      throw new RuntimeException("Illegal position!");
    }
    TagView tagView;
    if (mDefaultImageDrawableID != -1) {
      tagView = new TagView(getContext(), text, mDefaultImageDrawableID);
    } else {
      tagView = new TagView(getContext(), text);
    }
    initTagView(tagView, position);
    mChildViews.add(position, tagView);
    if (position < mChildViews.size()) {
      for (int i = position; i < mChildViews.size(); i++) {
        mChildViews.get(i).setTag(i);
      }
    } else {
      tagView.setTag(position);
    }
    addView(tagView, position);
  }

  private void initTagView(TagView tagView, int position) {
    int[] colors;
    if (mColorArrayList != null && mColorArrayList.size() > 0) {
      if (mColorArrayList.size() == mTags.size() &&
          mColorArrayList.get(position).length >= 4) {
        colors = mColorArrayList.get(position);
      } else {
        throw new RuntimeException("Illegal color list!");
      }
    } else {
      colors = onUpdateColorFactory();
    }

    tagView.setTagBackgroundColor(colors[0]);
    tagView.setTagBorderColor(colors[1]);
    tagView.setTagTextColor(colors[2]);
    tagView.setTagSelectedBackgroundColor(colors[3]);
    tagView.setTagMaxLength(mTagMaxLength);
    tagView.setTextDirection(mTagTextDirection);
    tagView.setTypeface(mTagTypeface);
    tagView.setBorderWidth(mTagBorderWidth);
    tagView.setBorderRadius(mTagBorderRadius);
    tagView.setTextSize(mTagTextSize);
    tagView.setHorizontalPadding(mTagHorizontalPadding);
    tagView.setVerticalPadding(mTagVerticalPadding);
    tagView.setIsViewClickable(isTagViewClickable);
    tagView.setIsViewSelectable(isTagViewSelectable);
    tagView.setBdDistance(mTagBdDistance);
    tagView.setOnTagClickListener(mOnTagClickListener);
    tagView.setRippleAlpha(mRippleAlpha);
    tagView.setRippleColor(mRippleColor);
    tagView.setRippleDuration(mRippleDuration);
    tagView.setEnableCross(mEnableCross);
    tagView.setCrossAreaWidth(mCrossAreaWidth);
    tagView.setCrossAreaPadding(mCrossAreaPadding);
    tagView.setCrossColor(mCrossColor);
    tagView.setCrossLineWidth(mCrossLineWidth);
    tagView.setTagSupportLettersRTL(mTagSupportLettersRTL);
    tagView.setBackgroundResource(mTagBackgroundResource);
  }

  private void invalidateTags() {
    for (View view : mChildViews) {
      final TagView tagView = (TagView) view;
      tagView.setOnTagClickListener(mOnTagClickListener);
    }
  }

  private void onRemoveTag(int position) {
    if (position < 0 || position >= mChildViews.size()) {
      throw new RuntimeException("Illegal position!");
    }
    mChildViews.remove(position);
    removeViewAt(position);
    for (int i = position; i < mChildViews.size(); i++) {
      mChildViews.get(i).setTag(i);
    }
    // TODO, make removed view null?
  }

  private void onRemoveConsecutiveTags(List<Integer> positions) {
    int smallestPosition = Collections.min(positions);
    for (int position : positions) {
      if (position < 0 || position >= mChildViews.size()) {
        throw new RuntimeException("Illegal position!");
      }
      mChildViews.remove(smallestPosition);
      removeViewAt(smallestPosition);
    }
    for (int i = smallestPosition; i < mChildViews.size(); i++) {
      mChildViews.get(i).setTag(i);
    }
    // TODO, make removed view null?
  }

  private int[] onGetNewPosition(View view) {
    int left = view.getLeft();
    int top = view.getTop();
    int bestMatchLeft = mViewPos[(int) view.getTag() * 2];
    int bestMatchTop = mViewPos[(int) view.getTag() * 2 + 1];
    int tmpTopDis = Math.abs(top - bestMatchTop);
    for (int i = 0; i < mViewPos.length / 2; i++) {
      if (Math.abs(top - mViewPos[i * 2 + 1]) < tmpTopDis) {
        bestMatchTop = mViewPos[i * 2 + 1];
        tmpTopDis = Math.abs(top - mViewPos[i * 2 + 1]);
      }
    }
    int rowChildCount = 0;
    int tmpLeftDis = 0;
    for (int i = 0; i < mViewPos.length / 2; i++) {
      if (mViewPos[i * 2 + 1] == bestMatchTop) {
        if (rowChildCount == 0) {
          bestMatchLeft = mViewPos[i * 2];
          tmpLeftDis = Math.abs(left - bestMatchLeft);
        } else {
          if (Math.abs(left - mViewPos[i * 2]) < tmpLeftDis) {
            bestMatchLeft = mViewPos[i * 2];
            tmpLeftDis = Math.abs(left - bestMatchLeft);
          }
        }
        rowChildCount++;
      }
    }
    return new int[]{bestMatchLeft, bestMatchTop};
  }

  private int onGetCoordinateReferPos(int left, int top) {
    int pos = 0;
    for (int i = 0; i < mViewPos.length / 2; i++) {
      if (left == mViewPos[i * 2] && top == mViewPos[i * 2 + 1]) {
        pos = i;
      }
    }
    return pos;
  }

  private void onChangeView(View view, int newPos, int originPos) {
    mChildViews.remove(originPos);
    mChildViews.add(newPos, view);
    for (View child : mChildViews) {
      child.setTag(mChildViews.indexOf(child));
    }

    removeViewAt(originPos);
    addView(view, newPos);
  }

  private int ceilTagBorderWidth() {
    return (int) Math.ceil(mTagBorderWidth);
  }

  private class DragHelperCallBack extends ViewDragHelper.Callback {

    @Override
    public void onViewDragStateChanged(int state) {
      super.onViewDragStateChanged(state);
      mTagViewState = state;
    }

    @Override
    public boolean tryCaptureView(View child, int pointerId) {
      requestDisallowInterceptTouchEvent(true);
      return mDragEnable;
    }

    @Override
    public int clampViewPositionHorizontal(View child, int left, int dx) {
      final int leftX = getPaddingLeft();
      final int rightX = getWidth() - child.getWidth() - getPaddingRight();
      return Math.min(Math.max(left, leftX), rightX);
    }

    @Override
    public int clampViewPositionVertical(View child, int top, int dy) {
      final int topY = getPaddingTop();
      final int bottomY = getHeight() - child.getHeight() - getPaddingBottom();
      return Math.min(Math.max(top, topY), bottomY);
    }

    @Override
    public int getViewHorizontalDragRange(View child) {
      return getMeasuredWidth() - child.getMeasuredWidth();
    }

    @Override
    public int getViewVerticalDragRange(View child) {
      return getMeasuredHeight() - child.getMeasuredHeight();
    }

    @Override
    public void onViewReleased(View releasedChild, float xvel, float yvel) {
      super.onViewReleased(releasedChild, xvel, yvel);
      requestDisallowInterceptTouchEvent(false);
      int[] pos = onGetNewPosition(releasedChild);
      int posRefer = onGetCoordinateReferPos(pos[0], pos[1]);
      onChangeView(releasedChild, posRefer, (int) releasedChild.getTag());
      mViewDragHelper.settleCapturedViewAt(pos[0], pos[1]);
      invalidate();
    }
  }

  public int getTagViewState() {
    return mTagViewState;
  }

  public float getTagBdDistance() {
    return mTagBdDistance;
  }

  public void setTagBdDistance(float tagBdDistance) {
    this.mTagBdDistance = dp2px(getContext(), tagBdDistance);
  }

  public void setTags(List<String> tags) {
    mTags = tags;
    onSetTag();
  }

  public void setTags(List<String> tags, List<int[]> colorArrayList) {
    mTags = tags;
    mColorArrayList = colorArrayList;
    onSetTag();
  }

  public void setTags(String... tags) {
    mTags = Arrays.asList(tags);
    onSetTag();
  }

  public void addTag(String text) {
    addTag(text, mChildViews.size());
  }

  public void addTag(String text, int position) {
    onAddTag(text, position);
    postInvalidate();
  }

  public void removeTag(int position) {
    onRemoveTag(position);
    postInvalidate();
  }

  public void removeConsecutiveTags(List<Integer> positions) {
    onRemoveConsecutiveTags(positions);
    postInvalidate();
  }

  public void removeAllTags() {
    mChildViews.clear();
    removeAllViews();
    postInvalidate();
  }

  public void setOnTagClickListener(TagView.OnTagClickListener listener) {
    mOnTagClickListener = listener;
    invalidateTags();
  }

  public void toggleSelectTagView(int position) {
    if (isTagViewSelectable) {
      TagView tagView = ((TagView) mChildViews.get(position));
      if (tagView.getIsViewSelected()) {
        tagView.deselectView();
      } else {
        tagView.selectView();
      }
    }
  }

  public void selectTagView(int position) {
    if (isTagViewSelectable)
      ((TagView) mChildViews.get(position)).selectView();
  }

  public void deselectTagView(int position) {
    if (isTagViewSelectable)
      ((TagView) mChildViews.get(position)).deselectView();
  }

  public List<Integer> getSelectedTagViewPositions() {
    List<Integer> selectedPositions = new ArrayList<>();
    for (int i = 0; i < mChildViews.size(); i++) {
      if (((TagView) mChildViews.get(i)).getIsViewSelected()) {
        selectedPositions.add(i);
      }
    }
    return selectedPositions;
  }

  public List<String> getSelectedTagViewText() {
    List<String> selectedTagText = new ArrayList<>();
    for (int i = 0; i < mChildViews.size(); i++) {
      TagView tagView = (TagView) mChildViews.get(i);
      if ((tagView.getIsViewSelected())) {
        selectedTagText.add(tagView.getText());
      }
    }
    return selectedTagText;
  }

  public int size() {
    return mChildViews.size();
  }

  public String getTagText(int position) {
    return ((TagView) mChildViews.get(position)).getText();
  }

  public List<String> getTags() {
    List<String> tmpList = new ArrayList<String>();
    for (View view : mChildViews) {
      if (view instanceof TagView) {
        tmpList.add(((TagView) view).getText());
      }
    }
    return tmpList;
  }

  public void setDragEnable(boolean enable) {
    this.mDragEnable = enable;
  }

  public boolean getDragEnable() {
    return mDragEnable;
  }

  public void setVerticalInterval(float interval) {
    mVerticalInterval = (int) dp2px(getContext(), interval);
    postInvalidate();
  }

  public int getVerticalInterval() {
    return mVerticalInterval;
  }

  public void setHorizontalInterval(float interval) {
    mHorizontalInterval = (int) dp2px(getContext(), interval);
    postInvalidate();
  }

  public int getHorizontalInterval() {
    return mHorizontalInterval;
  }

  public float getBorderWidth() {
    return mBorderWidth;
  }

  public void setBorderWidth(float width) {
    this.mBorderWidth = width;
  }

  public float getBorderRadius() {
    return mBorderRadius;
  }

  public void setBorderRadius(float radius) {
    this.mBorderRadius = radius;
  }

  public int getBorderColor() {
    return mBorderColor;
  }

  public void setBorderColor(int color) {
    this.mBorderColor = color;
  }

  public int getBackgroundColor() {
    return mBackgroundColor;
  }

  public void setBackgroundColor(int color) {
    this.mBackgroundColor = color;
  }

  public int getGravity() {
    return mGravity;
  }

  public void setGravity(int gravity) {
    this.mGravity = gravity;
  }

  public float getSensitivity() {
    return mSensitivity;
  }

  public void setSensitivity(float sensitivity) {
    this.mSensitivity = sensitivity;
  }

  public int getDefaultImageDrawableID() {
    return mDefaultImageDrawableID;
  }

  public void setDefaultImageDrawableID(int imageID) {
    this.mDefaultImageDrawableID = imageID;
  }

  public void setMaxLines(int maxLines) {
    mMaxLines = maxLines;
    postInvalidate();
  }

  public int getMaxLines() {
    return mMaxLines;
  }

  public void setTagMaxLength(int maxLength) {
    mTagMaxLength = maxLength < TAG_MIN_LENGTH ? TAG_MIN_LENGTH : maxLength;
  }

  public int getTagMaxLength() {
    return mTagMaxLength;
  }

  public void setTheme(int theme) {
    mTheme = theme;
  }

  public int getTheme() {
    return mTheme;
  }

  public boolean getIsTagViewClickable() {
    return isTagViewClickable;
  }

  public void setIsTagViewClickable(boolean clickable) {
    this.isTagViewClickable = clickable;
  }

  public boolean getIsTagViewSelectable() {
    return isTagViewSelectable;
  }

  public void setIsTagViewSelectable(boolean selectable) {
    this.isTagViewSelectable = selectable;
  }

  public float getTagBorderWidth() {
    return mTagBorderWidth;
  }

  public void setTagBorderWidth(float width) {
    this.mTagBorderWidth = width;
  }

  public float getTagBorderRadius() {
    return mTagBorderRadius;
  }

  public void setTagBorderRadius(float radius) {
    this.mTagBorderRadius = radius;
  }

  public float getTagTextSize() {
    return mTagTextSize;
  }

  public void setTagTextSize(float size) {
    this.mTagTextSize = size;
  }

  public int getTagHorizontalPadding() {
    return mTagHorizontalPadding;
  }

  public void setTagHorizontalPadding(int padding) {
    int ceilWidth = ceilTagBorderWidth();
    this.mTagHorizontalPadding = padding < ceilWidth ? ceilWidth : padding;
  }

  public int getTagVerticalPadding() {
    return mTagVerticalPadding;
  }

  public void setTagVerticalPadding(int padding) {
    int ceilWidth = ceilTagBorderWidth();
    this.mTagVerticalPadding = padding < ceilWidth ? ceilWidth : padding;
  }

  public int getTagBorderColor() {
    return mTagBorderColor;
  }

  public void setTagBorderColor(int color) {
    this.mTagBorderColor = color;
  }

  public int getTagBackgroundColor() {
    return mTagBackgroundColor;
  }

  public void setTagBackgroundColor(int color) {
    this.mTagBackgroundColor = color;
  }

  public int getTagTextColor() {
    return mTagTextColor;
  }

  public void setTagTextDirection(int textDirection) {
    this.mTagTextDirection = textDirection;
  }

  public Typeface getTagTypeface() {
    return mTagTypeface;
  }

  public void setTagTypeface(Typeface typeface) {
    this.mTagTypeface = typeface;
  }

  public int getTagTextDirection() {
    return mTagTextDirection;
  }

  public void setTagTextColor(int color) {
    this.mTagTextColor = color;
  }

  public int getRippleAlpha() {
    return mRippleAlpha;
  }


  public void setRippleAlpha(int mRippleAlpha) {
    this.mRippleAlpha = mRippleAlpha;
  }

  public int getRippleColor() {
    return mRippleColor;
  }

  public void setRippleColor(int mRippleColor) {
    this.mRippleColor = mRippleColor;
  }

  public int getRippleDuration() {
    return mRippleDuration;
  }

  public void setRippleDuration(int mRippleDuration) {
    this.mRippleDuration = mRippleDuration;
  }

  public int getCrossColor() {
    return mCrossColor;
  }

  public void setCrossColor(int mCrossColor) {
    this.mCrossColor = mCrossColor;
  }

  public float getCrossAreaPadding() {
    return mCrossAreaPadding;
  }

  public void setCrossAreaPadding(float mCrossAreaPadding) {
    this.mCrossAreaPadding = mCrossAreaPadding;
  }

  public boolean isEnableCross() {
    return mEnableCross;
  }

  public void setEnableCross(boolean mEnableCross) {
    this.mEnableCross = mEnableCross;
  }

  public float getCrossAreaWidth() {
    return mCrossAreaWidth;
  }

  public void setCrossAreaWidth(float mCrossAreaWidth) {
    this.mCrossAreaWidth = mCrossAreaWidth;
  }

  public float getCrossLineWidth() {
    return mCrossLineWidth;
  }

  public void setCrossLineWidth(float mCrossLineWidth) {
    this.mCrossLineWidth = mCrossLineWidth;
  }

  public boolean isTagSupportLettersRTL() {
    return mTagSupportLettersRTL;
  }

  public void setTagSupportLettersRTL(boolean mTagSupportLettersRTL) {
    this.mTagSupportLettersRTL = mTagSupportLettersRTL;
  }

  public TagView getTagView(int position) {
    if (position < 0 || position >= mChildViews.size()) {
      throw new RuntimeException("Illegal position!");
    }
    return (TagView) mChildViews.get(position);
  }

  public int getTagBackgroundResource() {
    return mTagBackgroundResource;
  }

  public void setTagBackgroundResource(@DrawableRes int tagBackgroundResource) {
    this.mTagBackgroundResource = tagBackgroundResource;
  }
}

