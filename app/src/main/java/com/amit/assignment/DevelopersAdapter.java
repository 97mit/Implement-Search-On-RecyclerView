package com.amit.assignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class  DevelopersAdapter extends RecyclerView.Adapter<DevelopersAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "MyActivity";

    public static final String KEY_NAME = "name";
   // public static final String KEY_IMAGE = "image";
    public static final String KEY_URL = "url";

    // we define a list from the DevelopersList java class

    private List<DevelopersList> developersLists;
    private List<DevelopersList> filter_list;

    private DevelopersFilter developersFilter;
    private Context context;

    public DevelopersAdapter(List<DevelopersList> developersLists, Context context) {

        // generate constructors to initialise the List and Context objects
        this.developersLists = developersLists;
        this.filter_list = developersLists;
        this.context = context;


    }
    @Override
    public Filter getFilter() {
        if(developersFilter == null)
            developersFilter = new DevelopersFilter(this, developersLists);
        return developersFilter;
    }
    private static class DevelopersFilter extends Filter {

        private final DevelopersAdapter adapter;

        private final List<DevelopersList> originalList;

        private final List<DevelopersList> filteredList;

        private DevelopersFilter(DevelopersAdapter adapter, List<DevelopersList> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final DevelopersList user : originalList) {
                    if (user.getTitle().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filter_list.clear();
            adapter.filter_list.addAll((ArrayList<DevelopersList>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String TAG = "MyActivity";

        // this method will bind the data to the ViewHolder from whence it'll be shown to other Views

        final DevelopersList developersList = developersLists.get(position);

        holder.login.setText(developersList.getTitle());
        holder.html_url.setText((CharSequence) developersList.getHtml_url());
        Glide.with(context)
                //.load(R.drawable.linked)
                .load(developersList.getAvatar_url())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.avatar_url);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"ItemClick");
                DevelopersList developersList1 = developersLists.get(position);
                Intent intent = new Intent(v.getContext(), ProfileView.class);
                intent.putExtra("profile_url", developersList1.getHtml_url());
                context.startActivity(intent);
                Log.d(TAG,"ProfileView");
            }
        });

    }

    @Override

    //return the size of the listItems (developersList)

    public int getItemCount() {
        return developersLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        // define the View objects

        public TextView login;
        public ImageView avatar_url;
        public TextView html_url;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);


            login = (TextView) itemView.findViewById(R.id.username);

            avatar_url = (ImageView) itemView.findViewById(R.id.imageView);
            html_url = (TextView) itemView.findViewById(R.id.htmUrl);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }

    }

}
