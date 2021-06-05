package amr.barakat.muzica.ui

import amr.barakat.muzica.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout


class RoundedGridItemView : ConstraintLayout {
    private var topLeftCornerRadius = 0f
    private var topRightCornerRadius = 0f
    private var bottomLeftCornerRadius = 0f
    private var bottomRightCornerRadius = 0f


    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundedGridItemView, 0, 0
        )

        //get the default value form the attrs
        topLeftCornerRadius = typedArray.getDimension(
            R.styleable.RoundedGridItemView_topLeftCornerRadius, 0f
        )
        topRightCornerRadius = typedArray.getDimension(
            R.styleable.RoundedGridItemView_topRightCornerRadius, 0f
        )
        bottomLeftCornerRadius = typedArray.getDimension(
            R.styleable.RoundedGridItemView_bottomLeftCornerRadius, 0f
        )
        bottomRightCornerRadius = typedArray.getDimension(
            R.styleable.RoundedGridItemView_bottomRightCornerRadius, 0f
        )

        typedArray.recycle()
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun dispatchDraw(canvas: Canvas) {
        val count: Int = canvas.save()
        val path = Path()
        val cornerDimensions = floatArrayOf(
            topLeftCornerRadius, topLeftCornerRadius,
            topRightCornerRadius, topRightCornerRadius,
            bottomRightCornerRadius, bottomRightCornerRadius,
            bottomLeftCornerRadius, bottomLeftCornerRadius
        )
        path.addRoundRect(
            RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat()),
            cornerDimensions,
            Path.Direction.CW
        )
        canvas.clipPath(path)
        canvas.clipPath(path)
        super.dispatchDraw(canvas)
        canvas.restoreToCount(count)
    }

    fun setTopLeftCornerRadius(topLeftCornerRadius: Float) {
        this.topLeftCornerRadius = topLeftCornerRadius
        invalidate()
    }

    fun setTopRightCornerRadius(topRightCornerRadius: Float) {
        this.topRightCornerRadius = topRightCornerRadius
        invalidate()
    }

    fun setBottomLeftCornerRadius(bottomLeftCornerRadius: Float) {
        this.bottomLeftCornerRadius = bottomLeftCornerRadius
        invalidate()
    }

    fun setBottomRightCornerRadius(bottomRightCornerRadius: Float) {
        this.bottomRightCornerRadius = bottomRightCornerRadius
        invalidate()
    }
}