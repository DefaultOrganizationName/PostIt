package post.it.project.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import post.it.project.fragment.PostFragment;
import post.it.project.postit.DraftsEntry;
import post.it.project.postit.R;

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
        holder.imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        holder.postView.setText(draft.post.post_text);
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
        DraftsViewHolder(View itemView) {
            super(itemView);
            postView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        static DraftsViewHolder newInstance(LayoutInflater layoutInflater, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.item_draft, parent, false);
            return new DraftsViewHolder(view);
        }
    }

    private final String TAG = "EntryAdapter";
}
