package cn.molue.jooyer.custommenu

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * Desc: 自定义普通菜单,格式如下
 *  图片 -- 文字  --------------- 文字/图片  图片
 * Author: Jooyer
 * Date: 2018-08-08
 * Time: 14:44
 */
class CustomMenu(context: Context, attr: AttributeSet, defStyleAttr: Int)
    : RelativeLayout(context, attr, defStyleAttr) {

    /**
     * 最左侧图标
     */
    private lateinit var iv_left_icon_menu: ImageView
    /**
     * 紧挨着左侧图标的文本
     */
    private lateinit var tv_left_name_menu: TextView
    /**
     * 紧挨着右侧的文本(与 紧挨着右侧的图片 只显示一种)
     */
    private lateinit var tv_right_name_menu: TextView
    /**
     * 紧挨着右侧的图片(与 紧挨着右侧的文本 只显示一种)
     */
    private lateinit var iv_near_right_icon_menu: ImageView
    /**
     * 最右侧图标(一般是向右箭头 →)
     */
    private lateinit var iv_right_arrow_menu: ImageView
    /**
     * 底部分割线
     */
    private lateinit var view_bottom_divider_menu: View

    private var rightTextRightMargin = 0

    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)

    init {
        initView()
        parseAttrs(context, attr)

    }

    private fun initView() {
        val parent = LayoutInflater.from(context).inflate( R.layout.menu_image_text_text_image, this,true)
        iv_left_icon_menu = parent.findViewById(R.id.iv_left_icon_menu)
        tv_left_name_menu = parent.findViewById(R.id.tv_left_name_menu)
        tv_right_name_menu = parent.findViewById(R.id.tv_right_name_menu)
        iv_near_right_icon_menu = parent.findViewById(R.id.iv_right_icon_menu)
        iv_right_arrow_menu = parent.findViewById(R.id.iv_right_arrow_menu)
        view_bottom_divider_menu = parent.findViewById(R.id.view_bottom_divider_menu)
    }

    private fun parseAttrs(context: Context, attr: AttributeSet) {
        val arr = context.obtainStyledAttributes(attr, R.styleable.CustomMenu)
        val leftImageVisible = arr.getBoolean(R.styleable.CustomMenu_cm_left_image_visible, true)
        val leftImageDrawable = arr.getDrawable(R.styleable.CustomMenu_cm_left_image_drawable)
        val leftImageWidth = arr.getDimension(R.styleable.CustomMenu_cm_left_image_width, dp2px(20F)).toInt()
        val leftImageHeight = arr.getDimension(R.styleable.CustomMenu_cm_left_image_height, dp2px(20F)).toInt()

        val leftTextInfo = arr.getText(R.styleable.CustomMenu_cm_left_text_info)
        val leftTextSize = arr.getInteger(R.styleable.CustomMenu_cm_left_text_size, 14).toFloat()
        val leftTextLeftMargin = arr.getDimension(R.styleable.CustomMenu_cm_left_text_left_margin, dp2px(10F)).toInt()
        val leftTextColor = arr.getColor(R.styleable.CustomMenu_cm_left_text_color,
                ContextCompat.getColor(context, R.color.color_111111))

        val rightTextInfo = arr.getText(R.styleable.CustomMenu_cm_right_text_info)
        val rightTextVisible = arr.getBoolean(R.styleable.CustomMenu_cm_right_text_visible, true)
        val rightTextSize = arr.getInt(R.styleable.CustomMenu_cm_right_text_size, 14).toFloat()
        rightTextRightMargin = arr.getDimension(R.styleable.CustomMenu_cm_right_text_right_margin, dp2px(20F)).toInt()
        val rightTextColor = arr.getColor(R.styleable.CustomMenu_cm_right_text_color,
                ContextCompat.getColor(context, R.color.color_111111))

        val rightNearImageVisible = arr.getBoolean(R.styleable.CustomMenu_cm_right_near_image_visible, false)
        val rightNearImageDrawable = arr.getDrawable(R.styleable.CustomMenu_cm_right_near_image_drawable)
        val rightNearImageWidth = arr.getDimension(R.styleable.CustomMenu_cm_right_near_image_width, dp2px(20F)).toInt()
        val rightNearImageHeight = arr.getDimension(R.styleable.CustomMenu_cm_right_near_image_height, dp2px(20F)).toInt()

        val rightDrawableVisible = arr.getBoolean(R.styleable.CustomMenu_cm_right_image_visible, true)
        val rightImageDrawable = arr.getDrawable(R.styleable.CustomMenu_cm_right_image_drawable)
        val rightImageWidth = arr.getDimension(R.styleable.CustomMenu_cm_right_image_width, dp2px(22F)).toInt()
        val rightImageHeight = arr.getDimension(R.styleable.CustomMenu_cm_right_image_height, dp2px(22F)).toInt()

        val bottomDividerVisible = arr.getBoolean(R.styleable.CustomMenu_cm_bottom_divider_visible, false)
        val bottomDividerColor = arr.getColor(R.styleable.CustomMenu_cm_bottom_divider_color,
                ContextCompat.getColor(context, R.color.color_EEEEEE))
        val bottomDividerLeftMargin = arr.getDimension(R.styleable.CustomMenu_cm_bottom_divider_left_margin, dp2px(20F)).toInt()

        iv_left_icon_menu.visibility = if (leftImageVisible) View.VISIBLE else View.GONE
        if (null != leftImageDrawable) {
            iv_left_icon_menu.setImageDrawable(leftImageDrawable)
        }
        val leftImageLp: RelativeLayout.LayoutParams = iv_left_icon_menu.layoutParams as LayoutParams
        leftImageLp.width = leftImageWidth
        leftImageLp.height = leftImageHeight
        iv_left_icon_menu.layoutParams = leftImageLp


        if (!TextUtils.isEmpty(leftTextInfo)) {
            tv_left_name_menu.text = leftTextInfo
            tv_left_name_menu.setTextColor(leftTextColor)
            tv_left_name_menu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, leftTextSize)

            val leftTextLp: RelativeLayout.LayoutParams = tv_left_name_menu.layoutParams as LayoutParams
            leftTextLp.marginStart = leftTextLeftMargin
            tv_left_name_menu.layoutParams = leftTextLp
        }


        tv_right_name_menu.visibility = if (rightTextVisible) View.VISIBLE else View.GONE
        if (!TextUtils.isEmpty(rightTextInfo)) {
            tv_right_name_menu.text = rightTextInfo
            tv_right_name_menu.setTextColor(rightTextColor)
            tv_right_name_menu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, rightTextSize)

            val rightTextLp: RelativeLayout.LayoutParams = tv_right_name_menu.layoutParams as LayoutParams
            if (rightDrawableVisible) {
                rightTextLp.marginEnd = rightTextRightMargin + dp2px(20F).toInt()
            } else {
                rightTextLp.marginEnd = rightTextRightMargin
            }
            tv_right_name_menu.layoutParams = rightTextLp
        }


        iv_near_right_icon_menu.visibility = if (rightNearImageVisible) View.VISIBLE else View.GONE
        if (null != rightNearImageDrawable) {
            iv_near_right_icon_menu.setImageDrawable(rightNearImageDrawable)
        }

        val rightNearImageLp: RelativeLayout.LayoutParams = iv_near_right_icon_menu.layoutParams as LayoutParams
        rightNearImageLp.width = rightNearImageWidth
        rightNearImageLp.height = rightNearImageHeight
        if (rightDrawableVisible) {
            rightNearImageLp.marginEnd = rightTextRightMargin + dp2px(20F).toInt()
        } else {
            rightNearImageLp.marginEnd = rightTextRightMargin
        }
        iv_near_right_icon_menu.layoutParams = rightNearImageLp


        iv_right_arrow_menu.visibility = if (rightDrawableVisible) View.VISIBLE else View.GONE
        if (null != rightImageDrawable) {
            iv_right_arrow_menu.setImageDrawable(rightImageDrawable)
        }

        val rightImageLp: RelativeLayout.LayoutParams = iv_right_arrow_menu.layoutParams as LayoutParams
        rightImageLp.width = rightImageWidth
        rightImageLp.height = rightImageHeight
        iv_right_arrow_menu.layoutParams = rightImageLp

        view_bottom_divider_menu.visibility = if (bottomDividerVisible) View.VISIBLE else View.GONE
        view_bottom_divider_menu.setBackgroundColor(bottomDividerColor)
        val bottomDividerLp: RelativeLayout.LayoutParams = view_bottom_divider_menu.layoutParams as LayoutParams
        bottomDividerLp.leftMargin = bottomDividerLeftMargin
        view_bottom_divider_menu.layoutParams = bottomDividerLp

        arr.recycle()
    }

    fun setRightTextVisible(isVisible: Boolean) {
        tv_right_name_menu.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setRightNearImageViewVisible(isVisible: Boolean) {
        iv_near_right_icon_menu.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun getRightNearImageView(): ImageView {
        return iv_near_right_icon_menu
    }

    fun setRightImageViewVisible(isVisible: Boolean) {
        val rightNearImageLp: RelativeLayout.LayoutParams = iv_near_right_icon_menu.layoutParams as LayoutParams
        val rightTextLp: RelativeLayout.LayoutParams = tv_right_name_menu.layoutParams as LayoutParams
        if (isVisible) {
            rightNearImageLp.marginEnd = rightTextRightMargin + dp2px(20F).toInt()
            rightTextLp.marginEnd = rightTextRightMargin + dp2px(20F).toInt()
            iv_right_arrow_menu.visibility = View.VISIBLE
        } else {
            rightNearImageLp.marginEnd = rightTextRightMargin
            rightTextLp.marginEnd = rightTextRightMargin
            iv_right_arrow_menu.visibility = View.GONE
        }

        iv_near_right_icon_menu.layoutParams = rightNearImageLp
        tv_right_name_menu.layoutParams = rightTextLp
    }


    fun setLeftText(text: String?) {
        text?.let {
            tv_left_name_menu.text = text
        }

    }

    fun setLeftText(text: String?, color: Int) {
        text?.let {
            tv_left_name_menu.text = text
            tv_left_name_menu.setTextColor(color)
        }
    }

    fun setRightText(text: String?, color: Int) {
        text?.let {
            tv_right_name_menu.text = it
            tv_right_name_menu.setTextColor(color)
        }
    }

    fun setRightText(text: String?) {
        text?.let {
            tv_right_name_menu.text = it
        }
    }

    private fun dp2px(def: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, def, context.resources.displayMetrics)
    }

}