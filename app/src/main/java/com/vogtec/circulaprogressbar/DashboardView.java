package com.vogtec.circulaprogressbar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class DashboardView extends View {
	private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private float maxSpeed;
	private float speed;
	private float currentSpeed;
	private int fromAngle;
	private int sweepAngle;
	private Path backgroundPath = new Path();
	private Path progressPath = new Path();

	private int rectHeight;
	private int rectWidth;

	private Shader shader;

	private int green;

	private Animator animator;
	// RectF rectF1 = new RectF();
	private RectF oval = new RectF();
	private Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	@TargetApi(21)
	public DashboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public DashboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public DashboardView(Context context) {
		super(context);
	}

	private void init(Context context, AttributeSet attrs) {
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DashboardView);
		green = Color.RED;
		maxSpeed = a.getInt(R.styleable.DashboardView_max_speed, 50);// 最大值
		speed = a.getInt(R.styleable.DashboardView_speed, 0);// 当前值
		fromAngle = a.getInt(R.styleable.DashboardView_from_angle, 160);// 开始角度
		sweepAngle = a.getInt(R.styleable.DashboardView_sweep_angle, 220);// 角度

		rectWidth = a.getDimensionPixelSize(R.styleable.DashboardView_rect_with, 10);// 方块的宽度
		rectHeight = a.getDimensionPixelSize(R.styleable.DashboardView_rect_height, 10);// 方块的高度
		a.recycle();

		backgroundPaint.setColor(getResources().getColor(R.color.background));
		backgroundPaint.setStrokeWidth(rectHeight);
		backgroundPaint.setStyle(Style.STROKE);
		backgroundPaint.setStrokeCap(Paint.Cap.ROUND);
		backgroundPaint.setAntiAlias(true);
		backgroundPaint.setDither(true);

		progressPaint.setColor(getResources().getColor(R.color.circle));
		progressPaint.setStrokeWidth(rectHeight);
		progressPaint.setStyle(Style.STROKE);
		progressPaint.setStrokeCap(Paint.Cap.ROUND);
		progressPaint.setAntiAlias(true);
		progressPaint.setDither(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		oval.set(0, 0, getMeasuredWidth() - rectHeight, getMeasuredWidth() - rectHeight);
		backgroundPath.reset();
		backgroundPath.arcTo(oval, fromAngle, sweepAngle);
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
		postInvalidate();
	}

	public void setSpeed(float speed) {
		if (speed > maxSpeed) {
			speed = maxSpeed;
		}
		animateTo(this.speed, speed);
		this.speed = speed;
	}

	private void animateTo(float fromSpeed, float toSpeed) {
		animator = ObjectAnimator.ofFloat(this, "currentSpeed", fromSpeed, toSpeed);
		animator.setDuration(1500);
		animator.start();
	}

	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
		progressPath.reset();
		progressPath.arcTo(oval, fromAngle, currentSpeed / maxSpeed * sweepAngle);
		postInvalidate();
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public float getSpeed() {
		return speed;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		int save = canvas.save();
		canvas.translate(rectHeight / 2f, rectHeight / 2f); // 把当前画布的原点移到(rectHeight
															// / 2f, rectHeight
															// /
															// 2f),后面的操作都以(rectHeight
															// / 2f, rectHeight
															// /
															// 2f)作为参照点，默认原点为(0,0)
		canvas.drawPath(backgroundPath, backgroundPaint);
		canvas.drawPath(progressPath, progressPaint);
		canvas.restoreToCount(save); // restoreToCount是返回到指定点的save(可能有多次save),restore是返回到最近的save点
		// canvas.drawPath(scalePath, paint);
		// canvas.drawArc(rectF1, -190, 199.5f, false, paint1);// 绘制圆弧

	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

}
