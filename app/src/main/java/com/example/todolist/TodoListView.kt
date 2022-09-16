package com.example.todolist

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.app.NotificationCompat
import com.example.todolist.databinding.ItemLayoutBinding
import com.example.todolist.db.Info
import com.example.todolist.utils.VibrateHelp
import com.example.todolist.utils.setAlarmToNotification
import com.example.todolist.utils.swipeDenoteDeleting
import com.example.todolist.utils.swipeDenoteNormal
import tyrantgit.explosionfield.ExplosionField
import kotlin.math.abs

class TodoListView : View {

    lateinit var info: Info
    private var millisecond = 0
    private lateinit var binding: ItemLayoutBinding
    private var isSwiping = false
    private var preEventX = 0f
    private var preEventY = 0f
    private var laterEventX = 0f
    private var laterEventY = 0f
    private var isStarted = false
    private var ifTrash = false
    private lateinit var mContext: Context
    var rightNum = 0
    var leftNum = 0
    private lateinit var leftImageRect: Rect
    private lateinit var rightImageRect: Rect
    private var timeY = 0f
    private var timeStr = ""
    private lateinit var typedArray: TypedArray
    private var startLineLen = 0f
    private var isDeleting = false
    private var leftImage: Drawable? = null
    private lateinit var leftBitmap: Bitmap
    private var rightImage: Drawable? = null
    private lateinit var rightBitmap: Bitmap
    private var textY = 0f
    private var mWidth = 0
    private var mHeight = 0
    private var mBackgroundColor = context.getColor(R.color.purple_200)
    private var mLightColor = context.getColor(R.color.purple_200)
    private var mTextSize = 15
    private var mText: String? = null
    private var rx = 0f
    private var ry = 0f
    var updateUI: (((String) -> Unit))? = null

    private val bgPaint = Paint().apply {
        color = mBackgroundColor
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val linePaint = Paint().apply {
        color = Color.parseColor("#e1e0e4")
        strokeWidth = 5f
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val timePaint = Paint().apply {
        textSize = 30f
        color = context.getColor(android.R.color.holo_blue_light)
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }


    private val textPaint = Paint().apply {
        color = context.getColor(R.color.black)
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val lightPaint = Paint().apply {
        color = Color.parseColor("#89FB04")
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    constructor(context: Context) : super(context)


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
        init(context, attributeSet)
    }


    constructor(context: Context, attributeSet: AttributeSet, style: Int) : super(
        context,
        attributeSet,
        style
    )

    private fun init(context: Context, attributeSet: AttributeSet) {
        typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TodoListView)
        initBackground()
        initColor()
        initText()
        initTime(timeStr)
        initLeftImage()
        initRightImage()
        typedArray.recycle()
    }

    private fun initBackground() {
        mBackgroundColor =
            typedArray.getColor(R.styleable.TodoListView_backgroundColor, 0)
        bgPaint.color = mBackgroundColor
    }

    private fun initColor() {
        mLightColor = typedArray.getColor(R.styleable.TodoListView_color, 0)
        lightPaint.color = mLightColor
    }

    private fun initText() {
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TodoListView_textSize, 15)
        textPaint.textSize = mTextSize.toFloat()
        mText = typedArray.getString(R.styleable.TodoListView_text)
        val fontFamily = typedArray.getString(R.styleable.TodoListView_fontFamily)
        if (fontFamily == "res/font/my_font.ttf") {
            val typeFace = resources.getFont(R.font.my_font)
            textPaint.typeface = typeFace
        }
    }

    fun initTime(time: String) {
        timeStr = if (time != " : ") {
            time
        } else {
            "12 : 00"
        }
    }

    fun getMillisecond(millisecond: Int) {
        this.millisecond = millisecond
    }

    private fun initLeftImage() {
        leftImage = typedArray.getDrawable(R.styleable.TodoListView_left_image)
        leftBitmap = BitmapFactory.decodeResource(resources, R.drawable.checked_empty)

    }

    fun remainTheStatusAndImage(text: String) {
        leftBitmap = if (leftNum % 2 != 0) {
            isDeleting = true
            textPaint.color = Color.parseColor("#e1e0e4")
            timePaint.color = Color.parseColor("#e1e0e4")
            val bounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, bounds)
            startDeleteAnimator(bounds, 1)
            BitmapFactory.decodeResource(resources, R.drawable.checked)
        } else {
            isDeleting = false
            textPaint.color = context.getColor(R.color.black)
            timePaint.color = context.getColor(android.R.color.holo_blue_light)
            BitmapFactory.decodeResource(resources, R.drawable.checked_empty)
        }

        rightBitmap = if (rightNum % 2 != 0) {
            BitmapFactory.decodeResource(resources, R.drawable.bell_small_yellow)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.bell_small)
        }
        invalidate()
    }

    private fun changeTheStatusWithLeftImage() {
        leftNum++
        leftBitmap = if (leftNum % 2 == 0) {
            cancelDeleteTheTask()
            BitmapFactory.decodeResource(resources, R.drawable.checked_empty)
        } else {
            deleteTheTask()
            BitmapFactory.decodeResource(resources, R.drawable.checked)
        }
        invalidate()
    }

    private fun initRightImage() {
        rightImage = typedArray.getDrawable(R.styleable.TodoListView_right_image)
        rightBitmap = BitmapFactory.decodeResource(resources, R.drawable.bell_small)


    }

    private fun changeTheStatusWithRightImage() {
        rightNum++
        rightBitmap = if (rightNum % 2 == 0) {
            BitmapFactory.decodeResource(resources, R.drawable.bell_small)
        } else {
            val push = Intent(mContext, MainActivity::class.java)
            val contentIntent =
                PendingIntent.getActivity(mContext, 0, push, PendingIntent.FLAG_IMMUTABLE)
            val notificationManager =
                mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel =
                NotificationChannel("status", "状态", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            val builder = NotificationCompat.Builder(mContext, "default")
                .setSmallIcon(R.drawable.icon)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("设置成功")
                .setContentIntent(contentIntent)
                .setFullScreenIntent(contentIntent, true)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setChannelId("status")
                .setContentText("将会在${timeStr}之前提醒您，请及时完成")
            notificationManager.notify(1, builder.build())
            VibrateHelp().vSimple(mContext, 200)


            mText?.let { setAlarmToNotification(mContext, millisecond, true, it, timeStr) }


            BitmapFactory.decodeResource(resources, R.drawable.bell_small_yellow)


        }
        invalidate()
    }


    private fun deleteTheTask() {
        isDeleting = true
        textPaint.color = Color.parseColor("#e1e0e4")
        timePaint.color = Color.parseColor("#e1e0e4")
        val bounds = Rect()
        textPaint.getTextBounds(mText, 0, mText!!.length, bounds)
        startDeleteAnimator(bounds, 400)
    }

    private fun cancelDeleteTheTask() {
        isDeleting = false
        textPaint.color = context.getColor(R.color.black)
        timePaint.color = context.getColor(android.R.color.holo_blue_light)
    }

    @SuppressLint("Recycle")
    private fun startDeleteAnimator(bounds: Rect, mDuration: Int) {
        ValueAnimator.ofFloat(240f, 240f + bounds.right + 5f).apply {
            interpolator = LinearInterpolator()
            duration = mDuration.toLong()
            addUpdateListener {
                startLineLen = it.animatedValue as Float
                invalidate()
            }
        }.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas.let {
            it!!.drawRoundRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), rx, rx, bgPaint)
            it.drawRoundRect(20f, 0f, 50f, mHeight.toFloat(), 0f, 0f, lightPaint)
            mText.let { text ->
                it.drawText(text!!, 0, text.length, 240f, textY, textPaint)
            }
            it.drawText(timeStr, 0, timeStr.length, 135f, timeY, timePaint)
            it.drawBitmap(leftBitmap, 70f, (height - leftBitmap.height) / 2f, lightPaint)
            it.drawBitmap(
                rightBitmap,
                (width - rightBitmap.width) - 30f,
                (height - leftBitmap.height) / 2f,
                lightPaint
            )

            if (isDeleting) {
                it.drawLine(240f, height / 2f, startLineLen, height / 2f, linePaint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        mWidth = width
        mHeight = height
        rx = mHeight * 0.2f
        ry = rx
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textY = getTextBaseline(textPaint)
        timeY = getTextBaseline(timePaint)

        leftImageRect = Rect(
            65,
            (height - leftBitmap.height) / 2 - 5,
            70 + leftBitmap.width + 5,
            (height - leftBitmap.height) / 2 + leftBitmap.height + 5
        )
        rightImageRect = Rect(
            (width - rightBitmap.width) - 50,
            (height - leftBitmap.height) / 2 - 20,
            (width - rightBitmap.width) - 30 + rightBitmap.width + 20,
            (height - leftBitmap.height) / 2 + rightBitmap.height + 20
        )
    }

    private fun getTextBaseline(paint: Paint): Float {
        val fontMetrics = paint.fontMetrics
        val bottom = fontMetrics.bottom
        val top = fontMetrics.top
        return height / 2f + (bottom + abs(top)) / 2f - bottom
    }

    fun setColor(color: String) {
        if (color != "0") {
            lightPaint.color = Color.parseColor(color)
            invalidate()
        }
    }

    fun setContent(content: String) {
        mText = content
        invalidate()
    }

    fun getCurrentInfo(info: Info) {
        this.info = info
    }

    fun getBinding(binding: ItemLayoutBinding) {
        this.binding = binding
        binding.imageView.setOnClickListener {
            it.visibility = INVISIBLE
            binding.view.visibility = INVISIBLE
            ExplosionField(mContext).explode(this@TodoListView)
            ifTrash = false
            updateUI?.let { it("") }

        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                preEventX = event.x
                preEventY = event.y

                //归零数据
                isStarted = false
            }
            MotionEvent.ACTION_UP -> {
                if (leftImageRect.contains((event.x).toInt(), (event.y).toInt())) {
                    changeTheStatusWithLeftImage()

                } else if (rightImageRect.contains((event.x).toInt(), (event.y).toInt())) {
                    changeTheStatusWithRightImage()
                }
            }

            MotionEvent.ACTION_MOVE -> {
                laterEventX = event.x
                laterEventY = event.y

                if (preEventX != laterEventX && preEventY != laterEventY) {
                    if (preEventX - laterEventX > 20 && preEventY - laterEventY < 30) {
                        if (!isSwiping) {
                            if (!isStarted) {
                                isStarted = true
                                isSwiping = true
                                swipeDenoteDeleting(this@TodoListView)
                                swipeDenoteDeleting(binding.view)
                                swipeDenoteDeleting(binding.imageView)
                                VibrateHelp().vSimple(mContext, 100)
                            }
                        }
                    }


                    if (laterEventX - preEventX > 20 && laterEventY - preEventY < 30) {
                        if (!isStarted) {
                            isStarted = true
                            isSwiping = false
                            swipeDenoteNormal(this@TodoListView)
                            swipeDenoteNormal(binding.view)
                            swipeDenoteNormal(binding.imageView)
                        }
                    }
                }
            }
        }
        return true
    }
}