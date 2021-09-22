package com.example.mylibrary.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.animation.MotionSpec

/**
 *  created by ws
 *   on 2021/4/11
 *   describe:自定义view可分为 自定义组合控件，继承系统ViewGroup，自绘View控件，自绘ViewGroup控件
 */
class CustomView {

}

//继承View类系统控件
//@JvmOverloads 自动帮助建造四个构造函数
class CustomViewExtendsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint1 = paint
        paint1.color = Color.RED
        paint1.strokeWidth = 5F
        canvas!!.drawLine(0F, height.toFloat(), width.toFloat(), height.toFloat(), paint1)
    }
}

class CustomViewExtendsViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas!!.drawColor(Color.parseColor("#50FF0000"))
    }
}

class SinceTheDrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    /**View默认在wrap_content和mathc_parent两个模式下都是match_parent,所以需要重写区分*/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == AT_MOST && heightMode == AT_MOST) {
            setMeasuredDimension(600, 600)
        } else if (widthMode == AT_MOST) {
            setMeasuredDimension(300, height)
        } else if (heightMode == AT_MOST) {
            setMeasuredDimension(width, 300)
        }
    }
}

/**onLayout必须要实现,如果里面的方法为空,那么该控件的子View就无法显示了.
 * 要想准确测量,onMeasure方法也要重写*/
class SinceTheDrawViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    var lastX = 0
    var lastY = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //获取宽高的测量模式以及测量值
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        //测量所有的子View
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val childAt = getChildAt(0)
        //如果没有子View,则View大小为0
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (widthMode == AT_MOST && heightMode == AT_MOST) {
            val measuredWidth = childAt.measuredWidth
            val measuredHeight = childAt.measuredHeight
            //根据需要的方式计算
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else if (widthMode == AT_MOST) {
            val measuredWidth = childAt.measuredWidth
            //宽为所有子View的宽度,高为父布局的高
            setMeasuredDimension(measuredWidth * childCount, height)
        } else if (height == AT_MOST) {
            val measuredHeight = childAt.measuredHeight
            //宽为父布局,高为子View
            setMeasuredDimension(width, measuredHeight)
        }
        //剩下的情况默认就是mathc_parent,不用判断了


    }

    /**对各个子View设置位置*/
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        var zuo: Int = 0
        for (i in 0..childCount) {
            val childAt = getChildAt(i)
            //根据需要自定义位置的设置
            if (childAt.visibility != View.GONE) {
                val measuredWidth = childAt.measuredWidth
                childAt.layout(zuo, 0, zuo + measuredWidth, childAt.measuredHeight)
                zuo += measuredWidth
            }
        }
    }


    //根据需要重写滑动事件,判断是否拦截
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var intercrpt = false
        //记录当前点击的坐标
        val x = ev!!.x
        val y = ev.y
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
               intercrpt = true
            }
            MotionEvent.ACTION_UP ->{

            }
            MotionEvent.ACTION_DOWN ->{

            }
        }
        //true 为拦截
        return intercrpt
    }

    //拦截事件后如何处理
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //让父层的View不要拦截点击事件
        //getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev)
    }

}

