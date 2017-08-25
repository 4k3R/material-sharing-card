package anjithsasindran.materialsharingcard;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends Activity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.main_card)
    CardView mainCard;
    @BindView(R.id.revealView)
    LinearLayout revealView;
    @BindView(R.id.layoutButtons)
    LinearLayout layoutButtons;
    @BindView(R.id.launchTwitterAnimation)
    ImageButton imageButton;
    @BindView(R.id.duplicate)
    Button duplicate;
    @BindView(R.id.schedule)
    Button schedule;
    @BindView(R.id.delete)
    Button delete;

    private Animation fadeIn;
    private Animation fadeOut;
    private Animation rotateClock;
    private Animation rotateAnticlock;
    private TransitionDrawable backgroundTransition;
    private TransitionDrawable iconTransition;
    private float pixelDensity;
    private boolean flag = false;
    private boolean duplicatePressed = false;
    private boolean schedulePressed = false;
    private boolean deletePressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pixelDensity = getResources().getDisplayMetrics().density;

        backgroundTransition = (TransitionDrawable) imageButton.getBackground();
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        rotateClock = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        rotateAnticlock = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlock);
        rotateClock.setFillAfter(true);
        rotateAnticlock.setFillAfter(true);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTwitter(view);
            }
        });

        duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!duplicatePressed){
                    duplicatePressed = true;
                    duplicate.setBackgroundResource(R.drawable.solid_button);
                    duplicate.setTextColor(Color.parseColor("#333333"));
                }else{
                    duplicatePressed = false;
                    duplicate.setBackgroundResource(R.drawable.stroke_button);
                    duplicate.setTextColor(Color.WHITE);
                }
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!schedulePressed){
                    schedulePressed = true;
                    schedule.setBackgroundResource(R.drawable.solid_button);
                    schedule.setTextColor(Color.parseColor("#333333"));
                }else{
                    schedulePressed = false;
                    schedule.setBackgroundResource(R.drawable.stroke_button);
                    schedule.setTextColor(Color.WHITE);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!deletePressed){
                    deletePressed = true;
                    delete.setBackgroundResource(R.drawable.solid_button);
                    delete.setTextColor(Color.parseColor("#333333"));
                }else{
                    deletePressed = false;
                    delete.setBackgroundResource(R.drawable.stroke_button);
                    delete.setTextColor(Color.WHITE);
                }
            }
        });

    }

    public void launchTwitter(View view) {

        float k = ((28 * pixelDensity) + (16 * pixelDensity));
        final int x = imageView.getRight() - (int) k;
        final int y = imageView.getBottom();
        final int hypotenuse = (int) Math.hypot(imageView.getWidth(), imageView.getHeight());

        if (flag == false) {
            flag = true;
            imageButton.startAnimation(rotateClock);
            backgroundTransition.startTransition(250);
            //imageButton.setBackgroundResource(R.drawable.rounded_cancel_button);
            //imageButton.setImageResource(R.mipmap.image_cancel);

            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams) revealView.getLayoutParams();
            parameters.height = imageView.getHeight();
            revealView.setLayoutParams(parameters);
            revealView.setVisibility(View.VISIBLE);
            layoutButtons.setVisibility(View.GONE);

            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, 0, hypotenuse);
            anim.setDuration(250);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.VISIBLE);
                    layoutButtons.startAnimation(fadeIn);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            anim.start();

        } else {

            imageButton.startAnimation(rotateAnticlock);
            backgroundTransition.reverseTransition(250);
            //imageButton.setBackgroundResource(R.drawable.xml_twitter_back);
            //imageButton.setImageResource(R.mipmap.image_cancel);
            layoutButtons.startAnimation(fadeOut);

            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layoutButtons.setVisibility(View.GONE);
                    Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, hypotenuse, 0);
                    anim.setDuration(250);
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            revealView.setVisibility(View.GONE);
                            flag = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    });
                    anim.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

}