import android.view.animation.Animation;
import android.view.animation.Transformation;


public class CircleArcAnimation extends Animation {

    private CircleArcView arcView;

    private float oldAngle;
    private float newAngle;

    public CircleArcAnimation(CircleArcView arcView, int newAngle) {
        this.oldAngle = arcView.getArcAngle();
        this.newAngle = newAngle;
        this.arcView = arcView;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = 0 + ((newAngle - oldAngle) * interpolatedTime);

        arcView.setArcAngle(angle);
        arcView.requestLayout();
    }
}
