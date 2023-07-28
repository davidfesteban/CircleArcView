

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class CircleArcView extends View {

    private List<Boolean> listaActivado;
    private float arcAngle = 0;
    private Paint mFirstArcPaint, mSecondArcPaint, mThirdArcPaint, mDefaultArcPaint;
    private RectF mRect;
    private int STROKE_WIDTH;
    private int centerX, centerY, radius;

    private List<Integer> colors;

    public CircleArcView(Context context) {
        super(context);
    }

    public CircleArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int strokeWidth, List<Integer> colors, List<Boolean> listaActivado) {
        STROKE_WIDTH = strokeWidth;
        this.listaActivado = listaActivado;
        this.colors = colors;

        //Todo: DFE -> Setearlo de con bucle
        mFirstArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setPaint(strokeWidth, mFirstArcPaint);
        mFirstArcPaint.setShader(new SweepGradient(0, getMeasuredHeight() / 2, ContextCompat.getColor(getContext(), colors.get(0)), ContextCompat.getColor(getContext(), colors.get(1))));

        mSecondArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setPaint(strokeWidth, mSecondArcPaint);


        mThirdArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setPaint(strokeWidth, mThirdArcPaint);


        mDefaultArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setPaint(strokeWidth, mDefaultArcPaint);
        mDefaultArcPaint.setShader(new SweepGradient(0, getMeasuredHeight() / 2, ContextCompat.getColor(getContext(), colors.get(6)), ContextCompat.getColor(getContext(), colors.get(6))));
    }

    private void setPaint(int strokeWidth, Paint mArcPaint) {
        mArcPaint.setDither(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(strokeWidth);
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setPathEffect(new CornerPathEffect(50));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRect == null) {
            centerX = getMeasuredWidth() / 2;
            centerY = getMeasuredHeight() / 2;
            radius = Math.min(centerX, centerY);

            int startTop = centerY - radius + STROKE_WIDTH / 2;
            int startLeft = centerX - radius + STROKE_WIDTH / 2;

            int endBottom = centerY + radius - STROKE_WIDTH / 2;
            int endRight = centerX + radius - STROKE_WIDTH / 2;

            mRect = new RectF(startLeft, startTop, endRight, endBottom);
        }

        canvasDrawThings(mRect, canvas);

    }

    private void canvasDrawThings(RectF mRect, Canvas canvas) {
        canvas.drawArc(mRect, 0F, 360F, false, mDefaultArcPaint);

        if (listaActivado.get(0)) {
            canvas.drawArc(mRect, 270F, arcAngle, false, mFirstArcPaint);
        }

        if (listaActivado.get(1)) {
            float[] positions2 = {30F / 360F, (arcAngle + 30F) / 360F};
            int[] colores2 = {ContextCompat.getColor(getContext(), colors.get(2)), ContextCompat.getColor(getContext(), colors.get(3))};
            mSecondArcPaint.setShader(new SweepGradient(0, getMeasuredHeight() / 2, colores2, positions2));
            canvas.drawArc(mRect, 30F, arcAngle, false, mSecondArcPaint);
        }

        if (listaActivado.get(2)) {
            float[] positions3 = {270F - arcAngle / 360F, 270F / 360F};
            int[] colores3 = {ContextCompat.getColor(getContext(), colors.get(4)), ContextCompat.getColor(getContext(), colors.get(5))};
            mThirdArcPaint.setShader(new SweepGradient(0, getMeasuredHeight() / 2, colores3, positions3));
            canvas.drawArc(mRect, 270F, arcAngle * -1, false, mThirdArcPaint);
        }
    }

    public List<Boolean> getListaActivado() {
        return listaActivado;
    }

    public void setListaActivado(List<Boolean> listaActivado) {
        this.listaActivado = listaActivado;
    }

    public float getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
    }
}
