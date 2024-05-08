package bee.corp.bcorder.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import bee.corp.bcorder.R;
import bee.corp.bcorder.utility.VideoAdapter;

public class VideoTab extends RecyclerView.ViewHolder {

    private TextView titleView;
    private TextView durationView;
    private ImageView videoPreviewView;
    private ImageButton deleteVideoButton;
    private ImageButton editVideoTitleButton;

    private Bitmap videoPreviewImage;
    private Animation scaleUpAnimation;
    private Animation scaleDownAnimation;

    private VideoAdapter adapter;

    private String videoPath;
    private OnVideoTabTouchListener onVideoTabTouchListener;

    @SuppressLint("ClickableViewAccessibility")
    public VideoTab(Context c, int resid) {
        super(LayoutInflater.from(c).inflate(resid, null));
        initAnimations(this.itemView.getContext());
        this.itemView.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                VideoTab.this.itemView.startAnimation(scaleUpAnimation);
                if(onVideoTabTouchListener != null) {
                    onVideoTabTouchListener.tabReleased();
                }
            } else if(event.getAction() == MotionEvent.ACTION_DOWN) {
                VideoTab.this.itemView.startAnimation(scaleDownAnimation);
                if(onVideoTabTouchListener != null) {
                    Log.v("tg", "yes");
                    onVideoTabTouchListener.tabPressed();
                }
            }
            return true;
        });
        initializeViews(this.itemView);
    }

    public void setAdapter(VideoAdapter va) {
        this.adapter = va;
    }

    public void setVideoTabTouchListener(OnVideoTabTouchListener e) {
        this.onVideoTabTouchListener = e;
    }
    public OnVideoTabTouchListener getOnVideoTabTouchListener() {return this.onVideoTabTouchListener;}

    private void initAnimations(Context c) {
        this.scaleUpAnimation = AnimationUtils.loadAnimation(c, R.anim.scale_up);
        this.scaleDownAnimation = AnimationUtils.loadAnimation(c, R.anim.scale_down);
    }

    private void initializeViews(View v) {
        this.titleView = v.findViewById(R.id.video_title);
        this.durationView = v.findViewById(R.id.video_duration);
        this.videoPreviewView = v.findViewById(R.id.video_preview_image);
        this.deleteVideoButton = v.findViewById(R.id.delete_button);
        this.editVideoTitleButton = v.findViewById(R.id.edit_button);
    }
    public void setVideoPath(String path) {this.videoPath = path;}
    public void setTitle(String title) {this.titleView.setText(title);}
    public void setDuration(String duration) {this.durationView.setText(duration);}
    public void setVideoPreview(Bitmap preview) {
        this.videoPreviewView.setImageBitmap(preview);
        this.videoPreviewImage = preview;
    }
    public ImageButton getEditVideoTitleButton() {return editVideoTitleButton;}
    public ImageButton getDeleteVideoTitleButton() {return deleteVideoButton;}
    public String getVideoPath() {return this.videoPath;}
    public String getTitle() {return titleView.getText().toString();}
    public String getDuration() {return durationView.getText().toString();}
    public Bitmap getVideoPreview() {return videoPreviewImage;}

    public interface OnVideoTabTouchListener {
        void tabPressed();
        void tabReleased();
    }
}
