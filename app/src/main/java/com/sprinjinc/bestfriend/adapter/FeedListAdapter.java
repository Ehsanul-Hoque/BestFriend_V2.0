package com.sprinjinc.bestfriend.adapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sprinjinc.bestfriend.FeedImageView;
import com.sprinjinc.bestfriend.HomeActivity;
import com.sprinjinc.bestfriend.QuestionStreamActivity;
import com.sprinjinc.bestfriend.R;
import com.sprinjinc.bestfriend.app.AppController;
import com.sprinjinc.bestfriend.data.FeedItem;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    private MediaController mediaControls;
    private ProgressDialog mProgressDialog;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    FeedItem item;
    ImageButton imageButton_love;
    ImageButton imageButton_comment;
    ImageButton imageButton_share;

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*@Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder mainViewHolder = null;

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.feed_item, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.timestamp = (TextView) convertView
                    .findViewById(R.id.timestamp);
            viewHolder.statusMsg = (TextView) convertView
                    .findViewById(R.id.txtStatusMsg);
            viewHolder.url = (TextView) convertView.findViewById(R.id.txtUrl);
            viewHolder.feedImageView = (FeedImageView) convertView
                    .findViewById(R.id.feedImage1);
            viewHolder.imageButton_love = (ImageButton) convertView
                    .findViewById(R.id.imageButton_love);
            viewHolder.imageButton_comment = (ImageButton) convertView
                    .findViewById(R.id.imageButton_comment);
            viewHolder.imageButton_share = (ImageButton) convertView
                    .findViewById(R.id.imageButton_share);

            viewHolder.imageButton_love.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ImageButton imageButton_love = (ImageButton) v;

                    if (imageButton_love.getDrawable().getConstantState() == activity.getResources().getDrawable( R.drawable.love_button_not_clicked).getConstantState()) {
                        imageButton_love.setImageResource(R.drawable.love_button_clicked);
                    } else {
                        imageButton_love.setImageResource(R.drawable.love_button_not_clicked);
                    }
                }
            });

            item = feedItems.get(position);

            viewHolder.name.setText(item.getName());

            // Converting timestamp into x ago format
            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(item.getTimeStamp()),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            viewHolder.timestamp.setText(timeAgo);

            // Chcek for empty status message
            if (!TextUtils.isEmpty(item.getStatus())) {
                viewHolder.statusMsg.setText(item.getStatus());
                viewHolder.statusMsg.setVisibility(View.VISIBLE);
            } else {
                // status is empty, remove from view
                viewHolder.statusMsg.setVisibility(View.GONE);
            }

            // Checking for null feed url
            if (item.getUrl() != null) {
                viewHolder.url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                        + item.getUrl() + "</a> "));

                // Making url clickable
                viewHolder.url.setMovementMethod(LinkMovementMethod.getInstance());
                viewHolder.url.setVisibility(View.VISIBLE);
            } else {
                // url is null, remove from the view
                viewHolder.url.setVisibility(View.GONE);
            }

            // Feed image
            if (item.getImage() != null) {
                viewHolder.feedImageView.setImageUrl(item.getImage(), imageLoader);
                viewHolder.feedImageView.setVisibility(View.VISIBLE);
                viewHolder.feedImageView
                        .setResponseObserver(new FeedImageView.ResponseObserver() {
                            @Override
                            public void onError() {
                                //Toast.makeText(activity.getApplicationContext(), "feedImageView is irresponsible. " + item.getId() + " "  + item.getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                //Toast.makeText(activity.getApplicationContext(), "YEEEEE " + item.getId() + " "  + item.getName(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                viewHolder.feedImageView.setVisibility(View.GONE);
            }

            convertView.setTag(viewHolder);

        }/* else {
            mainViewHolder = (ViewHolder) convertView.getTag();
            //mainViewHolder.name.setText(getItem(position));
        }*---------------------/

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        return convertView;
    }

    public class ViewHolder {

        TextView name;
        TextView timestamp;
        TextView statusMsg;
        TextView url;
        FeedImageView feedImageView;
        ImageButton imageButton_love;
        ImageButton imageButton_comment;
        ImageButton imageButton_share;

    }*/

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);
        VideoView feedVideoView = (VideoView) convertView
                .findViewById(R.id.feedVideo);
        imageButton_love = (ImageButton) convertView
                .findViewById(R.id.imageButton_love);
        imageButton_comment = (ImageButton) convertView
                .findViewById(R.id.imageButton_comment);
        imageButton_share = (ImageButton) convertView
                .findViewById(R.id.imageButton_share);

        /*imageButton_love.setEnabled(false);
        imageButton_comment.setEnabled(false);
        imageButton_share.setEnabled(false);*/

        /*imageButton_love.setVisibility(View.GONE);
        imageButton_comment.setVisibility(View.GONE);
        imageButton_share.setVisibility(View.GONE);*/

        /*imageButton_love.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //ImageButton imageButton_love = (ImageButton) v;
                    LinearLayout loveButtonParent = (LinearLayout) v.getParent();
                    ImageButton imageButton_love = (ImageButton) loveButtonParent.findViewById(R.id.imageButton_love);

                    /*if (imageButton_love.getDrawable().getConstantState() == activity.getResources().getDrawable( R.drawable.love_button_not_clicked).getConstantState()) {
                        imageButton_love.setImageResource(R.drawable.love_button_clicked);

                        LinearLayout statusBox = (LinearLayout) v.getParent().getParent();
                        Toast.makeText(activity.getApplicationContext(), ((TextView) statusBox.findViewById(R.id.txtStatusMsg)).getText().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        imageButton_love.setImageResource(R.drawable.love_button_not_clicked);

                        LinearLayout statusBox = (LinearLayout) v.getParent().getParent();
                        Toast.makeText(activity.getApplicationContext(), ((TextView) statusBox.findViewById(R.id.txtStatusMsg)).getText().toString(), Toast.LENGTH_SHORT).show();
                    }*/

                    /*if (!item.isLovedByCurrentUser()) {
                        imageButton_love.setImageResource(R.drawable.love_button_clicked);

                        LinearLayout statusBoxParent = (LinearLayout) v.getParent().getParent();
                        Toast.makeText(activity.getApplicationContext(), "status : " + ((TextView) statusBoxParent.findViewById(R.id.txtStatusMsg)).getText().toString(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(activity.getApplicationContext(), "id : " + item.getId(), Toast.LENGTH_SHORT).show();
                    } else {
                        imageButton_love.setImageResource(R.drawable.love_button_not_clicked);

                        LinearLayout statusBoxParent = (LinearLayout) v.getParent().getParent();
                        Toast.makeText(activity.getApplicationContext(), "status : " + ((TextView) statusBoxParent.findViewById(R.id.txtStatusMsg)).getText().toString(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(activity.getApplicationContext(), "id : " + item.getId(), Toast.LENGTH_SHORT).show();
                    }*----------------------/

                    imageButton_love.setVisibility(View.VISIBLE);
                    loveButtonParent.findViewById(R.id.imageButton_love).setVisibility(View.GONE);
                }
            });*/

        /*imageButton_love.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ImageButton imageButton_love = (ImageButton) v;
                    /*LinearLayout loveButtonParent = (LinearLayout) v.getParent();
                    ImageButton imageButton_love = (ImageButton) loveButtonParent.findViewById(R.id.imageButton_love);

                    imageButton_love.setVisibility(View.VISIBLE);
                    loveButtonParent.findViewById(R.id.imageButton_love).setVisibility(View.GONE);*---------------------------------------/

                    int id = Integer.parseInt(v.getTag(R.id.loveButtonCurrentItemID).toString());

                    Toast.makeText(activity.getApplicationContext(), "Tag : " + v.getTag(R.id.loveButtonCurrentItemID).toString()
                            + " position : " + v.getTag(R.id.loveButtonCurrentItemPosition).toString(), Toast.LENGTH_SHORT).show();

                    FeedItem currentItem = feedItems.get(Integer.parseInt(v.getTag(R.id.loveButtonCurrentItemPosition).toString()));

                    /*if (!currentItem.isLovedByCurrentUser()) {
                        if (Integer.parseInt(imageButton_love.getTag(R.id.loveButtonCurrentItemID).toString()) == id) imageButton_love.setImageResource(R.drawable.love_button_clicked);
                        currentItem.setLovedByCurrentUser(true);

                        LinearLayout statusBoxParent = (LinearLayout) v.getParent().getParent();
                        Toast.makeText(activity.getApplicationContext(), "status : " + currentItem.getStatus(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(activity.getApplicationContext(), "id : " + item.getId(), Toast.LENGTH_SHORT).show();
                    } else {
                        if (Integer.parseInt(imageButton_love.getTag(R.id.loveButtonCurrentItemID).toString()) == id) imageButton_love.setImageResource(R.drawable.love_button_not_clicked);
                        currentItem.setLovedByCurrentUser(false);

                        LinearLayout statusBoxParent = (LinearLayout) v.getParent().getParent();
                        Toast.makeText(activity.getApplicationContext(), "status : " + currentItem.getStatus(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(activity.getApplicationContext(), "id : " + item.getId(), Toast.LENGTH_SHORT).show();
                    }*--------------------------------------------------------------------/
                }
        });*/

        imageButton_love.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageButton imageButton_love = (ImageButton) v;
                int id = Integer.parseInt(v.getTag(R.id.loveButtonCurrentItemID).toString());
                FeedItem currentItem = feedItems.get(Integer.parseInt(v.getTag(R.id.loveButtonCurrentItemPosition).toString()));
                FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

                if (imageButton_love.getDrawable().getConstantState() == activity.getResources().getDrawable( R.drawable.love_button_not_clicked).getConstantState()) {
                    DatabaseReference postCountRef = FirebaseDatabase.getInstance().getReference();

                    postCountRef
                            .child("loves")
                            .child("post_" + id)
                            .child(QuestionStreamActivity._username)
                            .setValue(true);

                    imageButton_love.setImageResource(R.drawable.love_button_clicked);
                } else {
                    //currentItem.setLovedByCurrentUser(false);

                    DatabaseReference postCountRef = FirebaseDatabase.getInstance().getReference();

                    postCountRef
                            .child("loves")
                            .child("post_" + id)
                            .child(QuestionStreamActivity._username)
                            .setValue(null);

                    /*postCountRef
                            .child("loves")
                            .child("post_" + id)
                            .child(currentItem.getName())
                            .setValue(null);*/

                    imageButton_love.setImageResource(R.drawable.love_button_not_clicked);
                }
            }
        });

        item = feedItems.get(position);

        imageButton_love.setTag(R.id.loveButtonCurrentItemID, item.getId());
        imageButton_love.setTag(R.id.loveButtonCurrentItemPosition, position);
        //imageButton_love.setTag(R.id.loveButtonCurrentUsername, position);
        imageButton_comment.setTag(R.id.commentButtonCurrentItemID, item.getId());
        imageButton_comment.setTag(R.id.commentButtonCurrentItemPosition, position);
        imageButton_comment.setTag(R.id.commentButtonCurrentItemStatus, item.getStatus());
        imageButton_share.setTag(R.id.shareButtonCurrentItemID, item.getId());
        imageButton_share.setTag(R.id.shareButtonCurrentItemPosition, position);

        name.setText(item.getName());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // Feed image
        if (item.getImage() != null) {
            feedImageView.setImageUrl(item.getImage(), imageLoader);
            //feedImageView.setImageURI(Uri.parse(item.getImge()));

            //URL _url = new URL(item.getImge());
            //feedImageView.setImageURI(_url.toURI());
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                            //Toast.makeText(activity.getApplicationContext(), "feedImageView is irresponsible. " + item.getId() + " "  + item.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess() {
                            //Toast.makeText(activity.getApplicationContext(), "YEEEEE " + item.getId() + " "  + item.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });

            /*ImageView testImageView = (ImageView) convertView.findViewById(R.id.imageV_1);
            //if (item.hasImg) {
                Toast.makeText(activity.getApplicationContext(), "REACHED HERE!!! " + item.getId() + " "  + item.getName(), Toast.LENGTH_SHORT).show();
                testImageView.setImageURI(item.testImgUri);
            //}*/
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        /*DatabaseReference mDatabaseRef;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        mDatabaseRef.child("loves").child("post_" + item.getId()).child(item.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    item.setLovedByCurrentUser(true);
                } else {
                    item.setLovedByCurrentUser(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Toast.makeText(activity.getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });*/

        return convertView;
    }

}
