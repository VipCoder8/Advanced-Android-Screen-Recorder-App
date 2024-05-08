package bee.corp.bcorder.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import bee.corp.bcorder.R;
import bee.corp.bcorder.VideoActivity;
import bee.corp.bcorder.view.DialogCreator;
import bee.corp.bcorder.view.VideoTab;

public class VideoAdapter extends RecyclerView.Adapter<VideoTab>{

    private LayoutInflater layoutInflater;
    public ArrayList<VideoTab> videoTabs;
    private ArrayList<VideoTab> oldVideoTabs;
    private Activity activity;

    public VideoAdapter(ArrayList<VideoTab> vt, Context c, Activity a) {
        this.layoutInflater = LayoutInflater.from(c);
        this.videoTabs = vt;
        this.oldVideoTabs = new ArrayList<>(vt);
        this.activity = a;
    }

    @NonNull
    @Override
    public VideoTab onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View videoTab = layoutInflater.inflate(R.layout.video_item, parent, false);
        return new VideoTab(videoTab.getContext(), R.layout.video_item);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoTab holder, int position) {
        VideoTab tab = videoTabs.get(position);
        holder.setVideoPath(tab.getVideoPath());
        holder.setAdapter(this);
        View.OnClickListener editButtonClickListener = v -> {
            File file = new File(holder.getVideoPath());
            AlertDialog editTextDialog = DialogCreator.CreateEditTextDialog(this.layoutInflater.getContext(),
                    "Edit Name",
                    "Ok", "Cancel",
                    (dialog, which) -> {
                        if(file.renameTo(new File(file.getParent() + "/" + DialogCreator.getEditText().getText() + ".mp4"))) {
                            holder.setTitle(DialogCreator.getEditText().getText().toString() + ".mp4");
                            holder.setVideoPath(file.getParent() + "/" + DialogCreator.getEditText().getText() + ".mp4");
                            Toast.makeText(this.layoutInflater.getContext(), "Successfully renamed the file", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this.layoutInflater.getContext(), "Hmm, something went wrong while renaming file", Toast.LENGTH_SHORT).show();
                        }
                    }, (dialog, which) -> dialog.dismiss(), ConstraintLayout.LayoutParams.MATCH_PARENT, 30);
            editTextDialog.show();
        };
        View.OnClickListener deleteButtonClickListener = v -> {
            File file = new File(holder.getVideoPath());
            AlertDialog assuranceDialog = DialogCreator.CreateAssuranceDialog(this.layoutInflater.getContext(),
                    "File Deletion", "Are you sure you want to delete this file?", "YES",
                    "NO",
                    (dialog, which) -> {
                        if(file.delete()) {
                            videoTabs.remove(videoTabs.indexOf(tab));
                            notifyItemRemoved(videoTabs.indexOf(tab));
                            Toast.makeText(this.layoutInflater.getContext(), "Deleted File Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this.layoutInflater.getContext(), "Hmm, something went wrong while deleting file", Toast.LENGTH_SHORT).show();
                        }
                    },
                    (dialog, which) -> {
                        dialog.dismiss();
                    });
            assuranceDialog.show();
        };
        VideoTab.OnVideoTabTouchListener videoTabClickListener = new VideoTab.OnVideoTabTouchListener() {
            @Override public void tabPressed() {}
            @Override
            public void tabReleased() {
                Intent videoActivityIntent = new Intent(VideoAdapter.this.layoutInflater.getContext(), VideoActivity.class);
                videoActivityIntent.putExtra("video_path", holder.getVideoPath());
                activity.startActivity(videoActivityIntent);
            }
        };
        holder.setVideoTabTouchListener(videoTabClickListener);
        holder.getEditVideoTitleButton().setOnClickListener(editButtonClickListener);
        holder.getDeleteVideoTitleButton().setOnClickListener(deleteButtonClickListener);
        holder.setTitle(tab.getTitle());
        holder.setDuration(tab.getDuration());
        holder.setVideoPreview(tab.getVideoPreview());
    }

    public void setFilteredList(ArrayList<VideoTab> l) {
        this.videoTabs = l;
        this.notifyDataSetChanged();
    }

    public void restoreList() {
        System.out.println(this.oldVideoTabs.size());
        this.videoTabs = this.oldVideoTabs;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return videoTabs.size();
    }
}
