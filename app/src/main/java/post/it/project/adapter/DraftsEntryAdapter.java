package post.it.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import post.it.project.database.DraftDatabase;
import post.it.project.fragment.PostFragment;
import post.it.project.postit.DraftsEntry;
import post.it.project.postit.MainActivity;
import post.it.project.postit.ParcelablePost;
import post.it.project.postit.R;
import post.it.project.social_networks.Constants;
import post.it.project.social_networks.SocialNetworksActivity;
import post.it.project.utils.Utils;

/**
 * Created by Михаил on 16.12.2016.
 */

public class DraftsEntryAdapter extends RecyclerView.Adapter<DraftsEntryAdapter.DraftsViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<DraftsEntry> posts;

    public DraftsEntryAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setPost(List<DraftsEntry> posts) {
        this.posts = posts;
//        notifyDataSetChanged();
    }

    @Override
    public DraftsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DraftsViewHolder.newInstance(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(DraftsViewHolder holder, int position) {
        final DraftsEntry draft = posts.get(position);
        Log.d("adapter", draft.post.image_path);
        holder.imageView.setImageBitmap(Utils.rotatePic(draft.post.image_path));
//        holder.imageView.setImageBitmap(((BitmapDrawable) PostFragment.iw.getDrawable()).getBitmap());
        holder.postView.setText(draft.post.post_text);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DraftDatabase.delete(draft);
                MainActivity.mViewPager.getAdapter().notifyDataSetChanged();
            }
        });

        holder.postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostFragment.setPostFromDraft(draft.post);
                DraftDatabase.delete(draft);
                MainActivity.mViewPager.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (posts == null) {
            return 0;
        }
        return posts.size();
    }

    static class DraftsViewHolder extends RecyclerView.ViewHolder {
        final TextView postView;
        final ImageView imageView;
        final Button deleteBtn;
        final Button postBtn;

        DraftsViewHolder(View itemView) {
            super(itemView);
            postView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
            postBtn = (Button) itemView.findViewById(R.id.postBtn);
        }

        static DraftsViewHolder newInstance(LayoutInflater layoutInflater, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.item_draft, parent, false);
            return new DraftsViewHolder(view);
        }
    }

    private final String TAG = "EntryAdapter";
}
